package status.tree;

import java.util.Collection;
import java.util.Collections;

public class Node<T> {
  private final T data;
  private final Collection<Node<T>> children;

  public Node(T data) {
    this(data, Collections.emptyList());
  }

  public Node(T data, Collection<Node<T>> children) {
    this.data = data;
    this.children = children;
  }

  public T getData() {
    return data;
  }

  public Collection<Node<T>> getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return String.format("%s [ %s ]", children.isEmpty() ? "LEAF" : "NODE", data);
  }
}
