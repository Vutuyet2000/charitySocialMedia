package com.tuyet.charity.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {
    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "diacstfxz",
                "api_key", "395439149133535",
                "api_secret", "zdlfNTvxdo0NeS8BWr6Lf9xrZbE",
                "secure", true));

        return cloudinary;
    }
}
