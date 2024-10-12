package com.example.NotesCollector_V2.service;


import com.example.NotesCollector_V2.dto.UserStatus;
import com.example.NotesCollector_V2.dto.impl.UserDTO;

import java.util.List;

public interface UserService {
    void saveuser(UserDTO userDTO);
    List<UserDTO> getAllUser();
    UserStatus getUser(String userDTO);
    void deleteUser(String userId);
    void updateUser(String userID,UserDTO userDTO);
}
