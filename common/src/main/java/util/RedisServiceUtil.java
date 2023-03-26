package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 李广辉
 * @date 2023/3/12 22:43
 */

@Component
public class RedisServiceUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void testSet(String doctorId){
        String doctorRegistrationNumberKey = RedisKeyUtil.getDoctorRegistrationNumberKey(doctorId);
        redisTemplate.opsForValue().set(doctorId,1);
    }

    public Object testGet(String doctorId){
        return redisTemplate.opsForValue().get(doctorId);
    }

}
