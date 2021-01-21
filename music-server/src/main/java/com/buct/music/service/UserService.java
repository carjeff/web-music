package com.buct.music.service;

import com.buct.music.controller.dto.UserReq;
import com.buct.music.domin.Singer;
import com.buct.music.domin.User;

import java.util.List;

/**
 * @author JeffCar
 * @date 1/3/2021 - 4:47 PM
 */
public interface UserService {

    //根据id查找用户
    User selectUserById(Long id);

    //添加用户
    boolean insert(UserReq userReq);

    //删除用户
    boolean delete(Long id);

    //更新用户
    boolean update(UserReq userReq);

    //查找所有用户
    List<User> selectAllUsers();

    //根据username模糊查找用户
    List<User> selectUserByName(String username);

    boolean verifyPassword(String username, String password);
}
