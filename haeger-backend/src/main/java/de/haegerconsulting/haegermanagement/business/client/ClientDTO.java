package de.haegerconsulting.haegermanagement.business.client;

import de.haegerconsulting.haegermanagement.business.address.Address;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;



public record ClientDTO(int id,
                        @NotBlank String name,
                        @Valid @NotNull Address address,
                        @PositiveOrZero int contactEmployeeID) {

}
