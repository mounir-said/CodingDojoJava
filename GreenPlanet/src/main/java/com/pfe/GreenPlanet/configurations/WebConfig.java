package com.pfe.GreenPlanet.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.pfe.GreenPlanet.controller.AdminController.UPLOAD_DIR;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/plant_img/**")
                .addResourceLocations("file:" + UPLOAD_DIR)
                .setCacheControl(CacheControl.noStore());

        // For development, also add this:
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.noCache());
    }
}