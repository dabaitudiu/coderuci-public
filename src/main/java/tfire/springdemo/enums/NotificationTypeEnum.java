package tfire.springdemo.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1," Replied to Question "),
    REPLY_COMMENT(2, " Replied to Comment ");

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }



    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum e : NotificationTypeEnum.values()) {
            if (e.getType() == type) return e.getName();
        }
        return "";
    }
}
