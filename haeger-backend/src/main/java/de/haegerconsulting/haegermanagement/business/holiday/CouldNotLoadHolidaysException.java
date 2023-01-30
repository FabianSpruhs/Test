package de.haegerconsulting.haegermanagement.business.holiday;

public class CouldNotLoadHolidaysException extends RuntimeException {
    public CouldNotLoadHolidaysException() {
        super("Could Not Read Data From API!");
    }
}
