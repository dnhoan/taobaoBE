package com.example.taobao_be.Services;

import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.UserDTO;
import com.example.taobao_be.models.User;
import com.example.taobao_be.repositories.IService;
import com.example.taobao_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomerUserDetails(user);
    }

//    @Transactional
//    public List<User> getUsers() {
//        List<User> users = this.userRepository.
//    }


    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id)
        );
        return new CustomerUserDetails(user);
    }

    public PageDTO<UserDTO> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<User> page = this.userRepository.findAll(pageable);
        List<UserDTO> userDTOList = page.stream().map(u -> this.modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return new PageDTO<UserDTO>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                userDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    public UserDTO create(User user) throws Exception {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passwordEncrypt = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncrypt);
            this.userRepository.saveAndFlush(user);
            return this.modelMapper.map(user, UserDTO.class);
        } catch (Exception exception) {
            throw  new Exception("Create User false");
        }
    }

    public UserDTO update(User user) {
        String currentPassword = this.userRepository.findPasswordById(user.getId());
        user.setPassword(currentPassword);
        User u = this.userRepository.save(user);
        return this.modelMapper.map(u, UserDTO.class);
    }

    public void delete(User user)  {
        user.setStatus("0");
        this.userRepository.save(user);
    }

    public UserDTO getById(Long id) throws Exception {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return this.modelMapper.map(user.get(), UserDTO.class);
        }
        throw new Exception("Not found user by id");
    }
}
