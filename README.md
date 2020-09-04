# admin-demo
- 基于Spring Boot+antd pro的权限管理后台
- 实现了基于session的用户认证和基于RBAC的动态权限控制和系统管理(用户管理、角色管理和权限管理)

## 技术选型
- 应用框架:Spring Booot
- 持久层框架:MyBatis-Plus
- 安全框架:Shiro

- 前端框架:React
- 脚手架:Antd Pro

## 本地部署
- 创建数据库admin_demo,导入src/main/resources/schema.sql
- 修改application.properties内的MySQL配置的账号密码
- 启动服务，管理员账号密码为admin/admin

## 前端
https://github.com/tsl19972007/admin-demo-ui