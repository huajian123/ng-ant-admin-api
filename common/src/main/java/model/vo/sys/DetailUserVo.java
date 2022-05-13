package model.vo.sys;

import lombok.Data;

import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-03 10:36
 **/
@Data
public class DetailUserVo {
    private Integer id;

    private String userName;

    private Boolean available;

    private List<String> roleName;

    private Integer sex;

    private Long mobile;

    private String email;

    private String telephone;

    private List<Integer> roleId;

    private String departmentName;
    private Integer departmentId;

}
