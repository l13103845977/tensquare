package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {



    /**
     * 执行时间 pre 先执行
     * post 后执行
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序
     * 当有多个filter时，值小的先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * false 过滤器不生效
     * true 生效
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() throws ZuulException {
        JwtUtil jwtUtil=new JwtUtil();
        System.out.println("经过后台过滤器");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (request.getMethod().equals("OPTIONS")){
            return null;
        }
        if (request.getRequestURI().indexOf("login")>0){
            return null;
        }
        String header = request.getHeader("Authorization");
           System.out.println("header:"+header);
        if ( header!= null && !"".equals(header)) {
            if (header.startsWith("Bearer")) {
                String token = header.substring(7);
                System.out.println(token);
                try {
                    System.out.println("-----------");
                    Claims claims = jwtUtil.parseJWT(token);
                    System.out.println(claims);
                    String roles = (String) claims.get("roles");
                    System.out.println(roles + "-----后台过滤器捕获到的角色");
                    if (roles.equals("admin")) {
                        requestContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false);//终止运行
                }
            }
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }

}