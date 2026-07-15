package com.example.sistembimbingan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/mahasiswa/**",
                        "/dosen/**")
                .excludePathPatterns(
                        "/",
                        "/login",
                        "/register-mahasiswa",
                        "/register-dosen",
                        "/logout",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                );
    }
}