package status.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class TreeNode<T> implements Node<T> {
  private final T data;
  private final Collection<Node<T>> children;

  public TreeNode(T data) {
    this(data, Collections.emptyList());
  }

  public TreeNode(T data, Collection<Node<T>> children) {
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
  public int hashCode() {
    return Objects.hash(children, data);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    @SuppressWarnings("rawtypes")
    TreeNode other = (TreeNode) obj;
    return Objects.equals(children, other.children) && Objects.equals(data, other.data);
  }

  @Override
  public String toString() {
    return String.format("%s [ %s ]", children.isEmpty() ? "LEAF" : "NODE", data);
  }
}
