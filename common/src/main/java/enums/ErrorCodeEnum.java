package enums;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类功能描述:错误码枚举类
 *
 * @author fbl
 * @date 2020/6/1 9:00
 */
public enum
ErrorCodeEnum {

    /**
     * 分段定义  xx/yy
     *
     * xx:代表模块：10系统模块
     * yy:代表功能模块：
     */
    /****************************** 通用系统错误 **************************************/
    SYS_ERR_GLOBAL(1000, "系统处理异常，请稍后重试"),
    SYS_ERR_CREATE_FAILED(1001, "新增数据失败"),
    SYS_ERR_UPDATE_FAILED(1002, "修改数据失败"),
    SYS_ERR_DELETE_FAILED(1003, "删除数据失败"),
    SYS_ERR_SEARCH_FAILED(1004, "查询数据失败"),
    SYS_ERR_COUNT_FAILED(1005, "查询数据总数失败"),
    SYS_ERR_DUPLICATED_DATA(1006, "数据已存在"),
    SYS_ERR_RECORD_NOT_FOUND(1007, "数据不存在"),
    SYS_ERR_CRON(1008, "cron表达式错误"),
    SYS_ERR_PRODUCE_CAPTCHA(1009, "验证码生成异常"),
    SYS_ERR_TOKEN_SIGNATURE(1010, "无效的令牌！"),
    SYS_ERR_REPETITION_SUBMIT(1011, "请勿重复提交请求！"),
    SYS_ERR_TOKEN_EXPIRE(1012, "令牌已过期！"),


    /****************************** http请求错误 **************************************/
    SYS_ERR_HTTP_METHOD_NOT_ALLOWED(2001, "不支持当前请求方法，{0}"),
    SYS_ERR_HTTP_UNSUPPORTED_MEDIA_TYPE(2002, "不支持的媒体类型，{0}"),


    /****************************** 请求校验错误 **************************************/
    SYS_ERR_VALIDATION_MISSING_PARAMS(3001, "请求参数校验失败，缺少请求参数：{0}"),
    SYS_ERR_VALIDATION_PARAMS_ERROR(3002, "请求参数校验失败，{0}"),
    SYS_ERR_VALIDATION_PARAMS_TYPE_ERROR(3003, "请求参数校验失败，{0}:参数类型为{1}"),
    SYS_ERR_VALIDATION_PARAMS_JSON_TYPE_ERROR(3004, "请求参数校验失败，{0}：JSON格式不正确"),
    SYS_ERR_VALIDATION_BODY_JSON_TYPE_ERROR(3005, "请求参数校验失败，请求体JSON格式不正确"),

    /****************************** 用户管理 **************************************/
    SYS_ERR_LOGIN_FAIL(3006, "登录失败，用户名密码错误或该账号已被禁用！"),
    SYS_ERR_PHONE(3007, "座机格式错误"),
    SYS_ERR_DEL_ACCOUNT(3010, "用户不能删除自己的账号！"),
    SYS_ERR_ACCOUNT(3011, "该账号已存在！"),
    SYS_ERR_EXPIRE_CAPTCHA(3012, "验证码已过期！"),
    SYS_ERR_CAPTCHA(3013, "验证码输入错误！"),
    SYS_ERR_PASSWORD_ERROR(3014, "原密码输入错误！"),

    /****************************** 角色管理 **************************************/
    SYS_ERR_ROLE_REPETITION(3020, "角色名重复！"),
    SYS_ERR_ROLE_DELETE(3021, "该角色下有用户，无法删除！"),

    /****************************** 企业管理 **************************************/
    SYS_ERR_ENTPR_REPETITION(3030, "企业名称重复！"),

    /****************************** 部门管理 **************************************/
    SYS_ERR_DEPT_REPETITION(3040, "部门名称重复！"),
    SYS_ERR_DEPT_DELETE_USER(3041, "该部门中有人员，无法删除！"),
    SYS_ERR_DEPT_DELETE_CHILD(3041, "该部门中子部门，无法删除！"),

    /****************************** 权限管理 **************************************/
    SYS_ERR_MENU_CODE_REPETITION(3050, "菜单名称或权限码重复！"),
    SYS_ERR_DELETE_MENU(3051, "该菜单不可删除！");


    /****************************** 分隔符 **************************************/


    private Integer code;

    private String msg;

    private Object[] params;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return MessageFormat.format(msg, params);
    }

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public ErrorCodeEnum setParam(Object... param) {
        List<Object> list = new ArrayList<>(param.length);
        list.addAll(Arrays.asList(param));
        this.params = list.toArray(new Object[param.length]);
        return this;
    }

}
