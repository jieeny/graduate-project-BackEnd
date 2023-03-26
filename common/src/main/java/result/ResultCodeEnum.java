package result;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 李广辉
 * @date 2023/1/7 18:37
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201,"失败"),
    PARAM_ERROR(202,"参数不正确"),
    SERVICE_ERROR(203,"服务异常"),
    DATA_ERROR(204,"数据异常"),
    DATA_UPDATE_ERROR(205,"数据版本异常"),
    LOGIN_AUTH(208,"未登录"),
    PERMISSION(209,"没有权限"),
    CODE_ERROR(210,"验证码错误"),
    LOGIN_MODEL_ERROR(211,"账号不正确"),


    ;

    private Integer code;
    private String message;
//    SUCCESS(200,"成功"),


    private ResultCodeEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }


}
