package com.buct.music.controller;

import com.buct.music.controller.dto.SingerReq;
import com.buct.music.controller.dto.SongReq;
import com.buct.music.domin.Song;
import com.buct.music.enums.FilePathEnum;
import com.buct.music.result.CodeMsg;
import com.buct.music.result.Result;
import com.buct.music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Jeffrey
 * @version 2020/01/04 10:21
 */

@RestController
@Slf4j
@RequestMapping("/admin")
public class SongController {
    private final SongService songService;

    // 构造函数
    public SongController(SongService songService) {
        this.songService = songService;
    }

    /**
     * 添加一首歌
     * @param songReq 前端传递歌曲参数
     * @return Result<CodeMsg>
     */
    @PostMapping("/songs")
    public Result<CodeMsg> addSong(@RequestBody SongReq songReq) {
        try {
            boolean flag = songService.insert(songReq);
            log.info("添加歌曲参数为{}", songReq);
            if (flag)
                return Result.success(CodeMsg.SUCCESS);
            else
                return Result.error(CodeMsg.FAILURE);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 修改特定歌曲的信息
     * @param songReq 前端传来的歌曲参数
     * @return Result<CodeMsg>
     */
    @PutMapping("/songs")
    public Result<CodeMsg> updateSong(@RequestBody SongReq songReq) {
        try {
            boolean flag = songService.update(songReq);
            log.info("修改歌曲参数为{}", songReq);
            if (flag)
                return Result.success(CodeMsg.SUCCESS);
            else
                return Result.error(CodeMsg.FAILURE);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据id删除歌曲
     * @param id 前端传入的歌曲id
     * @return Result<Msg>
     */
    @DeleteMapping("/songs")
    public Result<CodeMsg> deleteSong(@RequestParam Long id) {
        if (id <= 0) {
            return Result.error(400, "id should > 0 !");
        }
        try {
            boolean flag = songService.delete(id);
            log.info("删除id为{}的歌曲", id);
            if (flag)
                return Result.success(CodeMsg.SUCCESS);
            else
                return Result.error(CodeMsg.FAILURE);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据id查找歌曲
     * @param id 歌曲id
     * @return Result<Song>
     */
    @GetMapping("/song")
    public Result<Song> getByPrimaryKey(@RequestParam Long id) {
        if (id <= 0) {
            return Result.error(400, "id should > 0 !");
        }
        try {
            Song song = songService.selectByPrimaryKey(id);
            log.info("歌曲id: {}, 歌曲信息: {}", id, song);
            return Result.success(song);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 查找所有歌曲
     * @return Result<List<Song>>
     */
    @GetMapping("/songs")
    public Result<List<Song>> getAllSongs() {
        try {
            List<Song> songList = songService.selectAllSongs();
            log.info("所有歌曲: {}", songList);
            return Result.success(songList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据歌曲名字查找歌曲
     * @param name 前端传入的歌曲名字
     * @return Result<List<SongReq>>
     */
    @GetMapping("/songs/name")
    public Result<List<SongReq>> getSongByName(@RequestParam String name) {
        try {
            List<SongReq> songList = songService.selectByName(name);
            log.info("根据名字查找歌曲: {}", songList);
            return Result.success(songList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据歌手名字查找对应歌曲
     * @param name 前端传入的歌曲名字
     * @return Result<List<SongReq>>
     */
    @GetMapping("/songs/singer_name")
    public Result<List<SongReq>> getSongBySinger(@RequestParam String name) {
        try {
            List<SongReq> songList = songService.selectBySinger(name);
            log.info("根据歌手名字查找歌曲: {}", songList);
            return Result.success(songList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 查找包含歌手名的所有歌曲
     * @return Result<List<SongReq>>
     */
    @GetMapping("/songs/songs_with_singer_name")
    public Result<List<SongReq>> getAllSongsWithSinger() {
        try {
            List<SongReq> songList = songService.getAllSongsWithSinger();
            log.info("所有歌曲: {}", songList);
            return Result.success(songList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 根据歌曲名字查找歌曲
     * @param keyword 前端传入的简介关键词
     * @return Result<List<SongReq>>
     */
    @GetMapping("/songs/introduction")
    public Result<List<SongReq>> getSongByIntroduction(@RequestParam String keyword) {
        try {
            List<SongReq> songList = songService.selectByIntroduction(keyword);
            log.info("根据名字查找歌曲: {}", songList);
            return Result.success(songList);
        } catch (Throwable throwable) {
            log.error("There is something error: {}", throwable.getMessage());
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

    /**
     * 更新歌曲图片
     * @param picture   要更新的图片
     * @param id    歌曲
     * @return  Result<CodeMsg>
     */
    @PostMapping("/songs/update_pic")
    public Result<CodeMsg> updateSongPic(@RequestParam("file") MultipartFile picture, @RequestParam Long id){
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
                + FilePathEnum.SONG_PIC.getPath();

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
        String databaseFilePath = FilePathEnum.SONG_PIC_PATH.getPath() + newFileName;

        try {
            // 将文件存储到磁盘
            picture.transferTo(fileToPath);

            SongReq songReq = new SongReq();
            songReq.setId(id);
            songReq.setPic(databaseFilePath);
            boolean flag = songService.update(songReq);

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

    /**
     * 更新歌曲
     * @param song   要更新的歌曲
     * @param id    歌曲
     * @return  Result<CodeMsg>
     */
    @PostMapping("/songs/update_song")
    public Result<CodeMsg> updateSong(@RequestParam("file") MultipartFile song, @RequestParam Long id){
        if (song.isEmpty()) {
            return Result.error(400, "song upload failed");
        }

        // 文件名 = 当前时间毫秒 + 原文件名
        String newFileName = System.currentTimeMillis() + song.getOriginalFilename();

        // 文件存储路径——文件夹（绝对路径）
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator")
                + FilePathEnum.SONG_PATH.getPath();

        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            log.info("file directory does not exits");
            boolean flag = file.mkdirs();
            if (flag) {
                log.info("create a directory success");
            } else {
                log.info("create a directory failed");
            }
        }

        // 实际文件存储地址(绝对路径)
        File fileToPath = new File(filePath + System.getProperty("file.separator") + newFileName);

        // 存储到数据库的相对文件路径
        String databaseFilePath = FilePathEnum.SONG_PATH.getPath() + newFileName;

        try {
            // 将文件存储到磁盘
            song.transferTo(fileToPath);

            SongReq songReq = new SongReq();
            songReq.setId(id);
            songReq.setUrl(databaseFilePath);
            boolean flag = songService.update(songReq);

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
