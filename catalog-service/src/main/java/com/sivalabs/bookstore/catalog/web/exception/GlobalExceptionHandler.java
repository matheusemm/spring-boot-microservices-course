package com.sivalabs.bookstore.catalog.web.exception;

import com.sivalabs.bookstore.catalog.domain.ProductNotFoundException;
import java.net.URI;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
    private static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/internal-server-error");

    private static final String SERVICE_NAME = "catalog-service";

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

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException e) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setTitle("Product Not Found");
        problem.setType(NOT_FOUND_TYPE);
        problem.setProperty("service", SERVICE_NAME);
        problem.setProperty("error_category", "Generic");
        problem.setProperty("timestamp", Instant.now());

        return problem;
    }
}
