package com.buct.music.controller.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JeffCar
 * @date 1/3/2021 - 4:34 PM
 */
@Data
public class UserReq implements Serializable {
    //自增主键
    private Long id;

    //用户名
    private String username;

    //用户密码
    private String password;

    //用户性别 1 : 男  0 ：女
    private Boolean sex;

    //电话
    private String phoneNumber;

    //邮箱
    private String email;

    //生日
    private Date birth;

    //签名
    private String introduction;

    //地址
    private String location;

    //头像
    private String avator;
}
