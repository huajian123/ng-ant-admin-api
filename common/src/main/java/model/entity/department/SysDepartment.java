package model.entity.department;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.core.annotation.Order;

import java.sql.Timestamp;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-04-01 09:14
 **/
@Data
@TableName("sys_department")
public class SysDepartment {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    @TableField("department_name")
    private String departmentName;


    /**
     * 父级菜单id 默认为0
     */
    @TableField("father_id")
    private Integer fatherId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Timestamp updateTime;

    /**
     * 部门状态 0 禁用 1 可用
     */
    @TableField("state")
    private Boolean state;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;
}
