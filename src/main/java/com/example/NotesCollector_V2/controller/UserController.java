package com.example.NotesCollector_V2.controller;


import com.example.NotesCollector_V2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import com.example.NotesCollector_V2.dto.UserStatus;
import com.example.NotesCollector_V2.dto.impl.UserDTO;
import com.example.NotesCollector_V2.exception.DataPersistException;
import com.example.NotesCollector_V2.exception.UserNotFoundException;
import com.example.NotesCollector_V2.service.UserService;
import com.example.NotesCollector_V2.util.AppUtil;
import com.example.NotesCollector_V2.util.RegexProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(
            @RequestPart ("firstName") String firstName,
            @RequestPart ("lastName") String lastName,
            @RequestPart ("email") String email,
            @RequestPart ("password") String password,
            @RequestPart ("profilepic") MultipartFile profilepic

    ){
        String base64ProfilePic =";";
        //profilePic --> Base64

        try {
            byte[] bytesProfilePic =profilepic.getBytes();
             base64ProfilePic = AppUtil.profilePicToBase64(bytesProfilePic);

            //UserId generate
            String userId = AppUtil.generateUserId();

            //Todo: Build the Object
            var buildUserDTO = new UserDTO();
            buildUserDTO.setUserId(userId);
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(password);
            buildUserDTO.setProfilePic(base64ProfilePic);
            userService.saveuser(buildUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable ("userId") String userId){
        if(!RegexProcess.userIdMatcher(userId)){
            return new SelectedUserAndNoteErrorStatus(1,"User ID is not valid");
        }
        return userService.getUser(userId);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable ("userId") String userId){
        try{
            if(!RegexProcess.userIdMatcher(userId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUser();
    }
    @PutMapping(value = "{userId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(UserDTO userDTO,
                           @RequestPart ("firstName") String firstName,
                           @RequestPart ("lastName") String lastName,
                           @RequestPart ("email") String email,
                           @RequestPart ("password") String password,
                           @RequestPart ("profilepic") MultipartFile profilepic,
                           @PathVariable ("userId") String userId)
    {
        String base64ProfilePic =";";
        //profilePic --> Base64

        try {
            byte[] bytesProfilePic =profilepic.getBytes();
            base64ProfilePic = AppUtil.profilePicToBase64(bytesProfilePic);
        }catch (IOException e){
            throw new RuntimeException(e);
        }


        //Todo: Build the Object
        var buildUserDTO = new UserDTO();
        buildUserDTO.setUserId(userId);
        buildUserDTO.setFirstName(firstName);
        buildUserDTO.setLastName(lastName);
        buildUserDTO.setEmail(email);
        buildUserDTO.setPassword(password);
        buildUserDTO.setProfilePic(base64ProfilePic);
        userService.updateUser(userId,buildUserDTO);

    }
}
