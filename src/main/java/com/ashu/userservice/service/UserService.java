package com.ashu.userservice.service;

import com.ashu.userservice.entity.User;

import java.util.List;

public interface UserService {
     User addUser(User user);
     List<User> getAll();
     User getUser(String userId);
     User deleteUser(String userId);
     User updateUser(User user);
}
