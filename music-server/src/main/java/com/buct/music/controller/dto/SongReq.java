package com.buct.music.controller.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jeffrey
 * @version 1.0 2021/01/04 10:18
 */

@Data
public class SongReq implements Serializable {
    // 自增主键
    private Long id;

    // 对应歌手id
    private Long singerId;

    // 对应歌手姓名
    private String singerName;

    // 歌名
    private String name;

    // 歌手简介
    private String introduction;

    // 歌曲封面
    private String pic;

    // 歌曲对应的本地存放路径
    private String url;
}
