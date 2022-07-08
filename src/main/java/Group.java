public class Group {
    private final long id;
    private Status status;

    public Group(long id, Status status) {
        this.id = id;
        this.status = status;
    }

    public Group(long id) {
        this(id, Status.UNKNOWN);
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("{ id: %d, status: %s }", id, status);
    }
}
