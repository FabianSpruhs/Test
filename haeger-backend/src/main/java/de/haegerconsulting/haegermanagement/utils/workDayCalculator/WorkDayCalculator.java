package de.haegerconsulting.haegermanagement.utils.workDayCalculator;

import de.haegerconsulting.haegermanagement.business.holiday.HolidayService;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.HolidayJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkDayCalculator {

    public final HolidayService holidayService;

    public int getWorkingDaysBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        validateDateInput(startDate, endDate);
        return calculateWorkDays(startDate, endDate);
    }

    private int calculateWorkDays(LocalDate startDate, LocalDate endDate) {
        int result = 1;
        while (!startDate.equals(endDate)) {
            startDate = startDate.plusDays(1);
            if (!isWeekend(startDate) && !isHoliday(startDate)) {
                result++;
            }
        }
        return result;
    }

    public void validateDateInput(LocalDate startDate, LocalDate endDate) {
        startDateIsAfterEndDate(startDate, endDate);
        startOrEndDateIsOnWeekend(startDate, endDate);
        startOrEndDateIsOnHoliday(startDate, endDate);
    }

    private void startOrEndDateIsOnHoliday(LocalDate startDate, LocalDate endDate) {
        if (isHoliday(startDate) || isHoliday(endDate)) {
            throw new WrongWorkDayCalculatingException("Start Date or End Date in on a Holiday!");
        }
    }

    private boolean isHoliday(LocalDate date) {
        List<HolidayJPA> holidays = holidayService.getHolidays();
        return holidays.stream().anyMatch(holidayJPA -> holidayJPA.getDate().isEqual(date));
    }

    private void startOrEndDateIsOnWeekend(LocalDate startDate, LocalDate endDate) {
        if (isWeekend(startDate) || isWeekend(endDate)) {
            throw new WrongWorkDayCalculatingException("Start Date or End Date is on a Weekend!");
        }
    }

    private void startDateIsAfterEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new WrongWorkDayCalculatingException("Start date ist after End Date!");
        }
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
