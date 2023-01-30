package de.haegerconsulting.haegermanagement.business.client;

import de.haegerconsulting.haegermanagement.business.address.Address;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.persistence.ClientPersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeePersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    Address address = new Address("Test Address", "5a", 10000, "Bonn");
    EmployeeJPA employeeJPA = new EmployeeJPA(1, "Test", "Test", null, null, null, null, null, 0.0F);
    ClientDTO clientDTO = new ClientDTO(0, "Test ClientDTO", address, 1);
    ClientJPA clientJPA = new ClientJPA(1, "Test ClientJPA", address, employeeJPA);

    @Mock
    ClientPersistence clientPersistence;

    @Mock
    EmployeePersistence employeePersistence;

    @Mock
    ClientMapper clientMapper;

    @InjectMocks
    ClientService clientService;

    @Test
    void createClient() throws EmployeeNotFoundException {
        ClientJPA expected = new ClientJPA(1, "Test ClientDTO", address, employeeJPA);
        when(clientMapper.clientDTOToClientJPA(clientDTO)).thenReturn(expected);
        clientService.createClient(clientDTO);
        verify(clientPersistence, times(1)).save(expected);
    }

    @Test
    void editClient() throws ClientNotFoundException, EmployeeNotFoundException {
        ClientJPA expected = new ClientJPA(1, "Test ClientDTO", address, employeeJPA);
        when(clientPersistence.findById(1)).thenReturn(Optional.ofNullable(clientJPA));
        clientService.editClient(1, clientDTO);
        assertEquals(expected.toString(), clientJPA.toString());
    }

    @Test
    void editClientWithoutClient() {
        when(clientPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () ->
                clientService.editClient(1, clientDTO), "ClientNotFoundException was expected");
    }

    @Test
    void editClientWithoutEmployee() {
        ClientDTO clientDTO = new ClientDTO(1, "Test ClientDTO", address, 2);
        when(clientPersistence.findById(1)).thenReturn(Optional.ofNullable(clientJPA));
        when(employeePersistence.findById(2)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () ->
                clientService.editClient(1, clientDTO), "EmployeeNotFoundException was expected");
    }

    @Test
    void showAllClients() {
        List<ClientJPA> expected = new LinkedList<>();
        expected.add(clientJPA);
        Pageable paging = PageRequest.of(1, 10);
        Page<ClientJPA> page = new PageImpl<>(expected);
        when(clientPersistence.findAll(paging)).thenReturn(page);
        assertEquals(expected, clientService.showAllClients(paging).getContent());
    }

    @Test
    void showClientByID() throws ClientNotFoundException {
        when(clientPersistence.findById(1)).thenReturn(Optional.of(clientJPA));
        assertEquals(clientJPA, clientService.showClientByID(1));
    }

    @Test
    void showClientByIDWithoutClient() {
        when(clientPersistence.findById(1)).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () ->
                clientService.showClientByID(1), "ClientNotFoundException was expected");
    }
}