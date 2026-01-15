package com.example.blogging.web;

import com.example.blogging.service.exception.conflict.ResourceAlreadyExistsException;
import com.example.blogging.service.exception.notFound.ResourceNotFoundException;
import com.example.blogging.web.exception.ParamsValidationDetails;
import com.example.blogging.web.exception.ProblemDetailBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.info("Validation Error has occurred: {}", ex.getMessage());

        List<ParamsValidationDetails> errors = ex.getFieldErrors().stream()
                .map(fieldError -> ParamsValidationDetails.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList();

        ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                .status(HttpStatus.BAD_REQUEST)
                .type(URI.create("urn:problem-type:validation-error"))
                .title("Validation Error")
                .detail("Validation error has occurred")
                .property("errors", errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
            log.info("Method Argument Type Mismatch has occurred: {}", ex.getMessage());

            ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .type(URI.create("urn:problem-type:method-argument-type-mismatch"))
                    .title("Method Argument Type Mismatch")
                    .detail(ex.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(problemDetail);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex) {
        log.info("Resource Not Found has occurred: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                .status(HttpStatus.NOT_FOUND)
                .type(URI.create("urn:problem-type:resource-not-found"))
                .title("Resource Not Found")
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        log.info("Resource Already Exists has occurred: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                .status(HttpStatus.CONFLICT)
                .type(URI.create("urn:problem-type:resource-already-exists"))
                .title("Resource Already Exists")
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleInternalServerError(RuntimeException ex) {
        log.error("Internal Server Error has occurred: {}", ex.getMessage());

        ProblemDetail problemDetail = ProblemDetailBuilder.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .type(URI.create("urn:problem-type:internal-server-error"))
                .title("Internal Server Error")
                .detail(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problemDetail);
    }
}
