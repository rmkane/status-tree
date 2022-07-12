package status.tree;

import java.io.PrintStream;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeFunctions {
  public static <T, K> Collection<Node<T>> createForestFromCollection(
      Collection<T> data, Function<T, K> keyFn, Function<T, K> parentKeyFn) {
    Collection<T> roots = new ArrayList<>();
    Map<K, Collection<T>> parentToChildren = new HashMap<>();

    for (T item : data) {
      K parentKey = parentKeyFn.apply(item);
      if (parentKey != null) {
        if (!parentToChildren.containsKey(parentKey)) {
          parentToChildren.put(parentKey, new ArrayList<>());
        }
        parentToChildren.get(parentKey).add(item);
      } else {
        roots.add(item);
      }
    }

    return roots.stream()
        .map(root -> createTreeFromCollection(root, keyFn, parentToChildren))
        .collect(Collectors.toList());
  }

  public static <T> Node<T> find(Node<T> tree, Function<T, Boolean> matcher) {
    return find(Collections.singletonList(tree), matcher);
  }

  public static <T> Node<T> find(Collection<Node<T>> forest, Function<T, Boolean> matcher) {
    for (Node<T> tree : forest) {
      if (matcher.apply(tree.getData())) {
        return tree;
      }
      if (!tree.getChildren().isEmpty()) {
        Node<T> found = find(tree.getChildren(), matcher);
        if (found != null) {
          return found;
        }
      }
    }
    return null;
  }

  public static <T> Node<T> map(Node<T> tree, BiFunction<T, Collection<T>, T> mapper) {
    Collection<Node<T>> children =
        tree.getChildren().stream().map(child -> map(child, mapper)).collect(Collectors.toList());
    return internal(
        mapper.apply(
            tree.getData(), children.stream().map(Node::getData).collect(Collectors.toList())),
        children);
  }

  public static <T> void traverse(Node<T> node, Consumer<Node<T>> visitor) {
    preOrderTraversal(node, visitor);
  }

  public static <T> void preOrderTraversal(Node<T> node, Consumer<Node<T>> visitor) {
    visitor.accept(node);
    node.getChildren().forEach(child -> preOrderTraversal(child, visitor));
  }

  public static <T> void postOrderTraversal(Node<T> node, Consumer<Node<T>> visitor) {
    node.getChildren().forEach(child -> postOrderTraversal(child, visitor));
    visitor.accept(node);
  }

  public static <T> void printNode(Node<T> node) {
    printNode(node, System.out);
  }

  public static <T> void printNode(Node<T> node, PrintStream printStream) {
    printNodeInternal(node, printStream, 0);
  }

  public static <T> void printNodeInternal(Node<T> node, PrintStream printStream, int depth) {
    printStream.printf("%s%s%n", "  ".repeat(depth), node);
    for (Node<T> child : node.getChildren()) {
      printNodeInternal(child, printStream, depth + 1);
    }
  }

  public static <T> Node<T> internal(T data, Collection<Node<T>> children) {
    return new TreeNode<>(data, children);
  }

  public static <T> Node<T> leaf(T data) {
    return new TreeNode<>(data);
  }

  @SafeVarargs
  public static <T> Node<T> tree(T data, Node<T>... children) {
    return new TreeNode<>(data, Arrays.asList(children));
  }

  private static <T, K> Node<T> createNodeFromCollection(
      T data, Function<T, K> keyFn, Map<K, Collection<T>> parentToChildren) {
    return internal(data, createNodesFromCollection(data, keyFn, parentToChildren));
  }

  private static <T, K> Collection<Node<T>> createNodesFromCollection(
      T data, Function<T, K> keyFn, Map<K, Collection<T>> parentToChildren) {
    return createNodesFromCollection(
        lookupChildren(data, keyFn, parentToChildren), keyFn, parentToChildren);
  }

  private static <T, K> Collection<Node<T>> createNodesFromCollection(
      Collection<T> data, Function<T, K> keyFn, Map<K, Collection<T>> parentToChildren) {
    return data.stream()
        .map(child -> createNodeFromCollection(child, keyFn, parentToChildren))
        .collect(Collectors.toList());
  }

  private static <T, K> Node<T> createTreeFromCollection(
      T data, Function<T, K> keyFn, Map<K, Collection<T>> parentToChildren) {
    return createNodeFromCollection(data, keyFn, parentToChildren);
  }

  private static <T, K> Collection<T> lookupChildren(
      T data, Function<T, K> keyFn, Map<K, Collection<T>> parentToChildren) {
    return parentToChildren.getOrDefault(keyFn.apply(data), Collections.emptyList());
  }
}
