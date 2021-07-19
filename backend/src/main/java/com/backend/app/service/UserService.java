package com.backend.app.service;

import com.backend.app.domain.entity.User;
import com.backend.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Random random = new Random();

    public String createUser(){
        String token = generateToken();
        User user = userRepository.save(new User(token, new ArrayList<>()));
        return user.getToken();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Optional<User> getUser(String token){
        return userRepository.findByToken(token);
    }

    private String generateToken(){
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 15;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
