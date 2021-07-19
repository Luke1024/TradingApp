package com.backend.app.service;

import com.backend.app.domain.dto.StringDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MiscService {

    @Autowired
    private UserService userService;

    public StringDto getToken(){
        return new StringDto(userService.createUser());
    }
}
