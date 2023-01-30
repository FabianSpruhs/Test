package de.haegerconsulting.haegermanagement.business.workingHour;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkingHourNotFoundException extends RuntimeException {

    public WorkingHourNotFoundException() {
        super("Working Hour Not Found!");
    }
}
