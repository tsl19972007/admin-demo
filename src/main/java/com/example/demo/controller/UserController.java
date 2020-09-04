package com.example.demo.controller;


import com.example.demo.common.Response;
import com.example.demo.common.ResponseCodeEnum;
import com.example.demo.config.shiro.ShiroUtil;
import com.example.demo.config.shiro.UserDetail;
import com.example.demo.model.dto.PageWrapper;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.dto.UserPageDTO;
import com.example.demo.model.query.UserQuery;
import com.example.demo.service.UserService;
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
 * 用户表 前端控制器
 * </p>
 *
 * @author tsl
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 新建用户,包括用户姓名、用户名和用户角色
     *
     * @param user
     * @return
     */
    @PostMapping("create")
    @RequiresPermissions("user:create")
    public Response create(@RequestBody UserDTO user) {
        if (StringUtils.isBlank(user.getName())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户姓名不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户名不能为空");
        }
        if (CollectionUtils.isEmpty(user.getRoleIds())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户角色不能为空");
        }
        userService.add(user);
        return Response.ofSuccess();
    }

    /**
     * 更新用户，根据用户id更新用户姓名和用户角色
     *
     * @param user
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("user:update")
    public Response update(@RequestBody UserDTO user) {
        if (user.getId() == null) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户id不能为空");
        }
        if (StringUtils.isBlank(user.getName())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户姓名不能为空");
        }
        if (CollectionUtils.isEmpty(user.getRoleIds())) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户角色不能为空");
        }
        userService.update(user);
        return Response.ofSuccess();
    }

    /**
     * 批量删除用户，根据用户id列表
     *
     * @param idList
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("user:delete")
    public Response deleteBatchByIds(@RequestBody List<Long> idList) {
        if (isSelf(idList)) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "不能删除自己");
        }
        if (CollectionUtils.isEmpty(idList)) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "列表不能为空");
        }
        idList = idList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        userService.delete(idList);
        return Response.ofSuccess();
    }

    /**
     * 分页查询，根据分页参数和姓名模糊查询
     *
     * @param userQuery
     * @return
     */
    @GetMapping("queryByPage")
    @RequiresPermissions("user:view")
    public Response queryByPage(UserQuery userQuery) {
        PageWrapper<UserPageDTO> pageWrapper = userService.queryUserPage(userQuery);
        return Response.ofSuccess(pageWrapper);
    }

    private boolean isSelf(List<Long> idList) {
        UserDetail userDetail = ShiroUtil.getCurrentUser();
        return idList.stream().anyMatch(id -> id.equals(userDetail.getUser().getId()));
    }

}

