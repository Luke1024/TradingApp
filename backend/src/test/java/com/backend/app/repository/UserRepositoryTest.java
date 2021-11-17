package com.backend.app.repository;

import com.backend.app.domain.entity.AppUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.Query;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    private Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByTokenWhenTokenCorrect(){
        AppUser appUser = new AppUser();
        String token = generateToken();

        appUser.setToken(token);

        userRepository.save(appUser);

        Assert.assertTrue(userRepository.findByToken(token).isPresent());
    }

    @Test
    public void findByTokenWhenTokenNotCorrect(){
        AppUser appUser = new AppUser();
        String token = "special_token";

        appUser.setToken(generateToken());

        userRepository.save(appUser);

        Assert.assertFalse(userRepository.findByToken(token).isPresent());
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