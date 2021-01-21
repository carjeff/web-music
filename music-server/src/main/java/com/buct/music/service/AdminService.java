package com.buct.music.service;

/**
 * @author Zhang JunFeng
 * @date 2020/12/31 13:12
 */
public interface AdminService {

    // 验证账号和密码
    boolean verifyPassword(String username, String password);
}
