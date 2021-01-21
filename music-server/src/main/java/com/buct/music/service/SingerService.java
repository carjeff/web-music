package com.buct.music.service;

import com.buct.music.controller.dto.SingerReq;
import com.buct.music.domin.Singer;

import java.util.List;

/**
 * @author Zhang JunFeng
 * @date 2021/1/2 10:40
 */
public interface SingerService {
    // 增加歌手
    boolean insert(SingerReq singerReq);

    // 需改歌手
    boolean update(SingerReq singerReq);

    // 删除歌手
    boolean delete(List<Long> idList);

    // 根据主键查询整个对象
    Singer selectByPrimaryKey(Long id);

    // 查询所有歌手
    List<Singer> selectAllSinger();

    // 根据歌手名字模糊查询
    List<Singer> selectByName(String name);

    // 根据男女查询
    List<Singer> selectBySex(Integer sex);

    // 根据歌手名查找歌手id
    Long getSingerId(String name);
}
