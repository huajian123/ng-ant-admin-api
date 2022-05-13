package model.vo.department;

import lombok.Data;
import model.entity.sys.SysUser;

import java.sql.Timestamp;
import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-04-01 10:05
 **/
@Data
public class DepartmentDetailVo {
    private Integer id;

    /**
     * 所属企业id
     */
    private Integer entprId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门描述
     */
    private String departmentDesc;

    /**
     * 部门领导用户id
     */
    private Integer departmentLeaderId;

    /**
     * 部门领导用户姓名
     */
    private String departmentLeaderName;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp updateTime;

}
