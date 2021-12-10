package core.zephyr.enums;

public enum ExecutionStatus {
    PASS("1"),
    FAIL("2"),
    WIP("3");


    private final String zephyrId;

    ExecutionStatus(String zephyrId) {
        this.zephyrId = zephyrId;
    }

    public String getZephyrId() {
        return zephyrId;
    }
}
