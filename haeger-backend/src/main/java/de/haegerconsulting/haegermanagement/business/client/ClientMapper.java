package de.haegerconsulting.haegermanagement.business.client;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeService.class })
public interface ClientMapper {

    @Mapping(source = "contactEmployee.id", target = "contactEmployeeID")
    ClientDTO clientJPAToClientDTO(ClientJPA clientJPA);

    @Mapping(source = "contactEmployeeID", target = "contactEmployee")
    @Mapping(target = "id", ignore = true)
    ClientJPA clientDTOToClientJPA(ClientDTO clientDTO);
}
