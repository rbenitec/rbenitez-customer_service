package service.customer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiRestExternalException extends RuntimeException {
    private final HttpStatus status;
    private final String detail;
    private final String url;

    public ApiRestExternalException(HttpStatus status, String title, String detail, String url) {
        super(title);
        this.status = status;
        this.detail = detail;
        this.url = url;
    }
}
