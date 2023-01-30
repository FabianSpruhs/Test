package de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday;

import java.util.List;

public interface IHolidayDataFetcher {

    List<HolidayJPA> readHolidayFromAPI(String year);

}
