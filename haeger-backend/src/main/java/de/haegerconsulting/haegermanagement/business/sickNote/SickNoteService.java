package de.haegerconsulting.haegermanagement.business.sickNote;

import de.haegerconsulting.haegermanagement.persistence.SickNotePersistence;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WorkDayCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class SickNoteService {

    private final SickNoteMapper mapper;
    private final SickNotePersistence sickNotePersistence;
    private final WorkDayCalculator workDayCalculator;

    public SickNoteDTO submitSickNote(SickNoteDTO sickNote) {
        workDayCalculator.validateDateInput(sickNote.beginDate(), sickNote.endDate());
        return mapper.sickNoteJPAToSickNoteDTO(sickNotePersistence.save(mapper.sickNoteDTOToSickNoteJPA(sickNote)));
    }

    public Page<SickNoteJPA> showAllSickNotes(Pageable paging) {
        return sickNotePersistence.findAll(paging);
    }
}
