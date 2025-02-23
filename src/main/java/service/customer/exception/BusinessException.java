package service.customer.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String operation;
    private final String message;

    public BusinessException(String operation, String message) {
        this.operation = operation;
        this.message = message;
    }
}
