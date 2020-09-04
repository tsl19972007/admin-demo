package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.constant.Constants;
import com.example.demo.config.shiro.ShiroUtil;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.model.dto.PermissionDTO;
import com.example.demo.model.dto.PermissionNodeDTO;
import com.example.demo.model.entity.Permission;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author tsl
 * @since 2020-08-18
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {
    /**
     * 根据id获取权限
     *
     * @param id
     * @return
     */
    public Permission getPermissionById(Long id) {
        Preconditions.checkNotNull(id);
        return getById(id);
    }

    /**
     * 按照树形结构查询权限
     *
     * @return
     */
    public List<PermissionNodeDTO> queryByTree() {
        List<Permission> permissions = list();
        return getPermissionTree(permissions, Constants.PERMISSION_ROOT_ID);
    }

    //递归查询权限
    private List<PermissionNodeDTO> getPermissionTree(List<Permission> list, Long parentId) {
        List<PermissionNodeDTO> roots = list.stream().filter(p -> parentId.equals(p.getParentId()))
                .map(PermissionNodeDTO::new).collect(Collectors.toList());
        roots.forEach(root -> root.setChildren(getPermissionTree(list, root.getId())));
        return roots;
    }

    /**
     * 新建权限，包括父权限id、权限名、权限描述和权限类型
     *
     * @param permission
     */
    public void add(PermissionDTO permission) {
        Permission entity = new Permission();
        entity.setParentId(permission.getParentId()).setName(permission.getName())
                .setDesc(permission.getDesc()).setType(permission.getType())
                .setCreateBy(ShiroUtil.getCurrentUsername());
        save(entity);
    }

    /**
     * 更新权限，包括权限描述
     *
     * @param permission
     */
    public void update(PermissionDTO permission) {
        Preconditions.checkNotNull(permission.getId());
        Permission entity = new Permission();
        entity.setId(permission.getId()).setDesc(permission.getDesc());
        updateById(entity);
    }

    /**
     * 批量删除权限
     *
     * @param idList
     */
    public void delete(List<Long> idList) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(idList));
        //删除权限
        idList.forEach(this::delete);
    }

    private void delete(Long id) {
        Preconditions.checkNotNull(id);
        removeById(id);
    }

}
