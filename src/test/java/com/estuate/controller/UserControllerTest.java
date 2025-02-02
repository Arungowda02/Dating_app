package com.estuate.controller;

import com.estuate.entity.User;
import com.estuate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    private User testUser1, testUser2, testUser3;

    @BeforeEach
    void setUp() {
        testUser1 = new User(1, "Alice", "female", 25, "Reading, Music");
        testUser2 = new User(2, "Bob", "male", 26, "Sports, Music");
        testUser3 = new User(3, "Charlie", "male", 24, "Gaming, Reading");
    }

    @Test
    void testRegisterPage_ShouldReturnRegisterView() {
        String view = userController.registerPage();
        assertEquals("register", view);
    }

    @Test
    void testIndexPage_ShouldReturnIndexView() {
        String view = userController.indexPage();
        assertEquals("index", view);
    }

    @Test
    void testSave_ShouldReturnRegisterView() {
        String view = userController.save(testUser1);
        verify(userService, times(1)).saveUser(testUser1);
        assertEquals("register", view);
    }

    @Test
    void testFindMatching_ShouldReturnIndexViewWithMatchingUsers() {
        List<User> matchingUsers = Arrays.asList(testUser2, testUser3);
        when(userService.findMatching(testUser1, 2)).thenReturn(matchingUsers);

        String view = userController.findMatching(testUser1, 2, model);

        verify(userService, times(1)).findMatching(testUser1, 2);
        verify(model, times(1)).addAttribute("matching", matchingUsers);
        assertEquals("index", view);
    }
}
