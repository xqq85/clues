package jump.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author: xqq
 * @Date: 2020/10/17
 */
@Component
public class MailUtils {
    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

    //使用@Value注入application.properties中指定的用户别名
    private static String from;

    private static String alias;

    //用于发送文件
    private static JavaMailSender mailSender;

    @Value("${spring.mail.username}")//静态函数用set或者constructor注入，注意方法不能是static
    public  void setFrom(String from) {
        MailUtils.from = from;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        MailUtils.mailSender = mailSender;
    }

    @Value("${spring.mail.alias}")
    public void setAlias(String alias) {
        MailUtils.alias = alias;
    }

    /**
     * 发送普通文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    public static void sendSimpleMail(String to, String subject, String content)throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress (to));//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(new InternetAddress(from, alias, "UTF-8"));//发信人

        mailSender.send(message);
    }
    /**
     * 发送HTML邮件
     * HTML文件就是指在文件内容中可以添加<html>等标签，收件人收到邮件后显示内容也和网页一样，比较丰富多彩
     * @param to 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    public static void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        logger.info("发送HTML邮件开始：{},{},{}", to, subject, content);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from,alias);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
            logger.info("发送HTML邮件成功");
        } catch (Exception e) {
            logger.error("发送HTML邮件失败：", e);
        }
    }
    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件路径
     */
    public static void sendAttachmentMail(String to, String subject, String content, String filePath){

        logger.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(from,alias);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
            logger.info("发送带附件邮件成功");
        } catch (Exception e) {
            logger.error("发送带附件邮件失败", e);
        }
    }
    /**
     * 发送带图片的邮件
     * 带图片即在正文中使用<img>标签，并设置我们需要发送的图片，也是在HTML基础上添加一些参数
     * @param to 收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId 图片ID，用于在<img>标签中使用，从而显示图片
     */
    public static void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        logger.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from,alias);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            logger.info("发送带图片邮件成功");
        } catch (Exception e) {
            logger.error("发送带图片邮件失败", e);
        }
    }
}