package com.estuate.service;

import com.estuate.entity.User;
import com.estuate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findMatching(User user, int limit) {

        List<User> userList = userRepo.findAll().stream().filter(u -> !user.getGender().equals(u.getGender()))
                .sorted(Comparator.comparingInt(u -> Math.abs(u.getAge() - user.getAge()))).limit(5)
                .toList();

        return userList.stream().sorted(Comparator.comparingInt((User u) -> commonInterestsCount(convertSet(u.getInterest()), convertSet(user.getInterest())))
                .reversed()).limit(limit).toList();

    }

    private Set<String> convertSet(String interest) {
        return Arrays.stream(interest.split(",")).map(String::trim).collect(Collectors.toSet());
    }

    private int commonInterestsCount(Set<String> user1, Set<String> user2) {
        return (int) user1.stream().filter(user2::contains).count();
    }


}
