package com.buct.music.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Zhang JunFeng
 * @date 2020/12/31 11:56
 */

@Mapper
@Repository
public interface AdminMapper {
    /**
     * 验证密码是否正确
     * @param username  账号
     * @param password  密码
     * @return 查询结果个数
     */
    int verifyPassword(@Param("username") String username, @Param("password") String password);
}
