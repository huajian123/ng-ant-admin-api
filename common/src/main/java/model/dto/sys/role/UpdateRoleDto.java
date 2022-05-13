package model.dto.sys.role;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-19 10:30
 **/
@Data
public class UpdateRoleDto {

    @NotNull
    private Integer id;

    @NotNull
    private String roleName;

    private String roleDesc;
}
