package jump.domain;

import jump.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * 产品信息
 */
public class Product {
     private int id; // 主键
     private String productNumber; // 编号 唯一
     @NotBlank(message = "产品名称不能为空")
     private String productName; // 名称
     @NotBlank(message = "产品产地不能为空")
     private String location; // 产地

     @NotNull
     @Past
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
     private Date productTime; // 时间
     private String productTimeStr;
     private String productPrice; // 产品价格
     private String productDesc; // 产品描述
     private Integer productStatus; // 状态 0 关闭 1 开启
     private String productStatusStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getProductTime() {
        return productTime;
    }

    public void setProductTime(Date productTime) {
        this.productTime = productTime;
    }

    public String getProductTimeStr() {
        if(productTime!=null){
            productTimeStr= DateUtils.date2String(productTime,"yyyy-MM-dd HH:mm");
        }
        return productTimeStr;
    }

    public void setProductTimeStr(String productTimeStr) {
        this.productTimeStr = productTimeStr;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatusStr() {
        if (productStatus != null) {
            // 状态 0 关闭 1 开启
            if(productStatus==0)
                productStatusStr="关闭";
            if(productStatus==1)
                productStatusStr="开启";
        }
        return productStatusStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productNumber='" + productNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", location='" + location + '\'' +
                ", productTime=" + productTime +
                ", productTimeStr='" + productTimeStr + '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                ", productStatus=" + productStatus +
                ", productStatusStr='" + productStatusStr + '\'' +
                '}';
    }
}
