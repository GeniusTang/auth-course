package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private UserRepository userRepo = mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void create_user_happy_path() throws Exception {
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void find_by_id_happy_path() throws Exception {
        User u1 = new User();
        u1.setUsername("test");

        when(userRepo.findById(0L)).thenReturn(java.util.Optional.of(u1));

        final ResponseEntity<User> response = userController.findById(0L);
        assertEquals(200, response.getStatusCodeValue());

        User u2 = response.getBody();
        assertNotNull(u2);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(u1.getUsername(), u2.getUsername());
    }

    @Test
    public void find_by_userName_happy_path() throws Exception {
        User u1 = new User();
        u1.setUsername("test");

        when(userRepo.findByUsername("test")).thenReturn(u1);

        final ResponseEntity<User> response = userController.findByUserName("test");
        assertEquals(200, response.getStatusCodeValue());

        User u2 = response.getBody();
        assertNotNull(u2);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(u1.getUsername(), u2.getUsername());
    }
}
