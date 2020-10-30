package jump.utils.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CrmebException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CrmebException() {}

    public CrmebException(String message) {
        super(message);
    }
}
