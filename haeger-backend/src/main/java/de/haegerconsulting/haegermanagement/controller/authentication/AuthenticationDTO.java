package de.haegerconsulting.haegermanagement.controller.authentication;

import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {
    private String userName;
    private String token;
    private int employeeID;
    private LoginUserRoles roles;
    private String firstName;
    private String lastName;
    private String mail;

}
