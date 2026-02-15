package com.example.sakshi.ecommerce.exception;
import com.example.sakshi.ecommerce.dto.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ErrorResponse error = new ErrorResponse(
                errorMessage,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(
                "Invalid parameter value: " + ex.getName(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse(
                "Malformed JSON request",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Database error: Duplicate or invalid data",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
/*
NOTES:

* All exceptions are converted into a structured ErrorResponse object containing:
Message – Description of the error
Timestamp – Time when the error occurred
Status Code – HTTP status code
*
*
* 1. ResourceAlreadyExistsException
HTTP Status: 409 CONFLICT
This exception is thrown when attempting to create a resource that already exists in the system.
Example Scenarios:
Registering a user with an already existing email
Adding a product with a duplicate name


2. ResourceNotFoundException

HTTP Status: 404 NOT FOUND
This exception is thrown when a requested resource is not found in the database.
Example Scenarios:
Fetching a product with a non-existing ID
Deleting a user that does not exist


3. MethodArgumentNotValidException

HTTP Status: 400 BAD REQUEST
This exception occurs when validation on request body fields fails (typically triggered by @Valid annotation).
Example Scenarios:
Empty required fields
Invalid email format
Negative price value
The handler extracts the first validation error and returns a meaningful message.


4. MethodArgumentTypeMismatchException

HTTP Status: 400 BAD REQUEST
This occurs when a path variable or request parameter is of the wrong type.
Example Scenario:
GET /products/abc
Where id should be a Long.


5. HttpMessageNotReadableException

HTTP Status: 400 BAD REQUEST
This exception occurs when the request body is malformed or unreadable.
Example Scenarios:
Invalid JSON syntax
Missing request body
Incorrect data types in JSON



6. DataIntegrityViolationException

HTTP Status: 400 BAD REQUEST
This exception is triggered when database constraints are violated.
Example Scenarios:
Unique constraint violation
Foreign key constraint violation
Attempting to delete a referenced entity

* */
