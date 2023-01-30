package de.haegerconsulting.haegermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.workingHour.*;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WorkingHourController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})
class WorkingHourControllerTest {

    EmployeeJPA employeeJPA1 = new EmployeeJPA(1, "FirstName 1", "LastName 1" ,null, null, null, null, null, 30.0F);
    EmployeeJPA employeeJPA2 = new EmployeeJPA(2, "FirstName 2", "LastName 2" ,null, null, null, null, null, 20.0F);
    WorkingHourJPA workingHourJPA1 = new WorkingHourJPA(1,employeeJPA1, LocalDate.of(2022, 11, 1), 1.0F, WorkingHourStatus.BOOKED);
    WorkingHourJPA workingHourJPA2 = new WorkingHourJPA(2, employeeJPA2, LocalDate.of(2022, 10, 1), 2.0F, WorkingHourStatus.FINALIZED);
    WorkingHourDTO workingHourDTO = new WorkingHourDTO(0, 1, LocalDate.of(2022, 11, 1), 5.0F, null);
    WorkingHourAccountingDTO workingHourAccountingDTO = new WorkingHourAccountingDTO(employeeJPA1, 10.0F);

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    WorkingHourService workingHourService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "STANDARD")
    void bookWorkingHour() throws Exception {
        mockMvc.perform(post("/api/working_hour/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workingHourDTO)))
                .andExpect(status().isCreated());
        verify(workingHourService, times(1)).bookWorkingHour(workingHourDTO);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void bookWorkingHourWithoutDay() throws Exception {
        WorkingHourDTO workingHourDTOWithoutDay = new WorkingHourDTO(workingHourDTO.id(), workingHourDTO.employeeID(), null, workingHourDTO.workingHours(), workingHourDTO.workingHourStatus());
        mockMvc.perform(post("/api/working_hour/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workingHourDTOWithoutDay)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void bookWorkingHourWithNegativeEmployeeID() throws Exception {
        WorkingHourDTO workingHourDTOWithNegativeEmployeeID = new WorkingHourDTO(workingHourDTO.id(), -1, workingHourDTO.workingDay(), workingHourDTO.workingHours(), workingHourDTO.workingHourStatus());
        mockMvc.perform(post("/api/working_hour/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workingHourDTOWithNegativeEmployeeID)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void bookWorkingHourWithNegativeWorkingHours() throws Exception {
        WorkingHourDTO workingHourDTOWithNegativeWorkingHours = new WorkingHourDTO(workingHourDTO.id(), workingHourDTO.employeeID(), workingHourDTO.workingDay(), -1, workingHourDTO.workingHourStatus());
        mockMvc.perform(post("/api/working_hour/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workingHourDTOWithNegativeWorkingHours)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void showBookedWorkingHourByEmployee() throws Exception {
        List<WorkingHourJPA> expected = new LinkedList<>();
        expected.add(workingHourJPA1);
        Pageable paging = PageRequest.of(0, 1);
        Page<WorkingHourJPA> page = new PageImpl<>(expected);

        when(workingHourService.showBookedWorkingHourByEmployee(1, paging)).thenReturn(page);

        mockMvc.perform(get("/api/working_hour/employee/{id}", 1).param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(workingHourService, times(1)).showBookedWorkingHourByEmployee(1, paging);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void editBookWorkingHour() throws Exception {
        mockMvc.perform(put("/api/working_hour/edit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workingHourDTO)))
                .andExpect(status().isOk());

        verify(workingHourService, times(1)).editBookWorkingHour(1, workingHourDTO);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void finalizeWorkingHourByEmployee() throws Exception {
        mockMvc.perform(put("/api/working_hour/finalize/{id}", 1))
                .andExpect(status().isOk());
        verify(workingHourService, times(1)).finalizeWorkingHourByEmployee(1);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void showWorkingHourAccountingByEmployee() throws Exception {
        when(workingHourService.showWorkingHourAccountingByEmployee(1)).thenReturn(workingHourAccountingDTO);
        mockMvc.perform(get("/api/working_hour/account/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeJPA.id", Matchers.is(workingHourAccountingDTO.employeeJPA().getId())));
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllFinalizedWorkingHours() throws Exception {
        List<WorkingHourJPA> expected = new LinkedList<>();
        expected.add(workingHourJPA2);
        Pageable paging = PageRequest.of(0, 1);
        Page<WorkingHourJPA> page = new PageImpl<>(expected);

        when(workingHourService.showAllFinalizedWorkingHours(paging)).thenReturn(page);

        mockMvc.perform(get("/api/working_hour/accounting/finalize/all").param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(workingHourService, times(1)).showAllFinalizedWorkingHours(paging);

    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllWorkingHourAccounting() throws Exception {
        List<WorkingHourAccountingDTO> expected = new LinkedList<>();
        expected.add(workingHourAccountingDTO);

        when(workingHourService.showAllWorkingHourAccounting()).thenReturn(expected);

        mockMvc.perform(get("/api/working_hour/accounting/account/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].employeeJPA.id", Matchers.is(workingHourAccountingDTO.employeeJPA().getId())));
    }
}