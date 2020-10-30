package jump.utils;

import jump.utils.constants.RegularConstants;
import jump.utils.exception.CrmebException;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

public class ValidateFormUtil {
    /**
     * 邮箱
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void isEmail(String value, String info) {
        regularException(value, info, RegularConstants.EMAIL, "邮箱");
    }

    /**
     * 手机
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void isPhone(String value, String info) {
        regularException(value, info, RegularConstants.PHONE, "手机");
    }

    /**
     * 验证必填
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void isRequire(String value, String info){
        if(StringUtils.isEmpty(value) ){
            throw new CrmebException("请填写/选择" + info);
        }
    }

    /**
     * 数字验证
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void isNumber(String value, String info){
        regularException(value, info, RegularConstants.NUMBER, "数字");
    }

    /**
     * 数字范围
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void range(String value, String info, Integer max, Integer min){
        isNumber(value, info);
        max(value, info, max);
        min(value, info, min);
    }

    /**
     * 最大数值
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void max(String value, String info, Integer max){
        isNumber(value, info);
        int number = Integer.parseInt(value);
        if(number > max ){
            throw new CrmebException(info + "不在取值范围内，最大不能大于" + max);
        }
    }

    /**
     * 最小数值
     * @param value String 值
     * @param info String 字段名
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void min(String value, String info, Integer min){
        isNumber(value, info);
        int number = Integer.parseInt(value);
        if(number > min ){
            throw new CrmebException(info + "不在取值范围内，最小不能小于" + min);
        }
    }

    /**
     * 正则表达式验证
     * @param value String 值
     * @param info String 字段名
     * @param regular String 正则表达式
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static void regularException(String value, String info, String regular, String title){
        if(!regular(value, info, regular)){
            //正则验证
            throw new CrmebException(info + " 格式必须为 " + title);
        }
    }

    /**
     * 正则表达式验证
     * @param value String 值
     * @param info String 字段名
     * @param regular String 正则表达式
     * @author Mr.Zhang
     * @since 2020-05-11
     */
    public static boolean regular(String value, String info, String regular){
        isRequire(value, info);
        Pattern pattern = Pattern.compile(regular);
        return pattern.matcher(value).matches();
    }
}

