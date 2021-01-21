package com.buct.music.controller;

import com.buct.music.controller.dto.SingerReq;
import com.buct.music.domin.Singer;
import com.buct.music.enums.FilePathEnum;
import com.buct.music.enums.SexEnum;
import com.buct.music.result.CodeMsg;
import com.buct.music.result.Result;
import com.buct.music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Zhang JunFeng
 * @date 2021/1/2 10:50
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class SingerController {
    private final SingerService singerService;

    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    /**
     * 添加一名歌手
     * @param singerReq 歌手参数
     * @return  Result<CodeMsg>
     */
    @PostMapping("/singer")
    public Result<CodeMsg> addSinger(@RequestBody SingerReq singerReq) {
        try {
            boolean flag = singerService.insert(singerReq);
            log.info("添加歌手参数为: {}", singerReq);
            if (flag) {
                return Result.success(CodeMsg.SUCCESS);
            } else {
                return Result.error(CodeMsg.FAILURE);
            }
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 更新歌手信息
     * @param singerReq 前端传递歌手参数
     * @return Result<CodeMsg>
     */
    @PutMapping("/singer")
    public Result<CodeMsg> updateSinger(@RequestBody SingerReq singerReq) {
        try {
            boolean flag = singerService.update(singerReq);
            log.info("修改歌手参数为: {}", singerReq);
            if (flag) {
                return Result.success(CodeMsg.SUCCESS);
            } else {
                return Result.error(CodeMsg.FAILURE);
            }
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 删除歌手信息
     * @param idList    要删除的歌手id
     * @return Result<CodeMsg>
     */
    @DeleteMapping("/singer")
    public Result<CodeMsg> deleteSinger(@RequestBody List<Long> idList) {
        if (idList.isEmpty()) {
            return Result.error(400, "idList should not be null !");
        }
        try {
            boolean flag = singerService.delete(idList);
            log.info("删除歌手的idList: {}", idList);
            if (flag) {
                return Result.success(CodeMsg.SUCCESS);
            }else {
                return Result.error(CodeMsg.FAILURE);
            }
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据主键查询歌手信息
     * @param id    要查询的歌手id
     * @return  Result<Singer>
     */
    @GetMapping("/singer")
    public Result<Singer> getByPrimaryKey(@RequestParam Long id) {
        if (id <= 0) {
            return Result.error(400, "id should > 0 !");
        }
        try {
            Singer singer = singerService.selectByPrimaryKey(id);
            log.info("查询歌手id: {}, 歌手信息：{}", id, singer);
            return Result.success(singer);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 获取所有歌手信息
     * @return  Result<List<Singer>>
     */
    @GetMapping("/singers")
    public Result<List<Singer>> getAllSingers() {
        try {
            List<Singer> singerList = singerService.selectAllSinger();
            log.info("查询歌手: {}", singerList);
            return Result.success(singerList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据姓名获取歌手信息
     * @param name  歌手姓名
     * @return  Result<List<Singer>>
     */
    @GetMapping("/singers/name")
    public Result<List<Singer>> getSingersByName(@RequestParam String name) {
        try {
            List<Singer> singerList = singerService.selectByName(name);
            log.info("根据姓名查询歌手: {}", singerList);
            return Result.success(singerList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据歌手性别查询歌手
     * @param sex   1：男；0：女
     * @return  Result<List<Singer>>
     */
    @GetMapping("/singers/sex")
    public Result<List<Singer>> getSingerBySex(@RequestParam Integer sex) {
        if (!sex.equals(SexEnum.MALE.getState())
            && !sex.equals(SexEnum.FEMALE.getState())
            && !sex.equals(SexEnum.GROUP.getState())) {
            return Result.error(400, "sex must be 1(male) or 0(female)!");
        }
        try {
            List<Singer> singerList = singerService.selectBySex(sex);
            log.info("根据姓名查询歌手: {}", singerList);
            return Result.success(singerList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 更新歌手图片
     * @param picture   要更新的图片
     * @param id    歌手id
     * @return  Result<CodeMsg>
     */
    @PostMapping("/singer/updatePic")
    public Result<CodeMsg> updateSingerPic(@RequestParam("file") MultipartFile picture, @RequestParam Long id){
        if (picture.isEmpty()) {
            return Result.error(400, "picture upload failed");
        }

        String type = picture.getContentType();
        if (type != null && !type.equals("image/jpeg") && !type.equals("image/png")) {
            return Result.error(400, "the type of picture should be 'jpg/jpeg/png' !");
        }

        long size = picture.getSize();
        if (size / 1024 / 1024 > 2) {
            return Result.error(400, "the size of picture should be < 2M !");
        }

        if (id <= 0) {
            return Result.error(400, "id should > 0 !");
        }

        // 文件名 = 当前时间毫秒 + 原文件名
        String newFileName = System.currentTimeMillis() + picture.getOriginalFilename();

        // 文件存储路径——文件夹（绝对路径）
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + FilePathEnum.IMG_PATH.getPath() + System.getProperty("file.separator")
                + FilePathEnum.SINGER_PIC.getPath();

        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            log.info("file directory is not exits");
            boolean flag = file.mkdirs();
            if (flag) {
                log.info("create a directory successed");
            } else {
                log.info("create a directory failed");
            }
        }

        // 实际文件存储地址(绝对路径)
        File fileToPath = new File(filePath + System.getProperty("file.separator") + newFileName);

        // 存储到数据库的相对文件路径
        String databaseFilePath = FilePathEnum.SINGER_PIC_PATH.getPath() + newFileName;

        try {
            // 将文件存储到磁盘
            picture.transferTo(fileToPath);

            SingerReq singerReq = new SingerReq();
            singerReq.setId(id);
            singerReq.setPic(databaseFilePath);
            boolean flag = singerService.update(singerReq);

            log.info("文件更新信息：id: {}, 文件在磁盘位置: {}, 存储到数据库中信息: {}", id, fileToPath, databaseFilePath);
            if (flag) {
                return Result.success(CodeMsg.SUCCESS);
            }else {
                return Result.error(CodeMsg.FAILURE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传文件失败，异常信息： {}", e.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
