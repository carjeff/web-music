package com.buct.music.domin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jeffrey
 * @version 1.0 2021/01/03 22:43
 */

@Data
public class Song implements Serializable {
    // 自增主键
    private Long id;

    // 对应歌手id
    private Long singerId;

    // 歌名
    private String name;

    // 歌手简介
    private String introduction;

    // 歌曲封面
    private String pic;

    // 歌曲对应的本地存放路径
    private String url;

    // 创建时间
    private Long createTime;

    // 更新时间
    private Long updateTime;
}
