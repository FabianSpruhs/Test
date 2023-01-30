package de.haegerconsulting.haegermanagement.business.project;


import de.haegerconsulting.haegermanagement.business.client.ClientService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ClientService.class })
public interface ProjectMapper {


    @Mapping(source = "clientID", target = "client")
    @Mapping(target = "id", ignore = true)
    ProjectJPA projectDTOToProjectJPA(ProjectDTO projectDTO);

    @Mapping(source = "client.id", target = "clientID")
    ProjectDTO projectJPAToProjectDTO(ProjectJPA projectJPA);

}
