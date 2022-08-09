package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;

@RestController
@RequestMapping("api")
@CrossOrigin(Constants.baseUrlFE)
@Slf4j
public class CommonController {

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @GetMapping(path = "image/{name}", produces = IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("name") String name) {
        try {
            File myFile = new File(FILE_DIRECTORY + name);
            BufferedImage bImage = ImageIO.read(myFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            log.error("Error file " + e);
        }
        return null;
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<ResponseDTO> fileUpload(@RequestParam("File") MultipartFile file) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String img = timestamp.getTime() + "_" + file.getOriginalFilename();
        File myFile = new File(FILE_DIRECTORY + img);
        myFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(myFile);
        fos.write(file.getBytes());
        fos.close();
        return  ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("success")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("img", img)).build()
        );
    }
}
