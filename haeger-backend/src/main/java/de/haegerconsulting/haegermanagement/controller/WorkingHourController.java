package de.haegerconsulting.haegermanagement.controller;

import de.haegerconsulting.haegermanagement.business.workingHour.*;
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
@RequestMapping("api/working_hour")
@CrossOrigin(origins = "${application.crossOrigin}")
public class WorkingHourController {

    private final WorkingHourService workingHourService;

    public WorkingHourController(@Autowired WorkingHourService workingHourService) {
        this.workingHourService = workingHourService;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkingHourDTO bookWorkingHour(@Valid @RequestBody WorkingHourDTO workingHour) {
        log.info(workingHour);
        return workingHourService.bookWorkingHour(workingHour);
    }

    @GetMapping("/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<WorkingHourJPA> showBookedWorkingHourByEmployee(@PathVariable int id, @ParameterObject Pageable paging) {
        log.info("Show Booked Working Hour from Employee with ID " + id + paging);
        return workingHourService.showBookedWorkingHourByEmployee(id, paging);
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkingHourDTO editBookWorkingHour(@PathVariable int id, @Valid @RequestBody WorkingHourDTO workingHour) {
        log.info("Edit Working Hour with ID " + id + " with " + workingHour);
        return workingHourService.editBookWorkingHour(id, workingHour);
    }

    @PutMapping("finalize/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void finalizeWorkingHourByEmployee(@PathVariable int id) {
        log.info("Finalize all Working Hour form Employee with ID " + id);
        workingHourService.finalizeWorkingHourByEmployee(id);
    }

    @GetMapping("/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkingHourAccountingDTO showWorkingHourAccountingByEmployee(@PathVariable int id) {
        log.info("Show Working Hour Accounting from Employee with ID " + id);
        return workingHourService.showWorkingHourAccountingByEmployee(id);
    }

    @GetMapping("/accounting/finalize/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<WorkingHourJPA> showAllFinalizedWorkingHours(@ParameterObject Pageable paging) {
        log.info("Show All Finalized working Hours" + paging);
        return workingHourService.showAllFinalizedWorkingHours(paging);
    }

    @GetMapping("/accounting/account/all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<WorkingHourAccountingDTO> showAllWorkingHourAccounting() {
        log.info("Show All Working Hour Accounting");
        return workingHourService.showAllWorkingHourAccounting();
    }
}
