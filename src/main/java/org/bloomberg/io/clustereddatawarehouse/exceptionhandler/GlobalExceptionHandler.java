package org.bloomberg.io.clustereddatawarehouse.exceptionhandler;

import org.bloomberg.io.clustereddatawarehouse.exceptions.DealNotFoundException;
import org.bloomberg.io.clustereddatawarehouse.exceptions.DuplicateFXDealException;
import org.bloomberg.io.clustereddatawarehouse.exceptions.InvalidDealRequestException;
import org.bloomberg.io.clustereddatawarehouse.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateFXDealException.class)
    public ResponseEntity<Object> handleDuplicateDealException(DuplicateFXDealException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT)
                .build()
        );
    }

    @ExceptionHandler(InvalidDealRequestException.class)
    public ResponseEntity<Object> handleInvalidDealException(InvalidDealRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build()
        );
    }

    @ExceptionHandler(DealNotFoundException.class)
    public ResponseEntity<Object> handleDealNotFoundException(DealNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build()
        );
    }

}

