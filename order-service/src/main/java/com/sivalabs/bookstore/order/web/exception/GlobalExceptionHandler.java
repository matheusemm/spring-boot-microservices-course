package com.sivalabs.bookstore.order.web.exception;

import com.sivalabs.bookstore.order.domain.InvalidOrderException;
import com.sivalabs.bookstore.order.domain.OrderNotFoundException;
import java.net.URI;
import java.time.Instant;
import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
    private static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/internal-server-error");
    private static final URI BAD_REQUEST_TYPE = URI.create("https://api.bookstore.com/errors/bad-request");

    private static final String SERVICE_NAME = "order-service";

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnhandledException(Exception e) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problem.setTitle("Internal Server Error");
        problem.setType(ISE_FOUND_TYPE);
        problem.setProperty("service", SERVICE_NAME);
        problem.setProperty("error_category", "Generic");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ProblemDetail handleOrderNotFoundException(OrderNotFoundException e) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("Order Not Found");
        problem.setType(NOT_FOUND_TYPE);
        problem.setProperty("service", SERVICE_NAME);
        problem.setProperty("error_category", "Generic");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @ExceptionHandler(InvalidOrderException.class)
    ProblemDetail handleInvalidOrderException(InvalidOrderException e) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problem.setTitle("Invalid Order Creation Request");
        problem.setType(BAD_REQUEST_TYPE);
        problem.setProperty("service", SERVICE_NAME);
        problem.setProperty("error_category", "Generic");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();

        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request payload");
        problem.setTitle("Bad Request");
        problem.setType(BAD_REQUEST_TYPE);
        problem.setProperty("errors", errors);
        problem.setProperty("service", SERVICE_NAME);
        problem.setProperty("error_category", "Generic");
        problem.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
}
