package model.vo.sys;

import lombok.Data;

/**
 * @program: spring-security
 * @description: 登录返回信息
 * @author: fbl
 * @create: 2020-12-08 08:15
 **/
@Data
public class JwtUserVo {
    private Integer userId;
    private String userName;
    private String token;
}
