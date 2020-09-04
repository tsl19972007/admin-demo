package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/8 13:37
 * @description：test of user
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Transactional
@Rollback
class UserTest {
    @Autowired
    UserService userService;

    @Test
    void testAdd() {
        User u = new User();
        u.setUsername("userName").setPassword("password").setSalt("salt")
                .setCreateBy("admin").setCreateTime(new Date());
        userService.save(u);
        log.info("userList:{}", userService.list());
    }

    @Test
    void testSelect() {
        User u = new User();
        u.setUsername("userName").setPassword("password").setSalt("salt")
                .setCreateBy("admin").setCreateTime(new Date());
        userService.save(u);
        log.info("userList:{}", userService.list());
        //查询
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.setEntity(new User().setUsername("userName"));
        log.info("userList:{}", userService.list(wrapper));
    }

    @Test
    void testUpdate() {
        User u = new User();
        u.setUsername("userName").setPassword("password").setSalt("salt")
                .setCreateBy("admin").setCreateTime(new Date());
        userService.save(u);
        log.info("userList:{}", userService.list());
        //根据Id更新
        u.setPassword("password2");
        userService.updateById(u);
        log.info("userList:{}", userService.list());
        //根据Wrapper更新
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.allEq(ImmutableMap.of("username", "userName", "password", "password2"))
                .set("password", "password3");
        userService.update(userUpdateWrapper);
        log.info("userList:{}", userService.list());
    }

    @Test
    void testDeleteById() {
        User u = new User();
        u.setUsername("userName").setPassword("password").setSalt("salt")
                .setCreateBy("admin").setCreateTime(new Date());
        userService.save(u);
        log.info("userList:{}", userService.list());
        userService.removeById(u.getId());
        log.info("userList:{}", userService.list());
    }

    @Test
    void testDeleteByCond() {
        User u1 = new User();
        User u2 = new User();
        u1.setUsername("userName").setPassword("password").setSalt("salt")
                .setCreateBy("admin").setCreateTime(new Date());
        u2.setUsername("userName2").setPassword("password").setSalt("salt")
                .setCreateBy("admin").setCreateTime(new Date());
        userService.saveBatch(Lists.newArrayList(u1, u2));
        log.info("userList:{}", userService.list());
        //利用wrapper删除，可以定制化sql
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("username", "Name2");
        userService.remove(userQueryWrapper);
        log.info("userList:{}", userService.list());
        //利用removeByMap删除
        userService.removeByMap(ImmutableMap.of("username", "userName"));
        log.info("userList:{}", userService.list());
    }
}
