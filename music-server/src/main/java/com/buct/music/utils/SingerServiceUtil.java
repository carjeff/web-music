package com.buct.music.utils;

import com.buct.music.controller.dto.SingerReq;
import com.buct.music.domin.Singer;

import java.util.Date;

/**
 * @author Zhang JunFeng
 * @date 2021/1/2 16:04
 */
public class SingerServiceUtil {
    public static Singer singerUtil(SingerReq singerReq) {
        Long id = singerReq.getId();
        String name = singerReq.getName();
        Integer sex = singerReq.getSex();
        String pic = singerReq.getPic();
        Date birth = singerReq.getBirth();
        String location = singerReq.getLocation();
        String introduction = singerReq.getIntroduction();

        Singer singer = new Singer();
        if (id != null) {
            singer.setId(id);
        }
        if (name != null) {
            singer.setName(name.trim());
        }
        if (sex != null) {
            singer.setSex(sex);
        }
        if (pic != null) {
            singer.setPic(pic.trim());
        }
        if (birth != null) {
            singer.setBirth(birth);
        }
        if (location != null) {
            singer.setLocation(location.trim());
        }
        if (introduction != null) {
            singer.setIntroduction(introduction.trim());
        }
        return singer;
    }
}
