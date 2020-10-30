package jump.domain;

import java.io.Serializable;
import java.util.List;

public class SendEMailVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String uid;
    private String token;

    // 待发送推送邮箱
    private List<String> email;

    // 模版id
    private Integer template;

    // 发送参数
    private String param;

    private String subject;

    private String content;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SendSmsVo{" +
                "uid='" + uid + '\'' +
                ", token='" + token + '\'' +
                ", email=" + email +
                ", template=" + template +
                ", param='" + param + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}