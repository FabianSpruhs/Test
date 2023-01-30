package de.haegerconsulting.haegermanagement.business.client;


import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.persistence.ClientPersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeePersistence;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ClientService {

    private final ClientPersistence clientPersistence;
    private final EmployeePersistence employeePersistence;
    private final ClientMapper clientMapper;

    public ClientDTO createClient(ClientDTO client) {
        return clientMapper.clientJPAToClientDTO(clientPersistence.save(clientMapper.clientDTOToClientJPA(client)));
    }

    public ClientDTO editClient(int id, ClientDTO client) {
        ClientJPA clientToUpdate = clientPersistence.findById(id).orElseThrow(ClientNotFoundException::new);
        updateClientFields(client, clientToUpdate);
        return clientMapper.clientJPAToClientDTO(clientPersistence.save(clientToUpdate));
    }

    private void updateClientFields(ClientDTO client, ClientJPA clientToUpdate) {
        clientToUpdate.setName(client.name());
        clientToUpdate.setAddress(client.address());
        if (clientToUpdate.getContactEmployee().getId() != client.contactEmployeeID()) {
            EmployeeJPA employee = employeePersistence.findById(client.contactEmployeeID()).orElseThrow(EmployeeNotFoundException::new);
            clientToUpdate.setContactEmployee(employee);
        }
    }

    public Page<ClientJPA> showAllClients(Pageable paging) {
        return clientPersistence.findAll(paging);
    }

    public ClientJPA showClientByID(int id) {
        return clientPersistence.findById(id).orElseThrow(ClientNotFoundException::new);
    }
}
