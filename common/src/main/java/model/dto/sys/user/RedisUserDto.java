package model.dto.sys.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import model.entity.sys.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @program: springBoot-demo - 副本
 * @description:
 * @author: fbl
 * @create: 2021-09-10 09:24
 **/
@Data
public class RedisUserDto implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;
    /**
     * 用户信息
     */
    private SysUser user;

    /**
     * 过期时间
     */
    private Long expireTime;

    private String token;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    private Long loginTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 用户登录时间
     *
     * @return
     */
    public Long getLoginTime() {
        return user.getLastLoginTime().getTime();
    }
}
