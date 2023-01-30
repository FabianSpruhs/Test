package de.haegerconsulting.haegermanagement.business.holiday;

import de.haegerconsulting.haegermanagement.persistence.HolidayPersistence;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.HolidayJPA;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.IHolidayDataFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

    List<HolidayJPA> holidays;

    @Mock
    HolidayPersistence holidayPersistence;

    @Mock
    IHolidayDataFetcher holidayDataFetcher;

    @InjectMocks
    HolidayService holidayService;

    @BeforeEach
    void setUp() {
        holidays = new LinkedList<>();
        holidays.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2023, 5, 2)));
        holidays.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2023, 3, 16)));
    }

    @Test
    void getHolidaysEmptyCache() {
        holidays.add(new HolidayJPA(0, "Test Day 3", LocalDate.of(2024, 1, 1)));
        when(holidayPersistence.findAll()).thenReturn(holidays);
        assertEquals(holidays, holidayService.getHolidays());
        verify(holidayDataFetcher, times(0)).readHolidayFromAPI("2022");
    }

    @Test
    void readFromApiWhenNotActual() {
        List<HolidayJPA> nextYear = new LinkedList<>();
        nextYear.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2024, 5, 2)));
        nextYear.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2024, 3, 16)));
        List<HolidayJPA> expected = new LinkedList<>();
        expected.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2023, 5, 2)));
        expected.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2023, 3, 16)));
        expected.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2024, 5, 2)));
        expected.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2024, 3, 16)));
        when(holidayPersistence.findAll()).thenReturn(holidays);
        when(holidayDataFetcher.readHolidayFromAPI("2023")).thenReturn(holidays);
        when(holidayDataFetcher.readHolidayFromAPI("2024")).thenReturn(nextYear);
        assertEquals(expected, holidayService.getHolidays());
    }

    @Test
    void readFromApiWhenVeryOldData() {
        List<HolidayJPA> oldData = new LinkedList<>();
        oldData.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2024, 5, 2)));
        List<HolidayJPA> nextYear = new LinkedList<>();
        nextYear.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2024, 5, 2)));
        nextYear.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2024, 3, 16)));
        List<HolidayJPA> expected = new LinkedList<>();
        expected.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2023, 5, 2)));
        expected.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2023, 3, 16)));
        expected.add(new HolidayJPA(0, "Test Day 1", LocalDate.of(2024, 5, 2)));
        expected.add(new HolidayJPA(0, "Test Day 2", LocalDate.of(2024, 3, 16)));
        when(holidayPersistence.findAll()).thenReturn(oldData);
        when(holidayDataFetcher.readHolidayFromAPI("2023")).thenReturn(holidays);
        when(holidayDataFetcher.readHolidayFromAPI("2024")).thenReturn(nextYear);
        assertEquals(expected, holidayService.getHolidays());
    }

    @Test
    void exceptionWhenApiSendsWrongData() {
        when(holidayPersistence.findAll()).thenReturn(holidays);
        when(holidayDataFetcher.readHolidayFromAPI("2023")).thenReturn(holidays);
        when(holidayDataFetcher.readHolidayFromAPI("2024")).thenReturn(new LinkedList<>());
        assertThrows(CouldNotLoadHolidaysException.class, () ->
                holidayService.getHolidays(), "CouldNotLoadHolidaysException was expected");
    }
}