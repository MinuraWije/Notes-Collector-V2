package com.example.NotesCollector_V2.controller;


import com.example.NotesCollector_V2.customStatusCodes.SelectedUserAndNoteErrorStatus;
import com.example.NotesCollector_V2.dto.NoteStatus;
import com.example.NotesCollector_V2.dto.impl.NoteDTO;
import com.example.NotesCollector_V2.exception.DataPersistException;
import com.example.NotesCollector_V2.exception.NoteNotFoundException;
import com.example.NotesCollector_V2.service.NoteService;
import com.example.NotesCollector_V2.util.RegexProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO){
        try{
            noteService.saveNote(noteDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping(value = "/{noteId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteStatus getSelectedNote(@PathVariable ("noteId") String noteId){
        if(!RegexProcess.noteIdMatcher(noteId)){
            return new SelectedUserAndNoteErrorStatus(1,"Note id is not valid");
        }
        return noteService.getNote(noteId);
    }
    public List<NoteDTO> getAllNotes(){
        return null;
    }
    @DeleteMapping(value = "{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable ("noteId") String noteId){
        try{
            if(!RegexProcess.noteIdMatcher(noteId)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.deleteNote(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(NoteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(value = "{noteId}")
    public ResponseEntity<Void> updateNote(@PathVariable ("noteId") String noteId,@RequestBody NoteDTO updatedNoteDTO){
        //validations
        try {
            if(!RegexProcess.noteIdMatcher(noteId) || updatedNoteDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.updateNote(noteId,updatedNoteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
