package tfire.springdemo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001, "The question you search doesn't Exist"),
    PAGE_NOT_EXIST(2002, "The page you search doesn't exist"),
    SERVER_ERROR(2003, "Sorry, it seems that something wrong with the server"),
    TARGET_PARAM_NOT_FOUND(2004, "No question or comment chosen to comment"),
    NO_LOGIN(2005, "Please sign in before you comment"),
    TYPE_PARAM_WRONG(2006, "Wrong comment type or comment not existed."),
    COMMENT_NOT_FOUND(2007, "The comment you replied doesn't exist any more"),
    CONTENT_IS_EMPTY(2008, "Comment cannot be null"),
    READ_NOTIFICATION_FAIL(2009, "Notification id mismatch"),
    NOTIFICATION_NOT_FOUND(2010, "Notification not found");

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
