package service.customer.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;
import service.customer.exception.ApiRestExternalException;
import service.customer.exception.BusinessException;
import service.customer.model.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiRestExternalException.class)
    public Mono<ResponseEntity<ErrorDto>> handleCustomException(ApiRestExternalException ex) {
        ErrorDto errorDto = new ErrorDto(
                ex.getStatus().value(),
                ex.getMessage(),
                ex.getDetail(),
                ex.getUrl()
        );
        return Mono.just(ResponseEntity.status(ex.getStatus()).body(errorDto));
    }

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ErrorDto>> businessException(BusinessException ex) {
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.PARTIAL_CONTENT.value(),
                ex.getOperation(),
                ex.getMessage(),
                null
        );
        return Mono.just(ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(errorDto));
    }
}
