package de.haegerconsulting.haegermanagement.business.employee;

import de.haegerconsulting.haegermanagement.business.address.Address;

import javax.validation.Valid;
import javax.validation.constraints.*;


public record EmployeeDTO(int id,
                          @NotBlank String firstName,
                          @NotBlank String lastName,
                          @Valid Address address,
                          @Email String mail,
                          String telephoneNumber,
                          @PositiveOrZero float scheduledVacationDays) {

}
