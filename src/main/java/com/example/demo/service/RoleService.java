package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.config.shiro.ShiroUtil;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.model.dto.PageWrapper;
import com.example.demo.model.dto.RoleDTO;
import com.example.demo.model.dto.RolePageDTO;
import com.example.demo.model.entity.Permission;
import com.example.demo.model.entity.Role;
import com.example.demo.model.query.PageQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author tsl
 * @since 2020-06-10
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    PermissionService permissionService;

    /**
     * 根据id获取角色
     *
     * @param id
     * @return
     */
    public Role getRoleById(Long id) {
        Preconditions.checkNotNull(id);
        return getById(id);
    }

    /**
     * 新增角色，包括角色名、角色描述和角色权限
     *
     * @param role
     */
    public void add(RoleDTO role) {
        Role entity = new Role();
        entity.setName(role.getName()).setDesc(role.getDesc())
                .setCreateBy(ShiroUtil.getCurrentUsername());
        save(entity);
        rolePermissionService.addPermissionsByRoleId(entity.getId(), role.getPermissionIds());
    }

    /**
     * 更新角色，根据id更新角色描述和角色权限
     *
     * @param role
     */
    public void update(RoleDTO role) {
        Preconditions.checkNotNull(role.getId());
        Role entity = new Role();
        entity.setId(role.getId()).setDesc(role.getDesc());
        updateById(entity);
        rolePermissionService.updatePermissionsByRoleId(role.getId(), role.getPermissionIds());
    }

    /**
     * （批量）删除角色
     *
     * @param idList
     */
    public void delete(List<Long> idList) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(idList));
        //删除角色以及对应的权限绑定信息
        idList.forEach(this::delete);
    }

    /**
     * 分页查询角色信息
     *
     * @param pageQuery
     * @return
     */
    public PageWrapper<RolePageDTO> queryRolePage(PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getCurrent(), pageQuery.getPageSize());
        PageInfo<Role> rolePage = PageInfo.of(list());
        //todo:目前pageHelper中pageInfo使用lambda转换list会丢失total,待更新
        return new PageWrapper<>(rolePage.getList().stream()
                .map(role -> new RolePageDTO().setId(role.getId())
                        .setName(role.getName())
                        .setDesc(role.getDesc())
                        .setCreateTime(role.getCreateTime())
                        .setCreateBy(role.getCreateBy())
                        .setPermissions(getPermissionsByRoleId(role.getId()))).collect(Collectors.toList()),
                rolePage.getTotal(), rolePage.getPageNum(), rolePage.getPageSize());
    }

    private void delete(Long id) {
        Preconditions.checkNotNull(id);
        removeById(id);
        rolePermissionService.deletePermissionsByRoleId(id);
    }

    public List<Permission> getPermissionsByRoleId(Long roleId) {
        List<Long> roleIds = rolePermissionService.getPermissionIdsByRoleId(roleId);
        return roleIds.stream().map(permissionId -> permissionService.getPermissionById(permissionId))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}
