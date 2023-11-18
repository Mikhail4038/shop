package com.keiko.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keiko.userservice.dto.model.user.UserDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.request.ModifyUserRolesRequest;
import com.keiko.userservice.service.impl.DefaultUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

import static com.keiko.userservice.constants.WebResourceKeyConstants.*;
import static com.keiko.userservice.util.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultUserService userService;

    @MockBean
    private Function<User, UserDto> toDtoConverter;

    @MockBean
    private Function<UserDto, User> toUserConverter;

    @Autowired
    private ObjectMapper objectMapper;

    private static User user;
    private static UserDto userDto;
    private static Role role;

    @BeforeAll
    static void setUp () {
        user = createTestUser ();
        userDto = createTestUserDto ();
        role = createTestRole ();
        user.setRoles (Set.of (role));
    }

    @Test
    void findUserByEmail_should_successfully () throws Exception {
        when (userService.findByEmail (user.getEmail ())).thenReturn (user);
        when (toDtoConverter.apply (user)).thenReturn (userDto);

        mockMvc.perform (get (USER_BASE + FIND_USER_BY_EMAIL)
                .queryParam ("email", user.getEmail ())
                .contentType ("application/json"))
                .andExpect (jsonPath ("$.email", is (user.getEmail ())))
                .andExpect (jsonPath ("$.password", is (user.getPassword ())))
                .andExpect (jsonPath ("$.name", is (user.getName ())))
                .andExpect (status ().isOk ());

        verify (userService, times (1)).findByEmail (anyString ());
        verifyNoMoreInteractions (userService);
        verify (toDtoConverter, times (1)).apply (any (User.class));
        verifyNoMoreInteractions (toDtoConverter);
    }

    @Test
    void deleteUserByEmail_should_successfully () throws Exception {

        mockMvc.perform (delete (USER_BASE + DELETE_USER_BY_EMAIL)
                .queryParam ("email", user.getEmail ())
                .contentType ("application/json"))
                .andExpect (status ().isOk ());

        verify (userService, times (1)).deleteByEmail (anyString ());
        verifyNoMoreInteractions (userService);
    }

    @Test
    void findUsersByRoleName_should_successfully_ () throws Exception {
        when (userService.findByRole (role.getName ())).thenReturn (Arrays.asList (user));

        mockMvc.perform (get (USER_BASE + FIND_USERS_BY_ROLE)
                .queryParam ("role", role.getName ())
                .contentType ("application/json"))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0]", is (user.getEmail ())))
                .andExpect (status ().isOk ());

        verify (userService, times (1)).findByRole (anyString ());
        verifyNoMoreInteractions (userService);
    }

    @Test
    void addUserRoles_should_successfully () throws Exception {
        ModifyUserRolesRequest request = new ModifyUserRolesRequest (1L, Set.of (1L));
        mockMvc.perform (post (USER_BASE + ADD_ROLES)
                .content (objectMapper.writeValueAsString (request))
                .contentType ("application/json"))
                .andExpect (status ().isOk ());

        verify (userService, times (1)).addRoles (any (ModifyUserRolesRequest.class));
        verifyNoMoreInteractions (userService);
    }

    @Test
    void deleteUserRoles_should_successfully () throws Exception {
        ModifyUserRolesRequest request = new ModifyUserRolesRequest (1L, Set.of (1L));
        mockMvc.perform (post (USER_BASE + DELETE_ROLES)
                .content (objectMapper.writeValueAsString (request))
                .contentType ("application/json"))
                .andExpect (status ().isOk ());

        verify (userService, times (1)).deleteRoles (any (ModifyUserRolesRequest.class));
        verifyNoMoreInteractions (userService);
    }
}
