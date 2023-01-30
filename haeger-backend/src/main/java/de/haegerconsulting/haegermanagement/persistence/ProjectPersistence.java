package de.haegerconsulting.haegermanagement.persistence;

import de.haegerconsulting.haegermanagement.business.project.ProjectJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPersistence extends JpaRepository<ProjectJPA, Integer> {
}
