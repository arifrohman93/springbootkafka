package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import com.example.demo.service.KafkaProducerService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        kafkaProducerService.sendMessage(userDTO);
        return userDTO;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setMethod("read");
        kafkaProducerService.sendMessage(userDTO);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setMethod("delete");
        kafkaProducerService.sendMessage(userDTO);
        return "Success delete";
    }

    @PutMapping
    public String updateUser(@RequestBody UserDTO userDTO) {
        kafkaProducerService.sendMessage(userDTO);
        return "Success update";
    }
}
