package de.haegerconsulting.haegermanagement.business.vacationRequest;

import de.haegerconsulting.haegermanagement.persistence.VacationRequestPersistence;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WorkDayCalculator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class VacationRequestService {

    private final VacationRequestPersistence vacationRequestPersistence;
    private final VacationRequestMapper mapper;
    private final WorkDayCalculator workDayCalculator;

    public Page<VacationRequestJPA> showAllVacationRequests(Pageable paging) {
        return vacationRequestPersistence.findAll(paging);
    }

    public VacationRequestDTO requestVacation(VacationRequestDTO vacationRequest) {
        VacationRequestJPA vacationRequestJPA = mapper.vacationRequestDTOToVacationRequestJPA(vacationRequest);
        vacationRequestJPA.setStatus(VacationRequestStatus.OPEN);
        vacationRequestJPA.setVacationDays(workDayCalculator.getWorkingDaysBetweenTwoDates(vacationRequest.beginDate(), vacationRequest.endDate()));
        return mapper.vacationRequestJPAToVacationRequestDTO(vacationRequestPersistence.save(vacationRequestJPA));
    }

    public Page<VacationRequestJPA> showVacationRequestByEmployee(int id, Pageable paging) {
        return vacationRequestPersistence.findByEmployeeId(id, paging);
    }

    public VacationRequestDTO editVacationRequest(int id, VacationRequestStatus status) {
        VacationRequestJPA vacationRequest = vacationRequestPersistence.findById(id).orElseThrow(VacationRequestNotFound::new);
        vacationRequest.setStatus(status);
        return mapper.vacationRequestJPAToVacationRequestDTO(vacationRequestPersistence.save(vacationRequest));
    }
}
