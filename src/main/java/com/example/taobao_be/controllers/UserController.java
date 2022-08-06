package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.ResponseDTO;
import com.example.taobao_be.DTO.UserDTO;
import com.example.taobao_be.Services.UserService;
import com.example.taobao_be.models.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(Constants.baseUrlFE)
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("users")
    public ResponseEntity<ResponseDTO> getUsers(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                     @RequestParam(value = "limit", defaultValue = "20") int limit) {
        PageDTO<UserDTO> items = this.userService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("users", items))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("users/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable("id") User user) {
        if (user != null) {
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("user", this.modelMapper.map(user, UserDTO.class)))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
        return ResponseEntity.ok(
                ResponseDTO.builder()
                            .statusCode(NO_CONTENT.value())
                        .timeStamp(LocalDateTime.now())
                        .status(NO_CONTENT)
                        .build()
        );
    }

    @PutMapping("users")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody User user) {
        UserDTO userDTO = this.userService.update(user);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("user", userDTO))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") User user) {
        if (user != null) {
            this.userService.delete(user);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .message("Delete success")
                            .status(OK)
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("User id not exist")
                        .status(NO_CONTENT)
                        .statusCode(NO_CONTENT.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("users")
    public ResponseEntity<?> creatUser(@RequestBody User user) {
        log.info("User insert to db {}", user.toString());
        try {
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .data(Map.of("user", this.userService.create(user)))
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("Error insert user ", e);
            return ResponseEntity.internalServerError().build();
//                    ResponseDTO.builder()
//                            .message("Error creat user")
//                            .status(EXPECTATION_FAILED)
//                            .statusCode(EXPECTATION_FAILED.value())
//                            .timeStamp(LocalDateTime.now())
//                            .build()

        }
    }
}
