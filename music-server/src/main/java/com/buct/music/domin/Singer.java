package com.buct.music.domin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhang JunFeng
 * @date 2021/1/1 22:34
 */
@Data
public class Singer implements Serializable {
    // 自增主键
    private Long id;

    // 歌手名
    private String name;

    // 歌手性别：1：男；0：女; 2：组合
    private Integer sex;

    // 头像
    private String pic;

    // 生日
    private Date birth;

    // 地区
    private String location;

    // 简介
    private String introduction;

    // 创建时间
    private Long createTime;

    // 更新时间
    private Long updateTime;

}
