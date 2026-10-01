package com.example.ServletCrudApplication.service;

import com.example.ServletCrudApplication.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final Map<Integer, User> userDB;

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
        List<User> userList = new ArrayList<>();
        for(User user : userDB.values())
            userList.add(user);
        return userList;
    }

    public User deleteUser(Integer id) {
        return userDB.remove(id);

    }

    public User updateUser(Integer id, String name, String email, String mobile) {
        User user = new User(userDB.get(id));
        if(name != null)
            user.setName(name);
        if(email != null)
            user.setEmail(email);
        if(mobile != null)
            user.setMobile(mobile);
         return userDB.replace(id,user);

    }
}
