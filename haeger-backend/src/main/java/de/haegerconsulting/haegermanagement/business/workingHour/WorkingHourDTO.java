package de.haegerconsulting.haegermanagement.business.workingHour;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public record WorkingHourDTO(int id,
                            @PositiveOrZero int employeeID,
                            @NotNull LocalDate workingDay,
                            @PositiveOrZero float workingHours,
                             WorkingHourStatus workingHourStatus) {



}
