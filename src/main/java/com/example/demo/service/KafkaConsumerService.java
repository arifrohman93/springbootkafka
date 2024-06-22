package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(User user) {
        userRepository.save(user);
    }
}
