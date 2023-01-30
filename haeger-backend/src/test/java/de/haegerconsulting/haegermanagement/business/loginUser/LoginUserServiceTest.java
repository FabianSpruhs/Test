package de.haegerconsulting.haegermanagement.business.loginUser;

import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

    LoginUser loginUser = new LoginUser(0, "admin", "password", LoginUserRoles.ADMIN, null);
    LoginUserDTO loginUserDTO = new LoginUserDTO(0, "admin", "password", "ADMIN", 1);

    @Mock
    LoginUserPersistence loginUserPersistence;

    @Mock
    LoginUserMapper mapper;

    @InjectMocks
    LoginUserService loginUserService;


    @Test
    void createNewLoginUser() {
    }

    @Test
    void deleteLoginUser() {
        when(loginUserPersistence.existsById(1)).thenReturn(true);
        loginUserService.deleteLoginUser(1);
        verify(loginUserPersistence, times(1)).deleteById(1);
    }

    @Test
    void deleteLoginUserNotInDatabase() {
        when(loginUserPersistence.existsById(1)).thenReturn(false);
        assertThrows(LoginUserNotFoundException.class, () ->
                loginUserService.deleteLoginUser(1), "LoginUserNotFoundException was expected");
    }

    @Test
    void showAllLoginUser() {
        List<LoginUserDTO> expected = new LinkedList<>();
        expected.add(loginUserDTO);
        List<LoginUser> loginUsers = new LinkedList<>();
        loginUsers.add(loginUser);
        when(loginUserPersistence.findAll()).thenReturn(loginUsers);
        when(mapper.loginUserToLoginUserDTO(loginUser)).thenReturn(loginUserDTO);
        assertEquals(expected.toString(), loginUserService.showAllLoginUser().toString());
    }
}