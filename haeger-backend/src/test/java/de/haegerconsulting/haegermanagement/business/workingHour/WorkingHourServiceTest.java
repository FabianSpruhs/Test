package de.haegerconsulting.haegermanagement.business.workingHour;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeToProject;
import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeePersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeeToProjectPersistence;
import de.haegerconsulting.haegermanagement.persistence.WorkingHourPersistence;
import org.junit.jupiter.api.BeforeEach;
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
class WorkingHourServiceTest {


    EmployeeJPA employeeJPA1 = new EmployeeJPA(1, "FirstName 1", "LastName 1" ,null, null, null, null, null, 30.0F);
    EmployeeJPA employeeJPA2 = new EmployeeJPA(2, "FirstName 2", "LastName 2" ,null, null, null, null, null, 20.0F);
    ProjectJPA project = new ProjectJPA(1, null, "Test Project");
    EmployeeToProject employeeToProject = new EmployeeToProject(0, employeeJPA1, project, 40);
    WorkingHourJPA workingHourJPA1;
    WorkingHourJPA workingHourJPA2;
    WorkingHourDTO workingHourDTO;

    @Mock
    WorkingHourPersistence workingHourPersistence;

    @Mock
    WorkingHourMapper mapper;

    @Mock
    EmployeeToProjectPersistence employeeToProjectPersistence;

    @Mock
    EmployeePersistence employeePersistence;

    @InjectMocks
    WorkingHourService workingHourService;

    @BeforeEach
    void setUp() {
        workingHourJPA1 = new WorkingHourJPA(1, employeeJPA1, LocalDate.of(2022, 11, 1), 1.0F, WorkingHourStatus.BOOKED);
        workingHourJPA2 = new WorkingHourJPA(2, employeeJPA2, LocalDate.of(2022, 10, 1), 2.0F, WorkingHourStatus.FINALIZED);
        workingHourDTO = new WorkingHourDTO(0, 1, LocalDate.of(2022, 11, 1), 5.0F, WorkingHourStatus.BOOKED);
    }

    @Test
    void bookWorkingHour() throws EmployeeNotFoundException {
        when(mapper.workingHourDTOToWorkingHourJPA(workingHourDTO)).thenReturn(workingHourJPA1);
        workingHourService.bookWorkingHour(workingHourDTO);
        verify(workingHourPersistence, times(1)).save(workingHourJPA1);
    }

    @Test
    void showBookedWorkingHourByEmployee() {
        LinkedList<WorkingHourJPA> expected = new LinkedList<>();
        expected.add(workingHourJPA1);
        Pageable paging = PageRequest.of(0, 1);
        Page<WorkingHourJPA> page = new PageImpl<>(expected);

        when(workingHourPersistence.findByEmployeeId(1, paging)).thenReturn(page);

        assertEquals(expected, workingHourService.showBookedWorkingHourByEmployee(1, paging).getContent());
    }

    @Test
    void editBookWorkingHour() throws WorkingHourNotFoundException {
        WorkingHourJPA expected = new WorkingHourJPA(1, employeeJPA1, LocalDate.of(2022, 11, 1), 5.0F, WorkingHourStatus.BOOKED);
        when(workingHourPersistence.findById(1)).thenReturn(Optional.of(workingHourJPA1));
        workingHourService.editBookWorkingHour(1, workingHourDTO);
        assertEquals(expected.toString(), workingHourJPA1.toString());
    }

    @Test
    void editBookWorkingHourWithoutWorkingHour() {
        when(workingHourPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(WorkingHourNotFoundException.class, () ->
                workingHourService.editBookWorkingHour(1, workingHourDTO), "WorkingHourNotFoundException was expected");
    }

    @Test
    void finalizeWorkingHourByEmployee() {
        List<WorkingHourJPA> workingHourJPAS = new LinkedList<>();
        workingHourJPAS.add(workingHourJPA1);

        when(workingHourPersistence.findByEmployeeId(1)).thenReturn(workingHourJPAS);
        workingHourService.finalizeWorkingHourByEmployee(1);
        workingHourJPAS.get(0).setWorkingHourStatus(WorkingHourStatus.FINALIZED);
        verify(workingHourPersistence, times(1)).saveAll(workingHourJPAS);
    }

    @Test
    void showWorkingHourAccountingByEmployee() throws EmployeeNotFoundException {
        List<WorkingHourJPA> workingHourJPAS = new LinkedList<>();
        workingHourJPAS.add(workingHourJPA1);

        List<EmployeeToProject> employeeToProjects = new LinkedList<>();
        employeeToProjects.add(employeeToProject);

        when(employeePersistence.findById(1)).thenReturn(Optional.of(employeeJPA1));
        when(workingHourPersistence.findByEmployeeId(1)).thenReturn(workingHourJPAS);
        when(employeeToProjectPersistence.findByEmployeeId(1)).thenReturn(employeeToProjects);

        assertEquals(new WorkingHourAccountingDTO(employeeJPA1, 39.0F), workingHourService.showWorkingHourAccountingByEmployee(1));
    }

    @Test
    void showWorkingHourAccountingByEmployeeWithoutEmployee() {
        when(employeePersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () ->
                workingHourService.showWorkingHourAccountingByEmployee(1), "EmployeeNotFoundException was expected");
    }

    @Test
    void showAllFinalizedWorkingHours() {
        List<WorkingHourJPA> expected = new LinkedList<>();
        expected.add(workingHourJPA2);
        Pageable paging = PageRequest.of(0, 1);
        Page<WorkingHourJPA> page = new PageImpl<>(expected);

        when(workingHourPersistence.findByWorkingHourStatus(WorkingHourStatus.FINALIZED, paging)).thenReturn(page);

        assertEquals(expected, workingHourService.showAllFinalizedWorkingHours(paging).getContent());
    }

}