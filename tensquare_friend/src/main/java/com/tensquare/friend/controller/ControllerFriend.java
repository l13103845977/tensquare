package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class ControllerFriend {

    @Resource
    private HttpServletRequest request;

    @Resource
    private FriendService friendService;
    @Autowired
    private UserClient userClient;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
     //判断是否登录，拿ID，user可以，admi不能加好友
           Claims claims= (Claims) request.getAttribute("claims_user");
           if (claims==null ){
               return new Result(false, StatusCode.ERROR,"请登录用户哦，亲");
           }
        String userid=claims.getId();

        //判断是否喜欢 1
        if (type!=null){
            if (type.equals("1")){
               //添加好友
               int  flag=friendService.addFriend(userid,friendid);
                System.out.println(flag+"添加好友");
               if (flag==0){
                   return new Result(false, StatusCode.ERROR,"矜持(添加一次就够了)");
               }
                if (flag==1){
                    System.out.println("userClient.updatefanscountandfollowcount()");
                    userClient.updatefanscountandfollowcount(userid,friendid,1);
                    return new Result(true, StatusCode.OK,"添加成功（喜欢）");
                }
            }else if (type.equals("2")){
                //添加非好友
               int flag= friendService.addNoFriend(userid,friendid);
                {
                    if (flag==0){
                        return new Result(false, StatusCode.ERROR,"不能重复添加非好友");
                    }
                    if (flag==1){
                        return new Result(true, StatusCode.OK,"添加成功（不喜欢）");
                    }
                }
            }
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false, StatusCode.ERROR,"参数异常");
        }
    }


    /**
     * 删除好友
     *
     */

    @DeleteMapping("/{friendid}")
    public  Result deleteFriend(@PathVariable String friendid){
        Claims claims= (Claims) request.getAttribute("claims_user");
        if (claims==null ){
            return new Result(false, StatusCode.ERROR,"权限不足");
        }
        String userid=claims.getId();
        System.out.println("删除好友的时候userid---"+userid+"//////好友id"+friendid);
        friendService.deleteFriend(userid,friendid);
        userClient.updatefanscountandfollowcount(userid,friendid,-1);
        return  new Result(true,StatusCode.OK,"好友删除成功");
    }
}
