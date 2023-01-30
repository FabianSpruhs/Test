package de.haegerconsulting.haegermanagement.business.workingHour;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
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
class WorkingHourMapperTest {

    EmployeeJPA employeeJPA1 = new EmployeeJPA(1, "FirstName 1", "LastName 1" ,null, null, null, null, null, 30.0F);
    WorkingHourJPA workingHourJPA1 = new WorkingHourJPA(0,employeeJPA1, LocalDate.of(2022, 11, 1), 1.0F, null);
    WorkingHourDTO workingHourDTO = new WorkingHourDTO(0, 1, LocalDate.of(2022, 11, 1), 1.0F, null);

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    WorkingHourMapper workingHourMapper = Mappers.getMapper(WorkingHourMapper.class);

    @Test
    void workingHourDTOToWorkingHourJPA() throws EmployeeNotFoundException {
        when(employeeService.showEmployee(1)).thenReturn(employeeJPA1);
        assertEquals(workingHourJPA1.toString(), workingHourMapper.workingHourDTOToWorkingHourJPA(workingHourDTO).toString());
    }

    @Test
    void workingHourJPAToWorkingHourDTO() {
        assertEquals(workingHourDTO, workingHourMapper.workingHourJPAToWorkingHourDTO(workingHourJPA1));
    }
}