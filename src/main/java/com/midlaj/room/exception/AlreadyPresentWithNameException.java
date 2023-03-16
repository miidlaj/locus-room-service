package com.midlaj.room.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyPresentWithNameException extends RuntimeException{
    private String entityName;

    private String name;

    public AlreadyPresentWithNameException( String entityName, String name) {
        super(entityName + " with " + name + " already exist!");
        this.entityName = entityName;
        log.warn(super.getMessage());
    }

}
