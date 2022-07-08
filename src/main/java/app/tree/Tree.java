package app.tree;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Tree<DATA, GROUP> {
	private final Node<GROUP> root;

	public Tree(List<DATA> data) {
		this.root = this.createRoot(data);
	}

	public abstract String keyFn(DATA data);

	public abstract String parentKeyFn(DATA data);

	public abstract GROUP createGroupFromData(DATA data);
	
	public abstract GROUP createEmptyGroup();

	public abstract void process();

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
		Node<GROUP> root = new Node<>(createEmptyGroup());
		Map<String, Node<GROUP>> idMap = new HashMap<>();
		for (DATA item : data) {
			idMap.put(keyFn(item), new Node<>(createGroupFromData(item)));
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
