package de.haegerconsulting.haegermanagement.business.employee;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("Employee not Found!");
    }
}
