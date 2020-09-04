DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(100) NOT NULL COMMENT '姓名',
    `username`    varchar(100) NOT NULL COMMENT '用户名',
    `password`    varchar(100) NOT NULL COMMENT '密码',
    `salt`        varchar(100) NOT NULL COMMENT '盐值',
    `create_by`   varchar(100) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_sys_user_username` (`username`)
) COMMENT ='用户表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(100) NOT NULL COMMENT '角色名称',
    `desc`        varchar(100) DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(100) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) COMMENT ='角色表';

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '编号',
    `type`        tinyint(1)   NOT NULL COMMENT '权限类型(0菜单 1按钮)',
    `name`        varchar(100) DEFAULT NULL COMMENT '权限名称(权限字符串)',
    `desc`        varchar(100) NOT NULL COMMENT '权限描述',
    `parent_id`   bigint(20)   DEFAULT NULL COMMENT '父编号',
    `create_by`   varchar(100) DEFAULT NULL COMMENT '创建人',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) COMMENT ='权限表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `user_id` bigint(20) NOT NULL COMMENT '用户编号',
    `role_id` bigint(20) NOT NULL COMMENT '角色编号',
    PRIMARY KEY (`id`)
) COMMENT ='用户角色关联表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `role_id`       bigint(20) NOT NULL COMMENT '角色编号',
    `permission_id` bigint(20) NOT NULL COMMENT '权限编号',
    PRIMARY KEY (`id`)
) COMMENT ='角色权限关联表';

# 添加默认权限、角色和用户
INSERT INTO `sys_permission`(`type`, `name`, `desc`, `create_by`) value (0, "root", "根权限", "admin");
INSERT INTO `sys_permission`(`type`, `name`, `desc`, `parent_id`, `create_by`) value (1, "sys", "系统管理", "1", "admin");
INSERT INTO `sys_permission`(`type`, `name`, `desc`, `parent_id`, `create_by`) value (1, "user", "用户管理", "2", "admin");
INSERT INTO `sys_permission`(`type`, `name`, `desc`, `parent_id`, `create_by`) value (1, "role", "角色管理", "2", "admin");
INSERT INTO `sys_permission`(`type`, `name`, `desc`, `parent_id`, `create_by`) value (1, "permission", "权限管理", "2", "admin");

INSERT INTO `sys_role`(`name`, `desc`, `create_by`) value ("admin", "管理员", "admin");

INSERT INTO `sys_user`(`name`, `username`, `password`, `salt`, `create_by`) value ("管理员", "admin", "b073e9301c412431159e7075340c4c66", "salt", "admin");

INSERT INTO `sys_user_role`(`user_id`, `role_id`) value (1, 1);

INSERT INTO `sys_role_permission`(`role_id`, `permission_id`) value (1, 2);
INSERT INTO `sys_role_permission`(`role_id`, `permission_id`) value (1, 3);
INSERT INTO `sys_role_permission`(`role_id`, `permission_id`) value (1, 4);
INSERT INTO `sys_role_permission`(`role_id`, `permission_id`) value (1, 5);