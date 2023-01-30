package de.haegerconsulting.haegermanagement.controller;

import de.haegerconsulting.haegermanagement.business.project.ProjectDTO;
import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import de.haegerconsulting.haegermanagement.business.project.ProjectService;
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
@RequestMapping("api/project")
@CrossOrigin(origins = "${application.crossOrigin}")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(@Autowired ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/accounting/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjectJPA> showAllProjects(@ParameterObject Pageable paging) {
        log.info("Show All Projects" + paging);
        return projectService.showAllProjects(paging);
    }

    @GetMapping("employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<ProjectJPA> showProjectsByEmployee(@PathVariable int id) {
        log.info("Show Projects by Employee with ID " + id);
        return projectService.showProjectsByEmployee(id);
    }


    @PutMapping("/accounting/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO editProject(@PathVariable int id, @RequestBody ProjectDTO project) {
        log.info("Edit Project with ID " + id + " with " + project);
        return projectService.editProject(id, project);
    }

    @PostMapping("/accounting/assign")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO assignProjectToClient(@RequestParam(name = "project_id") int projectID, @RequestParam(name = "client_id") int clientID) {
        log.info("Assign Project with ID " + projectID + " to Client with ID " + clientID);
        return projectService.assignProjectToClient(projectID, clientID);
    }

    @PostMapping("/accounting/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDTO createProject(@Valid @RequestBody ProjectDTO project) {
        log.info("create " + project);
        return projectService.createProject(project);
    }
}
