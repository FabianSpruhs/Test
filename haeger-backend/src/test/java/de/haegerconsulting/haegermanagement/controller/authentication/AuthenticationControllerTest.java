package de.haegerconsulting.haegermanagement.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserDTO;
import de.haegerconsulting.haegermanagement.business.loginUser.LoginUserService;
import de.haegerconsulting.haegermanagement.configurations.security.JwtUtils;
import de.haegerconsulting.haegermanagement.configurations.security.MyBasicAuthenticationEntryPoint;
import de.haegerconsulting.haegermanagement.configurations.security.UserDetailsServiceImpl;
import de.haegerconsulting.haegermanagement.configurations.security.WebSecurityConfig;
import de.haegerconsulting.haegermanagement.persistence.LoginUserPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@Import({WebSecurityConfig.class, JwtUtils.class, UserDetailsServiceImpl.class})
class AuthenticationControllerTest {

    @MockBean
    MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @MockBean
    LoginUserPersistence loginUserPersistence;

    @MockBean
    LoginUserService loginUserService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void authenticate() {
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewLoginUser() throws Exception{
        LoginUserDTO loginUserDTO = new LoginUserDTO(0, "admin", "password", "ADMIN", 1);
        mockMvc.perform(post("/api/auth/admin/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDTO))).andExpect(status().isCreated());
        verify(loginUserService, times(1)).createNewLoginUser(loginUserDTO);

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteLoginUser() throws Exception {
        mockMvc.perform(delete("/api/auth/admin/1")).andExpect(status().isOk());
        verify(loginUserService, times(1)).deleteLoginUser(1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showAllLoginUser() throws Exception {

        mockMvc.perform(get("/api/auth/admin")).andExpect(status().isOk());
        verify(loginUserService, times(1)).showAllLoginUser();
    }
}