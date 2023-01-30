package de.haegerconsulting.haegermanagement.controller.exceptionHandler;

import de.haegerconsulting.haegermanagement.business.client.ClientNotFoundException;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeNotFoundException;
import de.haegerconsulting.haegermanagement.business.holiday.CouldNotLoadHolidaysException;
import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserNotFoundException;
import de.haegerconsulting.haegermanagement.business.project.ProjectNotFoundException;
import de.haegerconsulting.haegermanagement.business.vacationRequest.VacationRequestNotFound;
import de.haegerconsulting.haegermanagement.business.workingHour.WorkingHourNotFoundException;
import de.haegerconsulting.haegermanagement.utils.workDayCalculator.WrongWorkDayCalculatingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    protected ResponseEntity<Object> clientNotFound(ClientNotFoundException e, WebRequest request) {
       log.error(e.getMessage());
       return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    protected ResponseEntity<Object> employeeNotFound(EmployeeNotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = ProjectNotFoundException.class)
    protected ResponseEntity<Object> projectNotFound(ProjectNotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = VacationRequestNotFound.class)
    protected ResponseEntity<Object> vacationRequestNotFound(VacationRequestNotFound e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = WorkingHourNotFoundException.class)
    protected ResponseEntity<Object> workingHourNotFound(WorkingHourNotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = WrongWorkDayCalculatingException.class)
    protected ResponseEntity<Object> workingHourNotFound(WrongWorkDayCalculatingException e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = CouldNotLoadHolidaysException.class)
    protected ResponseEntity<Object> couldNotLoadHoliday(CouldNotLoadHolidaysException e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = LoginUserNotFoundException.class)
    protected ResponseEntity<Object> loginUserNotFound(LoginUserNotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
