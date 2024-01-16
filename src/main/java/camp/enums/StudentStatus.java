package camp.enums;

public enum StudentStatus {
    GREEN, RED, YELLOW;

    public static StudentStatus of (String status) {
        for (StudentStatus value : values()) {
            if (value.name().equals(status)) {
                return value;
            }
        }
        throw new NullPointerException(status + " 해당 상태는 존재하지 않습니다.");
    }
}
