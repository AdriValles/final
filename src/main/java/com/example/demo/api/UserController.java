package com.example.demo.api;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.api.request.UserCreationRequest;
import com.example.demo.models.User;
import com.example.demo.services.UserService;



@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestAttribute UserCreationRequest userCreationRequest){
        return userService.createUser(userCreationRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.removeUser(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id).orElse(null);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
