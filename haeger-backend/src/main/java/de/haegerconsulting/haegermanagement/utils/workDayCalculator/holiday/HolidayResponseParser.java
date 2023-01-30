package de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
@Log4j2
public class HolidayResponseParser implements IHolidayResponseParser {

    @Override
    public List<HolidayJPA> parseJsonToHolidayJPAList(String jsonInput) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return executeMapping(jsonInput, objectMapper);
    }

    private List<HolidayJPA> executeMapping(String jsonInput, ObjectMapper objectMapper) {
        HolidayJPA[] result;
        try {
            JsonNode node = objectMapper.readTree(jsonInput);
            result = objectMapper.readValue(String.valueOf(node.get("feiertage")), HolidayJPA[].class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return new LinkedList<>();
        }
        return Arrays.asList(result);
    }

}
