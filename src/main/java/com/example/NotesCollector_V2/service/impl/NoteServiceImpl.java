package com.example.NotesCollector_V2.service.impl;

import com.example.NotesCollector_V2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import com.example.NotesCollector_V2.dao.NoteDao;
import com.example.NotesCollector_V2.dto.NoteStatus;
import com.example.NotesCollector_V2.dto.impl.NoteDTO;
import com.example.NotesCollector_V2.entity.impl.NoteEntity;
import com.example.NotesCollector_V2.exception.DataPersistException;
import com.example.NotesCollector_V2.exception.NoteNotFoundException;
import com.example.NotesCollector_V2.service.NoteService;
import com.example.NotesCollector_V2.util.AppUtil;
import com.example.NotesCollector_V2.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private Mapping noteMapping;
    /*private static List<NoteDTO> noteDTOList = new ArrayList<>();

    NoteServiceImpl(){
        noteDTOList.add(new NoteDTO())
    }*/
    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.generateNoteId());
        noteDTO.setCreatedDate(String.valueOf(LocalDate.now()));
        NoteEntity savedNote = noteDao.save(noteMapping.toNoteEntity(noteDTO));
        if(savedNote == null){
            throw new DataPersistException("Note not saved.");
        }
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return noteMapping.asNoteDTOList(noteDao.findAll());
    }

    @Override
    public NoteStatus getNote(String noteId) {
        if(noteDao.existsById(noteId)){
            var selectedNote = noteDao.getReferenceById(noteId);
            return noteMapping.toNoteDTO(selectedNote);
        }
        return new SelectedUserAndNoteErrorStatus(2, "Selected Note not found.");
    }

    @Override
    public void deleteNote(String noteID) {
        Optional<NoteEntity> foundNote = noteDao.findById(noteID);
        if(!foundNote.isPresent()){
            throw new NoteNotFoundException("Note not found.");
        }else{
            noteDao.deleteById(noteID);
        }
    }

    @Override
    public void updateNote(String noteId, NoteDTO noteDTO) {
        Optional<NoteEntity> findNote = noteDao.findById(noteId);
        if(!findNote.isPresent()){
            throw new NoteNotFoundException("Note not found");
        }else {
            findNote.get().setNoteTitle(noteDTO.getNoteTitle());
            findNote.get().setCreatedDate(noteDTO.getCreatedDate());
            findNote.get().setNoteDesc(noteDTO.getNoteDesc());
            findNote.get().setPriorityLevel(noteDTO.getPriorityLevel());
        }
    }
}
