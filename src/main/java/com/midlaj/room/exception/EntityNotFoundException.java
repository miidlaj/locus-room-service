package com.midlaj.room.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Slf4j
public class EntityNotFoundException extends RuntimeException {

    private String entityName;

    public EntityNotFoundException( String entityName) {
        super("Cannot Find " + entityName + ".");
        this.entityName = entityName;
        log.warn(super.getMessage());
    }

}