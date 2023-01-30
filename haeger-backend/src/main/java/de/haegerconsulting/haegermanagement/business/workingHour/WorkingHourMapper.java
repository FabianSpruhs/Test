package de.haegerconsulting.haegermanagement.business.workingHour;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeService.class })
public interface WorkingHourMapper {

    @Mapping(source = "employeeID", target = "employee")
    @Mapping(target = "id", ignore = true)
    WorkingHourJPA workingHourDTOToWorkingHourJPA(WorkingHourDTO workingHourDTO);

    @Mapping(source = "employee.id", target = "employeeID")
    WorkingHourDTO workingHourJPAToWorkingHourDTO(WorkingHourJPA workingHourJPA);
}
