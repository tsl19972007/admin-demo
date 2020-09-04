package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.ResponseCodeEnum;
import com.example.demo.config.shiro.ShiroUtil;
import com.example.demo.exception.BusinessException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.PageWrapper;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.dto.UserPageDTO;
import com.example.demo.model.entity.Permission;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tsl
 * @since 2020-05-26
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    public void add(UserDTO user) {
        if (getByUsername(user.getUsername()) != null) {
            throw new BusinessException(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户名已存在");
        }
        User entity = new User();
        //创建人、加盐、加密
        String salt = ShiroUtil.createSalt();
        entity.setUsername(user.getUsername()).setName(user.getName())
                .setSalt(salt)
                .setPassword(ShiroUtil.encrypt(user.getUsername(), salt))
                .setCreateBy(ShiroUtil.getCurrentUsername());
        save(entity);
        userRoleService.addRolesByUserId(entity.getId(), user.getRoleIds());
    }

    /**
     * 更新用户信息，包括姓名、角色
     *
     * @param user
     * @return
     */
    public void update(UserDTO user) {
        Preconditions.checkNotNull(user.getId());
        User entity = new User();
        entity.setId(user.getId()).setName(user.getName());
        updateById(entity);
        userRoleService.updateRolesByUserId(user.getId(), user.getRoleIds());
    }

    /**
     * （批量）删除用户
     *
     * @param idList
     */
    public void delete(List<Long> idList) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(idList));
        //删除用户以及对应的角色绑定信息
        idList.forEach(this::delete);
    }

    /**
     * 分页查询用户信息(根据name模糊查询)
     *
     * @param userQuery
     * @return
     */
    public PageWrapper<UserPageDTO> queryUserPage(UserQuery userQuery) {
        PageInfo<User> userPage;
        PageHelper.startPage(userQuery.getCurrent(), userQuery.getPageSize());
        if (StringUtils.isNotBlank(userQuery.getName())) {
            userPage = PageInfo.of(list(new QueryWrapper<User>().like("name", userQuery.getName())));
        } else {
            userPage = PageInfo.of(list());
        }
        //todo:目前pageHelper中pageInfo使用lambda转换list会丢失total,待更新
        return new PageWrapper<>(userPage.getList().stream()
                .map(user -> new UserPageDTO().setId(user.getId())
                        .setName(user.getName())
                        .setUsername(user.getUsername())
                        .setCreateTime(user.getCreateTime())
                        .setCreateBy(user.getCreateBy())
                        .setRoles(getRolesByUserId(user.getId()))).collect(Collectors.toList()),
                userPage.getTotal(), userPage.getPageNum(), userPage.getPageSize());
    }

    private void delete(Long id) {
        Preconditions.checkNotNull(id);
        removeById(id);
        userRoleService.deleteRolesByUserId(id);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    public User getByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }

    /**
     * 根据用户名查询用户对应角色名
     *
     * @param userId
     * @return
     */
    public List<String> getRoleNamesByUserId(Long userId) {
        List<Role> roles = getRolesByUserId(userId);
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    /**
     * 根据用户名查询用户对应权限名
     *
     * @param userId
     * @return
     */
    public List<String> getPermissionNamesByUserId(Long userId) {
        List<String> permissionNames = new ArrayList<>();
        List<Role> roles = getRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                List<Permission> permissions = roleService.getPermissionsByRoleId(role.getId());
                permissionNames.addAll(
                        permissions.stream().map(Permission::getName).collect(Collectors.toList()));
            }
        }
        return permissionNames;
    }

    private List<Role> getRolesByUserId(Long userId) {
        List<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
        return roleIds.stream().map(roleId -> roleService.getRoleById(roleId))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}
