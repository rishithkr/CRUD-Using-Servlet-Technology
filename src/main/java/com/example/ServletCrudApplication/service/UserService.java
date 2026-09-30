package com.example.ServletCrudApplication.service;

import com.example.ServletCrudApplication.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private Map<Integer, User> userDB;

    public UserService(){
        userDB  = new HashMap<>();
    }

    public User createUser(User userRequest) {
        userDB.put(userRequest.getId(), userRequest);
        return userRequest;
    }

    public User getUserById(Integer id) {
        return userDB.get(id);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        for(User user : userDB.values())
            userList.add(user);
        return userList;
    }
}
