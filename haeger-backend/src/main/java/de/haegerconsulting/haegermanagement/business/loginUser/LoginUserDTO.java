package de.haegerconsulting.haegermanagement.business.loginUser;

import javax.validation.constraints.NotNull;

public record LoginUserDTO(int id,
                           @NotNull String username,
                           @NotNull String password,
                           @NotNull String userRole,
                           int employeeID) {
}
