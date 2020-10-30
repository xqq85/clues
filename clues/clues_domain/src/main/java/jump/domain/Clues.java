package jump.domain;

import jump.utils.constants.Constants;
import jump.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

//线索
public class Clues {
    private int id;
    private String cNumber;
    @NotBlank(message = "线索名称不能为空")
    private String cluesName;
    @Pattern(regexp = Constants.PHONE, message = Constants.PHONEERROR)
    private String mobile;
    @Email
    private String email;
    @NotBlank(message = "线索地址不能为空")
    private String address;

    @NotNull(message = Constants.DATENOTNULL)
    @Past
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date entryTime;
    private String entryTimeStr;

    private int transform;
    private String transformStr;
    private String cluesDesc;
    private String trackNumber;
    private String entryNumber;

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

    public String getCluesName() {
        return cluesName;
    }

    public void setCluesName(String cluesName) {
        this.cluesName = cluesName;
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

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public int getTransform() {
        return transform;
    }

    public String getCluesDesc() {
        return cluesDesc;
    }

    public void setCluesDesc(String cluesDesc) {
        this.cluesDesc = cluesDesc;
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

    public String getEntryTimeStr() {
        if(entryTime!=null){
            entryTimeStr= DateUtils.date2String(entryTime,"yyyy-MM-dd HH:mm");
        }
        return entryTimeStr;
    }

    public void setEntryTimeStr(String entryTimeStr) {
        this.entryTimeStr = entryTimeStr;
    }

    @Override
    public String toString() {
        return "Clues{" +
                "id=" + id +
                ", cNumber='" + cNumber + '\'' +
                ", cluesName='" + cluesName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", entryTime=" + entryTime +
                ", entryTimeStr='" + entryTimeStr + '\'' +
                ", transform=" + transform +
                ", transformStr='" + transformStr + '\'' +
                ", cluesDesc='" + cluesDesc + '\'' +
                ", trackNumber='" + trackNumber + '\'' +
                ", entryNumber='" + entryNumber + '\'' +
                '}';
    }
}
