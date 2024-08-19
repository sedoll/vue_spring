package com.spring316.user.service;

import com.spring316.user.dto.Role;
import com.spring316.user.dto.Status;
import com.spring316.user.dto.User;
import com.spring316.user.dto.UserDto;
import com.spring316.user.repo.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto> userList() {
        List<User> userList = userRepository.findAll();
        List<UserDto> UserDtoList = userList.stream().map(
                        user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return UserDtoList;
    }

    @Override
    public void userDelete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void userInsert(UserDto userDTO) {
        String ppw = passwordEncoder.encode(userDTO.getPw());
        userDTO.setPw(ppw);
        userDTO.setRole(Role.USER);
        userDTO.setStatus(Status.ACTIVE);
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }

    @Override
    public boolean idCheck(String id) {
        boolean pass = true;
        int cnt = userRepository.countById(id);
        if(cnt > 0) pass = false;
        return pass;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    @Override
    public UserDto getId(String id) {
        User user = userRepository.getId(id);
        UserDto userDTO = modelMapper.map(user, UserDto.class);
        return userDTO;
    }

    @Override
    public User LoginId(String id) {
        User user = userRepository.getId(id);
        return user;
    }

    @Override
    public void userUpdate(UserDto userDTO) {
        Optional<User> user = userRepository.getUser(userDTO.getId());
        User user2 = user.orElseThrow();
        user2.change(userDTO);
        userRepository.save(user2);
    }

    @Override
    public void emailUpdate(UserDto userDTO) {
        Optional<User> user = userRepository.getUser(userDTO.getId());
        User user2 = user.orElseThrow();
        user2.emailUpdate(userDTO);
        userRepository.save(user2);
    }

    @Override
    public void stateUpdate(UserDto userDTO) {
        Optional<User> user = userRepository.getUser(userDTO.getId());
        User user2 = user.orElseThrow();
        user2.stateUpdate(userDTO);
        userRepository.save(user2);
    }

    @Override
    public void roleUpdate(UserDto userDTO) {
        Optional<User> user = userRepository.getUser(userDTO.getId());
        User user2 = user.orElseThrow();
        user2.roleUpdate(userDTO);
        userRepository.save(user2);
    }

    @Override
    public int loginPro(String id) {
        int pass = 0;
        User user = userRepository.getId(id);
        LocalDateTime local = LocalDateTime.now().minusDays(30);
        if (local.isAfter(user.getLoginAt())) {
            user.setStatus(Status.REST);
            userRepository.save(user);
            pass = 2;
        } else {
            if (user.getStatus().equals(Status.ACTIVE)) {
                user.setLoginAt(LocalDateTime.now());
                userRepository.save(user);
                pass = 1;
            } else if (user.getStatus().equals(Status.REST)) {
                pass = 2;
            } else if (user.getStatus().equals(Status.OUT)) {
                pass = 3;
            }
        }
        return pass;
    }

    @Override
    public void userchangePw(UserDto userDTO) {
        String ppw = passwordEncoder.encode(userDTO.getPw());
        userDTO.setPw(ppw);
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
    }
}