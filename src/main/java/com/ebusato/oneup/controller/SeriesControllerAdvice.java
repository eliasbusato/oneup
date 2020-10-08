package com.ebusato.oneup.controller;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Slf4j
public class SeriesControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Object> handleFeignException(FeignException ex) {
        log.warn("exception handled", ex);
        switch (ex.status()) {
            case 404:
                return new ResponseEntity<>(null, NOT_FOUND);
            case 401:
                return new ResponseEntity<>(null, UNAUTHORIZED);
        }
        return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleFeignException(Exception ex) {
        log.warn("exception handled", ex);
        return new ResponseEntity<>(null, INTERNAL_SERVER_ERROR);
    }
}
