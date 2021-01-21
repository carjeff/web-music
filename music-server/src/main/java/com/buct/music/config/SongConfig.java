package com.buct.music.config;

import com.buct.music.enums.FilePathEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jeffrey
 * @date 2021/1/9 18:12
 */
@Configuration
public class SongConfig implements WebMvcConfigurer {
    /**
     * 映射数据库歌曲路径到本地
     * @param registry  registry对象
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FilePathEnum.SONG_PATH.getPath() + "**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + FilePathEnum.SONG_PATH.getPath() + System.getProperty("file.separator")
        );
    }
}