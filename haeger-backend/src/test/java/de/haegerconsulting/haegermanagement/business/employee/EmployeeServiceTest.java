package de.haegerconsulting.haegermanagement.business.employee;

import de.haegerconsulting.haegermanagement.business.address.Address;
import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import de.haegerconsulting.haegermanagement.business.project.ProjectNotFoundException;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestJPA;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestStatus;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeePersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeeToProjectPersistence;
import de.haegerconsulting.haegermanagement.persistence.ProjectPersistence;
import de.haegerconsulting.haegermanagement.persistence.VacationRequestPersistence;
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
class EmployeeServiceTest {

    EmployeeJPA employee1;
    EmployeeJPA employee2;
    Address address = new Address("Test Address", "5a", 10000, "Bonn");
    VacationRequestJPA vacationRequest1 = new VacationRequestJPA(1, employee1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2), VacationRequestStatus.APPROVED, 1);
    VacationRequestJPA vacationRequest2 = new VacationRequestJPA(2, employee1, LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 3), VacationRequestStatus.APPROVED, 2);
    ProjectJPA project = new ProjectJPA(1, null, "Test Project");
    EmployeeToProject employeeToProject;
    EmployeeDTO employeeDTO = new EmployeeDTO(1, "Test 1", "Test 2", address, "test@test.com", "+49 221 111222", 10.0F);
    Pageable paging = PageRequest.of(0, 1);

    @Mock
    EmployeePersistence employeePersistence;

    @Mock
    ProjectPersistence projectPersistence;

    @Mock
    EmployeeToProjectPersistence employeeToProjectPersistence;

    @Mock
    VacationRequestPersistence vacationRequestPersistence;

    @Mock
    EmployeeMapper employeeMapper;

    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employee1 = new EmployeeJPA(1, "FirstName 1", "LastName 1", address, "test@test.com", "+49 221 111222", null, null, 30);
        employee2 = new EmployeeJPA(2, "FirstName 2", "LastName 2", address, "tests@tests.com", "+49 2233 111222", null, null, 28);
        employeeToProject = new EmployeeToProject(0, employee1, project, 40);
    }

    @Test
    void hireEmployee() {
        when(employeeMapper.employeeDTOToEmployeeJPA(employeeDTO)).thenReturn(employee1);
        employeeService.hireEmployee(employeeDTO);
        verify(employeePersistence, times(1)).save(employee1);
    }

    @Test
    void findEmployeeByID() throws EmployeeNotFoundException {
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        assertEquals(employee1, employeeService.showEmployee(1));
    }

    @Test
    void fire() throws EmployeeNotFoundException {
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        employeeService.fire(employee1.getId());
        verify(employeePersistence, times(1)).delete(employee1);
    }

    @Test
    void fireWithoutEmployee() {
        when(employeePersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.fire(1), "EmployeeNotFoundException was expected");
    }

    @Test
    void showAllEmployees() {
        List<EmployeeJPA> expected = new LinkedList<>();
        expected.add(employee1);
        expected.add(employee2);
        Pageable paging = PageRequest.of(0, 2);
        Page<EmployeeJPA> page = new PageImpl<>(expected);

        when(employeePersistence.findAll(paging)).thenReturn(page);

        assertEquals(expected, employeeService.showAllEmployees(paging).getContent());
    }

    @Test
    void editEmployee() throws EmployeeNotFoundException {
        EmployeeJPA expected = new EmployeeJPA(1, employeeDTO.firstName(), employeeDTO.lastName(), employeeDTO.address(), employeeDTO.mail(), employeeDTO.telephoneNumber(), employee1.getStatus(), employee1.getRole(), employeeDTO.scheduledVacationDays());
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        employeeService.editEmployeeData(1, employeeDTO);
        assertEquals(expected.toString(), employee1.toString());
    }

    @Test
    void editEmployeeWithNulls() throws EmployeeNotFoundException {
        EmployeeJPA expected = new EmployeeJPA(employee1.getId(), employee1.getFirstName(), employee1.getLastName(), employee1.getAddress(), employee1.getMail(), employee1.getTelephoneNumber(), employee1.getStatus(), employee1.getRole(), employee1.getScheduledVacationDays());
        EmployeeDTO employeeWithNull = new EmployeeDTO(1, employee1.getFirstName(), employee1.getLastName(), null, null, null, 0.0F);
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        employeeService.editEmployeeData(1, employeeWithNull);
        assertEquals(expected.toString(), employee1.toString());
    }

    @Test
    void showRemainingVacationDaysByEmployee() throws EmployeeNotFoundException {
        List<VacationRequestJPA> vacationRequests = new LinkedList<>();
        vacationRequests.add(vacationRequest1);
        vacationRequests.add(vacationRequest2);
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        when(vacationRequestPersistence.findByEmployeeIdAndStatus(1, VacationRequestStatus.APPROVED)).thenReturn(vacationRequests);

        assertEquals(27, employeeService.showRemainingVacationDaysByEmployee(1));

    }

    @Test
    void showEmployeesByStatus() {
        employee1.setStatus(EmployeeStatus.ACTIVE);
        employee2.setStatus(EmployeeStatus.ACTIVE);
        List<EmployeeJPA> expected = new LinkedList<>();
        expected.add(employee1);
        expected.add(employee2);
        Page<EmployeeJPA> page = new PageImpl<>(expected);
        when(employeePersistence.findEmployeeByStatus(EmployeeStatus.ACTIVE, paging)).thenReturn(page);
        assertEquals(expected, employeeService.showEmployeesByStatus(EmployeeStatus.ACTIVE, paging).getContent());
    }

    @Test
    void assignEmployeeToProject() throws ProjectNotFoundException, EmployeeNotFoundException {
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        when(projectPersistence.findById(1)).thenReturn(Optional.of(project));
        employeeService.assignEmployeeToProject(1, 1, 40);
        verify(employeeToProjectPersistence, times(1)).save(employeeToProject);
    }

    @Test
    void assignEmployeeToProjectWithoutProject() {
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        when(projectPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () ->
                employeeService.assignEmployeeToProject(1, 1, 40), "ProjectNotFoundException was expected");
    }

    @Test
    void assignEmployeeToProjectWithoutEmployee() {
        when(employeePersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.assignEmployeeToProject(1, 1, 40), "EmployeeNotFoundException was expected");
    }

    @Test
    void editEmployeeStatus() throws EmployeeNotFoundException {
        EmployeeJPA expected = new EmployeeJPA(employee1.getId(), employee1.getFirstName(), employee1.getLastName(), employee1.getAddress(), employee1.getMail(), employee1.getTelephoneNumber(), EmployeeStatus.PASSIVE, employee1.getRole(), employee1.getScheduledVacationDays());
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        employeeService.editEmployeeStatus(1, EmployeeStatus.PASSIVE);
        assertEquals(expected.toString(), employee1.toString());
    }

    @Test
    void editEmployeeRole() throws EmployeeNotFoundException {
        EmployeeJPA expected = new EmployeeJPA(employee1.getId(), employee1.getFirstName(), employee1.getLastName(), employee1.getAddress(), employee1.getMail(), employee1.getTelephoneNumber(), employee1.getStatus(), EmployeeRole.ADMIN, employee1.getScheduledVacationDays());
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        employeeService.editEmployeeRole(1, EmployeeRole.ADMIN);
        assertEquals(expected.toString(), employee1.toString());
    }

    @Test
    void showEmployee() throws EmployeeNotFoundException {
        when(employeePersistence.findById(1)).thenReturn(Optional.of(employee1));
        assertEquals(employee1, employeeService.showEmployee(1));
    }

    @Test
    void showEmployeeWithoutEmployee() {
        when(employeePersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.showEmployee(1), "EmployeeNotFoundException was expected");
    }
}