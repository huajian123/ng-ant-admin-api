package code;

/**
 * @program: fire_control
 * @description: 权限码配置
 * @author: fbl
 * @create: 2021-02-25 14:02
 **/
public interface PermissionCode {
    /**
     * 仪表盘
     */
    String DASHBOARD = "0001";

    /**
     * 表单页
     */
    String FROM_PAGE = "0002";

    /**
     * 列表页
     */
    String LIST_PAGE = "0003";

    /**
     * 详情页
     */
    String DETAIL_PAGE = "0004";

    /**
     * 结果页
     */
    String RESULT_PAGE = "0005";

    /**
     * 异常页
     */
    String EXCEPTION_PAGE = "0006";

    /**
     * 个人页
     */
    String PERSON_PAGE = "0007";

    /**
     * 内部管理
     */
    String INTERIOR_MANAGER = "0008";

    /**
     * 角色管理
     */
    String ROLE_MANAGER = "0018";

    /**
     * 角色管理新增
     */
    String ROLE_MANAGER_ADD = "0118";

    /**
     * 角色管理编辑
     */
    String ROLE_MANAGER_UPDATE = "0218";

    /**
     * 角色管理删除
     */
    String ROLE_MANAGER_DELETE = "0318";

    /**
     * 角色管理设置权限
     */
    String ROLE_MANAGER_SET_PERMISSION = "0418";

    /**
     * 角色管理批量删除
     */
    String ROLE_MANAGER_MORE_DELETE = "0518";

    /**
     * 用户管理
     */
    String USER_MANAGER = "0038";

    /**
     * 用户管理查看详情
     */
    String USER_MANAGER_DETAIL = "0138";

    /**
     * 用户管理编辑
     */
    String USER_MANAGER_UPDATE = "0238";

    /**
     * 日志管理
     */
    String LOG_MANAGER = "0048";














}
