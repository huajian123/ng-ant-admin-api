package model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-15 09:31
 **/
@Data
@TableName("user_role")
public class UserRole {

    @TableField("user_id")
    private Integer userId;

    @TableField("role_id")
    private Integer roleId;
}
