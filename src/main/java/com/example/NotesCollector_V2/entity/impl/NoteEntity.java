package com.example.NotesCollector_V2.entity.impl;

import com.example.NotesCollector_V2.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "note")
public class NoteEntity implements SuperEntity {
    @Id
    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String createdDate;
    private String priorityLevel;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
}
