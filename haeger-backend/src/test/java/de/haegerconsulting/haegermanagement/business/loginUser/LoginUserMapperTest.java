package de.haegerconsulting.haegermanagement.business.loginUser;

import de.haegerconsulting.haegermanagement.business.employee.EmployeeJPA;
import de.haegerconsulting.haegermanagement.business.employee.EmployeeService;
import de.haegerconsulting.haegermanagement.business.sickNote.SickNoteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserMapperTest {

    EmployeeJPA employeeJPA = new EmployeeJPA(1, "Test", "Test", null, null, null, null, null, 0.0F);
    LoginUser loginUser = new LoginUser(0, "admin", "password", LoginUserRoles.ADMIN, employeeJPA);
    LoginUserDTO loginUserDTO = new LoginUserDTO(0, "admin", "password", "ADMIN", 1);

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    LoginUserMapper loginUserMapper = Mappers.getMapper(LoginUserMapper.class);

    @Test
    void loginUserToLoginUserDTO() {
        assertEquals(loginUserDTO, loginUserMapper.loginUserToLoginUserDTO(loginUser));
    }

    @Test
    void loginUserDTOToLoginUser() {
        when(employeeService.showEmployee(1)).thenReturn(employeeJPA);
        assertEquals(loginUser.toString(), loginUserMapper.loginUserDTOToLoginUser(loginUserDTO).toString());
    }
}