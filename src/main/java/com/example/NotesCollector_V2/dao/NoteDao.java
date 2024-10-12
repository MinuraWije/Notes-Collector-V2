package com.example.NotesCollector_V2.dao;

import com.example.NotesCollector_V2.entity.impl.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface NoteDao extends JpaRepository<NoteEntity,String> {
}
