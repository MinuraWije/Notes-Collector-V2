package com.example.NotesCollector_V2.util;

import com.example.NotesCollector_V2.dto.impl.NoteDTO;
import com.example.NotesCollector_V2.dto.impl.UserDTO;
import com.example.NotesCollector_V2.entity.impl.NoteEntity;
import com.example.NotesCollector_V2.entity.impl.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //for user mapping
    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,UserEntity.class);
    }

    public UserDTO touserDTO(UserEntity userEntity){
        return modelMapper.map(userEntity,UserDTO.class);
    }

    public List<UserDTO> asUserDTOList(List<UserEntity> userEntities){
        return modelMapper.map(userEntities, new TypeToken<List<UserDTO>>() {}.getType());
    }
    //for note mapping
    public NoteDTO toNoteDTO(NoteEntity noteEntity){
        return modelMapper.map(noteEntity, NoteDTO.class);
    }
    public NoteEntity toNoteEntity(NoteDTO noteDTO){
        return modelMapper.map(noteDTO, NoteEntity.class);
    }
    public List<NoteDTO> asNoteDTOList(List<NoteEntity> noteEntityList){
        return modelMapper.map(noteEntityList, new TypeToken<List<NoteDTO>>() {}.getType());
    }
}
