package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Pl;
import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
	@Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? ORDER BY replytime desc ",nativeQuery = true)
    public Page<Problem> newlist(String labelid, Pageable pageable);


	@Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? ORDER BY reply desc ",nativeQuery = true)
    public Page<Problem> hotlist(String labelid, Pageable pageable);

	@Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? and reply=0 ORDER BY createtime desc ",nativeQuery = true)
    public Page<Problem> waitlist(String labelid, Pageable pageable);

	@Query(value = "SELECT * FROM tb_problem WHERE id IN (SELECT problemid FROM tb_pl WHERE labelid=1) ",nativeQuery = true)
   public  Page<Problem> findNewlistByLabelId(String labelid,Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem WHERE id IN (SELECT problemid FROM tb_pl WHERE labelid=1) ",nativeQuery = true)
    public  Page<Problem> findHotlistByLabelId(String labelid,Pageable pageable);



     List<Problem> findAllByUserid(String userid);

}
