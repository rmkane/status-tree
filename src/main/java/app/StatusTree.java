package app;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import app.model.Data;
import app.model.StatusGroup;
import app.tree.Node;
import app.tree.Tree;
import app.type.Status;

public class StatusTree extends Tree<Data, StatusGroup> {
	public static Status calculateMaxStatus(Status... statuses) {
		return Arrays.stream(statuses)
				.filter(s -> s != Status.UNKNOWN)
				.max(Comparator.comparing(Status::getPriority))
				.orElse(Status.UNKNOWN);
	}

	public StatusTree(List<Data> data) {
		super(data);
	}

	@Override
	public String keyFn(Data data) {
		return String.valueOf(data.getId());
	}

	@Override
	public String parentKeyFn(Data data) {
		return String.valueOf(data.getParentId());
	}

	@Override
	public StatusGroup createGroupFromData(Data data) {
		return new StatusGroup(data.getId(), StatusTree.calculateMaxStatus(data.getStatuses()));
	}

	@Override
	public StatusGroup createEmptyGroup() {
		return new StatusGroup(0L, Status.UNKNOWN);
	}

	@Override
	public void process() {
		calculateStatusInternal(this.getRoot());
	}

	private Status calculateStatusInternal(Node<StatusGroup> node) {
		Status status = Optional.of(node).map(Node::getData).map(StatusGroup::getStatus).orElse(Status.UNKNOWN);

		for (Node<StatusGroup> child : node.getChildren()) {
			status = calculateMaxStatus(status, calculateStatusInternal(child));
		}

		if (node.getData() != null) {
			node.getData().setStatus(status);
		}

		return status;
	}
}
