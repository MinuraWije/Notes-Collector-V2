package com.example.NotesCollector_V2.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String generateNoteId(){
        return "Note-"+UUID.randomUUID();
    }

    public static String generateUserId(){
        return "User-"+UUID.randomUUID();
    }
    public static String profilePicToBase64(byte [] profilePic){
        return Base64.getEncoder().encodeToString(profilePic);
    }

}
