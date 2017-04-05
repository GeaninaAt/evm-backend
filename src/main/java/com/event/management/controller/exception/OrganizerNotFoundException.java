package com.event.management.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Organizer does not exist.")
public class OrganizerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OrganizerNotFoundException(String message){
        super(message);
    }

}
