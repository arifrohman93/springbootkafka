package com.example.demo.service;

import com.example.demo.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "users";

    @Autowired
    private KafkaTemplate<String, UserDTO> kafkaTemplate;

    public void sendMessage(UserDTO user) {
        kafkaTemplate.send(TOPIC, user);
    }
}
