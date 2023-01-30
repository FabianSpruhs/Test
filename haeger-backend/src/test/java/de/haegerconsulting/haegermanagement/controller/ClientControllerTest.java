package de.haegerconsulting.haegermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.address.Address;
import de.haegerconsulting.haegermanagement.business.client.ClientDTO;
import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import de.haegerconsulting.haegermanagement.business.client.ClientService;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.configurations.security.JwtUtils;
import de.haegerconsulting.haegermanagement.configurations.security.MyBasicAuthenticationEntryPoint;
import de.haegerconsulting.haegermanagement.configurations.security.UserDetailsServiceImpl;
import de.haegerconsulting.haegermanagement.configurations.security.WebSecurityConfig;
import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})

class ClientControllerTest {

    Address address = new Address("Test Address", "5a", 10000, "Bonn");
    EmployeeJPA employeeJPA = new EmployeeJPA(1, "Test", "Test", null, null, null, null, null, 0.0F);
    ClientDTO clientDTO = new ClientDTO(0, "Test Client", address, 1);
    ClientJPA clientJPA = new ClientJPA(1, "Test Client", address, employeeJPA);

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    ClientService clientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createClient() throws Exception {
        mockMvc.perform(post("/api/client/accounting").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO))).andExpect(status().isCreated());
        verify(clientService, times(1)).createClient(clientDTO);
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createClientWithoutName() throws Exception {
        ClientDTO clientWithoutName = new ClientDTO(clientDTO.id(), null, clientDTO.address(), clientDTO.contactEmployeeID());
        mockMvc.perform(post("/api/client/accounting").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientWithoutName))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createClientWithoutAddress() throws Exception {
        ClientDTO clientWithoutAddress = new ClientDTO(clientDTO.id(), clientDTO.name(), null, clientDTO.contactEmployeeID());
        mockMvc.perform(post("/api/client/accounting").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientWithoutAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void createClientWithNegativeContact() throws Exception {
        ClientDTO clientWithoutAddress = new ClientDTO(clientDTO.id(), clientDTO.name(), clientDTO.address(), -10);
        mockMvc.perform(post("/api/client/accounting").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientWithoutAddress))).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void editClient() throws Exception {
        mockMvc.perform(put("/api/client/accounting/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());
        verify(clientService, times(1)).editClient(1, clientDTO);

    }

    @Test
    @WithMockUser(roles = "ACCOUNTING")
    void showAllClients() throws Exception {
        List<ClientJPA> expected = new LinkedList<>();
        expected.add(clientJPA);
        Page<ClientJPA> page = new PageImpl<>(expected);
        Pageable paging = PageRequest.of(1, 10);


        when(clientService.showAllClients(paging)).thenReturn(page);

        mockMvc.perform(get("/api/client/accounting/all").param("page", "1").param("size", "10"))
                .andExpect(status().isOk());
        verify(clientService, times(1)).showAllClients(paging);
    }
}