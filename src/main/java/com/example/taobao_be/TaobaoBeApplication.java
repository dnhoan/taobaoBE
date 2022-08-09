package com.example.taobao_be;

import com.example.taobao_be.models.User;
import com.example.taobao_be.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;

@SpringBootApplication
public class TaobaoBeApplication {

//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(TaobaoBeApplication.class, args);

    }

}
