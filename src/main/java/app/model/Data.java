package app.model;

import app.type.Status;

public class Data {
  private final long id;
  private final Long parentId;
  private final Status[] statuses;

  public Data(long id, Long parentId, Status... statuses) {
    this.id = id;
    this.parentId = parentId;
    this.statuses = statuses;
  }

  public Data(long id, Status... statuses) {
    this(id, null, statuses);
  }

  public long getId() {
    return id;
  }

  public Long getParentId() {
    return parentId;
  }

  public Status[] getStatuses() {
    return statuses;
  }
}
