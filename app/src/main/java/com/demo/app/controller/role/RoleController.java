package com.demo.app.controller.role;

import com.demo.app.service.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.sys.user.InsertRoleDto;
import model.dto.sys.role.UpdateRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

import javax.validation.constraints.NotNull;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-19 10:23
 **/
@RestController
@Validated
@Api(value = "UserRoleController", tags = "角色管理")
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/list")
    @PreAuthorize("@ss.hasPer('default:system:role-manager')")
    @ApiOperation(value = "展示角色信息")
    public Result roleList(@RequestBody @Validated SearchFilter searchFilter) {
        return roleService.roleList(searchFilter);
    }

    @PostMapping
    @PreAuthorize("@ss.hasPer('default:system:role-manager:add')")
    @ApiOperation(value = "新增角色信息")
    public Result insertRole(@RequestBody @Validated InsertRoleDto roleDto) {
        return roleService.insertRole(roleDto);
    }

    @PutMapping
    @PreAuthorize("@ss.hasPer('default:system:role-manager:edit')")
    @ApiOperation(value = "修改角色信息")
    public Result updateRole(@RequestBody UpdateRoleDto roleDto) {
        return roleService.updateRole(roleDto);
    }

    @PostMapping("/del")
    @PreAuthorize("@ss.hasPer('default:system:role-manager:del')")
    @ApiOperation(value = "批量删除角色信息")
    public Result batchDeleteRole(@RequestBody @Validated BatchDeleteDto batchDeleteRoleDto) {
        return roleService.batchDeleteRole(batchDeleteRoleDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPer('default:system:role-manager')")
    @ApiOperation(value = "查询角色详情")
    public Result getRole(@PathVariable Integer id) {
        return roleService.getRole(id);
    }

}
