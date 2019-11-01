package com.tensquare.qa.controller;

import com.tensquare.qa.client.BaseClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/qa")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest request;
	@Resource
	private BaseClient baseClient;

	@Resource
	private JwtUtil jwtUtil;

/**
 *
 */
@RequestMapping(value = "/label/user", method = RequestMethod.GET)
public Result findByUserId(){
	String token= (String) request.getAttribute("claims_user");
	System.out.println(token);
	if (token==null||"".equals(token)){
		return new Result(false,StatusCode.ERROR,"权限不足");
	};
List<Problem> list= problemService.findAllByUserid(((Claims)jwtUtil.parseJWT(token)).getId());
for (Problem p:list){
	System.out.println(p);
}
return new Result(true,StatusCode.OK,"查村成功",problemService.findAllByUserid(((Claims)jwtUtil.parseJWT(token)).getId()));

}
	/**
	 *  根据问题id查询label
	 * @param
	 * @return
	 */
//	@RequestMapping(value = "/search/problem/{problemid}")
//	public  Result findLabelByProblemId(@PathVariable String problemid){
//		System.out.println("根据问题查询label");
//	List<Pl> pls= plService.findAllByProblemid(problemid);
//	for (Pl p:pls){
//		System.out.println(p);
//		findByLabelId(p.getLabelid());
//	}
//		return  new Result(true,StatusCode.OK,"查询成功");
//	}

	@RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
	public Result findByLabelId(@PathVariable("labelId") String labelId){
		System.out.println("根据labelid获取label");
		return baseClient.findById(labelId);

	}

	@RequestMapping("/search/newlist/{labelid}/{page}/{size}")
	public  Result newlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int  size){
		System.out.println("查询newlist");
		Page<Problem> pageData=problemService.newlist(labelid,page,size);
 return  new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
	}

//	@RequestMapping("/search/newlist/{labelid}/{page}/{size}")
//	public  Result newlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int  size){
//		System.out.println("查询newlist");
//		Page<Problem> pageData=problemService.findNewlistByLabelId(labelid,page,size);
//		return  new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
//	}

	@RequestMapping("/search/hotlist/{labelid}/{page}/{size}")
	public  Result hotlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int  size){
		Page<Problem> pageData=problemService.hotlist(labelid,page,size);
		return  new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
	}

	@RequestMapping("/search/waitlist/{labelid}/{page}/{size}")
	public  Result waitlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int  size){
		Page<Problem> pageData=problemService.waitlist(labelid,page,size);
		return  new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pageData.getTotalElements(),pageData.getContent()));
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		System.out.println("查询所有问题");
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
	String token= (String) request.getAttribute("claims_user");
		System.out.println(token);
	if (token==null||"".equals(token)){
		return new Result(false,StatusCode.ERROR,"权限不足");
	}
	problem.setUserid(((Claims)jwtUtil.parseJWT(token)).getId());
		problem.setNickname(((Claims)jwtUtil.parseJWT(token)).getSubject());
		problem.setCreatetime(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
