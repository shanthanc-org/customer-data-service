package org.shanthan.customerdataservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomerDataException extends RuntimeException {

    private HttpStatus httpStatus;

    public CustomerDataException(String message) {
        super(message);
        httpStatus = null;
    }

    public CustomerDataException(String message, Throwable cause) {
        super(message, cause);
        httpStatus = null;
    }

    public CustomerDataException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CustomerDataException(HttpStatus httpStatus, String message, Throwable throwable) {
        super(message, throwable);
        this.httpStatus = httpStatus;
    }
}
