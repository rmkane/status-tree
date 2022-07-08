import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final List<Data> dataList;

    static {
        dataList = new ArrayList<>();

        dataList.add(new Data(1L));
        dataList.add(new Data(2L, Status.DEBUG, Status.DEBUG));
        dataList.add(new Data(3L, Status.INFO, Status.DEBUG));
        dataList.add(new Data(4L, 1L, Status.INFO, Status.DEBUG));
        dataList.add(new Data(5L, 1L, Status.DEBUG));
        dataList.add(new Data(6L, 1L, Status.WARNING));
        dataList.add(new Data(7L, 2L, Status.INFO, Status.DEBUG));
        dataList.add(new Data(8L, 7L, Status.DEBUG));
        dataList.add(new Data(9L, 8L));
        dataList.add(new Data(10L, 3L, Status.ERROR, Status.ERROR));
    }

    public static void main(String[] args) {
        Tree<Data, Group> tree = new Tree<>(dataList,
                d -> String.valueOf(d.getId()),
                d -> String.valueOf(d.getParentId()),
                d -> new Group(d.getId(), calculateMaxStatus(d.getStatuses())));

        tree.printTree();
    }

    private static Status calculateMaxStatus(Status... statuses) {
        Status max = Status.UNKNOWN;
        for (Status status : statuses) {
            if (status.getPriority() > max.getPriority()) {
                max = status;
            }
        }
        return max;
    }
}
