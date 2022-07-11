package status.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static status.tree.Tree.toLeaf;
import static status.tree.Tree.toNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import status.model.GroupStatus;
import status.model.Status;

public class TreeTest {

  private static final GroupStatus ROOT = new GroupStatus("ROOT");
  private static Collection<GroupStatus> dataList;

  @BeforeAll
  public static void setup() {
    dataList = new ArrayList<>();

    dataList.add(new GroupStatus("A", Status.UNKNOWN));
    dataList.add(new GroupStatus("B", Status.DEBUG));
    dataList.add(new GroupStatus("C", "A", Status.DEBUG));
    dataList.add(new GroupStatus("D", "A", Status.WARNING));
    dataList.add(new GroupStatus("E", "B", Status.ERROR));
    dataList.add(new GroupStatus("F", "B", Status.UNKNOWN));
    dataList.add(new GroupStatus("G", "B", Status.UNKNOWN));
    dataList.add(new GroupStatus("H", "C", Status.INFO));
    dataList.add(new GroupStatus("I", "C", Status.UNKNOWN));
  }

  @Test
  public void testMap() {
    Collection<Node<GroupStatus>> forest =
        Tree.createForestFromCollection(dataList, GroupStatus::getId, GroupStatus::getParentId);
    Node<GroupStatus> tree = Tree.toInternal(ROOT, forest);

    System.out.println("==== Tree ====");
    Tree.printNode(tree);

    System.out.println("\n==== Pre-Order Traversal ====");
    Tree.preOrderTraversal(tree, System.out::println);

    System.out.println("\n==== Post-Order Traversal ====");
    Tree.postOrderTraversal(tree, System.out::println);

    Node<GroupStatus> copy = Tree.map(tree, GroupStatus::rollupStatus);

    System.out.println("\n==== Copy ====");
    Tree.printNode(copy);

    assertEquals(Status.ERROR, copy.getData().getStatus());
    assertEquals(Status.WARNING, findStatusById(copy, "A"));
    assertEquals(Status.ERROR, findStatusById(copy, "B"));
    assertEquals(Status.INFO, findStatusById(copy, "C"));
    assertEquals(Status.WARNING, findStatusById(copy, "D"));
    assertEquals(Status.ERROR, findStatusById(copy, "E"));
    assertEquals(Status.UNKNOWN, findStatusById(copy, "F"));
    assertEquals(Status.UNKNOWN, findStatusById(copy, "G"));
    assertEquals(Status.INFO, findStatusById(copy, "H"));
    assertEquals(Status.UNKNOWN, findStatusById(copy, "I"));
  }

  @Test
  public void testToTree() {
    Node<String> tree = toNode("A", toLeaf("B"), toNode("C", toLeaf("D")));
    List<Node<String>> children = (List<Node<String>>) tree.getChildren();

    assertEquals("B", children.get(0).getData());
    assertEquals("C", children.get(1).getData());

    List<Node<String>> children2 = (List<Node<String>>) children.get(1).getChildren();

    assertEquals("D", children2.get(0).getData());

    Tree.printNode(tree);
  }

  private static Status findStatusById(Node<GroupStatus> node, String id) {
    return Optional.ofNullable(Tree.find(node, data -> data.getId().equals(id)))
        .map(Node::getData)
        .map(GroupStatus::getStatus)
        .orElse(Status.UNKNOWN);
  }
}
