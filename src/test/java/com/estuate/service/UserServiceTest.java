package com.estuate.service;

import com.estuate.entity.User;
import com.estuate.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private User testUser1, testUser2, testUser3, testUser4;

    @BeforeEach
    void setUp() {
        testUser1 = new User(1, "Alice", "female", 25, "Reading, Music");
        testUser2 = new User(2, "Bob", "male", 26, "Sports, Music");
        testUser3 = new User(3, "Charlie", "male", 24, "Gaming, Reading");
        testUser4 = new User(4, "David", "male", 27, "Travel, Reading");
    }

    @Test
    void testGetUsers_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(testUser1, testUser2, testUser3);
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test
    void testSaveUser_ShouldReturnSavedUser() {
        when(userRepo.save(testUser1)).thenReturn(testUser1);

        User savedUser = userService.saveUser(testUser1);

        assertNotNull(savedUser);
        assertEquals("Alice", savedUser.getName());
        verify(userRepo, times(1)).save(testUser1);
    }

    @Test
    void testFindMatching_ShouldReturnSortedMatchingUsers() {
        List<User> users = Arrays.asList(testUser2, testUser3, testUser4);
        when(userRepo.findAll()).thenReturn(users);

        List<User> matchingUsers = userService.findMatching(testUser1, 2);

        assertEquals(2, matchingUsers.size());
        assertEquals("Charlie", matchingUsers.get(0).getName()); // More common interests
        assertEquals("Bob", matchingUsers.get(1).getName());
    }

    @Test
    void testFindMatching_ShouldReturnEmptyListWhenNoMatch() {
        when(userRepo.findAll()).thenReturn(List.of());

        List<User> matchingUsers = userService.findMatching(testUser1, 3);

        assertTrue(matchingUsers.isEmpty());
    }

    @Test
    void testFindMatching_ShouldLimitResults() {
        List<User> users = Arrays.asList(testUser2, testUser3, testUser4);
        when(userRepo.findAll()).thenReturn(users);

        List<User> matchingUsers = userService.findMatching(testUser1, 1);

        assertEquals(1, matchingUsers.size());
    }
}
