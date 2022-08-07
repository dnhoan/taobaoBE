package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.ResponseDTO;
import com.example.taobao_be.DTO.UserDTO;
import com.example.taobao_be.Services.CustomerUserDetails;
import com.example.taobao_be.configs.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@CrossOrigin(Constants.baseUrlFE)
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> authenticateUserPost(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((CustomerUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("token", jwt))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("random")
    public RandomStuff randomStuff() {
        return new RandomStuff("Jwt howpj lej moi cos the thay dc messsage");
    }
}

@Data
class LoginRequest {
    @NotBlank //dsf
    private String email;
    @NotBlank
    private String password;
}

@Data
@AllArgsConstructor
class RandomStuff {
    private String message;
}
