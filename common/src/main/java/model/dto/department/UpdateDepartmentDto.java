package model.dto.department;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-04-01 10:14
 **/
@Data
public class UpdateDepartmentDto {
    @NotNull
    private Integer id;

    /**
     * 部门名称
     */
    @NotBlank
    private String departmentName;

    @NotNull
    private Integer fatherId;
    /**
     * 排序
     */
    @NotNull
    private Integer orderNum;
    @NotNull
    private Boolean state;
    

}
