package com.example.demo.controller;


import com.example.demo.common.Response;
import com.example.demo.common.ResponseCodeEnum;
import com.example.demo.model.dto.PageWrapper;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.model.dto.RolePageDTO;
import com.example.demo.model.entity.Role;
import com.example.demo.model.query.PageQuery;
import com.example.demo.service.RoleService;
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
 * 角色表 前端控制器
 * </p>
 *
 * @author tsl
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * 新建角色,包括角色名、角色描述和角色权限
     *
     * @param role
     * @return
     */
    @PostMapping("create")
    @RequiresPermissions("role:create")
    public Response create(@RequestBody RoleDTO role) {
        if (StringUtils.isBlank(role.getName())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "角色名不能为空");
        }
        if (StringUtils.isBlank(role.getDesc())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "角色描述不能为空");
        }
        if (CollectionUtils.isEmpty(role.getPermissionIds())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "角色权限不能为空");
        }
        roleService.add(role);
        return Response.ofSuccess();
    }

    /**
     * 更新角色，根据id更新角色描述和角色权限
     *
     * @param role
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("role:update")
    public Response update(@RequestBody RoleDTO role) {
        if (role.getId() == null) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "角色id不能为空");
        }
        if (StringUtils.isBlank(role.getDesc())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "角色描述不能为空");
        }
        if (CollectionUtils.isEmpty(role.getPermissionIds())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "角色权限不能为空");
        }
        roleService.update(role);
        return Response.ofSuccess();
    }

    /**
     * 批量删除角色，根据角色id列表
     *
     * @param idList
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("role:delete")
    public Response deleteBatchByIds(@RequestBody List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "列表不能为空");
        }
        idList = idList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        roleService.delete(idList);
        return Response.ofSuccess();
    }

    /**
     * 分页查询
     *
     * @param pageQuery
     * @return
     */
    @GetMapping("queryByPage")
    @RequiresPermissions("user:view")
    public Response queryByPage(PageQuery pageQuery) {
        PageWrapper<RolePageDTO> pageWrapper = roleService.queryRolePage(pageQuery);
        return Response.ofSuccess(pageWrapper);
    }

    /**
     * 获取所有角色(用于选择)
     *
     * @return
     */
    @GetMapping("query")
    public Response query() {
        List<Role> roleList = roleService.list();
        return Response.ofSuccess(roleList);
    }

}

