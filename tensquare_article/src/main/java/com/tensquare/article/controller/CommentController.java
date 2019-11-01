package com.tensquare.article.controller;


import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
   @Autowired
   private IdWorker idWorker;

     @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
         comment.set_id(idWorker.nextId()+"");
       commentService.save(comment);
       return new Result(true, StatusCode.OK,"评论成功");
    }
    @RequestMapping(value = "/article/{articleid}",method = RequestMethod.GET)
    public  Result  findByArticleid(String articleid){
        return new Result(true, StatusCode.OK,"查询成功",
                commentService.findByArticleid(articleid));
    }
    @RequestMapping(value = "/article/{commentid}",method = RequestMethod.GET)
    public  Result  deleteById(String articleid){
         commentService.deleteById(articleid);
        return new Result(true, StatusCode.OK,"删除成功");
    }
}
