package com.tesquare.search.service;

import com.tesquare.search.dao.ArticleDao;
import com.tesquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    //@Autowired
    //private IdWorker idWorker;

    public void save(Article article){
        articleDao.save(article);
    }

    public Page<Article> findByTitleOrContentLike(String key,int page,int size){
        Pageable pageable= PageRequest.of(page-1,size);
       Page<Article> pageData= articleDao.findByTitleOrContentLike(key,key,pageable);
       return  pageData;
    }
}

