package com.qianfeng.config;

import com.qianfeng.Interceptor.MyInterceptors;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: qianfeng
 * @Date: 2024/04/22/13:49
 * @Description:
 */
@SpringBootConfiguration
public class MyConfig implements WebMvcConfigurer {
//    @Override
//    //拦截器
//    public void addInterceptors(InterceptorRegistry registry) {
//        //addPathPatterns拦截路径
//        //excludePathPatterns不拦截路径
//        registry.addInterceptor(new MyInterceptors()).addPathPatterns("/book");
////        registry.addInterceptor(new MyInterceptors()).addPathPatterns("/").addPathPatterns("/success");
//    }

    @Override
    //映射器
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送/book请求来到view
        //这里是指定静态html
        registry.addViewController("/book").setViewName("index.html");
        //指定动态jsp
        //待做

    }
}
