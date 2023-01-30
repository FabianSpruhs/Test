package de.haegerconsulting.haegermanagement.business.project;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;


public record ProjectDTO(int id,
                        @PositiveOrZero int clientID,
                        @NotBlank String name) {

}
