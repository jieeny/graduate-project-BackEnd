package helper;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author 李广辉
 * @date 2023/1/8 13:06
 */
public class jwtHelper {

    //过期时间
//    private static long tokenExpiration=60*3*60*60;
    private static long tokenExpiration=3600000000L;
    //签名密钥
    private static String tokenSignKey="12345";

    //根据参数生成token
    public static String createToken(String userId,String username,String type){
            String token=Jwts.builder()
                    .setSubject("USER")
                    .setExpiration(new Date(System.currentTimeMillis()+tokenExpiration))
                    .claim("userId",userId)
                    .claim("userName",username)
                    .claim("userType",type)
                    .signWith(SignatureAlgorithm.HS512,tokenSignKey)
                    .compressWith(CompressionCodecs.GZIP)
                    .compact();
            return token;
    }

    /**
     * 根据token获取用户id
     * @param token
     * @return
     */
    public static String getUserId(String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }

        Jws<Claims> claimsJws=Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims= claimsJws.getBody();
        String userId= (String) claims.get("userId");
        return userId;
    }

    /**
     * 根据token获取用户类型
     * @param token
     * @return
     */
    public static String getUserType(String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }

        Jws<Claims> claimsJws=Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims= claimsJws.getBody();
        String userType= (String) claims.get("userType");
        return userType;
    }

    /**
     * 根据token字符串得到用户名称
     * @param token
     * @return
     */
    public static String getUserName(String token){
        if(StringUtils.isEmpty(token)){
            return "";
        }

        Jws<Claims> claimsJws=Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims= claimsJws.getBody();
        return (String) claims.get("userName");
    }

    /**
     * 判断token是否存在以及是否有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken){
        if(StringUtils.isEmpty(jwtToken)){
            return false;
        }try{
            Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(jwtToken);
        }catch (Exception e){
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        String token = jwtHelper.createToken(1, "lucy1");
//        System.out.println(token);
//        System.out.println(jwtHelper.getUserId(token));
//        System.out.println(jwtHelper.getUserName(token));
        String token="eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUr" +
                "JSCg12DVLSUUqtKFCyMjQzNzUyNTEyN9RRKi1OLfJMAYpBmH6JualA1TmlyZWGSrUA" +
                "wujb9z0AAAA.eaN3rDbTWbVgsvOS-azyea5kY8YD9CfoWH4aSyJwNaU6JjGRIcBXHF" +
                "gWL7RpjTK_WGUytjgCvrczAhGcEap78Q";
        System.out.println(checkToken(token));
    }

}
