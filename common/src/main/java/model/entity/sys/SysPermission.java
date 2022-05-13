package model.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 【请填写功能名称】对象 sys_permission_up
 *
 * @author fbl
 * @date 2022-03-31
 */
@Data
@TableName("sys_permission")
public class SysPermission {

    /**
     * ${column.columnComment}
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 菜单名字
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 菜单权限码
     */
    @TableField("code")
    private String code;
    /**
     * 父级菜单id 默认为0
     */
    @TableField("father_id")
    private Integer fatherId;
    /**
     * 显示顺序
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 路由地址
     */
    @TableField("path")
    private String path;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField("menu_type")
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    @TableField("visible")
    private Boolean visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField("status")
    private Boolean status;
    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;
    /**
     * 阿里icon
     */
    @TableField("al_icon")
    private String alIcon;
    /**
     * 是否是外链 0 否 1 是
     */
    @TableField("is_new_link")
    private Boolean newLinkFlag;
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

}
