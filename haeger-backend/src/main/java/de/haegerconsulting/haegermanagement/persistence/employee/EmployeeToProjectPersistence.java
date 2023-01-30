package de.haegerconsulting.haegermanagement.persistence.employee;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeToProject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeToProjectPersistence extends CrudRepository<EmployeeToProject, Integer> {

    Iterable<EmployeeToProject> findByEmployeeId(int id);
}
