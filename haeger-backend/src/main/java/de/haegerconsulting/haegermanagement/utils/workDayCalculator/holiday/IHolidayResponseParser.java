package de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IHolidayResponseParser {

    List<HolidayJPA> parseJsonToHolidayJPAList(String jsonInput);

}
