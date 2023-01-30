package de.haegerconsulting.haegermanagement.business.vacationRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public record VacationRequestDTO(int id,
                                 @PositiveOrZero int employeeID,
                                 @NotNull LocalDate beginDate,
                                 @NotNull LocalDate endDate,
                                 float vacationDays,
                                 VacationRequestStatus status) {

}
