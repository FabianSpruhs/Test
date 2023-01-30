package de.haegerconsulting.haegermanagement.business.loginUser;

public class LoginUserNotFoundException extends RuntimeException {

    public LoginUserNotFoundException() {
        super("User not Found!");
    }
}
