package de.haegerconsulting.haegermanagement.business.project;

import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import de.haegerconsulting.haegermanagement.business.client.ClientNotFoundException;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeToProject;
import de.haegerconsulting.haegermanagement.persistence.ClientPersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeeToProjectPersistence;
import de.haegerconsulting.haegermanagement.persistence.ProjectPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    EmployeeJPA employeeJPA1 = new EmployeeJPA(1, "Test FirstName 1", "Test LastName 2", null, null, null, null, null, 0.0F);
    ClientJPA clientJPA = new ClientJPA(1, null, null, null);
    ProjectDTO projectDTO = new ProjectDTO(0, 1, "Test Project 1");
    ProjectJPA projectJPA1 = new ProjectJPA(1, clientJPA, "Test Project 1");
    EmployeeToProject employeeToProject1 = new EmployeeToProject(1, employeeJPA1, projectJPA1, 40);

    @Mock
    ProjectMapper projectMapper;

    @Mock
    ProjectPersistence projectPersistence;

    @Mock
    ClientPersistence clientPersistence;

    @Mock
    EmployeeToProjectPersistence employeeToProjectPersistence;

    @InjectMocks
    ProjectService projectService;

    @Test
    void showAllProjects() {
        List<ProjectJPA> expected = new LinkedList<>();
        expected.add(projectJPA1);
        Pageable paging = PageRequest.of(0, 1);
        Page<ProjectJPA> page = new PageImpl<>(expected);

        when(projectPersistence.findAll(paging)).thenReturn(page);

        assertEquals(expected, projectService.showAllProjects(paging).getContent());
    }

    @Test
    void showProjectsByEmployee() {
        List<EmployeeToProject> employeeToProjects = new LinkedList<>();
        employeeToProjects.add(employeeToProject1);

        List<ProjectJPA> expected = new LinkedList<>();
        expected.add(projectJPA1);

        when(employeeToProjectPersistence.findByEmployeeId(1)).thenReturn(employeeToProjects);

        assertEquals(expected, projectService.showProjectsByEmployee(1));
    }

    @Test
    void createProject() {
        when(projectMapper.projectDTOToProjectJPA(projectDTO)).thenReturn(projectJPA1);
        projectService.createProject(projectDTO);
        verify(projectPersistence, times(1)).save(projectJPA1);
    }

    @Test
    void assignProjectToClient() throws ProjectNotFoundException, ClientNotFoundException {
        ProjectJPA projectJPA = new ProjectJPA(1, null, projectJPA1.getName());
        when(projectPersistence.findById(1)).thenReturn(Optional.of(projectJPA));
        when(clientPersistence.findById(1)).thenReturn(Optional.of(clientJPA));
        projectService.assignProjectToClient(1, 1);
        assertEquals(projectJPA.toString(), projectJPA1.toString());
    }

    @Test
    void assignProjectToClientWithoutProject() {
        when(projectPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () ->
                projectService.assignProjectToClient(1, 1), "ProjectNotFoundException was expected");
    }

    @Test
    void assignProjectToClientWithoutClient() {
        ProjectJPA projectJPA = new ProjectJPA(1, null, projectJPA1.getName());
        when(projectPersistence.findById(1)).thenReturn(Optional.of(projectJPA));
        when(clientPersistence.findById(1)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () ->
                projectService.assignProjectToClient(1, 1), "ClientNotFoundException was expected");
    }

    @Test
    void editProject() throws ProjectNotFoundException {
        ProjectJPA projectJPA = new ProjectJPA(1, projectJPA1.getClient(), null);
        when(projectPersistence.findById(1)).thenReturn(Optional.of(projectJPA));
        projectService.editProject(1, projectDTO);
        assertEquals(projectJPA1.toString(), projectJPA.toString());

    }

    @Test
    void editProjectWithoutProject() {
        when(projectPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () ->
                projectService.editProject(1, projectDTO), "ProjectNotFoundException was expected");
    }
}