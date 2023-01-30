package de.haegerconsulting.haegermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.address.Address;
import de.haegerconsulting.haegermanagement.business.employee.*;
import de.haegerconsulting.haegermanagement.configurations.security.JwtUtils;
import de.haegerconsulting.haegermanagement.configurations.security.MyBasicAuthenticationEntryPoint;
import de.haegerconsulting.haegermanagement.configurations.security.UserDetailsServiceImpl;
import de.haegerconsulting.haegermanagement.configurations.security.WebSecurityConfig;
import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})
class EmployeeControllerTest {

    Address address = new Address("Test Address", "5a", 10000, "Bonn");
    EmployeeDTO employeeDTO = new EmployeeDTO(1, "FirstName", "LastName", address, "test@testen.com", "+49 221 111222", 20);
    EmployeeJPA employeeJPA = new EmployeeJPA(1, "FirstName 1", "LastName 1", address, "test@testen.com", "+49 221 111222", EmployeeStatus.ACTIVE, null, 30);

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployee() throws Exception {
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeJPA))).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithoutFirstname() throws Exception {
        EmployeeDTO employeeDTOWithoutFirstname = new EmployeeDTO(employeeDTO.id(), null, employeeDTO.lastName(), employeeDTO.address(), employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithoutFirstname))).andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithoutLastname() throws Exception {
        EmployeeDTO employeeDTOWithoutLastname = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), null, employeeDTO.address(), employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithoutLastname))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithWrongMail() throws Exception {
        EmployeeDTO employeeDTOWithWrongMail = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), employeeDTO.lastName(), employeeDTO.address(), "Mail Address", employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithWrongMail))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithoutStreet() throws Exception {
        Address wrongAddress = new Address(null, address.getHouseNumber(), address.getPostcode(), address.getCity());
        EmployeeDTO employeeDTOWithAddress = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), employeeDTO.lastName(), wrongAddress, employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithoutHouseNumber() throws Exception {
        Address wrongAddress = new Address(address.getStreet(), null, address.getPostcode(), address.getCity());
        EmployeeDTO employeeDTOWithAddress = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), employeeDTO.lastName(), wrongAddress, employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithToLowPostcode() throws Exception {
        Address wrongAddress = new Address(address.getStreet(), address.getHouseNumber(), 10, address.getCity());
        EmployeeDTO employeeDTOWithAddress = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), employeeDTO.lastName(), wrongAddress, employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithToHighPostcode() throws Exception {
        Address wrongAddress = new Address(address.getStreet(), address.getHouseNumber(), 100000, address.getCity());
        EmployeeDTO employeeDTOWithAddress = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), employeeDTO.lastName(), wrongAddress, employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void hireEmployeeWithoutCity() throws Exception {
        Address wrongAddress = new Address(address.getStreet(), address.getHouseNumber(), address.getPostcode(), null);
        EmployeeDTO employeeDTOWithAddress = new EmployeeDTO(employeeDTO.id(), employeeDTO.firstName(), employeeDTO.lastName(), wrongAddress, employeeDTO.mail(), employeeDTO.telephoneNumber(), employeeDTO.scheduledVacationDays());
        mockMvc.perform(post("/api/employee/admin/hire").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTOWithAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void fireEmployee() throws Exception{
        mockMvc.perform(delete("/api/employee/admin/fire/{id}", 1)).andExpect(status().isOk());
        verify(employeeService, times(1)).fire(1);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllEmployees() throws Exception {
        Pageable paging = PageRequest.of(0, 1);
        List<EmployeeJPA> expected = new LinkedList<>();
        expected.add(employeeJPA);
        Page<EmployeeJPA> page = new PageImpl<>(expected);

        when(employeeService.showAllEmployees(paging)).thenReturn(page);

        mockMvc.perform(get("/api/employee/accounting/all").param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(employeeService, times(1)).showAllEmployees(paging);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void showEmployee() throws Exception{
        when(employeeService.showEmployee(1)).thenReturn(employeeJPA);
        mockMvc.perform(get("/api/employee/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is(employeeJPA.getFirstName())));
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void editEmployee() throws Exception {
        mockMvc.perform(put("/api/employee/accounting/edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());
        verify(employeeService, times(1)).editEmployeeData(1, employeeDTO);
    }

    //TODO verify Number
    @Test
    @WithMockUser(roles = "STANDARD")
    void showRemainingVacationDaysByEmployee() throws Exception{
        when(employeeService.showRemainingVacationDaysByEmployee(1)).thenReturn(20.0F);
        mockMvc.perform(get("/api/employee/remaining_vacation/{id}", 1))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("" , Matchers.is(20.0F)));
        verify(employeeService, times(1)).showRemainingVacationDaysByEmployee(1);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showEmployeesByStatus() throws Exception {
        Pageable paging = PageRequest.of(0, 1);
        List<EmployeeJPA> expected = new LinkedList<>();
        expected.add(employeeJPA);
        Page<EmployeeJPA> page = new PageImpl<>(expected);

        when(employeeService.showEmployeesByStatus(EmployeeStatus.ACTIVE, paging)).thenReturn(page);

        mockMvc.perform(get("/api/employee/accounting/status/{status}", EmployeeStatus.ACTIVE).param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(employeeService, times(1)).showEmployeesByStatus(EmployeeStatus.ACTIVE, paging);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void assignEmployeeToProject() throws Exception {
        mockMvc.perform(post("/api/employee/accounting/assign_toProject")
                .param("employee_id", "1")
                .param("project_id", "1")
                .param("working_hours", "40"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).assignEmployeeToProject(1, 1, 40);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editEmployeeStatus() throws Exception {
        mockMvc.perform(put("/api/employee/admin/edit/status")
                        .param("employee_id", "1")
                        .param("status", "ACTIVE"))
                        .andExpect(status().isOk());
        verify(employeeService, times(1)).editEmployeeStatus(1, EmployeeStatus.ACTIVE);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editEmployeeRole() throws Exception {
        mockMvc.perform(put("/api/employee/admin/edit/role")
                        .param("employee_id", "1")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk());
        verify(employeeService, times(1)).editEmployeeRole(1, EmployeeRole.ADMIN);
    }
}