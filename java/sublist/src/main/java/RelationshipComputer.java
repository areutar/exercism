import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RelationshipComputer<T> {
    Relationship computeRelationship(List<T> firstList, List<T> secondList) {
        if (firstList.equals(secondList)) {
            return Relationship.EQUAL;
        }
        int size1 = firstList.size();
        int size2 = secondList.size();
        if (size1 < size2 && checkIfSublist(firstList, secondList)) {
            return Relationship.SUBLIST;
        }
        if (size1 > size2 && checkIfSublist(secondList, firstList)) {
            return Relationship.SUPERLIST;
        }
        return Relationship.UNEQUAL;
    }

    private boolean checkIfSublist(List<T> firstList, List<T> secondList) {
        int size1 = firstList.size();
        int size2 = secondList.size();
       return IntStream.rangeClosed(0, size2 - size1)
                .anyMatch(i -> sublist(secondList, i, size1 + i).equals(firstList));
    }

    private List<T> sublist(List<T> list, int start, int end) {
        return list.stream()
                .skip(start)
                .limit((long) end - start)
                .collect(Collectors.toList());
    }
}
