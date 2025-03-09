package backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    MISSING_PARAMETERS(1001, "Missing Parameters"),
    ILLEGAL_PARAMETERS(1002, "Illegal Parameters"),

    USER_NOT_FOUND(2001, "User not found"),
    WRONG_PASSWORD(2002, "Wrong password");

    private final Integer code;
    private final String message;
}
