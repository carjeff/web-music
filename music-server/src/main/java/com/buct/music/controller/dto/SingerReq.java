package com.buct.music.controller.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhang JunFeng
 * @date 2021/1/2 11:10
 */
@Data
public class SingerReq implements Serializable {
    //主键id
    private Long id;

    // 歌手名
    private String name;

    // 歌手性别：1：男；0：女; 2: 组合
    private Integer sex;

    // 头像
    private String pic;

    // 生日
    private Date birth;

    // 地区
    private String location;

    // 简介
    private String introduction;
}
