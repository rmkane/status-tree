package app.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
  private final T data;
  private final List<Node<T>> children;

  public Node(T data) {
    this.data = data;
    this.children = new ArrayList<>();
  }

  public T getData() {
    return data;
  }

  public List<Node<T>> getChildren() {
    return children;
  }

  @Override
  public String toString() {
    return String.valueOf(data);
  }
}
