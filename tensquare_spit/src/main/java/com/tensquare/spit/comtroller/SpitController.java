package com.tensquare.spit.comtroller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired //默认按照类型注入，找不到按spitservice(名字)注入
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
   private JwtUtil jwtUtil;

    @RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
    public Result findSearch( @PathVariable int page, @PathVariable int size){

        Page<Spit> pageList = spitService.findSearch(page, size);
        return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()) );
    }
    @RequestMapping("text")
    public Result findByNoParent(){
       List<Spit> spits= spitService.text();
        return  new Result(true, StatusCode.OK,"查询成功",spits);
    }

    @RequestMapping
 public Result findAll(){
     return  new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
 }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        System.out.println(spitService.findById(spitId));
        return  new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        System.out.println("save spit"+spit);
        //spit.setNickname(((Claims)jwtUtil.parseJWT(token)).getSubject());
        spitService.save(spit);
        return  new Result(true, StatusCode.OK,"保存成功");
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        System.out.println("修改 spit");
        spit.set_id(spitId);
        spitService.update(spit);
        return  new Result(true, StatusCode.OK,"修改成功");
    }
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId){
        System.out.println("删除吐槽"+spitId);
       spitService.deleteById(spitId);
        return  new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int  page,@PathVariable int size){
        System.out.println("根据spitid"+parentid+"查询他的吐槽");
        Page<Spit> pagedata=spitService.findByParentid(parentid,page,size);
        return  new Result(true, StatusCode.OK,"查询成功",new PageResult<Spit>(pagedata.getTotalElements(),pagedata.getContent()));
    }

@RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public  Result thumbup(@PathVariable String spitId){
         String jwt=(String) request.getAttribute("claims_user");
              Claims claims= jwtUtil.parseJWT(jwt);
    System.out.println( claims.getId()+"用户id");
        String userid=claims.getId();
        if (redisTemplate.opsForValue().get("thumbup_"+userid+"_"+spitId)!=null){
            System.out.println("已经咱过了");
            return new Result(false,StatusCode.REPERROR,"亲，赞多伤身哦");
        }
        spitService.thumbup(spitId);
    redisTemplate.opsForValue().set("thumbup_"+userid+"_"+spitId,1);
    System.out.println("点赞成功");
        return  new Result(true,StatusCode.OK,"点赞成功");
}






            @RequestMapping("/share/{spitId}")
            public  Result share(@PathVariable String spitId){
                String jwt=(String) request.getAttribute("claims_user");
                Claims claims= jwtUtil.parseJWT(jwt);
                System.out.println( claims.getId()+"用户id");
                String userid=claims.getId();
            if(redisTemplate.opsForValue().get("share_"+userid)!=null){
                return  new Result(false,StatusCode.REPERROR,"分享一次就够了，不要贪心哦");
            }
            spitService.share(spitId);
                redisTemplate.opsForValue().set("share_"+userid,1);
                return   new Result(true,StatusCode.OK,"分享成功");
            }

    @RequestMapping("/visits/{spitId}")
    public  Result visits(@PathVariable String spitId){
        String jwt=(String) request.getAttribute("claims_user");
        Claims claims= jwtUtil.parseJWT(jwt);
        System.out.println( claims.getId()+"用户id");
        String userid=claims.getId();
        if(redisTemplate.opsForValue().get("visits_"+userid)!=null){
            return  new Result(false,StatusCode.REPERROR,"只能分享一次");
        }
        spitService.visits(spitId);
        redisTemplate.opsForValue().set("visits_"+userid,1);
        return   new Result(true,StatusCode.OK,"正在浏览");
    }


}
