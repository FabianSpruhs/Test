package de.haegerconsulting.haegermanagement.controller;


import de.haegerconsulting.haegermanagement.business.employee.*;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
@RequestMapping("api/employee")
@CrossOrigin(origins = "${application.crossOrigin}")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(@Autowired EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/admin/hire")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO hireEmployee(@Valid @RequestBody EmployeeDTO employee) {
        log.info(employee);
        return employeeService.hireEmployee(employee);
    }

    @DeleteMapping("/admin/fire/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void fireEmployee(@PathVariable int id) {
        log.info("ID to Delete: " + id);
        employeeService.fire(id);
    }

    @GetMapping("/accounting/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeJPA> showAllEmployees(@ParameterObject Pageable paging) {
        log.info("Show all Employees" + paging);
        return employeeService.showAllEmployees(paging);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeJPA showEmployee(@PathVariable int id) {
        log.info("Show Employee with ID " + id);
        return employeeService.showEmployee(id);
    }

    @PutMapping("/accounting/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO editEmployeeData(@PathVariable int id, @Valid @RequestBody EmployeeDTO employee) {
        log.info("ID to edit " + id + " with " + employee);
        return employeeService.editEmployeeData(id, employee);
    }

    @PutMapping("/admin/edit/status")
    @ResponseStatus(HttpStatus.OK)
    public void editEmployeeStatus(@RequestParam(name = "employee_id") int id, @RequestParam(name = "status") EmployeeStatus status) {
        log.info("ID to edit " + id + " with Status " + status);
        employeeService.editEmployeeStatus(id, status);
    }

    @PutMapping("/admin/edit/role")
    @ResponseStatus(HttpStatus.OK)
    public void editEmployeeRole(@RequestParam(name = "employee_id") int id, @RequestParam(name = "role") EmployeeRole role) {
        log.info("ID to edit " + id + " with Role " + role);
        employeeService.editEmployeeRole(id, role);
    }


    @GetMapping("/remaining_vacation/{id}")
    @ResponseStatus(HttpStatus.OK)
    public float showRemainingVacationDaysByEmployee(@PathVariable int id) {
        log.info("Remaining Vacation Days of ID " + id);
        return employeeService.showRemainingVacationDaysByEmployee(id);
    }

    @GetMapping("/accounting/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeJPA> showEmployeesByStatus(@PathVariable EmployeeStatus status, @ParameterObject Pageable paging) {
        log.info("Show Employee by Status " + status + paging);
        return  employeeService.showEmployeesByStatus(status, paging);
    }

    @PostMapping("/accounting/assign_toProject")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeToProject assignEmployeeToProject(@RequestParam(name = "employee_id") int employeeId,
                                                     @RequestParam(name = "project_id") int projectId,
                                                     @RequestParam(name = "working_hours") float workingHours) {
        log.info("Assign Employee with ID: " + employeeId + " to Project with ID " + projectId + " with " + workingHours + " Working Hours");
        return employeeService.assignEmployeeToProject(employeeId, projectId, workingHours);
    }

}
