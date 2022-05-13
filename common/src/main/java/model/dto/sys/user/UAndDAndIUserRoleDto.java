package model.dto.sys.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: fire_control
 * @description: 修改删除插入用户角色
 * @author: fbl
 * @create: 2021-01-18 11:00
 **/
@Data
public class UAndDAndIUserRoleDto {
    @NotNull
    private Integer userId;

    @NotNull
    private List<Integer> roleId;
}
