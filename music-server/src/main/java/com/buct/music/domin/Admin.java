package com.buct.music.domin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhang JunFeng
 * @date 2020/12/31 11:50
 */

@Data
public class Admin implements Serializable {
    // 自增主键
    private Long id;

    // 账号
    private String username;

    // 密码
    private String password;

}
