package com.buct.music.service.impl;

import com.buct.music.controller.dto.UserReq;
import com.buct.music.dao.UserMapper;
import com.buct.music.domin.User;
import com.buct.music.service.UserService;
import com.buct.music.utils.UserServiceUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author JeffCar
 * @date 1/3/2021 - 4:48 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper =userMapper;
    }


    @Override
    public User selectUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public boolean insert(UserReq userReq) {
        User user = UserServiceUtil.setUser(userReq);
        long time = System.currentTimeMillis();
        user.setCreateTime(time);
        user.setUpdateTime(time);
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean delete(Long id) {

        return userMapper.delete(id) > 0;
    }

    @Override
    public boolean update(UserReq userReq) {
        User user = UserServiceUtil.setUser(userReq);
        long time = System.currentTimeMillis();
        user.setUpdateTime(time);
        return userMapper.update(user) > 0;
    }

    @Override
    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public List<User> selectUserByName(String username) {
        return userMapper.selectUserByName("%" + username + "%");
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        return userMapper.verifyPassword(username, password) > 0;
    }
}
