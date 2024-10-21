package org.example.service;

import org.example.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void add(User user);

    void delete(long id);

    void edit(User user);

    User getById(long id);
}
