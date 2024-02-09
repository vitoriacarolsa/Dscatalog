package com.devsuperior.dsCatagog.resources.exceptions;

import com.devsuperior.dsCatagog.services.exceptions.DatabaseException;
import com.devsuperior.dsCatagog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
   @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
    HttpStatus status = HttpStatus.NOT_FOUND;
    StandarError err = new StandarError();
    err.setTimestamp(Instant.now());
    err.setStatus(status.value());
    err.setError("Resource not found");
    err.setMessage(e.getMessage());
    err.setPath(request.getRequestURI());
    return ResponseEntity.status(status.value()).body(err);
    }

 @ExceptionHandler(DatabaseException.class)
 public ResponseEntity<StandarError> database(DatabaseException e, HttpServletRequest request){
    HttpStatus status= HttpStatus.BAD_REQUEST;
  StandarError err = new StandarError();
  err.setTimestamp(Instant.now());
  err.setStatus(status.value());
  err.setError("Database exception");
  err.setMessage(e.getMessage());
  err.setPath(request.getRequestURI());
  return ResponseEntity.status(status.value()).body(err);
 }


}
