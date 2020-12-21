package itmo.healthtracking.HealthTrackingBackEnd.controller;


import itmo.healthtracking.HealthTrackingBackEnd.model.Users;
import itmo.healthtracking.HealthTrackingBackEnd.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.*;

@RestController
@CrossOrigin
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("users/{userId}")
    public Optional<Users> getUser(@PathVariable Long userId){
        return usersRepository.findById(userId);
    }

    @PostMapping("/users/add")
    public Users addUsers(@RequestBody Users user){
        usersRepository.save(user);
        return user;
    }

    @PostMapping("users/login")
    public Users login(@RequestBody Users user){
        String username = user.getUsersName();
        String password = user.getPassword();
        System.out.println(username);
        Optional<Users> optionalUsers = usersRepository.findByUsersName(username);
        if(optionalUsers.isEmpty()) return null;
        Users userChecker = optionalUsers.get();
        return userChecker.getPassword().equals(password) ? user : null;
    }
}

