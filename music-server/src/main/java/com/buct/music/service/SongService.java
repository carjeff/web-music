package com.buct.music.service;

import com.buct.music.controller.dto.SongReq;
import com.buct.music.domin.Song;

import java.util.List;

/**
 * @author Jeffrey
 * @version 2021/01/04 10:57
 */
public interface SongService {
    // 增加歌曲
    boolean insert(SongReq songReq);

    // 修改歌曲
    boolean update(SongReq songReq);

    // 删除歌曲
    boolean delete(Long id);

    // 根据主键查询整个对象
    Song selectByPrimaryKey(Long id);

    // 查询所有歌手
    List<Song> selectAllSongs();

    // 根据歌曲名字模糊查询
    List<SongReq> selectByName(String name);

    // 根据歌手名字模糊查询
    List<SongReq> selectBySinger(String name);

    // 返回带歌手名字的歌曲数据
    List<SongReq> getAllSongsWithSinger();

    // 根据简介查询歌曲
    List<SongReq> selectByIntroduction(String keyword);
}
