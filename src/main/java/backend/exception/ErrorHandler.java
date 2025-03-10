package backend.exception;

import backend.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDto<Void> exceptionHandler(Exception e) {
        System.out.println("Unknown Error: " + e);
        return new ResponseDto<Void>(1000, "Unknown Error: " + e.getMessage(), null);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto<Void> exceptionHandler(BusinessException e) {
        System.out.println("Business Exception: " + e);
        return new ResponseDto<Void>(e.getCode(), "Error: " + e.getMessage(), null);
    }
}
