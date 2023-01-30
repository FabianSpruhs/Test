package de.haegerconsulting.haegermanagement.business.project;

import de.haegerconsulting.haegermanagement.business.client.ClientJPA;
import de.haegerconsulting.haegermanagement.business.client.ClientNotFoundException;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeToProject;
import de.haegerconsulting.haegermanagement.persistence.ClientPersistence;
import de.haegerconsulting.haegermanagement.persistence.employee.EmployeeToProjectPersistence;
import de.haegerconsulting.haegermanagement.persistence.ProjectPersistence;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectPersistence projectPersistence;
    private final ClientPersistence clientPersistence;
    private final EmployeeToProjectPersistence employeeToProjectPersistence;
    private final ProjectMapper projectMapper;

    public Page<ProjectJPA> showAllProjects(Pageable paging) {
        return projectPersistence.findAll(paging);
    }

    public List<ProjectJPA> showProjectsByEmployee(int id) {
        Iterable<EmployeeToProject> employeeToProjects = employeeToProjectPersistence.findByEmployeeId(id);
        List<ProjectJPA> result = new LinkedList<>();
        for (EmployeeToProject employeeToProject : employeeToProjects) {
            result.add(employeeToProject.getProject());
        }
        return result;
    }

    public ProjectDTO createProject(ProjectDTO project) {
        return projectMapper.projectJPAToProjectDTO(projectPersistence.save(projectMapper.projectDTOToProjectJPA(project)));
    }

    public ProjectDTO assignProjectToClient(int projectID, int clientID) {
        ProjectJPA project = projectPersistence.findById(projectID).orElseThrow(ProjectNotFoundException::new);
        ClientJPA client = clientPersistence.findById(clientID).orElseThrow(ClientNotFoundException::new);
        project.setClient(client);
        return projectMapper.projectJPAToProjectDTO(projectPersistence.save(project));
    }

    public ProjectDTO editProject(int id, ProjectDTO project) {
        ProjectJPA projectToEdit =  projectPersistence.findById(id).orElseThrow(ProjectNotFoundException::new);
        projectToEdit.setName(project.name());
        return projectMapper.projectJPAToProjectDTO(projectPersistence.save(projectToEdit));
    }
}
