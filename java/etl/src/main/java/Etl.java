import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Etl {
    Map<String, Integer> transform(Map<Integer, List<String>> old) {
        Map<String, Integer> res = new HashMap<>();
        old.forEach((key, value) -> value
                .forEach(s -> res.putIfAbsent(s.toLowerCase(), key)));
        return res;
    }
}
