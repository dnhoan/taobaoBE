package com.example.taobao_be;

import com.example.taobao_be.models.User;
import com.example.taobao_be.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaobaoBeApplication {

//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(TaobaoBeApplication.class, args);
    }

//    @Override
//    public void run(String...args) {
//        User u = new User();
//        u.setEmail("admin@gmail.com");
//        u.setPassword(passwordEncoder.encode("123"));
//        u.setFullname("asdfas");
//        u.setFullname("admin");
//        u.setAddress("adf");
//        u.setGender(1);
//        u.setImage("2234");
//        u.setPhoneNumber("23123");
//        u.setRole(1);
//        u.setStatus("1");
//        this.userRepository.save(u);

//    }


}
