package com.tesquare.search.controller;

import com.tesquare.search.pojo.Article;
import com.tesquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return  new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{key}/{page}/{size}")
    public Result findByTitleOrContentLike(@PathVariable String key,@PathVariable int page,@PathVariable int size){
       Page<Article> pageData= articleService.findByTitleOrContentLike(key,page,size);
        return  new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(pageData.getTotalPages(),pageData.getContent()));
    }
}
