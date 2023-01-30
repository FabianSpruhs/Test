package de.haegerconsulting.haegermanagement.business.vacationRequest;

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
class VacationRequestMapperTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "FirstName", "LastName" ,null, null, null, null, null, 2.0F);
    VacationRequestJPA vacationRequestJPA = new VacationRequestJPA(0, employeeJPA, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), null, 2.0F);
    VacationRequestDTO vacationRequestDTO = new VacationRequestDTO(0, 1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), 2, null);

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    VacationRequestMapper vacationRequestMapper = Mappers.getMapper(VacationRequestMapper.class);

    @Test
    void vacationRequestDTOToVacationRequestJPA() throws EmployeeNotFoundException {
        when(employeeService.showEmployee(1)).thenReturn(employeeJPA);
        assertEquals(vacationRequestJPA.toString(), vacationRequestMapper.vacationRequestDTOToVacationRequestJPA(vacationRequestDTO).toString());
    }

    @Test
    void vacationRequestJPAToVacationRequestDTO() {
        assertEquals(vacationRequestDTO, vacationRequestMapper.vacationRequestJPAToVacationRequestDTO(vacationRequestJPA));
    }
}