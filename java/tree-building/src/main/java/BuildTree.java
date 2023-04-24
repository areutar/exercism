import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BuildTree {
    public static final String INVALID_RECORDS = "Invalid Records";
    ArrayList<TreeNode> treeNodes = new ArrayList<>();

    TreeNode buildTree(ArrayList<Record> records) throws InvalidRecordsException {
        records.sort(Comparator.comparing(Record::getRecordId));
        List<Integer> ids = records.stream()
                .map(Record::getRecordId)
                .collect(Collectors.toList());
        validate(records, ids);

        IntStream.range(0, ids.size()).forEach(i -> treeNodes.add(new TreeNode(i)));

        for (Record record : records) {
            TreeNode node = treeNodes.get(record.getParentId());
            TreeNode child = treeNodes.get(record.getRecordId());
            addChild(node, child);
        }

        if (!treeNodes.isEmpty()) {
            return treeNodes.get(0);
        }
        return null;
    }

    void addChild(TreeNode node, TreeNode child) {
        if (node.getNodeId() != child.getNodeId()) {
            node.getChildren().add(child);
        }
    }

    private void validate(ArrayList<Record> records, List<Integer> ids) throws InvalidRecordsException {
        if (!records.isEmpty()) {
            if (ids.get(ids.size() - 1) != ids.size() - 1 || ids.get(0) != 0) {
                throw new InvalidRecordsException(INVALID_RECORDS);
            }
        }
        for (Record record : records) {
            if (record.getRecordId() == 0 && record.getParentId() != 0 ||
                    record.getRecordId() < record.getParentId() ||
                    record.getRecordId() == record.getParentId() && record.getRecordId() != 0
            ) {
                throw new InvalidRecordsException(INVALID_RECORDS);
            }
        }
    }
}
