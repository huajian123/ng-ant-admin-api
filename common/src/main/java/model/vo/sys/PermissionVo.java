package model.vo.sys;

import lombok.Data;

import java.util.Collections;
import java.util.List;


/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-22 15:49
 **/
@Data
public class PermissionVo {
    private Integer id;

    private Integer fatherId;

    private String menuName;

    private String code;

    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 菜单类型（C菜单 F按钮）
     */
    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */
    private Boolean visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    private Boolean status;
    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 阿里icon
     */
    private String alIcon;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否是外链 0 否 1 是
     */
    private Boolean newLinkFlag;

    private List<PermissionVo> permissionVo = Collections.emptyList();
}
