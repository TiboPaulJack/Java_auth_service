package com.authentication.service.authentication;

import com.authentication.service.authentication.controller.UserController;
import com.authentication.service.authentication.model.User;
import com.authentication.service.authentication.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    // Test for the signUp method
    @Test
    public void testSignUp() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.saveUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.signUp(user);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("test", response.getBody().getUsername());
    }

    // Test for the signUp method when the saveUser method throws an exception
    @Test
    public void testSignUpWhenSaveUserThrowsException() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.saveUser(any(User.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> userController.signUp(user));
    }

    // Test for the getAllUsers method
    @Test
    public void testGetAllUsers() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("test", response.getBody().get(0).getUsername());
    }

    // Test for the getUserById method
    @Test
    public void testGetUserById() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.getUserById("1")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById("1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", response.getBody().getUsername());
    }

    // Test for the getUserById method when the user does not exist
    @Test
    public void testGetUserByIdWhenUserDoesNotExist() {
        when(userService.getUserById("1")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById("1");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for the updateUser method
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.getUserById("1")).thenReturn(Optional.of(user));
        when(userService.updateUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser("1", user);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test", response.getBody().getUsername());
    }

    // Test for the updateUser method when the user does not exist
    @Test
    public void testUpdateUserWhenUserDoesNotExist() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.getUserById("1")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.updateUser("1", user);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Test for the deleteUser method
    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        when(userService.getUserById("1")).thenReturn(Optional.of(user));

        ResponseEntity<Void> response = userController.deleteUser("1");

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser("1");
    }

    // Test for the deleteUser method when the user does not exist
    @Test
    public void testDeleteUserWhenUserDoesNotExist() {
        when(userService.getUserById("1")).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userController.deleteUser("1");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
