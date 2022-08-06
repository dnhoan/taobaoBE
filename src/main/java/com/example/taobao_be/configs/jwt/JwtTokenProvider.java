package com.example.taobao_be.configs.jwt;

import com.example.taobao_be.DTO.UserDTO;
import com.example.taobao_be.Services.CustomerUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    @Autowired
    private ModelMapper modelMapper;

    private final String JWT_SECRET = "SECRET";

    private final long JWT_EXPIRATION = 10 * 24 * 60 * 60  * 1000;
//    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    public String generateToken(CustomerUserDetails userDetails) throws JsonProcessingException {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        User userDetails = userDetails.getUser();
//        UserDTO userDTO = objectMap
//        String user = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userDetails.getUser());
        log.info("Gen token {}", userDetails.getUser());
        UserDTO userDTO = modelMapper.map(userDetails.getUser(), UserDTO.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.convertValue(userDTO, Map.class);
        return Jwts.builder().setClaims(map)
                .setSubject(userDTO.getId() + "")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        log.info("claim user request {} subject {}", claims, claims.getSubject());
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT toke");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("Jwt claims string is empty");
        }
        return false;
    }
}
