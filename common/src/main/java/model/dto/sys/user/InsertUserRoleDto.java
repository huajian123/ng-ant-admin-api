package model.dto.sys.user;

import lombok.Data;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-01 09:45
 **/
@Data
public class InsertUserRoleDto {

    private Integer userId;

    private Integer roleId;
}
