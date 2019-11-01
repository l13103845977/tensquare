//package com.tensquare.qa.controller;
//
//import com.tensquare.qa.client.BaseClient;
//import com.tensquare.qa.pojo.Pl;
//import com.tensquare.qa.pojo.Problem;
//import com.tensquare.qa.service.PlService;
//import com.tensquare.qa.service.ProblemService;
//import entity.Result;
//import entity.StatusCode;
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import util.JwtUtil;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/pl")
//public class PlController {
//
//
//    @Autowired
//    private PlService plService;
//
//    @Autowired
//    private HttpServletRequest request;
//    @Resource
//    private BaseClient baseClient;
//
//    @Resource
//    private JwtUtil jwtUtil;
//
//    @RequestMapping(value="/{pid}/{lid}",method= RequestMethod.POST)
//    public Result add( @PathVariable("pid") String pid,@PathVariable("lid") String lid ){
//        System.out.println("问题增加标签");
//        plService.add(pid,lid);
//        return new Result(true,StatusCode.OK,"增加成功");
//    }
//}
