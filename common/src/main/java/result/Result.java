package result;

import lombok.Data;
import org.omg.PortableInterceptor.SUCCESSFUL;

/**全局统一返回结果类
 *
 * @author 李广辉
 * @date 2023/1/7 18:47
 */

@Data
public class Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result(){}

    protected static <T> Result<T> build(T data){
        Result<T> result=new Result<T>();
        if(data!=null){
            result.setData(data);
        }
        return  result;
    }


    public static <T> Result<T> build(T body,ResultCodeEnum resultCodeEnum){
        Result<T> result=build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> ok(T data){
        Result<T> result=build(data);
        return build(data,ResultCodeEnum.SUCCESS);
    }


    public static <T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     *
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(T data){
        Result<T> result=build(data);
        return build(data,ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

}
