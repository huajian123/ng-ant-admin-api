package com.demo.app.controller.department;

import com.demo.app.service.department.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.del.BatchDeleteDto;
import model.dto.department.InsertDepartmentDto;
import model.dto.department.UpdateDepartmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import result.Result;
import util.SearchFilter;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-04-01 09:56
 **/
@RestController
@Validated
@Api(value = "DepartmentController", tags = "部门管理")
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/list")
    @ApiOperation(value = "展示部门信息")
    @PreAuthorize("@ss.hasPer('default:system:dept')")
    public Result departmentList(@RequestBody @Validated SearchFilter searchFilter) {
        return departmentService.departmentList(searchFilter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "展示部门详情")
    @PreAuthorize("@ss.hasPer('default:system:dept')")
    public Result departmentDetail(@PathVariable Integer id) {
        return departmentService.departmentDetail(id);
    }

    @PutMapping
    @ApiOperation(value = "修改部门信息")
    @PreAuthorize("@ss.hasPer('default:system:account:edit')")
    public Result updateDepartment(@RequestBody @Validated UpdateDepartmentDto updateDepartmentDto) {
        return departmentService.updateDepartment(updateDepartmentDto);
    }

    @PostMapping
    @ApiOperation(value = "新增部门信息")
    @PreAuthorize("@ss.hasPer('default:system:account:add')")
    public Result addDepartment(@RequestBody @Validated InsertDepartmentDto insertDepartmentDto) {
        return departmentService.addDepartment(insertDepartmentDto);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除部门信息")
    @PreAuthorize("@ss.hasPer('default:system:dept:del')")
    public Result delDepartment(@RequestBody @Validated BatchDeleteDto batchDeleteDto) {
        return departmentService.delDepartment(batchDeleteDto);
    }

}
