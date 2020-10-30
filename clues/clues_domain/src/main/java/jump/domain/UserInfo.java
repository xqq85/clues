package jump.domain;

import jump.utils.constants.Constants;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//与数据库中users对应
public class UserInfo {
    private int id;
    @Pattern(regexp = Constants.USERNAMR, message = Constants.USERNAMRERROR)
    private String username;
    @NotBlank(message = Constants.NAMEERROR)
    private String name;
    @Email(message = Constants.EMAILERROR)
    private String email;
    @Length(min = 6, max = 16, message = Constants.PASSWORDERROR)
    private String password;
    @Pattern(regexp = Constants.PHONE, message = Constants.PHONEERROR)
    private String phoneNum;
    private int status;
    private String statusStr;
    private List<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        //状态 0未开启，1开启
        if(status==0){
            statusStr="未开启";
        }
        else if(status==1){
            statusStr="开启";
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
