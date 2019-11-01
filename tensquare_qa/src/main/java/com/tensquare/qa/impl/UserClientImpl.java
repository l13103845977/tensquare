package com.tensquare.qa.impl;

import com.tensquare.qa.client.UserClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements UserClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"熔断器触发");
    }
}
