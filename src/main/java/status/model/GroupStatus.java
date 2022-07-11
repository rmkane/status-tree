package status.model;

import java.util.Collection;

public class GroupStatus {
  private final String id;
  private final String parentId;
  private final Status status;

  public GroupStatus(String id, String parentId, Status status) {
    this.id = id;
    this.parentId = parentId;
    this.status = status;
  }

  public GroupStatus(String id) {
    this(id, null, Status.UNKNOWN);
  }

  public GroupStatus(String id, Status status) {
    this(id, null, status);
  }

  public GroupStatus(String id, String parentId) {
    this(id, parentId, Status.UNKNOWN);
  }

  public String getId() {
    return id;
  }

  public String getParentId() {
    return parentId;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return String.format("{ id: '%s', parentId: '%s', status: %s }", id, parentId, status);
  }

  public static GroupStatus rollupStatus(GroupStatus existing, Collection<GroupStatus> others) {
    Status status =
        Status.calculateMaxStatus(
            existing.getStatus(), others.stream().map(GroupStatus::getStatus));
    return new GroupStatus(existing.getId(), existing.getParentId(), status);
  }
}
