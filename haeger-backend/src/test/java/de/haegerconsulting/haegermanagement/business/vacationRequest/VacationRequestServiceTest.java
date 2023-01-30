package de.haegerconsulting.haegermanagement.business.vacationRequest;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.persistence.VacationRequestPersistence;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WorkDayCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationRequestServiceTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "FirstName", "LastName" ,null, null, null, null, null, 0.0F);
    VacationRequestJPA vacationRequestJPA = new VacationRequestJPA(1, employeeJPA, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), VacationRequestStatus.OPEN, 1.0F);
    VacationRequestDTO vacationRequestDTO = new VacationRequestDTO(0, 1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), 2, VacationRequestStatus.OPEN);


    @Mock
    VacationRequestPersistence vacationRequestPersistence;

    @Mock
    VacationRequestMapper mapper;

    @Mock
    WorkDayCalculator workDayCalculator;

    @InjectMocks
    VacationRequestService vacationRequestService;


    @Test
    void showAllVacationRequests() {
        List<VacationRequestJPA> excepted = new LinkedList<>();
        excepted.add(vacationRequestJPA);
        Pageable paging = PageRequest.of(0, 1);
        Page<VacationRequestJPA> page = new PageImpl<>(excepted);

        when(vacationRequestPersistence.findAll(paging)).thenReturn(page);

        assertEquals(excepted, vacationRequestService.showAllVacationRequests(paging).getContent());
    }

    @Test
    void requestVacation() throws EmployeeNotFoundException {
        when(mapper.vacationRequestDTOToVacationRequestJPA(vacationRequestDTO)).thenReturn(vacationRequestJPA);
        when(workDayCalculator.getWorkingDaysBetweenTwoDates(vacationRequestDTO.beginDate(), vacationRequestDTO.endDate())).thenReturn(1);
        vacationRequestService.requestVacation(vacationRequestDTO);
        verify(vacationRequestPersistence, times(1)).save(vacationRequestJPA);
    }

    @Test
    void showVacationRequestByEmployee() {
        List<VacationRequestJPA> excepted = new LinkedList<>();
        excepted.add(vacationRequestJPA);
        Pageable paging = PageRequest.of(0, 1);
        Page<VacationRequestJPA> page = new PageImpl<>(excepted);

        when(vacationRequestPersistence.findByEmployeeId(1, paging)).thenReturn(page);

        assertEquals(excepted, vacationRequestService.showVacationRequestByEmployee(1, paging).getContent());
    }

    @Test
    void editVacationRequest() throws VacationRequestNotFound {
        VacationRequestJPA expected = new VacationRequestJPA(1, vacationRequestJPA.getEmployee(), vacationRequestJPA.getBeginDate(), vacationRequestJPA.getEndDate(), VacationRequestStatus.APPROVED, vacationRequestJPA.getVacationDays());

        when(vacationRequestPersistence.findById(1)).thenReturn(Optional.of(vacationRequestJPA));
        vacationRequestService.editVacationRequest(1, VacationRequestStatus.APPROVED);
        assertEquals(expected.toString(), vacationRequestJPA.toString());
    }

    @Test
    void editVacationRequestWithoutVacationRequest() {
        when(vacationRequestPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(VacationRequestNotFound.class, () ->
                vacationRequestService.editVacationRequest(1, VacationRequestStatus.APPROVED), "VacationRequestNotFound was expected");
    }
}