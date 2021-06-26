package com.backend.app.service;

import com.backend.app.domain.dto.TradingStateDto;
import org.springframework.stereotype.Service;

@Service
public class MiscService {

    public String getToken(){
        return "";
    }

    public TradingStateDto getTradingState(){
        return new TradingStateDto();
    }
}
