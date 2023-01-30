package de.haegerconsulting.haegermanagement.controller.authentication;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull
    private String userName;
    @NotNull
    private String password;

}
