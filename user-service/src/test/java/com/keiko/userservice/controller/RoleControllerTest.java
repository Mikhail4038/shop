package com.keiko.userservice.controller;

import com.keiko.userservice.dto.model.role.RoleDto;
import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.service.impl.DefaultRoleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.ROLE_BASE;
import static com.keiko.userservice.constants.WebResourceKeyConstants.FIND_ROLE_BY_NAME;
import static com.keiko.userservice.constants.WebResourceKeyConstants.GET_USER_ROLES;
import static com.keiko.userservice.util.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultRoleService roleService;

    @MockBean
    private Function<Role, RoleDto> toDtoConverter;

    @MockBean
    private Function<RoleDto, Role> toRoleConverter;

    private static Role role;
    private static RoleDto roleDto;
    private static User user;

    @BeforeAll
    static void setUp () {
        role = testRole ();
        user = testUser ();
        role.setUsers (Set.of (user));
        roleDto = testRoleDto ();
    }

    @Test
    void findRoleByName_should_successfully () throws Exception {
        when (roleService.findByName (role.getName ())).thenReturn (role);
        when (toDtoConverter.apply (role)).thenReturn (roleDto);

        mockMvc.perform (get (ROLE_BASE + FIND_ROLE_BY_NAME)
                .queryParam ("name", role.getName ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$.name", is (role.getName ())))
                .andExpect (status ().isOk ());

        verify (roleService, times (1)).findByName (anyString ());
        verify (toDtoConverter, times (1)).apply (any (Role.class));
    }

    @Test
    void findUserRolesByEmail_should_successfully () throws Exception {
        when (roleService.getUserRoles (user.getEmail ())).thenReturn (Arrays.asList (role));

        mockMvc.perform (get (ROLE_BASE + GET_USER_ROLES)
                .queryParam ("email", user.getEmail ())
                .contentType (APPLICATION_JSON_VALUE))
                .andExpect (jsonPath ("$", hasSize (1)))
                .andExpect (jsonPath ("$[0]", is (role.getName ())))
                .andExpect (status ().isOk ());

        verify (roleService, times (1)).getUserRoles (anyString ());
    }
}
