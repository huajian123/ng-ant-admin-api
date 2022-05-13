package enums;


/**
 * @author fanbaolin
 * token配置项
 */

public enum TokenEnum {
    /**
     * token头
     */
    TOKEN_HEADER("Authorization"),

    /**
     * token前缀
     */
    TOKEN_PREFIX("Bearer "),

    /**
     * token秘钥
     */
    SECRET("ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI"),

    ISS("echisan"),

    /**
     * token过期时间 一个小时
     */
    EXPIRATION(3600),

    /**
     * 毫秒数
     */
    MILLIS_SECOND(1000),

    /**
     * 二十分钟的毫秒数
     */
    MILLIS_MINUTE_TEN(20 * 60 * 1000),

    /**
     * 一分钟的毫秒数
     */
    MILLIS_MINUTE(60000),

    /**
     * 选择记住我 七天
     */
    EXPIRATION_REMEMBER(604800),

    /**
     * 添加角色的key
     */
    ROLE_CLAIMS("rol"),

    /**
     * 角色id
     */
    USER_ID("userId");

    private String value;
    private Integer time;


    TokenEnum(String value) {
        this.value = value;
    }

    TokenEnum(Integer time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public Integer getTime() {
        return time;
    }
}
