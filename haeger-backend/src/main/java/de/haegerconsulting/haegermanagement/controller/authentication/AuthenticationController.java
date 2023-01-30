package de.haegerconsulting.haegermanagement.controller.authentication;

import de.haegerconsulting.haegermanagement.business.loginUser.LoginUser;
import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserDTO;
import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserService;
import de.haegerconsulting.haegermanagement.configurations.security.JwtUtils;
import de.haegerconsulting.haegermanagement.configurations.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${application.crossOrigin}")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final LoginUserService loginUserService;

    @PostMapping
    public ResponseEntity<AuthenticationDTO> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());
        log.info(request);
        log.info(user);
        if (user != null) {
            LoginUser employee = loginUserService.getUserByUsername(user.getUsername());
            AuthenticationDTO authenticationDTO = new AuthenticationDTO(
                    employee.getUsername(),
                    jwtUtils.generateToken(user),
                    employee.getEmployee().getId(),
                    employee.getUserRole(),
                    employee.getEmployee().getFirstName(),
                    employee.getEmployee().getLastName(),
                    employee.getEmployee().getMail());
            return ResponseEntity.ok(authenticationDTO);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/admin/new")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginUserDTO createNewLoginUser(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        log.info(loginUserDTO);
        return loginUserService.createNewLoginUser(loginUserDTO);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteLoginUser(@PathVariable int id) {
        log.info("Delete Login User with ID: " + id);
        loginUserService.deleteLoginUser(id);
        return ResponseEntity.ok("User with ID " + id + " is deleted");
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<LoginUserDTO> showAllLoginUser() {
        return loginUserService.showAllLoginUser();
    }
}
