package model.dto.sys.permission;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 【请填写功能名称】对象 sys_permission
 *
 * @author fbl
 * @date 2022-03-31
 */
@Data
public class UpdatePermissionDto {

    /**
     * ${column.columnComment}
     */
    @NotNull
    private Integer id;
    /**
     * 菜单名字
     */
    @NotBlank
    private String menuName;
    /**
     * 菜单权限码
     */
    @NotBlank
    private String code;
    /**
     * 父级菜单id 默认为0
     */
    @NotNull
    private Integer fatherId;
    /**
     * 显示顺序
     */
    @NotNull
    private Integer orderNum;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotBlank
    @Size(max = 1)
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
     * 菜单图标
     */
    private String alIcon;

    /**
     * 是否是外链 0 否 1 是
     */
    @NotNull
    private Boolean newLinkFlag;


}
