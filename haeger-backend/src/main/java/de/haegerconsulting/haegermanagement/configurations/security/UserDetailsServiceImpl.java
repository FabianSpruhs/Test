package de.haegerconsulting.haegermanagement.configurations.security;

import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserNotFoundException;
import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import de.haegerconsulting.haegermanagement.business.loginUser.LoginUser;

import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private LoginUserPersistence userPersistence;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = userPersistence.findByUsername(username).orElseThrow(LoginUserNotFoundException::new);
        UserBuilder builder = User.builder();
        return builder.username(loginUser.getUsername()).password(loginUser.getPassword()).roles(loginUser.getUserRole().toString()).build();
    }

}
