package jump.service;

import jump.domain.SendEMailVo;
import jump.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class SendEMailService implements Runnable{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    @Async
    public void init(){
        //启动线程实例
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true){
            try {
                sendEMail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEMail(){
        while (true){
            SendEMailVo eMailSendVo =(SendEMailVo)redisTemplate.boundListOps("eMailSend").rightPop(10,TimeUnit.SECONDS);
            System.out.println(eMailSendVo);
            if(eMailSendVo == null){
                continue;
            }else {
                for (String email:eMailSendVo.getEmail()) {
                    if(email == null || email == ""){
                        continue;
                    }else {
                        try {
                            MailUtils.sendHtmlMail(email,eMailSendVo.getSubject(),eMailSendVo.getContent());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
