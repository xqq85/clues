package jump.service.impl;

import jump.dao.RedisDao;
import jump.domain.SendEMailVo;
import jump.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisDao redisDao;

    @Override
    public void pushList(SendEMailVo sendEMailVo) throws Exception{
        redisDao.pushList(sendEMailVo);
    }
}
