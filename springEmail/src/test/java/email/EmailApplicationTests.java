package email;

import jump.MySpringBootApplication;
import jump.domain.SendEMailVo;
import jump.service.MailService;
import jump.utils.MailUtils;
import jump.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootApplication.class)
public class EmailApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("2324139353@qq.com","测试spring boot imail-主题","测试spring boot imail - 内容");
    }

    @Test
    public void sendHtmlMail() throws MessagingException {

        String content = "<html>\n" +
                "<body>\n" +
                "<h3>hello world</h3>\n" +
                "<h1>html</h1>\n" +
                "<body>\n" +
                "</html>\n";

        mailService.sendHtmlMail("2324139353@qq.com","这是一封HTML邮件",content);
    }

    @Test
    public void sendAttachmentsMail() throws MessagingException {
        String filePath = "/ijiangtao/软件开发前景.docx";
        String content = "<html>\n" +
                "<body>\n" +
                "<h3>hello world</h3>\n" +
                "<h1>html</h1>\n" +
                "<h1>附件传输</h1>\n" +
                "<body>\n" +
                "</html>\n";
        mailService.sendAttachmentsMail("2324139353@qq.com","这是一封HTML邮件",content, filePath);
    }

    @Test
    public void sendInlinkResourceMail() throws MessagingException {
        //TODO 改为本地图片目录
        String imgPath = "/ijiangtao/img/blob/dd9899b4cf95cbf074ddc4607007046c022564cb/blog/animal/dog/dog-at-work-with-computer-2.jpg?raw=true";
        String rscId = "admxj001";
        String content = "<html>" +
                "<body>" +
                "<h3>hello world</h3>" +
                "<h1>html</h1>" +
                "<h1>图片邮件</h1>" +
                "<img src='cid:"+rscId+"'></img>" +
                "<body>" +
                "</html>";

        mailService.sendInlinkResourceMail("2324139353@qq.com","这是一封图片邮件",content, imgPath, rscId);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id","ispringboot");

        String emailContent = templateEngine.process("emailTeplate", context);
        mailService.sendHtmlMail("2324139353@qq.com","这是一封HTML模板邮件",emailContent);

    }

    @Test
    public void RedisSet() {
        boolean b = redisUtil.set("string","123");
    }

    @Test
    public void RedisGet() {
        String string = (String)redisUtil.get("string");
        System.out.println(string);
    }

    @Test
    public void sendEmail() {
        SendEMailVo eMailSendVo =(SendEMailVo)redisTemplate.boundListOps("eMailSend").rightPop(0, TimeUnit.SECONDS);
        System.out.println(eMailSendVo);
        for (String email:eMailSendVo.getEmail()) {
            try {
                MailUtils.sendHtmlMail(email,eMailSendVo.getSubject(),eMailSendVo.getContent());
            }catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
