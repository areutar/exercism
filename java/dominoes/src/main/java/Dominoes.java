import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dominoes {
    private static final String NO_DOMINO_CHAIN_FOUND = "No domino chain found.";
    private Set<Integer> vertices;

    public List<Domino> formChain(List<Domino> dominoesList) throws ChainNotFoundException {
        vertices = getVertices(dominoesList);
        checkDominoListIsOk(dominoesList);
        if (dominoesList.isEmpty()) {
            return dominoesList;
        }
        List<Domino> copyDominoesList = new ArrayList<>(dominoesList);

        Optional<Domino> optionalDomino = getNonDoubleDomino(copyDominoesList, vertices);

        if (optionalDomino.isEmpty()) {
            if (vertices.size() == 1) {
                return dominoesList;
            } else {
                throw new ChainNotFoundException(NO_DOMINO_CHAIN_FOUND);
            }
        }

        List<Domino> chainedList = new ArrayList<>();
        Set<Integer> chainVertices = new HashSet<>();

        while (optionalDomino.isPresent()) {
            Domino current = optionalDomino.get();

            //remove bridge
            copyDominoesList.remove(current);

            // find new cycle
            List<Domino> cycle = findPath(current.getLeft(), current.getRight(), copyDominoesList);
            copyDominoesList.add(current);

            if (chainVertices.contains(current.getLeft())) {
                cycle.add(new Domino(current.getRight(), current.getLeft()));
            } else {
                cycle.add(0, new Domino(current.getRight(), current.getLeft()));
            }

            // here insert cycle into chainList
            addCycleToChain(chainedList, cycle);

            chainVertices = getVertices(chainedList);

            //remove chainedList from copyList
            cycle.forEach(copyDominoesList::remove);
            optionalDomino = getNonDoubleDomino(copyDominoesList, chainVertices);
            if (optionalDomino.isEmpty() && chainVertices.size() != vertices.size()) {
                throw new ChainNotFoundException(NO_DOMINO_CHAIN_FOUND);
            }
        }

        return addLoopsToList(chainedList, dominoesList);
    }

    private Optional<Domino> getNonDoubleDomino(List<Domino> copyList, Set<Integer> vertices) {
        return copyList.stream()
                .filter(domino -> domino.getLeft() != domino.getRight() &&
                        (vertices.contains(domino.getLeft()) ||
                                vertices.contains(domino.getRight())))
                .findFirst();
    }

    private void addCycleToChain(List<Domino> chainedList, List<Domino> cycle) {
        int index = IntStream.range(0, chainedList.size())
                .filter(i -> chainedList.get(i).getLeft() == cycle.get(0).getLeft())
                .findFirst().orElse(0);
        IntStream.range(0, cycle.size())
                .forEach(i -> chainedList.add(index, cycle.get(cycle.size() - 1 - i)));
    }

    public List<Domino> findPath(Integer start, Integer goal, List<Domino> list) throws ChainNotFoundException {
        Deque<Integer> toExplore = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> vertices = getVertices(list);
        Map<Integer, Integer> pathsMap = new HashMap<>();
        boolean found = false;

        toExplore.push(start);
        while (!vertices.isEmpty()) {
            Integer current = toExplore.pop();
            if (current.equals(goal)) {
                found = true;
                break;
            }

            List<Integer> adjacentList = getAdjacentList(list, current);
            ListIterator<Integer> iterator = adjacentList.listIterator(adjacentList.size());
            while (iterator.hasPrevious()) {
                Integer next = iterator.previous();
                if (!visited.contains(next)) {
                    visited.add(next);
                    pathsMap.put(next, current);
                    toExplore.push(next);
                }
            }
        }

        if (!found) {
            throw new ChainNotFoundException(NO_DOMINO_CHAIN_FOUND);
        }

        // reconstruct the path
        List<Domino> path = new ArrayList<>();
        Integer current = goal;
        while (!current.equals(start)) {
            path.add(0, new Domino(pathsMap.get(current), current));
            current = pathsMap.get(current);
        }
        return path;

    }

    private int getLoopsCount(List<Domino> dominoesList, Integer i) {
        return (int) dominoesList.stream().filter(domino -> domino.equals(new Domino(i, i))).count();
    }

    private List<Domino> addLoopsToList(List<Domino> chainedList, List<Domino> dominoesList) {
        List<Domino> result = new ArrayList<>();
        Set<Integer> verticesCopy = new HashSet<>(vertices);
        for (Domino domino : chainedList) {
            int left = domino.getLeft();
            if (verticesCopy.contains(left)) {
                verticesCopy.remove(left);
                int loopsCount = getLoopsCount(dominoesList, domino.getLeft());
                for (int j = 0; j < loopsCount; j++) {
                    result.add(new Domino(domino.getLeft(), domino.getLeft()));
                }
            }
            result.add(domino);
        }
        System.out.println();
        return result;
    }

    private Set<Integer> getVertices(List<Domino> dominoesList) {
        return dominoesList.stream()
                .flatMap(
                        domino -> Stream.of(domino.getLeft(), domino.getRight())
                )
                .collect(Collectors.toSet());
    }

    private void checkDominoListIsOk(List<Domino> dominoesList) throws ChainNotFoundException {
        List<Integer> frequencies = vertices.stream()
                .map(i -> getAdjacentList(dominoesList, i).size())
                .collect(Collectors.toList());
        int countOddFrequencies = (int) frequencies.stream().filter(i -> i % 2 != 0).count();
        if (countOddFrequencies > 0) {
            throw new ChainNotFoundException(NO_DOMINO_CHAIN_FOUND);
        }
    }

    private List<Integer> getAdjacentList(List<Domino> list, int i) {
        return list.stream()
                .filter(domino -> domino.getLeft() == i ^ domino.getRight() == i)
                .map(domino -> domino.getLeft() == i ? domino.getRight() : domino.getLeft())
                .collect(Collectors.toList());
    }
}
