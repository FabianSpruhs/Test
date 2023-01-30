package de.haegerconsulting.haegermanagement.business.vacationRequest;

public class VacationRequestNotFound extends RuntimeException{

    public VacationRequestNotFound() {
        super("Vacation Request Not Found!");
    }
}
