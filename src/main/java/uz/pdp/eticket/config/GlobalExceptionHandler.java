package uz.pdp.eticket.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.eticket.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<String> authException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
    @ExceptionHandler(value = DataAlreadyExistsException.class)
    public ResponseEntity<String> dataAlreadyExists (DataAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> authException(AuthenticationCredentialsNotFoundException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<String> dataNotFound (DataNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(value = CannotBeChangedException.class)
    public ResponseEntity<String> cannotBeChanged(CannotBeChangedException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> qovunAuthExceptionHandler(BindException e) {
        StringBuilder errors = new StringBuilder();
        e.getAllErrors().forEach(error -> {
            errors.append(error.getDefaultMessage()).append("\n");
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }

    @ExceptionHandler(value = ExceededLimitException.class)
    public ResponseEntity<String> badRequest(ExceededLimitException e) {
        return ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).body(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> forbidden(AccessDeniedException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }

}
