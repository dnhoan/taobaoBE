package com.example.taobao_be.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;

    private String fullname;

    private Integer gender;

    private String phoneNumber;

    private String address;

    private String email;

    private String image;

    private Integer role;

    private String status;
}
