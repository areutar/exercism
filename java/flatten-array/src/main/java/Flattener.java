import java.util.*;

public class Flattener {
    public List<Object> flatten(List<Object> list) {
//        return list.stream()
//                .filter(Objects::nonNull)
//                .map(o -> o instanceof List ? flatten((List<Object>) o) : List.of(o))
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
        List<Object> result = new ArrayList<>();
        Stack<Object> stack = new Stack<>();
        List<Object> objects = list;
        stack.push(objects);

        // todo  needed Queue !!!
        while (!stack.empty()) {

            Iterator<Object> iterator = objects.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o == null) {
                    continue;
                }
                if (o instanceof List) {
                    stack.push(o);
                } else {
                    result.add(o);
                }
            }
            objects = (List<Object>) stack.pop();

        }
        return result;
    }
}