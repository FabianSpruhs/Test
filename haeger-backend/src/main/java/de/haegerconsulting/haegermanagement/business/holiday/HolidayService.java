package de.haegerconsulting.haegermanagement.business.holiday;

import de.haegerconsulting.haegermanagement.persistence.HolidayPersistence;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.HolidayJPA;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.IHolidayDataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Log4j2
public class HolidayService {

    private final HolidayPersistence holidayPersistence;
    private final IHolidayDataFetcher holidayDataFetcher;
    private List<HolidayJPA> holidayCache = new LinkedList<>();


    @Transactional
    protected void saveHolidays(List<HolidayJPA> holidayList) {
        holidayPersistence.deleteAll();
        holidayPersistence.saveAll(holidayList);
    }

    public List<HolidayJPA> getHolidays() {
        readFromDatabaseWhenCacheIsEmpty();
        readFromApiWhenNotActual();
        return holidayCache;
    }

    private void readFromApiWhenNotActual() {
        if (holidayIsNotActual()) {
            log.info("Holiday List not Actual.");
            readHolidayDataFromApi();
            saveHolidays(holidayCache);
        }
    }

    private void readFromDatabaseWhenCacheIsEmpty() {
        if (holidayCache.isEmpty()) {
            holidayCache = StreamSupport.stream(holidayPersistence.findAll().spliterator(), false).toList();
            log.info("Load Holiday List From Database.");
        }
    }

    private void readHolidayDataFromApi() {
        List<HolidayJPA> actualYear = holidayDataFetcher.readHolidayFromAPI(String.valueOf(getActualYear()));
        List<HolidayJPA> nextYear = holidayDataFetcher.readHolidayFromAPI(String.valueOf(getActualYear() + 1));
        if (actualYear.isEmpty() || nextYear.isEmpty()) {
            throw new CouldNotLoadHolidaysException();
        }
        holidayCache = Stream.concat(actualYear.stream(), nextYear.stream()).toList();
    }

    private int getActualYear() {
        return LocalDate.now().getYear();
    }

    private boolean holidayIsNotActual() {
        return !(cacheContainsYear(getActualYear()) && cacheContainsYear(getActualYear() + 1));
    }

    private boolean cacheContainsYear(int year) {
        return holidayCache.stream().anyMatch(holidayJPA -> holidayJPA.getDate().getYear() == year);
    }

}
