package org.example.springthreory;

import org.example.springthreory.testPrac.ProductDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ProductDao productDao(){
        return new ProductDao();
    }
}
