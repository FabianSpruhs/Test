package de.haegerconsulting.haegermanagement.persistence.employee;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePersistence extends JpaRepository<EmployeeJPA, Integer> {

    Page<EmployeeJPA> findEmployeeByStatus(EmployeeStatus status,
                                           Pageable pageable);
}
