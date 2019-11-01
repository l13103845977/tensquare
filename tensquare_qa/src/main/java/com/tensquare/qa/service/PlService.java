package com.tensquare.qa.service;

import com.tensquare.qa.dao.PlDao;
import com.tensquare.qa.pojo.Pl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PlService {

    @Autowired
    private PlDao plDao;



    public void add(String pid, String lid) {
        plDao.save(new Pl(pid,lid));
    }
}
