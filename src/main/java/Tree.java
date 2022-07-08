import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Tree<DATA, GROUP> {
    private final Node<GROUP> root;

    private final Function<DATA, String> keyFn;
    private final Function<DATA, String> parentKeyFn;
    private final Function<DATA, GROUP> mapper;

    public Tree(List<DATA> data, Function<DATA, String> keyFn, Function<DATA, String> parentKeyFn, Function<DATA, GROUP> mapper) {
        this.keyFn = keyFn;
        this.parentKeyFn = parentKeyFn;
        this.mapper = mapper;
        this.root = this.createRoot(data);
    }

    public void printTree() {
        printNode(this.root, 0);
    }

    private void printNode(Node<GROUP> node, int depth) {
        String indent = "  ".repeat(depth);
        System.out.printf("%s%s%n", indent, node);
        for (Node<GROUP> child : node.getChildren()) {
            printNode(child, depth + 1);
        }
    }

    private Node<GROUP> createRoot(List<DATA> data) {
        Node<GROUP> root = new Node<>(null);
        Map<String, Node<GROUP>> idMap = new HashMap<>();
        for (DATA item : data) {
            idMap.put(keyFn.apply(item), new Node<>(mapper.apply(item)));
        }
        for (DATA item : data) {
            idMap.getOrDefault(parentKeyFn.apply(item), root).getChildren().add(idMap.get(keyFn.apply(item)));
        }
        return root;
    }
}
