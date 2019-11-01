package com.tensquare.user.controller;
import com.tensquare.user.service.UserService;
import com.tensquare.user.pojo.User;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Resource
	private JwtUtil jwtUtil;

	/**
	 *  更新粉丝数和关注数
	 *  a关注b a关注数增加，b粉丝数增加
	 */
	       @RequestMapping(value = "/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
          public void  updatefanscountandfollowcount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int x){
			   System.out.println("更新粉丝数和关注数controller层");
           userService.updatefanscountandfollowcount(x,userid,friendid);
		  }




	/**
	 * user登录
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
    public  Result login(@RequestBody User user){
    	User userlogin=userService.login(user.getMobile(),user.getPassword());
		System.out.println(user.getMobile());
    	if (userlogin==null){
    		return  new Result(false,StatusCode.LOGINERROR,"登陆失败");
		}
     String token=jwtUtil.createJWT(userlogin.getId(),userlogin.getNickname(),"user");
    	Map<String,Object> map=new HashMap<>();
    	map.put("roles","user");
    	map.put("token",token);
    	map.put("user",userlogin);
    	map.put("name",userlogin.getNickname());
    	map.put("avatar",userlogin.getAvatar());
    	return new Result(true,StatusCode.OK,"登陆成功",map);
	}

	/**
	 *
	 * @param code
	 * @param user
	 * @return
	 * 勤俭节约，电话输入邮箱,注册，
	 */
    @RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result regist(@PathVariable String code,@RequestBody User user){
    String checkcodeRedis=(String)redisTemplate.opsForValue().get("checkcode_"+user.getMobile());
    if (checkcodeRedis.isEmpty()){
    	return new Result(false,StatusCode.ERROR,"亲，请先获取验证码");
	}
    if (!checkcodeRedis.equals(code)){
		return new Result(false,StatusCode.ERROR,"亲，擦亮双眼，获取正确的验证码哦");
	}
    userService.add(user);
    return new Result(true,StatusCode.OK,"用户注册成功");
	}

	/**
	 * 发送短信验证码
	 */
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
public  void sendSms(@PathVariable String mobile){
		System.out.println("注册获取验证码"+mobile);
		userService.sendSms(mobile+".com");
		//return  new Result(true,StatusCode.OK,"发送成功");

}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		System.out.println("查询所有");
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		System.out.println("查询用户Id"+id);
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		System.out.println("删除用户"+id);
		try {
			userService.deleteById(id);
			return new Result(true,StatusCode.OK,"删除成功");
		}
		catch (Exception e){
			return new Result(false,StatusCode.ERROR,"权限不足");
		}
	}
	
}
