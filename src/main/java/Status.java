public enum Status {
    UNKNOWN(0),
    DEBUG(1),
    INFO(2),
    WARNING(3),
    ERROR(4);

    private int priority;

    Status(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
