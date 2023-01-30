package de.haegerconsulting.haegermanagement.business.employee;

import de.haegerconsulting.haegermanagement.business.address.Address;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    Address address = new Address("Test Street", "5a", 10000, "Bonn");
    EmployeeJPA employeeJPA = new EmployeeJPA(0, "FirstName", "LastName", address, "test@testen.com", "+49 221 111222", EmployeeStatus.ACTIVE, EmployeeRole.STANDARD, 30.0F);
    EmployeeJPA employeeJPAAfterMap = new EmployeeJPA(0, "FirstName", "LastName", address, "test@testen.com", "+49 221 111222", null, null, 30.0F);
    EmployeeDTO employeeDTO = new EmployeeDTO(0,  "FirstName", "LastName", address, "test@testen.com", "+49 221 111222", 30.0F);


    EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    void employeeDTOToEmployeeJPA() {
        assertEquals(employeeJPAAfterMap.toString(), employeeMapper.employeeDTOToEmployeeJPA(employeeDTO).toString());
    }

    @Test
    void employeeJPAToEmployeeDTO() {
        assertEquals(employeeDTO, employeeMapper.employeeJPAToEmployeeDTO(employeeJPA));
    }
}