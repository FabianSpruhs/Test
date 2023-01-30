package de.haegerconsulting.haegermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import de.haegerconsulting.haegermanagement.business.project.ProjectDTO;
import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import de.haegerconsulting.haegermanagement.business.project.ProjectService;
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


import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})
class ProjectControllerTest {

    ClientJPA clientJPA = new ClientJPA(1, null, null, null);
    ProjectJPA projectJPA1 = new ProjectJPA(1, clientJPA, "Test Project 1");
    ProjectDTO projectDTO = new ProjectDTO(0, 1, "Test Project 1");

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    ProjectService projectService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllProjects() throws Exception {
        List<ProjectJPA> expected = new LinkedList<>();
        expected.add(projectJPA1);
        Pageable paging = PageRequest.of(0, 1);
        Page<ProjectJPA> page = new PageImpl<>(expected);

        when(projectService.showAllProjects(paging)).thenReturn(page);

        mockMvc.perform(get("/api/project/accounting/all").param("page", "0").param("size", "1"))
                .andExpect(status().isOk());
        verify(projectService, times(1)).showAllProjects(paging);
    }

    @Test
    @WithMockUser(roles = "STANDARD")
    void showProjectsByEmployee() throws Exception{
        List<ProjectJPA> expected = new LinkedList<>();
        expected.add(projectJPA1);

        when(projectService.showProjectsByEmployee(1)).thenReturn(expected);

        mockMvc.perform(get("/api/project/employee/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Matchers.is(projectJPA1.getName())));
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void editProject() throws Exception {
        mockMvc.perform(put("/api/project/accounting/edit/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk());
        verify(projectService, times(1)).editProject(1, projectDTO);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void assignProjectToClient() throws Exception {

        mockMvc.perform(post("/api/project/accounting/assign")
                .param("project_id", "1")
                .param("client_id", "1"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).assignProjectToClient(1, 1);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createProject() throws Exception {
        mockMvc.perform(post("/api/project/accounting/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isCreated());
        verify(projectService, times(1)).createProject(projectDTO);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createProjectWithoutName() throws Exception {
        ProjectDTO projectDTOWithoutName = new ProjectDTO(projectDTO.id(), projectDTO.clientID(), null);
        mockMvc.perform(post("/api/project/accounting/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDTOWithoutName)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createProjectWithNegativeClientId() throws Exception {
        ProjectDTO projectDTOWithoutName = new ProjectDTO(projectDTO.id(), -1, projectDTO.name());
        mockMvc.perform(post("/api/project/accounting/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDTOWithoutName)))
                .andExpect(status().isBadRequest());
    }
}