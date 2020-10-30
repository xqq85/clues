package jump.domain;

import jump.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//客户
public class Clients {
    private int id;
    private String cNumber;
    private String clientName;
    private String mobile;
    private String email;
    private String address;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date transformTime;
    private String transformTimeStr;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date lastTime;
    private String lastTimeStr;

    private int transform;
    private String transformStr;

    private String clientDesc;
    private String trackNumber;

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

    public Date getTransformTime() {
        return transformTime;
    }

    public void setTransformTime(Date transformTime) {
        this.transformTime = transformTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getTransformTimeStr() {
        if(transformTime!=null){
            transformTimeStr= DateUtils.date2String(transformTime,"yyyy-MM-dd HH:mm");
        }
        return transformTimeStr;
    }

    public void setTransformTimeStr(String transformTimeStr) {
        this.transformTimeStr = transformTimeStr;
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

    public int getTransform() {
        return transform;
    }

    public void setTransform(int transform) {
        this.transform = transform;
    }

    public String getTransformStr() {
        //状态 0在跟踪，1已跟丢
        if(transform==0){
            transformStr="在跟踪";
        }
        else if(transform==1){
            transformStr="已跟丢";
        }
        return transformStr;
    }

    public void setTransformStr(String transformStr) {
        this.transformStr = transformStr;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id=" + id +
                ", cNumber='" + cNumber + '\'' +
                ", clientName='" + clientName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", transformTime=" + transformTime +
                ", transformTimeStr='" + transformTimeStr + '\'' +
                ", lastTime=" + lastTime +
                ", lastTimeStr='" + lastTimeStr + '\'' +
                ", transform=" + transform +
                ", transformStr='" + transformStr + '\'' +
                ", clientDesc='" + clientDesc + '\'' +
                ", trackNumber='" + trackNumber + '\'' +
                '}';
    }
}
