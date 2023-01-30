package de.haegerconsulting.haegermanagement.business.workingHour;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;

public record WorkingHourAccountingDTO (
    EmployeeJPA employeeJPA, float workingHour ) {
}
