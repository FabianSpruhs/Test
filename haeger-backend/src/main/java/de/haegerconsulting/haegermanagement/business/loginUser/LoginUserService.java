package de.haegerconsulting.haegermanagement.business.loginUser;

import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class LoginUserService {

    private final LoginUserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final LoginUserPersistence loginUserPersistence;

    public LoginUserDTO createNewLoginUser(LoginUserDTO loginUserDTO) {
        LoginUser loginUser = mapper.loginUserDTOToLoginUser(loginUserDTO);
        loginUser.setPassword(passwordEncoder.encode(loginUser.getPassword()));
        return mapper.loginUserToLoginUserDTO(loginUserPersistence.save(loginUser));
    }

    public void deleteLoginUser(int id) {
        if (!loginUserPersistence.existsById(id)) {
            throw new LoginUserNotFoundException();
        }
        loginUserPersistence.deleteById(id);
    }

    public List<LoginUserDTO> showAllLoginUser() {
        List<LoginUserDTO> result = new LinkedList<>();
        Iterable<LoginUser> loginUsers = loginUserPersistence.findAll();
        loginUsers.forEach(loginUser -> result.add(mapper.loginUserToLoginUserDTO(loginUser)));
        return result;
    }

    public LoginUser getUserByUsername(String username) {
        return loginUserPersistence.findByUsername(username).orElseThrow(LoginUserNotFoundException::new);
    }
}
