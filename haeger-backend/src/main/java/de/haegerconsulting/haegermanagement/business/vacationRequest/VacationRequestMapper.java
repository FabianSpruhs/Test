package de.haegerconsulting.haegermanagement.business.vacationRequest;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeService.class })
public interface VacationRequestMapper {

    @Mapping(source = "employeeID", target = "employee")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "vacationDays", target = "vacationDays")
    VacationRequestJPA vacationRequestDTOToVacationRequestJPA(VacationRequestDTO vacationRequestDTO);

    @Mapping(source = "employee.id", target = "employeeID")
    VacationRequestDTO vacationRequestJPAToVacationRequestDTO(VacationRequestJPA vacationRequestJPA);
}
