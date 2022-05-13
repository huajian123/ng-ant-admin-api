package model.dto.sys.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: fire_control
 * @description: 设置角色权限dto
 * @author: fbl
 * @create: 2021-01-25 08:31
 **/
@Data
public class UpdateRolePermissionDto {

    @NotNull
    private Integer roleId;

    @NotNull
    private List<Integer> permissionIds;
}
