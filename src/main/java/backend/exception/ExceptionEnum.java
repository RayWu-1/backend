package backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    MISSING_PARAMETERS(1001, "Missing Parameters"),
    ILLEGAL_PARAMETERS(1002, "Illegal Parameters"),

    USER_NOT_FOUND(2001, "User not found"),
    WRONG_PASSWORD(2002, "Wrong password"),
    CONTENT_NOT_FOUND(2003, "Content not found"),
    IS_NOT_PUBLISHED(2004, "Content is not published"),
    TAG_NOT_FOUND(2005, "Tag not found"),
    TAG_IN_USE(2006, "Tag is in use and cannot be deleted"),
    TAG_NAME_ALREADY_EXISTS(2007, "Tag of the same name already exists, consider merge the tags"),
    FILE_NOT_FOUND(2008, "File not found"),
    MALFORMED_FILE_PATH(2009, "Malformed file path"),
    INVALID_FILE_REQUEST_TYPE(2010, "Invalid file request type (normal request or range request)");

    private final Integer code;
    private final String message;
}
