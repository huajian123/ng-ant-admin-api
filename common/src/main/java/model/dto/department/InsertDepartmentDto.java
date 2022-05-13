package model.dto.department;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-04-01 10:21
 **/
@Data
public class InsertDepartmentDto {

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

    private Boolean state;

}
