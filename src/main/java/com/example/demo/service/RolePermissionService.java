package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.RolePermissionMapper;
import com.example.demo.model.entity.RolePermission;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author tsl
 * @since 2020-08-18
 */
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {
    /**
     * 增加角色的权限
     *
     * @param roleId
     * @param permissionIds
     */
    public void addPermissionsByRoleId(Long roleId, List<Long> permissionIds) {
        Preconditions.checkNotNull(roleId);
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }

        List<RolePermission> userRoleList = permissionIds.stream().map(permissionId ->
                new RolePermission().setRoleId(roleId).setPermissionId(permissionId))
                .collect(Collectors.toList());
        saveBatch(userRoleList);
    }

    /**
     * 删除角色的权限
     *
     * @param roleId
     */
    public void deletePermissionsByRoleId(Long roleId) {
        Preconditions.checkNotNull(roleId);
        remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
    }

    /**
     * 更新角色的权限(覆盖当前)
     *
     * @param roleId
     * @param permissionIds
     */
    public void updatePermissionsByRoleId(Long roleId, List<Long> permissionIds) {
        deletePermissionsByRoleId(roleId);
        addPermissionsByRoleId(roleId, permissionIds);
    }

    /**
     * 获得角色的权限Id
     *
     * @param roleId
     * @return
     */
    public List<Long> getPermissionIdsByRoleId(Long roleId) {
        List<RolePermission> rolePermissionList = list(new QueryWrapper<RolePermission>()
                .eq("role_id", roleId).select("permission_id"));
        return rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
    }
}
