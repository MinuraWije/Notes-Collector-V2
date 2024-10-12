package com.example.NotesCollector_V2.util;

import java.util.regex.Pattern;

public class RegexProcess {
    public static boolean noteIdMatcher(String noteId) {
        String regexForNoteId = "^NOTE-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForNoteId);
        return regexPattern.matcher(noteId).matches();
    }
    public static boolean userIdMatcher(String userId){
        String regexForUserId = "^USER-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        return regexPattern.matcher(userId).matches();
    }
}
