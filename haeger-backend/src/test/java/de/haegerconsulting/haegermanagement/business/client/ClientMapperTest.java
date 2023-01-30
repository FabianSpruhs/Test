package de.haegerconsulting.haegermanagement.business.client;

import de.haegerconsulting.haegermanagement.business.address.Address;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientMapperTest {

    Address address = new Address("Test Street", "5a", 10000, "Bonn");
    EmployeeJPA employeeJPA = new EmployeeJPA(2, "FirstName", "LastName", null, null, null, null, null, 0.0F);

    ClientJPA clientJPA = new ClientJPA(0, "Test Client", address, employeeJPA);
    ClientDTO clientDTO = new ClientDTO(0, "Test Client", address, 2);

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Test
    void clientJPAToClientDTO() {
        assertEquals(clientDTO, clientMapper.clientJPAToClientDTO(clientJPA));
    }

    @Test
    void clientDTOToClientJPA() {
        when(employeeService.showEmployee(2)).thenReturn(employeeJPA);
        assertEquals(clientJPA.toString(), clientMapper.clientDTOToClientJPA(clientDTO).toString());
    }
}