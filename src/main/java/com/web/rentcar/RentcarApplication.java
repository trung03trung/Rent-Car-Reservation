package com.web.rentcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import com.github.mjstewart.querystring.dialect.QueryStringDialect;
@SpringBootApplication(scanBasePackages = {"com.web.rentcar"})
public class RentcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentcarApplication.class, args);
    }

    @Bean
    public QueryStringDialect queryStringDialect() {
        return new QueryStringDialect();
    }

}
