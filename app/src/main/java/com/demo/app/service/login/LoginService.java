package com.demo.app.service.login;

import com.demo.app.config.exception.UserPasswordNotMatchException;
import com.demo.app.config.jwt.JwtTokenUtils;
import com.demo.app.config.redis.RedisCache;
import com.demo.app.mapper.permission.PermissionMapper;
import com.demo.app.mapper.permission.RolePermissionMapper;
import com.demo.app.mapper.user.UserMapper;
import com.demo.app.mapper.user.UserRoleMapper;
import com.google.code.kaptcha.Producer;
import constant.Constants;
import enums.ErrorCodeEnum;
import model.dto.sys.user.RedisUserDto;
import model.dto.sys.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import result.Result;
import util.sign.Base64;
import util.uuid.IdUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-12 08:31
 **/
@Service
public class LoginService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    // 验证码类型
    @Value("${fbl.captchaType}")
    private String captchaType;

    /**
     * @return UserRoleDto
     * @Author fbl
     * @Description 登录
     * @Date 9:39 2021/1/18
     * @Param userInfo
     */
    public Result login(UserDto user) {
        String code = user.getCode();
        if (Objects.nonNull(code)) {
            String uuid = user.getUuid();
            // 有验证码
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                return Result.failure(ErrorCodeEnum.SYS_ERR_EXPIRE_CAPTCHA);
            }
            if (!code.equalsIgnoreCase(captcha)) {
                return Result.failure(ErrorCodeEnum.SYS_ERR_CAPTCHA);
            }
        }
        String userName = user.getUserName();
        String password = user.getPassword();
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException || e.getMessage().equals(ErrorCodeEnum.SYS_ERR_LOGIN_FAIL.getMsg())) {
                throw new UserPasswordNotMatchException();
            } else {
                throw new RuntimeException(e.getMessage());
            }
        }

        RedisUserDto redisUserDto = (RedisUserDto) authentication.getPrincipal();

        // 生产token
        String token = jwtTokenUtils.createToken(redisUserDto);
        return Result.success(token);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public Result captcha() {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_PRODUCE_CAPTCHA);
        }
        HashMap<String, String> res = new HashMap<>(2);
        res.put("uuid", uuid);
        res.put("img", Base64.encode(os.toByteArray()));
        return Result.success(res);
    }
}
