package de.haegerconsulting.haegermanagement.business.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    EmployeeJPA employeeDTOToEmployeeJPA(EmployeeDTO employeeDTO);

    EmployeeDTO employeeJPAToEmployeeDTO(EmployeeJPA employeeJPA);

}
