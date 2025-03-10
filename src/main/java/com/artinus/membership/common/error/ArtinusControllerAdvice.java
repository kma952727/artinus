package com.artinus.membership.common.error;

import com.artinus.membership.common.dto.ArtinuseErrorResponse;
import com.artinus.membership.common.error.exception.ArtinusAbstractException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import static com.artinus.membership.common.error.ArtinusErrorCode.INVALID__REQUEST_DATA;
import static com.artinus.membership.common.error.ArtinusErrorCode.SERVER_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Hidden
@RestControllerAdvice
public class ArtinusControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ArtinuseErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        var messages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        log.warn("handleConstraintViolationException { message: \"{}\" }", e.getMessage());

        return ResponseEntity
            .status(BAD_REQUEST)
            .body(new ArtinuseErrorResponse(INVALID__REQUEST_DATA, messages.toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArtinuseErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        var messages = bindingResult.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .reduce((msg1, msg2) -> msg1 + ", " + msg2)
            .orElse("Validation error");

        log.warn("handleMethodArgumentNotValidException { message: \"{}\", messages: \"{}\" }", e.getMessage(), messages);

        return ResponseEntity
            .status(BAD_REQUEST)
            .body(new ArtinuseErrorResponse(INVALID__REQUEST_DATA, messages));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ArtinuseErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String resultMessage;
        if (e.getCause() instanceof MismatchedInputException mismatchedInputException) {
            resultMessage = mismatchedInputException.getPath().getFirst().getFieldName() + " 데이터를 확인해주세요.";
        } else {
            resultMessage = e.getCause() != null ? e.getCause().toString() : "알 수 없는 에러";
        }

        log.warn("handleHttpMessageNotReadableException { message: \"{}\", resultMessage: \"{}\" }", e.getMessage(), resultMessage);

        return ResponseEntity
            .status(BAD_REQUEST)
            .body(new ArtinuseErrorResponse(INVALID__REQUEST_DATA, resultMessage));
    }

    @ExceptionHandler(ArtinusAbstractException.class)
    public ResponseEntity<ArtinuseErrorResponse> handleArtinusException(ArtinusAbstractException e) {
        log.warn("handleArtinusException { message: \"{}\" }", e.getMessage());

        return ResponseEntity
            .status(e.httpStatus)
            .body(new ArtinuseErrorResponse(e.errorCode, e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ArtinuseErrorResponse> handleServerException(RuntimeException e) {
        log.error("handleServerException", e);

        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(new ArtinuseErrorResponse(SERVER_ERROR, "서버 에러"));
    }
}
