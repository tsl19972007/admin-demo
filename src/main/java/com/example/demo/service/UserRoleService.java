package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.model.entity.UserRole;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author tsl
 * @since 2020-06-10
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {
    /**
     * 增加用户的角色
     *
     * @param userId
     * @param roleIds
     */
    public void addRolesByUserId(Long userId, List<Long> roleIds) {
        Preconditions.checkNotNull(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }

        List<UserRole> userRoleList = roleIds.stream().map(roleId ->
                new UserRole().setUserId(userId).setRoleId(roleId))
                .collect(Collectors.toList());
        saveBatch(userRoleList);
    }

    /**
     * 更新用户的角色(覆盖当前)
     *
     * @param userId
     * @param roleIds
     */
    public void updateRolesByUserId(Long userId, List<Long> roleIds) {
        deleteRolesByUserId(userId);
        addRolesByUserId(userId, roleIds);
    }

    /**
     * 删除用户的角色
     *
     * @param userId
     */
    public void deleteRolesByUserId(Long userId) {
        Preconditions.checkNotNull(userId);
        remove(new QueryWrapper<UserRole>().eq("user_id", userId));
    }

    /**
     * 获得用户的角色Id
     *
     * @param userId
     * @return
     */
    public List<Long> getRoleIdsByUserId(Long userId) {
        List<UserRole> userRoleList = list(new QueryWrapper<UserRole>()
                .eq("user_id", userId).select("role_id"));
        return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}
