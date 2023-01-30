package de.haegerconsulting.haegermanagement.controller;


import de.haegerconsulting.haegermanagement.business.client.ClientDTO;
import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import de.haegerconsulting.haegermanagement.business.client.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/client")
@Log4j2
@CrossOrigin(origins = "${application.crossOrigin}")
public class ClientController {

    private final ClientService clientService;

    public ClientController(@Autowired ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/accounting")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO createClient(@Valid @RequestBody ClientDTO client){
        log.info(client);
        return clientService.createClient(client);
    }

    @PutMapping("/accounting/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO editClient(@PathVariable int id, @Valid @RequestBody ClientDTO client){
        log.info("ID to Edit: " + id + " with " + client);
        return clientService.editClient(id, client);
    }

    @GetMapping("/accounting/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientJPA> showAllClients(@ParameterObject Pageable paging){
        log.info("Show All Clients" + paging.toString());
        return clientService.showAllClients(paging);
    }

}

