package com.mordor.exception;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ApiError error = getApiRunTimeError(resourceNotFoundException, HttpStatus.NOT_FOUND);
        error.setTitle("Resource Not Found");

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(LeftOverSeatsException.class)
    public ResponseEntity<ApiError> handleLeftOverSeatsException(LeftOverSeatsException leftOverSeatsException) {
        ApiError error = getApiRunTimeError(leftOverSeatsException, HttpStatus.BAD_REQUEST);
        error.setTitle("Bad Request");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(SeatReservedException.class)
    public ResponseEntity<ApiError> handleSeatReservedException(SeatReservedException seatReservedException) {
        ApiError error = getApiRunTimeError(seatReservedException, HttpStatus.BAD_REQUEST);
        error.setTitle("Bad Request");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ReservationTimeException.class)
    public ResponseEntity<ApiError> handleSeatReservedException(ReservationTimeException reservationTimeException) {
        ApiError error = getApiRunTimeError(reservationTimeException, HttpStatus.BAD_REQUEST);
        error.setTitle("Bad Request");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
    	
        List<String> errors = new ArrayList<String>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
        	errors.add(error.getField() + ": " + error.getDefaultMessage());
        	});
        
        ApiError apiError = new ApiError(status, ex.getClass().getName(),
        		new Timestamp(System.currentTimeMillis()), errors);
        apiError.setTitle("Data Not Valid");
        
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }
    
    private ApiError getApiRunTimeError(RuntimeException exception, HttpStatus status) {
    	ApiError error = new ApiError();
        error.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        error.setStatus(status);
        error.setDetail(exception.getMessage());
        error.setMessage(exception.getClass().getName());
        
        return error;	
    } 
} 