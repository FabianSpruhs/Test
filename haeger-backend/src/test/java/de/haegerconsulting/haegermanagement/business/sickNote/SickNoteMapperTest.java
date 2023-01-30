package de.haegerconsulting.haegermanagement.business.sickNote;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SickNoteMapperTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "Test", "Test", null, null, null, null, null, 0.0F);
    SickNoteJPA sickNoteJPA = new SickNoteJPA(0, employeeJPA, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2));
    SickNoteDTO sickNoteDTO = new SickNoteDTO(0, 1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2));

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    SickNoteMapper sickNoteMapper = Mappers.getMapper(SickNoteMapper.class);

    @Test
    void sickNoteDTOToSickNoteJPA() {
        when(employeeService.showEmployee(1)).thenReturn(employeeJPA);
        assertEquals(sickNoteJPA.toString(), sickNoteMapper.sickNoteDTOToSickNoteJPA(sickNoteDTO).toString());
    }

    @Test
    void sickNoteJPAToSickNoteDTO() {
        assertEquals(sickNoteDTO, sickNoteMapper.sickNoteJPAToSickNoteDTO(sickNoteJPA));
    }
}