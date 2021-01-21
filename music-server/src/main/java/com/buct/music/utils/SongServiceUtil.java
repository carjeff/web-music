package com.buct.music.utils;

import com.buct.music.controller.dto.SongReq;
import com.buct.music.dao.SingerMapper;
import com.buct.music.domin.Song;

/**
 * @author Jeffrey
 * @version 2.0 2021/01/05 10:31
 */

public class SongServiceUtil {
    private final SingerMapper singerMapper;

    public SongServiceUtil(SingerMapper singerMapper) {
        this.singerMapper = singerMapper;
    }

    public Song songUtil(SongReq songReq) {
        Long id = songReq.getId();
        String name = songReq.getName();
        Long singerId = songReq.getSingerId();
        String singerName = songReq.getSingerName();
        String pic = songReq.getPic();
        String url = songReq.getUrl();
        String introduction = songReq.getIntroduction();

        Song song = new Song();
        if (id != null) {
            song.setId(id);
        }
        if (singerId != null) {
            song.setSingerId(singerId);
        }
        if (singerName != null) {
            // 先要根据歌手姓名调用歌手id;
            Long tempSingerId = singerMapper.getSingerId(singerName);
            song.setSingerId(tempSingerId);
        }
        if (name != null) {
            song.setName(name.trim());
        }
        if (pic != null) {
            song.setPic(pic.trim());
        }
        if (url != null) {
            song.setUrl(url.trim());
        }
        if (introduction != null) {
            song.setIntroduction(introduction.trim());
        }
        return song;
    }
}
