package app.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Tree<DATA, GROUP> {
  private final Node<GROUP> root;
  private final Function<DATA, GROUP> dataToGroup;

  public Tree(List<DATA> data, Function<DATA, GROUP> dataToGroup) {
    this.dataToGroup = dataToGroup;
    this.root = this.createRoot(data);
  }

  public abstract String keyFn(DATA data);

  public abstract String parentKeyFn(DATA data);

  public abstract GROUP createEmptyGroup();

  public abstract void process();

  public void walk(Consumer<Node<GROUP>> visitor) {
    visit(this.root, visitor);
  }

  private void visit(Node<GROUP> node, Consumer<Node<GROUP>> visitor) {
    visitor.accept(node);
    for (Node<GROUP> child : node.getChildren()) {
      visit(child, visitor);
    }
  }

  public void print() {
    printNode(this.root, 0);
  }

  private static String repeat(String str, int count) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < count; i++) {
      buff.append(str);
    }
    return buff.toString();
  }

  private void printNode(Node<GROUP> node, int depth) {
    String indent = repeat("  ", depth);
    System.out.printf("%s%s%n", indent, node);
    for (Node<GROUP> child : node.getChildren()) {
      printNode(child, depth + 1);
    }
  }

  private Node<GROUP> createRoot(List<DATA> data) {
    Node<GROUP> root = new Node<>(createEmptyGroup());
    Map<String, Node<GROUP>> idMap = new HashMap<>();
    for (DATA item : data) {
      idMap.put(keyFn(item), new Node<>(dataToGroup.apply(item)));
    }
    for (DATA item : data) {
      idMap.getOrDefault(parentKeyFn(item), root).getChildren().add(idMap.get(keyFn(item)));
    }
    return root;
  }

  protected Node<GROUP> getRoot() {
    return this.root;
  }
}
