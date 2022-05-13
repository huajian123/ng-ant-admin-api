package model.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-15 09:51
 **/
@Data
@TableName("role_permission")
public class RolePermission {

    @TableField("role_id")
    private Integer roleId;

    @TableField("permission_id")
    private Integer permissionId;
}
