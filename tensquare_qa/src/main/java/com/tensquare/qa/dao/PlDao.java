package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Pl;
import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PlDao extends JpaRepository<Pl,String>, JpaSpecificationExecutor<Pl> {
    List<Pl> findAllByProblemid(String problemid);

    @Modifying
    @Query(value = "INSERT INTO tb_pl VALUES (problemid,labelid) ",nativeQuery = true)
    public void addPl(String problemid,String labelid);

}
