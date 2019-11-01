package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Spit> findAll(){
        return  spitDao.findAll();
    }

    public Spit findById(String id){
        System.out.println(spitDao.findById(id).get()+"根据id查spit");
        return  spitDao.findById(id).get();

    }

    public void save(Spit spit){
       spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime( new SimpleDateFormat("yy/MM/dd HH:mm").format(new Date())); //发布日
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        if (spit.getParentid()!=null&& !"".equals(spit.getParentid())){
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
       spitDao.save(spit);
    }

    public void update(Spit spit){
      spitDao.save(spit);
    }
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentid,int page,int size){
        Pageable pageable= PageRequest.of(page-1,size);
        System.out.println(pageable+"pageable");
         return  spitDao.findByParentid(parentid,pageable);
    }

    public void thumbup(String spitId) {
        //一条点赞，数据库交互两次，效率太低
//        Spit spit= spitDao.findById(spitId).get();
//        spit.setThumbup((spit.getThumbup()==null?0:spit.getThumbup())+1);
//        spitDao.save(spit);
 Query query=new Query();
 query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
     mongoTemplate.updateFirst(query,update,"spit");
    }

    //分享
    public void share(String spitId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("share",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    public void visits(String spitId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("visits",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

//    public Page<Spit> findSearch(int page, int size) {
//        Pageable pageable =  PageRequest.of(page-1, size);
//
//        return spitDao.findAll(pageable);
//    }
    public Page<Spit> findSearch(int page, int size) {
        Pageable pageable =  PageRequest.of(page-1, size);
        return  spitDao.findAllByParentid(null,pageable);
    }

public  List<Spit>  text(){
    Spit spit=new Spit();
    spit.setParentid(null);
//    ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
//            .withIncludeNullValues()//改变“Null值处理方式”：包括
//            .withIgnorePaths("_id","content","publishtime","userid","nickname","visits","thumbup","share","comment","state");
     Example<Spit> example=Example.of(spit);
    System.out.println(spitDao.findAll(example).size());
    return  spitDao.findAll(example);
}

}



