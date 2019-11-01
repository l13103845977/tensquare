package jwttext;

import io.jsonwebtoken.Claims;
import org.junit.Test;
import util.JwtUtil;

public class JwtTest {



    @Test
    public void  test()
{
        JwtUtil jwtUtil=new JwtUtil();
        Claims claims= jwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDkwNTQ1NDI1MjIxNTU0MTc2Iiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1NTIyOTIwNjIsInJvbGVzIjoiYWRtaW4iLCJleHAiOjE1NTIyOTU2NjJ9.698W7ZynPKaN_TwuR5a4PGnFJdlGGnj7njDDexm3GaY");
        System.out.println(claims.get("roles"));
        }
        }