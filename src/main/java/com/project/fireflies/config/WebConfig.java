package com.project.fireflies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path.img.avatars}")
    private String pathImgAvatars;

    @Value("${upload.path.img.posts}")
    private String pathImgPosts;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/a/*")
                .addResourceLocations("file:/" + Paths.get(pathImgAvatars).toFile().getAbsolutePath() + "/");
        registry.addResourceHandler("/img/p/*")
                .addResourceLocations("file:/" + Paths.get(pathImgPosts).toFile().getAbsolutePath() + "/");
    }
}
