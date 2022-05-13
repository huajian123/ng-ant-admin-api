package com.demo.app.mapper.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.entity.sys.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-15 09:45
 **/
public interface PermissionMapper extends BaseMapper<SysPermission> {

    Set<String> selectPermissionByUserId(@Param("userId") Integer userId);

    List<Integer> selectPermission(@Param("menuName") String menuName,@Param("code") String code,@Param("id") Integer id);

    List<SysPermission> selectMenuPerByUserId(Integer userId);
}
