package com.example.jwtprj.security.jwt;

import com.example.jwtprj.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//tạo token
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret ="duythaid19ptit@gmail.com";//key để đăng ký
    private int jwtExpiration = 86400;
    /*
    *create token gọi qua login
    * 2 hàm còn lại gọi qua filter
    */
    public String createToken(Authentication authentication){//tạo token
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();//get ra user hiện tại trên hệ thống
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))//thời điểm hiện tại cộng thời gian hết hạn
                .signWith(SignatureAlgorithm.HS512,jwtSecret)//kiểu mã hóa
                .compact();//đóng lại
    }
    public boolean validateToken(String token){//kiểm tra token hợp lệ hay không
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT signature -> Message: {}",e);//chữ kí không hợp lệ
        }catch (MalformedJwtException e){
            logger.error("Invalid format token -> Message {}",e);//mã thông báo định dạng không hợp lợ
        }catch (ExpiredJwtException e){
            logger.error("Exprired JWT token -> Message: {}",e);//token hết  hạn
        }catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token -> Message: {}",e);//không hỗ trợ token
        }catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty -> Message: {}",e);//
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return username;
    }
}
