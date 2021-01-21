package com.buct.music.dao;

import com.buct.music.domin.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JeffCar
 * @date 1/3/2021 - 3:39 PM
 */
@Mapper
@Repository
public interface UserMapper {

    //添加用户
    int insert(User user);

    //删除用户
    int delete(Long id);

    //修改用户
    int update(User user);

    //根据主键查询用户
    User selectUserById(Long id);

    // 查询所有用户
    List<User> selectAllUsers();

    // 根据用户名字模糊查询
    List<User> selectUserByName(String username);

    // 验证密码
    int verifyPassword(String username, String password);

}
