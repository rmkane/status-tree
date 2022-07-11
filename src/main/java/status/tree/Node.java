package status.tree;

import java.util.Collection;

public interface Node<T> {
  T getData();

  Collection<Node<T>> getChildren();
}
