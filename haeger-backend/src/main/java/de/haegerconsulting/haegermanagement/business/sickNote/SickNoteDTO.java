package de.haegerconsulting.haegermanagement.business.sickNote;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;


public record SickNoteDTO(int id,
                          @PositiveOrZero int employeeId,
                          @NotNull LocalDate beginDate,
                          @NotNull LocalDate endDate) {


}
