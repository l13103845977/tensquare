package com.tensquare.qa.client;

import com.tensquare.qa.impl.UserClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(value = "tensqure-user",fallback = UserClientImpl.class)
public interface UserClient {

    @RequestMapping(value="/user/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
