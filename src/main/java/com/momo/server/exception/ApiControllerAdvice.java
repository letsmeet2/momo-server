package com.momo.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.momo.server.dto.CmRespDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleNotFoundException(CommonException e) {
	log.info("CommonException", e);

	ResponseEntity<?> responseCode = new ResponseEntity<>(HttpStatus.NOT_FOUND);
	return new ResponseEntity<>(new CmRespDto<>(responseCode, e.getMessage(), null), HttpStatus.NOT_FOUND);
    }

}
