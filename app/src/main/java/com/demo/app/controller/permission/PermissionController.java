package com.demo.app.controller.permission;

import com.demo.app.service.permission.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.sys.permission.InsertPermissionDto;
import model.dto.sys.permission.UpdatePermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

/**
 * @program: SysPermissionUpController层
 * @description:
 * @author fbl
 * @date 2022-03-31
 **/
@RestController
@Validated
@Api(value = "SysPermissionUpController", tags = "权限管理")
@RequestMapping("/api/sysPermission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @PostMapping("/list")
    @ApiOperation(value = "查询权限表")
    @PreAuthorize("@ss.hasPer('default:system:menu')")
    public Result selectSysPermissionList(@RequestBody @Validated SearchFilter searchFilter){
        return  permissionService.selectSysPermissionList(searchFilter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询权限表详情")
    @PreAuthorize("@ss.hasPer('default:system:menu')")
    public Result sysPermissionDetail(@PathVariable Integer id){
        return  permissionService.selectSysPermissionById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改权限表信息")
    @PreAuthorize("@ss.hasPer('default:system:menu:edit')")
    public Result updateSysPermission(@RequestBody @Validated UpdatePermissionDto updatePermissionDto){
        return permissionService.updateSysPermission(updatePermissionDto);
    }

    @PostMapping
    @ApiOperation(value = "新增权限表信息")
    @PreAuthorize("@ss.hasPer('default:system:menu:add')")
    public Result addSysPermission(@RequestBody @Validated InsertPermissionDto sysPermissionDto){
        return permissionService.insertSysPermission(sysPermissionDto);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除权限表信息")
    @PreAuthorize("@ss.hasPer('default:system:menu:del')")
    public Result delSysPermission(@RequestBody @Validated BatchDeleteDto batchDeleteDto){
        return permissionService.deleteSysPermissionByIds(batchDeleteDto.getIds());
    }

    @GetMapping("/menu/{userId}")
    @ApiOperation(value = "获取用户菜单权限")
    public Result selectUserMenuPer(@PathVariable Integer userId) {
        return permissionService.selectUserMenuPer(userId);
    }

}
