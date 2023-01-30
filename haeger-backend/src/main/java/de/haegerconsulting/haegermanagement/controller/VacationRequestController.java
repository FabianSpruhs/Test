package de.haegerconsulting.haegermanagement.controller;

import de.haegerconsulting.haegermanagement.business.vacationRequest.*;
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
@RequestMapping("api/vacation_request")
@CrossOrigin(origins = "${application.crossOrigin}")
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;

    public VacationRequestController(@Autowired VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @GetMapping("/accounting/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<VacationRequestJPA> showAllVacationRequests(@ParameterObject Pageable paging) {
        log.info("Show all Vacation Requests" + paging);
        return vacationRequestService.showAllVacationRequests(paging);
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public VacationRequestDTO requestVacation(@Valid @RequestBody VacationRequestDTO vacationRequest) {
        log.info(vacationRequest);
        return vacationRequestService.requestVacation(vacationRequest);
    }

    @GetMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<VacationRequestJPA> showVacationRequestByEmployee(@PathVariable int id, @ParameterObject Pageable paging) {
        log.info("Show Vacation Request By Employee with ID " + id + paging);
        return vacationRequestService.showVacationRequestByEmployee(id, paging);
    }

    @PutMapping("/accounting/edit")
    @ResponseStatus(HttpStatus.OK)
    public VacationRequestDTO editVacationRequest(@RequestParam(name = "id") int id,@RequestParam(name = "status") VacationRequestStatus status) {
        log.info("Edit Vacation Request with ID " + id + " with Status " + status);
        return vacationRequestService.editVacationRequest(id, status);
    }

}
