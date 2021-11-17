package com.backend.app.service;

import com.backend.app.domain.entity.AppUser;
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
        AppUser appUser = userRepository.save(new AppUser(token, new ArrayList<>()));
        return appUser.getToken();
    }

    public void saveUser(AppUser appUser){
        userRepository.save(appUser);
    }

    public Optional<AppUser> getUser(String token){
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
