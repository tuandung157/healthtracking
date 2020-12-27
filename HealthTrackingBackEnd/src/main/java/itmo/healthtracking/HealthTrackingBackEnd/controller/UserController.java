package itmo.healthtracking.HealthTrackingBackEnd.controller;


import itmo.healthtracking.HealthTrackingBackEnd.model.User;
import itmo.healthtracking.HealthTrackingBackEnd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("users/{userId}")
    public Optional<User> getUser(@PathVariable Long userId){
        return userRepository.findById(userId);
    }

    @PostMapping("/users/add")
    public User addUsers(@RequestBody User user){
//        System.out.println(user.toString());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userRepository.save(user);
        return user;
    }

    @PostMapping("users/login")
    public User login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println(username);
        Optional<User> optionalUsers = userRepository.findByUsername(username);
        if(optionalUsers.isEmpty()) return null;
        User userChecker = optionalUsers.get();
        return userChecker.getPassword().equals(password) ? user : null;
    }
}

