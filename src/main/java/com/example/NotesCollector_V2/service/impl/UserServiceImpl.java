package com.example.NotesCollector_V2.service.impl;

import com.example.NotesCollector_V2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import com.example.NotesCollector_V2.dao.UserDao;
import com.example.NotesCollector_V2.dto.UserStatus;
import com.example.NotesCollector_V2.dto.impl.UserDTO;
import com.example.NotesCollector_V2.entity.impl.UserEntity;
import com.example.NotesCollector_V2.exception.DataPersistException;
import com.example.NotesCollector_V2.exception.UserNotFoundException;
import com.example.NotesCollector_V2.service.UserService;
import com.example.NotesCollector_V2.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveuser(UserDTO userDTO) {
        /*UserEntity savedUser = userDao.save(mapping.toUserEntity(userDTO));
        mapping.touserDTO(savedUser);*/
        UserEntity savedUser = userDao.save(mapping.toUserEntity(userDTO));
        if(savedUser == null){
            throw new DataPersistException("User not saved.");
        }
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<UserEntity> allUsers = userDao.findAll();
        return mapping.asUserDTOList(allUsers);
    }

    @Override
    public UserStatus getUser(String userId) {
        if(userDao.existsById(userId)){
            UserEntity selectedUser = userDao.getReferenceById(userId);
            return mapping.touserDTO(selectedUser);
        }else{
            return new SelectedUserAndNoteErrorStatus(2, "User with id "+ userId + "not found.");
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> existedUser = userDao.findById(userId);
        if(!existedUser.isPresent()){
            throw new UserNotFoundException("User with id "+userId+" not found");
        }else {
            userDao.deleteById(userId);
        }
    }

    @Override
    public void updateUser(String userID, UserDTO userDTO) {
        Optional<UserEntity> tmpUser = userDao.findById(userID);
        if(tmpUser.isPresent()){
            tmpUser.get().setFirstName(userDTO.getFirstName());
            tmpUser.get().setLastName(userDTO.getLastName());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setProfilePic(userDTO.getProfilePic());
        }
    }
}
