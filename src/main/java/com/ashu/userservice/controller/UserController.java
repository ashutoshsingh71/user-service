package com.ashu.userservice.controller;

import com.ashu.userservice.entity.User;
import com.ashu.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        User savedUser =  userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser(){
         List<User> usersList = userService.getAll();
         return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        User retrievedUser = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){
        User user = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/add")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
}
