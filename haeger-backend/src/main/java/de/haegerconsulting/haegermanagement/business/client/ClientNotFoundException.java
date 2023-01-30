package de.haegerconsulting.haegermanagement.business.client;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException() {
        super("Client Not Found!");
    }
}
