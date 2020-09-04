package com.example.demo.controller;


import com.example.demo.common.Response;
import com.example.demo.common.ResponseCodeEnum;
import com.example.demo.model.dto.PermissionDTO;
import com.example.demo.model.dto.PermissionNodeDTO;
import com.example.demo.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author tsl
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    /**
     * 新建权限,包括父权限id、权限名、权限描述和权限类型
     *
     * @param permission
     * @return
     */
    @PostMapping("create")
    @RequiresPermissions("permission:create")
    public Response create(@RequestBody PermissionDTO permission) {
        if (permission.getParentId() == null) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "父权限id不能为空");
        }
        if (StringUtils.isBlank(permission.getName())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "权限名不能为空");
        }
        if (StringUtils.isBlank(permission.getDesc())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "权限描述不能为空");
        }
        permissionService.add(permission);
        return Response.ofSuccess();
    }

    /**
     * 更新权限，根据id更新权限描述
     *
     * @param permission
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("permission:update")
    public Response update(@RequestBody PermissionDTO permission) {
        if (permission.getId() == null) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "权限id不能为空");
        }
        if (StringUtils.isBlank(permission.getDesc())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "权限描述不能为空");
        }
        permissionService.update(permission);
        return Response.ofSuccess();
    }

    /**
     * 批量删除权限，根据权限id列表
     * 若删除父权限，则需要一起传递其所有子权限id
     *
     * @param idList
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("permission:delete")
    public Response deleteBatchByIds(@RequestBody List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "列表不能为空");
        }
        idList = idList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        permissionService.delete(idList);
        return Response.ofSuccess();
    }

    /**
     * 树状结构查询
     *
     * @param
     * @return
     */
    @GetMapping("queryByTree")
    @RequiresPermissions("permission:view")
    public Response queryByTree() {
        List<PermissionNodeDTO> roots = permissionService.queryByTree();
        return Response.ofSuccess(roots);
    }
}

