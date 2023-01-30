package de.haegerconsulting.haegermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteDTO;
import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteJPA;
import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteService;
import de.haegerconsulting.haegermanagement.configurations.security.*;
import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(SickNoteController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})
class SickNoteControllerTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "Test", "Test", null, null, null, null, null, 0.0F);
    SickNoteDTO sickNoteDTO = new SickNoteDTO(0, 1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2));
    SickNoteJPA sickNoteJPA = new SickNoteJPA(0, employeeJPA, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2));

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    SickNoteService sickNoteService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "STANDARD")
    void submitSickNote() throws Exception {
        mockMvc.perform(post("/api/sick_note/submit").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sickNoteDTO))).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void submitSickNoteWithoutBegin() throws Exception {
        SickNoteDTO sickNoteDTOWithoutBegin = new SickNoteDTO(sickNoteDTO.id(), sickNoteDTO.employeeId(), null, sickNoteDTO.endDate());
        mockMvc.perform(post("/api/sick_note/submit").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sickNoteDTOWithoutBegin))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void submitSickNoteWithoutEnd() throws Exception {
        SickNoteDTO sickNoteDTOWithoutEnd = new SickNoteDTO(sickNoteDTO.id(), sickNoteDTO.employeeId(), sickNoteDTO.beginDate(), null);
        mockMvc.perform(post("/api/sick_note/submit").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sickNoteDTOWithoutEnd))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void submitSickNoteWithoutEmployee() throws Exception {
        SickNoteDTO sickNoteDTOWithoutEmployee = new SickNoteDTO(sickNoteDTO.id(), -1, sickNoteDTO.beginDate(), sickNoteDTO.endDate());
        mockMvc.perform(post("/api/sick_note/submit").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sickNoteDTOWithoutEmployee))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllSickNotes() throws Exception {
        List<SickNoteJPA> expected = new LinkedList<>();
        expected.add(sickNoteJPA);
        Pageable paging = PageRequest.of(0, 1);
        Page<SickNoteJPA> page = new PageImpl<>(expected);

        Mockito.when(sickNoteService.showAllSickNotes(paging)).thenReturn(page);

        mockMvc.perform(get("/api/sick_note/accounting/all").param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(sickNoteService, times(1)).showAllSickNotes(paging);

    }
}