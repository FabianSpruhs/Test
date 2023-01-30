package de.haegerconsulting.haegermanagement.business.project;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException() {
        super("Project not Found!");
    }
}
