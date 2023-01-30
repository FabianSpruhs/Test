package de.haegerconsulting.haegermanagement.persistence;

import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestJPA;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VacationRequestPersistence extends JpaRepository<VacationRequestJPA, Integer> {

    Page<VacationRequestJPA> findByEmployeeId(int id, Pageable pageable);

    Iterable<VacationRequestJPA> findByEmployeeIdAndStatus(int id, VacationRequestStatus status);

}
