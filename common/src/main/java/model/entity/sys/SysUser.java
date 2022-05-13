package model.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @program: fire_control
 * @description: 用户实体类
 * @author: fbl
 * @create: 2021-01-12 08:28
 **/
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_name")
    private String userName;

    private String password;

    @TableField("is_available")
    private Boolean available;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp updateTime;

    private Integer sex;

    private Long mobile;

    private String email;

    @TableField("last_login_time")
    private Timestamp lastLoginTime;

    private Integer education;

    private String telephone;

    @TableField("department_id")
    private Integer departmentId;

}
