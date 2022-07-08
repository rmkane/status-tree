package app.model;

import app.type.Status;

public class StatusGroup {
  private final long id;
  private Status status;

  public StatusGroup(long id, Status status) {
    this.id = id;
    this.status = status;
  }

  public StatusGroup(long id) {
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
