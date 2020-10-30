package jump.dao;

import jump.domain.SendEMailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    //private final static long validTime = 60;//设置时效


    public void pushList(SendEMailVo sendEMailVo) throws Exception{
        redisTemplate.boundListOps("eMailSend").leftPush(sendEMailVo);
    }

    public SendEMailVo popList() {
        return (SendEMailVo) redisTemplate.boundListOps("smsSend").rightPop(0,TimeUnit.SECONDS);
    }
}
