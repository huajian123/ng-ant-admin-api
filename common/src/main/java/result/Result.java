package result;



import enums.ErrorCodeEnum;
import lombok.Data;

/**
 * 类功能描述:Result通用返回类
 *
 * @author fbl
 * @date 2020/6/1 9:00
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 请求成功，无返回数据
     */
    public static Result success() {
        return new Result(CommonConstants.ResultCodeStatus.RESULT_SUCCESS, CommonConstants.ResultCodeMessage.RESULT_SUCCESS_MESSAGE);
    }

    /**
     * 请求成功，有返回数据
     */
    @SuppressWarnings("unchecked")
    public static Result success(Object data) {
        return new Result(CommonConstants.ResultCodeStatus.RESULT_SUCCESS, CommonConstants.ResultCodeMessage.RESULT_SUCCESS_MESSAGE, data);
    }

    /**
     * 请求失败，返回通用异常信息
     */
    public static Result failure() {
        return new Result(CommonConstants.ResultCodeStatus.RESULT_FAILURE, CommonConstants.ResultCodeMessage.RESULT_FAILURE_MESSAGE);
    }

    /**
     * 请求失败，返回异常信息（所有异常必须先在ErrorCodeEnum中定义，统一管理）
     */
    public static Result failure(ErrorCodeEnum errorCodeEnum) {
        return new Result(errorCodeEnum.getCode(), errorCodeEnum.getMsg());
    }

    public static Result failure(Integer code,String message) {
        return new Result(code,message);
    }
}
