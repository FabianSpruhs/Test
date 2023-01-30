package de.haegerconsulting.haegermanagement.business.workingHour;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeToProject;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeePersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeeToProjectPersistence;
import de.haegerconsulting.haegermanagement.persistence.WorkingHourPersistence;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkingHourService {

    private final WorkingHourPersistence workingHourPersistence;
    private final WorkingHourMapper mapper;
    private final EmployeeToProjectPersistence employeeToProjectPersistence;
    private final EmployeePersistence employeePersistence;

    public WorkingHourDTO bookWorkingHour(WorkingHourDTO workingHour) {
        WorkingHourJPA workingHourJPA = mapper.workingHourDTOToWorkingHourJPA(workingHour);
        workingHourJPA.setWorkingHourStatus(WorkingHourStatus.BOOKED);
        return mapper.workingHourJPAToWorkingHourDTO(workingHourPersistence.save(workingHourJPA));
    }

    public Page<WorkingHourJPA> showBookedWorkingHourByEmployee(int id, Pageable paging) {
        return workingHourPersistence.findByEmployeeId(id, paging);
    }

    public WorkingHourDTO editBookWorkingHour(int id, WorkingHourDTO workingHour) {
        WorkingHourJPA workingHourToEdit = workingHourPersistence.findById(id).orElseThrow(WorkingHourNotFoundException::new);
        workingHourToEdit.setWorkingDay(workingHour.workingDay());
        workingHourToEdit.setWorkingHours(workingHour.workingHours());
        return mapper.workingHourJPAToWorkingHourDTO(workingHourPersistence.save(workingHourToEdit));
    }

    public void finalizeWorkingHourByEmployee(int id) {
        Iterable<WorkingHourJPA> workingHoursToFinalize = workingHourPersistence.findByEmployeeId(id);
        for (WorkingHourJPA workingHour : workingHoursToFinalize) {
            workingHour.setWorkingHourStatus(WorkingHourStatus.FINALIZED);
        }
        workingHourPersistence.saveAll(workingHoursToFinalize);
    }

    public WorkingHourAccountingDTO showWorkingHourAccountingByEmployee(int id) {
        EmployeeJPA employee = employeePersistence.findById(id).orElseThrow(EmployeeNotFoundException::new);
        return new WorkingHourAccountingDTO(employee, calculateScheduledWorkingHours(id) - calculateActualWorkingHours(id));
    }

    private float calculateActualWorkingHours(int id) {
        Iterable<WorkingHourJPA> workingHours = workingHourPersistence.findByEmployeeId(id);
        float actualWorkingHours = 0.0F;
        for (WorkingHourJPA workingHour : workingHours) {
            actualWorkingHours += workingHour.getWorkingHours();
        }
        return actualWorkingHours;
    }

    private float calculateScheduledWorkingHours(int id) {
        Iterable<EmployeeToProject> employeeToProjects = employeeToProjectPersistence.findByEmployeeId(id);
        float scheduledWorkingHours = 0.0F;
        for (EmployeeToProject employeeToProject : employeeToProjects) {
            scheduledWorkingHours += employeeToProject.getScheduledWorkingHours();
        }
        return scheduledWorkingHours;
    }

    public Page<WorkingHourJPA> showAllFinalizedWorkingHours(Pageable paging) {
        return workingHourPersistence.findByWorkingHourStatus(WorkingHourStatus.FINALIZED, paging);
    }

    public List<WorkingHourAccountingDTO> showAllWorkingHourAccounting() {
        Iterable<EmployeeJPA> employees = employeePersistence.findAll();
        List<WorkingHourAccountingDTO> result = new LinkedList<>();
        for (EmployeeJPA employee : employees) {
            result.add(showWorkingHourAccountingByEmployee(employee.getId()));
        }
        return result;
    }
}
