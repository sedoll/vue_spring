package com.spring316.user.service;

import com.spring316.user.dto.User;
import com.spring316.user.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    public List<UserDto> userList();
    public void userInsert(UserDto UserDto);
    public PasswordEncoder passwordEncoder();
    public UserDto getId(String id);
    public User LoginId(String id);
    public void userUpdate(UserDto UserDto);
    public void emailUpdate(UserDto UserDto);
    public void stateUpdate(UserDto UserDto);
    public void roleUpdate(UserDto UserDto);
    public void userDelete(Integer id);
    public int loginPro(String id);
    public boolean idCheck(String id);
    public void userchangePw(UserDto UserDto);
}