package com.tensquare.base.service;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.dao.LabelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private  IdWorker idWorker;

    public List<Label> findAll(){
        return  labelDao.findAll();
    }

    public Label findById(String id){
        return  labelDao.findById(id).get();
    }

    public  void  save(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public  void  update(Label label){
        labelDao.save(label);
    }
    public  void deleteById(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到哪个对象中
             * @param criteriaQuery 封装的都是关键条件，group by等，基本不用
             * @param cb 用来封装对象
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
              //new 一个集合，存放条件
                List<Predicate> list =new ArrayList<>();
               if (label.getLabelname()!=null&&!"".equals(label.getLabelname())){
                 Predicate predicate = cb.like(root.get("labelname").as(String.class), "%"+label.getLabelname()+"%"); //where labelname like "%"
              list.add(predicate);
               }
                if (label.getState()!=null&&!"".equals(label.getState())){
                    Predicate predicate = cb.like(root.get("state").as(String.class), "%"+label.getState()+"%"); //where labelname like "%"
                    list.add(predicate);
                }
                Predicate[] paar=new Predicate[list.size()];
               list.toArray(paar);
                return cb.and(paar);
            }
        });

    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        //配置分页对象
        Pageable pageable= PageRequest.of(page-1,size);
        return  labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到哪个对象中
             * @param criteriaQuery 封装的都是关键条件，group by等，基本不用
             * @param cb 用来封装对象
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                //new 一个集合，存放条件
                List<Predicate> list =new ArrayList<>();
                if (label.getLabelname()!=null&&!"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%"+label.getLabelname()+"%"); //where labelname like "%"
                    list.add(predicate);
                }
                if (label.getState()!=null&&!"".equals(label.getState())){
                    Predicate predicate = cb.like(root.get("state").as(String.class), "%"+label.getState()+"%"); //where labelname like "%"
                    list.add(predicate);
                }
                Predicate[] paar=new Predicate[list.size()];
                list.toArray(paar);
                return cb.and(paar);
            }
        },pageable);
    }
}
