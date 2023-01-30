package de.haegerconsulting.haegermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestDTO;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestJPA;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestService;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestStatus;
import de.haegerconsulting.haegermanagement.configurations.security.JwtUtils;
import de.haegerconsulting.haegermanagement.configurations.security.MyBasicAuthenticationEntryPoint;
import de.haegerconsulting.haegermanagement.configurations.security.UserDetailsServiceImpl;
import de.haegerconsulting.haegermanagement.configurations.security.WebSecurityConfig;
import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VacationRequestController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})
class VacationRequestControllerTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "FirstName", "LastName" ,null, null, null, null, null, 0.0F);
    VacationRequestJPA vacationRequestJPA = new VacationRequestJPA(1, employeeJPA, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), VacationRequestStatus.OPEN, 1.0F);
    VacationRequestDTO vacationRequestDTO = new VacationRequestDTO(0, 1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), 2, VacationRequestStatus.OPEN);

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    VacationRequestService vacationRequestService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllVacationRequests() throws Exception {
        List<VacationRequestJPA> expected = new LinkedList<>();
        expected.add(vacationRequestJPA);
        Pageable paging = PageRequest.of(0, 1);
        Page<VacationRequestJPA> page = new PageImpl<>(expected);

        Mockito.when(vacationRequestService.showAllVacationRequests(paging)).thenReturn(page);

        mockMvc.perform(get("/api/vacation_request/accounting/all").param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(vacationRequestService, times(1)).showAllVacationRequests(paging);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void requestVacation() throws Exception {
        mockMvc.perform(post("/api/vacation_request/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequestDTO)))
                .andExpect(status().isCreated());
        verify(vacationRequestService, times(1)).requestVacation(vacationRequestDTO);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void requestVacationWithoutBegin() throws Exception {
        VacationRequestDTO vacationRequestDTOWithoutBegin = new VacationRequestDTO(vacationRequestDTO.id(), vacationRequestDTO.employeeID(), null, vacationRequestDTO.endDate(), vacationRequestDTO.vacationDays(), vacationRequestDTO.status());
        mockMvc.perform(post("/api/vacation_request/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacationRequestDTOWithoutBegin)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void requestVacationWithoutEnd() throws Exception {
        VacationRequestDTO vacationRequestDTOWithoutEnd = new VacationRequestDTO(vacationRequestDTO.id(), vacationRequestDTO.employeeID(), vacationRequestDTO.beginDate(), null, vacationRequestDTO.vacationDays(), vacationRequestDTO.status());
        mockMvc.perform(post("/api/vacation_request/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacationRequestDTOWithoutEnd)))
                        .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void requestVacationWithNegativeEmployeeID() throws Exception {
        VacationRequestDTO vacationRequestDTOWithNegativeEmployeeID = new VacationRequestDTO(vacationRequestDTO.id(), -1, vacationRequestDTO.beginDate(), vacationRequestDTO.endDate(), vacationRequestDTO.vacationDays(), vacationRequestDTO.status());
        mockMvc.perform(post("/api/vacation_request/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vacationRequestDTOWithNegativeEmployeeID)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = "STANDARD")
    void showVacationRequestByEmployee() throws Exception {
        List<VacationRequestJPA> expected = new LinkedList<>();
        expected.add(vacationRequestJPA);
        Pageable paging = PageRequest.of(0, 1);
        Page<VacationRequestJPA> page = new PageImpl<>(expected);

        when(vacationRequestService.showVacationRequestByEmployee(1, paging)).thenReturn(page);

        mockMvc.perform(get("/api/vacation_request/employee/{id}", 1).param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(vacationRequestService, times(1)).showVacationRequestByEmployee(1, paging);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void editVacationRequest() throws Exception {
        mockMvc.perform(put("/api/vacation_request/accounting/edit")
                .param("id", "1")
                .param("status", "OPEN"))
                .andExpect(status().isOk());
        verify(vacationRequestService, times(1)).editVacationRequest(1, VacationRequestStatus.OPEN);
    }
}