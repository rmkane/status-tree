package status.model;

import java.util.Comparator;
import java.util.stream.Stream;

public enum Status {
  UNKNOWN(0),
  DEBUG(1),
  INFO(2),
  WARNING(3),
  ERROR(4);

  private final int priority;

  Status(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public static Status calculateMaxStatus(Stream<Status> statuses) {
    return statuses.max(Comparator.comparingInt(Status::getPriority)).orElse(Status.UNKNOWN);
  }

  public static Status calculateMaxStatus(Status status, Stream<Status> statuses) {
    return calculateMaxStatus(Stream.concat(Stream.of(status), statuses));
  }
}
