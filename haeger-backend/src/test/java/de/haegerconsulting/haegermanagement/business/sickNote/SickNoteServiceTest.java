package de.haegerconsulting.haegermanagement.business.sickNote;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.persistence.SickNotePersistence;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WorkDayCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SickNoteServiceTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "Test", "Test", null, null, null, null, null, 0.0F);
    SickNoteJPA sickNoteJPA = new SickNoteJPA(0, employeeJPA, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2));
    SickNoteDTO sickNoteDTO = new SickNoteDTO(0, 1, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 2));

    @Mock
    SickNotePersistence sickNotePersistence;

    @Mock
    SickNoteMapper mapper;

    @Mock
    WorkDayCalculator workDayCalculator;

    @InjectMocks
    SickNoteService sickNoteService;

    @Test
    void submitSickNote() throws EmployeeNotFoundException {
        when(mapper.sickNoteDTOToSickNoteJPA(sickNoteDTO)).thenReturn(sickNoteJPA);
        sickNoteService.submitSickNote(sickNoteDTO);
        verify(sickNotePersistence, times(1)).save(sickNoteJPA);
    }

    @Test
    void showAllSickNotes() {
        List<SickNoteJPA> expected = new ArrayList<>();
        expected.add(sickNoteJPA);
        Pageable paging = PageRequest.of(0, 1);
        Page<SickNoteJPA> page = new PageImpl<>(expected);

        when(sickNotePersistence.findAll(paging)).thenReturn(page);
        assertEquals(expected, sickNoteService.showAllSickNotes(paging).getContent());
    }
}