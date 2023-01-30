package de.haegerconsulting.haegermanagement.utils;

import de.haegerconsulting.haegermanagement.business.holiday.HolidayService;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WorkDayCalculator;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WrongWorkDayCalculatingException;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.HolidayJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkDayCalculatorTest {

    List<HolidayJPA> holidays;

    @Mock
    HolidayService holidayService;

    @InjectMocks
    WorkDayCalculator workDayCalculator;

    @BeforeEach
    void setUp() {
        holidays = new LinkedList<>();
        holidays.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2022, 5, 2)));
        holidays.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2022, 3, 16)));
    }

    @Test
    void getWorkingDaysBetweenTwoDatesEndBeforeStart() {
        LocalDate start = LocalDate.of(2022, 11, 2);
        LocalDate end = LocalDate.of(2022, 11, 1);
        assertThrows(WrongWorkDayCalculatingException.class, () ->
                workDayCalculator.getWorkingDaysBetweenTwoDates(start, end), "WrongWorkDayCalculatingException was expected");
    }

    @Test
    void getWorkingDaysBetweenTwoDatesStartsOnWeekend() {
        LocalDate start = LocalDate.of(2022, 10, 30);
        LocalDate end = LocalDate.of(2022, 11, 1);
        assertThrows(WrongWorkDayCalculatingException.class, () ->
                workDayCalculator.getWorkingDaysBetweenTwoDates(start, end), "WrongWorkDayCalculatingException was expected");
    }

    @Test
    void getWorkingDaysBetweenTwoDatesStartsHoliday() {
        LocalDate start = LocalDate.of(2022, 5, 2);
        LocalDate end = LocalDate.of(2022, 5, 3);
        when(holidayService.getHolidays()).thenReturn(holidays);
        assertThrows(WrongWorkDayCalculatingException.class, () ->
                workDayCalculator.getWorkingDaysBetweenTwoDates(start, end), "WrongWorkDayCalculatingException was expected");
    }
    @Test
    void getWorkingDaysBetweenTwoDatesWithoutWeekend() {
        LocalDate start = LocalDate.of(2022, 11, 1);
        LocalDate end = LocalDate.of(2022, 11, 2);
        assertEquals(2, workDayCalculator.getWorkingDaysBetweenTwoDates(start, end));
    }

    @Test
    void getWorkingDaysBetweenTwoDatesSameDay() {
        LocalDate start = LocalDate.of(2022, 11, 1);
        LocalDate end = LocalDate.of(2022, 11, 1);
        assertEquals(1, workDayCalculator.getWorkingDaysBetweenTwoDates(start, end));
    }

    @Test
    void getWorkingDaysBetweenTwoDatesWithWeekend() {
        LocalDate start = LocalDate.of(2022, 11, 2);
        LocalDate end = LocalDate.of(2022, 11, 8);
        assertEquals(5, workDayCalculator.getWorkingDaysBetweenTwoDates(start, end));
    }

    @Test
    void getWorkingDaysBetweenTwoDatesWithHoliday() {
        LocalDate start = LocalDate.of(2022, 3, 15);
        LocalDate end = LocalDate.of(2022, 3, 17);
        when(holidayService.getHolidays()).thenReturn(holidays);
        assertEquals(2, workDayCalculator.getWorkingDaysBetweenTwoDates(start, end));
    }

}