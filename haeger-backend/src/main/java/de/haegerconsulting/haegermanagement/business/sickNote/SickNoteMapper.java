package de.haegerconsulting.haegermanagement.business.sickNote;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EmployeeService.class })
public interface SickNoteMapper {

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(target = "id", ignore = true)
    SickNoteJPA sickNoteDTOToSickNoteJPA(SickNoteDTO sickNoteDTO);

    @Mapping(source = "employee.id", target = "employeeId")
    SickNoteDTO sickNoteJPAToSickNoteDTO(SickNoteJPA sickNoteJPA);
}
