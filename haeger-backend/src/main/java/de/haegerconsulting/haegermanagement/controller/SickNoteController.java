package de.haegerconsulting.haegermanagement.controller;

import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteDTO;
import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteJPA;
import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteService;
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
@RequestMapping("api/sick_note")
@CrossOrigin(origins = "${application.crossOrigin}")
public class SickNoteController {

    private final SickNoteService sickNoteService;

    public SickNoteController(@Autowired SickNoteService sickNoteService) {
        this.sickNoteService = sickNoteService;
    }

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.CREATED)
    public SickNoteDTO submitSickNote(@Valid @RequestBody SickNoteDTO sickNote) {
        log.info(sickNote);
        log.info("TEST TEST TEST");
        return sickNoteService.submitSickNote(sickNote);
    }

    @GetMapping("/accounting/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<SickNoteJPA> showAllSickNotes(@ParameterObject Pageable paging){
        log.info("Show All Sick Notes" + paging);
        return sickNoteService.showAllSickNotes(paging);
    }
}
