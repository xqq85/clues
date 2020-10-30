package jump.domain;

import jump.utils.constants.Constants;
import jump.utils.DateUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

//公海
public class PublicSea {
    private int id;
    private String cNumber;
    @NotBlank(message = "线索名称不能为空")
    private String clientName;
    @Pattern(regexp = Constants.PHONE, message = Constants.PHONEERROR)
    private String mobile;
    @Email
    private String email;
    @NotBlank(message = "线索地址不能为空")
    private String address;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date lastTime;
    private String lastTimeStr;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date entryTime;
    private String entryTimeStr;

    private String trackNumber;
    private String entryNumber;
    private int transform;
    private String transformStr;

    private String clientDesc;
    @Length(min=6,max=32,message = "跟踪失败描述长度为6~32")
    private String failueDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastTimeStr() {
        if(lastTime!=null){
            lastTimeStr= DateUtils.date2String(lastTime,"yyyy-MM-dd HH:mm");
        }
        return lastTimeStr;
    }

    public void setLastTimeStr(String lastTimeStr) {
        this.lastTimeStr = lastTimeStr;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public String getEntryTimeStr() {
        if(entryTime!=null){
            entryTimeStr= DateUtils.date2String(lastTime,"yyyy-MM-dd HH:mm");
        }
        return entryTimeStr;
    }

    public void setEntryTimeStr(String entryTimeStr) {
        this.entryTimeStr = entryTimeStr;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(String entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public String getFailueDesc() {
        return failueDesc;
    }

    public void setFailueDesc(String failueDesc) {
        this.failueDesc = failueDesc;
    }

    public int getTransform() {
        return transform;
    }

    public void setTransform(int transform) {
        this.transform = transform;
    }

    public String getTransformStr() {
        //状态 0未转化，1转化
        if(transform==0){
            transformStr="未转化";
        }
        else if(transform==1){
            transformStr="已转化";
        }
        return transformStr;
    }

    public void setTransformStr(String transformStr) {
        this.transformStr = transformStr;
    }

    @Override
    public String toString() {
        return "PublicSea{" +
                "id=" + id +
                ", cNumber='" + cNumber + '\'' +
                ", clientName='" + clientName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", lastTime=" + lastTime +
                ", lastTimeStr='" + lastTimeStr + '\'' +
                ", entryTime=" + entryTime +
                ", entryTimeStr='" + entryTimeStr + '\'' +
                ", trackNumber='" + trackNumber + '\'' +
                ", entryNumber='" + entryNumber + '\'' +
                ", transform=" + transform +
                ", transformStr='" + transformStr + '\'' +
                ", clientDesc='" + clientDesc + '\'' +
                ", failueDesc='" + failueDesc + '\'' +
                '}';
    }
}
