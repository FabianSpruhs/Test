package de.haegerconsulting.haegermanagement.business.project;

import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import de.haegerconsulting.haegermanagement.business.client.ClientNotFoundException;
import de.haegerconsulting.haegermanagement.business.client.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectMapperTest {

    ClientJPA clientJPA = new ClientJPA(1, null, null, null);
    ProjectDTO projectDTO = new ProjectDTO(0, 1, "Test Project 1");
    ProjectJPA projectJPA1 = new ProjectJPA(0, clientJPA, "Test Project 1");

    @Mock
    ClientService clientService;

    @InjectMocks
    ProjectMapper projectMapper = Mappers.getMapper(ProjectMapper.class);


    @Test
    void projectDTOToProjectJPA() throws ClientNotFoundException {
        when(clientService.showClientByID(1)).thenReturn(clientJPA);
        assertEquals(projectJPA1.toString(), projectMapper.projectDTOToProjectJPA(projectDTO).toString());
    }

    @Test
    void projectJPAToProjectDTO() {
        assertEquals(projectDTO, projectMapper.projectJPAToProjectDTO(projectJPA1));
    }
}