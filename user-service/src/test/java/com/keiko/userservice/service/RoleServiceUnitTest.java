package com.keiko.userservice.service;

import com.keiko.userservice.entity.Role;
import com.keiko.userservice.entity.User;
import com.keiko.userservice.repository.RoleRepository;
import com.keiko.userservice.service.impl.DefaultRoleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.keiko.userservice.util.TestData.createTestRole;
import static com.keiko.userservice.util.TestData.createTestUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class RoleServiceUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private static RoleService roleService;

    private static Role role;
    private static User user;

    @BeforeAll
    static void setUp () {
        roleService = new DefaultRoleService ();
        role = createTestRole ();
        user = createTestUser ();
        role.setUsers (Set.of (user));
    }

    @Test
    void should_successfully_findByName () {
        final String name = role.getName ();
        when (roleRepository.findByName (name)).thenReturn (Optional.of (role));
        Role result = roleService.findByName (name);
        assertEquals (result, role);
        verify (roleRepository, times (1)).findByName (anyString ());
        verifyNoMoreInteractions (roleRepository);
    }

    @Test
    void should_successfully_findRolesByUserEmail () {
        final String email = user.getEmail ();
        when (roleRepository.findByUsers_email (email)).thenReturn (Arrays.asList (role));
        List<Role> roles = roleService.getUserRoles (email);
        assertFalse (roles.isEmpty ());
        assertTrue (roles.size () == 1);
        assertTrue (roles.contains (role));
        verify (roleRepository, times (1)).findByUsers_email (anyString ());
        verifyNoMoreInteractions (roleRepository);
    }
}
