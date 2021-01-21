package com.buct.music.service.impl;

import com.buct.music.controller.dto.SingerReq;
import com.buct.music.dao.SingerMapper;
import com.buct.music.domin.Singer;
import com.buct.music.service.SingerService;
import com.buct.music.utils.SingerServiceUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhang JunFeng
 * @date 2021/1/2 10:41
 */
@Service
public class SingerServiceImpl implements SingerService {

    private final SingerMapper singerMapper;

    public SingerServiceImpl(SingerMapper singerMapper) {
        this.singerMapper = singerMapper;
    }

    // 增加歌手
    @Override
    public boolean insert(SingerReq singerReq) {
        Singer singer = SingerServiceUtil.singerUtil(singerReq);
        long timeNow = System.currentTimeMillis();
        singer.setCreateTime(timeNow);
        singer.setUpdateTime(timeNow);
        return singerMapper.insert(singer) > 0;
    }

    // 修改歌手
    @Override
    public boolean update(SingerReq singerReq) {
        Singer singer = SingerServiceUtil.singerUtil(singerReq);
        long timeNow = System.currentTimeMillis();
        singer.setUpdateTime(timeNow);
        return singerMapper.update(singer) > 0;
    }

    // 删除歌手
    @Override
    public boolean delete(List<Long> idList) {
        return singerMapper.delete(idList) > 0;
    }

    // 根据主键查询整个对象
    @Override
    public Singer selectByPrimaryKey(Long id) {
        return singerMapper.selectByPrimaryKey(id);
    }

    // 查询所有歌手
    @Override
    public List<Singer> selectAllSinger() {
        return singerMapper.selectAllSinger();
    }

    // 根据歌手名字模糊查询
    @Override
    public List<Singer> selectByName(String name) {
        String likeName = name.trim();
        return singerMapper.selectByName("%" + likeName + "%");
    }

    // 根据性别查询
    @Override
    public List<Singer> selectBySex(Integer sex) {
        return singerMapper.selectBySex(sex);
    }

    // 根据歌手名查找歌手id
    @Override
    public Long getSingerId(String name) {
        return singerMapper.getSingerId(name);
    }
}
