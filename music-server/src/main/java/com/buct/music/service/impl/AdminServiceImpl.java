package com.buct.music.service.impl;

import com.buct.music.dao.AdminMapper;
import com.buct.music.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author Zhang JunFeng
 * @date 2020/12/31 13:13
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
     *
     * @param username  账号
     * @param password  密码
     * @return  存在：true，否则：false
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username, password) > 0;
    }
}
