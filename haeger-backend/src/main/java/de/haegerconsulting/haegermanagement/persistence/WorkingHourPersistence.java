package de.haegerconsulting.haegermanagement.persistence;

import de.haegerconsulting.haegermanagement.business.workingHour.WorkingHourJPA;
import de.haegerconsulting.haegermanagement.business.workingHour.WorkingHourStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHourPersistence extends JpaRepository<WorkingHourJPA, Integer> {

    Page<WorkingHourJPA> findByEmployeeId(int id, Pageable pageable);
    Iterable<WorkingHourJPA> findByEmployeeId(int id);
    Page<WorkingHourJPA> findByWorkingHourStatus(WorkingHourStatus status, Pageable pageable);
    Iterable<WorkingHourJPA> findByWorkingHourStatus(WorkingHourStatus status);

}
