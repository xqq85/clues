package jump.service;


import jump.domain.SendEMailVo;

public interface RedisService {

    public void pushList(SendEMailVo sendEMailVo) throws Exception;
}
