package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    boolean addUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(long id);

    User getUserByPrincipalName(String principalName);

    UserDetails loadUserByUsername(String username);
}
