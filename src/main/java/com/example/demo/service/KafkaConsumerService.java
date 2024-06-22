package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private UserService userService;

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(UserDTO userDTO) {
        if ("create".equalsIgnoreCase(userDTO.getMethod())){
            User user = new User();
            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            userService.saveUser(user);
            logger.info("user saved");
        } else if ("delete".equalsIgnoreCase(userDTO.getMethod())){
            userService.deleteUser(userDTO.getId());
            logger.info("user deleted");
        } else if ("update".equalsIgnoreCase(userDTO.getMethod())){
            User user = userService.getUserById(userDTO.getId());
            if(user!=null){
                user.setName(userDTO.getName());
                user.setEmail(userDTO.getEmail());
                userService.saveUser(user);
            }
            logger.info("user updated : \n" + user);

        } else {
            User user = userService.getUserById(userDTO.getId());
            logger.info("user read : \n" + user);
        }

    }
}
