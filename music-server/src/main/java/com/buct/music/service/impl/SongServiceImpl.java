package com.buct.music.service.impl;

import com.buct.music.controller.dto.SongReq;
import com.buct.music.dao.SingerMapper;
import com.buct.music.domin.Song;
import com.buct.music.service.SongService;
import com.buct.music.utils.SongServiceUtil;
import org.springframework.stereotype.Service;
import com.buct.music.dao.SongMapper;

import java.util.List;

/**
 * @author Jeffrey
 * @version 1.0 2021/01/04 11:03
 */

@Service
public class SongServiceImpl implements SongService {
    private final SongMapper songMapper;
    private final SingerMapper singerMapper;

    // 构造函数
    public SongServiceImpl(SongMapper songMapper, SingerMapper singerMapper) {
        this.songMapper = songMapper;
        this.singerMapper = singerMapper;
    }

    // 增加歌曲
    @Override
    public boolean insert(SongReq songReq) {
        SongServiceUtil songServiceUtil = new SongServiceUtil(singerMapper);
        Song song = songServiceUtil.songUtil(songReq);
        long timeNow = System.currentTimeMillis();
        song.setCreateTime(timeNow);
        song.setUpdateTime(timeNow);
        return songMapper.insert(song) > 0;
    }

    // 更新歌曲
    @Override
    public boolean update(SongReq songReq) {
        SongServiceUtil songServiceUtil = new SongServiceUtil(singerMapper);
        Song song = songServiceUtil.songUtil(songReq);
        long timeNow = System.currentTimeMillis();
        song.setUpdateTime(timeNow);
        return songMapper.update(song) > 0;
    }

    // 删除歌曲
    @Override
    public boolean delete(Long id) {
        return songMapper.delete(id) > 0;
    }

    // 根据id查找歌曲
    @Override
    public Song selectByPrimaryKey(Long id) {
        return songMapper.selectByPrimaryKey(id);
    }

    // 查找所有歌曲
    @Override
    public List<Song> selectAllSongs() {
        return songMapper.selectAllSongs();
    }

    // 根据歌曲姓名查找歌曲
    @Override
    public List<SongReq> selectByName(String name) {
        String likeName = name.trim();
        return songMapper.selectByName("%" + likeName + "%");
    }

    // 根据简介查找歌曲
    @Override
    public List<SongReq> selectByIntroduction(String keyword) {
        String likeString = keyword.trim();
        return songMapper.selectByIntroduction("%" + likeString + "%");
    }

    // 根据歌手姓名查找歌曲
    @Override
    public List<SongReq> selectBySinger(String name) {
        String likeName = name.trim();
        SingerServiceImpl singerServiceImpl = new SingerServiceImpl(singerMapper);
        Long tempId = singerServiceImpl.getSingerId(likeName);
        return songMapper.selectBySinger(tempId);
    }

    // 返回带歌手姓名的歌曲数据
    @Override
    public List<SongReq> getAllSongsWithSinger() {
        return songMapper.selectAllSongsWithSingerName();
    }
}
