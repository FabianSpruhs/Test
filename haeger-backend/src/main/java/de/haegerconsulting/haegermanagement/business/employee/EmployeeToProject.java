package de.haegerconsulting.haegermanagement.business.employee;

import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeToProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = EmployeeJPA.class)
    private EmployeeJPA employee;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = ProjectJPA.class)
    private ProjectJPA project;

    private float scheduledWorkingHours;

}
