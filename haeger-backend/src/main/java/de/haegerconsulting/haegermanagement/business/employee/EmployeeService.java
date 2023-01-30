package de.haegerconsulting.haegermanagement.business.employee;

import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import de.haegerconsulting.haegermanagement.business.project.ProjectNotFoundException;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestJPA;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestStatus;
import de.haegerconsulting.haegermanagement.persistence.*;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeePersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeeToProjectPersistence;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeePersistence employeePersistence;
    private final ProjectPersistence projectPersistence;
    private final EmployeeToProjectPersistence employeeToProjectPersistence;
    private final VacationRequestPersistence vacationRequestPersistence;
    private final EmployeeMapper employeeMapper;

    public EmployeeDTO hireEmployee(EmployeeDTO employee) {
        EmployeeJPA employeeJPA = employeeMapper.employeeDTOToEmployeeJPA(employee);
        employeeJPA.setRole(EmployeeRole.STANDARD);
        employeeJPA.setStatus(EmployeeStatus.ACTIVE);
        return employeeMapper.employeeJPAToEmployeeDTO(employeePersistence.save(employeeJPA));
    }

    public EmployeeJPA showEmployee(int id) {
        return employeePersistence.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public void fire(int id) {
        EmployeeJPA employee = employeePersistence.findById(id).orElseThrow(EmployeeNotFoundException::new);
        employeePersistence.delete(employee);
    }

    public Page<EmployeeJPA> showAllEmployees(Pageable paging) {
        return employeePersistence.findAll(paging);
    }

    public EmployeeDTO editEmployeeData(int id, EmployeeDTO employee) {
        EmployeeJPA employeeToEdit = showEmployee(id);
        updateEmployeeFields(employee, employeeToEdit);
        return employeeMapper.employeeJPAToEmployeeDTO(employeePersistence.save(employeeToEdit));
    }

    private void updateEmployeeFields(EmployeeDTO employee, EmployeeJPA employeeToEdit) {
        employeeToEdit.setFirstName(employee.firstName());
        employeeToEdit.setLastName(employee.lastName());
        editOptionalFields(employee, employeeToEdit);
    }

    private void editOptionalFields(EmployeeDTO employee, EmployeeJPA employeeToEdit) {
        editAddress(employee, employeeToEdit);
        editMail(employee, employeeToEdit);
        editTelephoneNumber(employee, employeeToEdit);
        editScheduledVacationDays(employee, employeeToEdit);
    }

    private void editAddress(EmployeeDTO employee, EmployeeJPA employeeToEdit) {
        if (employee.address() != null) {
            employeeToEdit.setAddress(employee.address());
        }
    }

    private void editMail(EmployeeDTO employee, EmployeeJPA employeeToEdit) {
        if (employee.mail() != null) {
            employeeToEdit.setMail(employee.mail());
        }
    }

    private void editTelephoneNumber(EmployeeDTO employee, EmployeeJPA employeeToEdit) {
        if (employee.telephoneNumber() != null) {
            employeeToEdit.setTelephoneNumber(employee.telephoneNumber());
        }
    }

    private void editScheduledVacationDays(EmployeeDTO employee, EmployeeJPA employeeToEdit) {
        if (employee.scheduledVacationDays() != 0.0F) {
            employeeToEdit.setScheduledVacationDays(employee.scheduledVacationDays());
        }
    }

    public float showRemainingVacationDaysByEmployee(int id) {
        EmployeeJPA employee = showEmployee(id);
        Iterable<VacationRequestJPA> vacationRequests = vacationRequestPersistence.findByEmployeeIdAndStatus(id, VacationRequestStatus.APPROVED);
        float vacationDaysSum = 0.0F;
        for (VacationRequestJPA vacationRequest : vacationRequests) {
            vacationDaysSum += vacationRequest.getVacationDays();
        }
        return employee.getScheduledVacationDays() - vacationDaysSum;
    }

    public Page<EmployeeJPA> showEmployeesByStatus(EmployeeStatus status, Pageable paging) {
        return employeePersistence.findEmployeeByStatus(status, paging);
    }

    public EmployeeToProject assignEmployeeToProject(int employeeId, int projectId, float workingHours) {
        EmployeeJPA employee = showEmployee(employeeId);
        ProjectJPA project = projectPersistence.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        EmployeeToProject employeeToProject = new EmployeeToProject();
        employeeToProject.setEmployee(employee);
        employeeToProject.setProject(project);
        employeeToProject.setScheduledWorkingHours(workingHours);
        return employeeToProjectPersistence.save(employeeToProject);
    }

    public void editEmployeeStatus(int id, EmployeeStatus status) {
        EmployeeJPA employeeToEdit = showEmployee(id);
        employeeToEdit.setStatus(status);
        employeePersistence.save(employeeToEdit);
    }

    public void editEmployeeRole(int id, EmployeeRole role) {
        EmployeeJPA employeeToEdit = showEmployee(id);
        employeeToEdit.setRole(role);
        employeePersistence.save(employeeToEdit);
    }
}
