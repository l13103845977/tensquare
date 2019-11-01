package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    public List<Comment> findByArticleid(String articleid){
        return  commentDao.findByArticleid(articleid);
    }
     public void deleteById(String commentid) {
        commentDao.deleteById(commentid);
    }
}
