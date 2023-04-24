import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class CustomSet<T> extends HashSet<T> {
    public CustomSet(Collection<T> collection) {
        super(collection);
    }

    public boolean isSubset(CustomSet<T> secondSet) {
        return this.containsAll(secondSet);
    }

    public boolean isDisjoint(CustomSet<T> customSet) {
        return Collections.disjoint(this, customSet);
    }

    public CustomSet<T> getIntersection(CustomSet<Object> customSet) {
        return new CustomSet<>(this.stream()
                .filter(customSet::contains)
                .collect(Collectors.toSet()));
    }

    public CustomSet<T> getDifference(CustomSet<Object> customSet) {
        return new CustomSet<>(this.stream()
                .filter(t -> !customSet.contains(t))
                .collect(Collectors.toSet()));
    }

    public CustomSet<T> getUnion(CustomSet<T> customSet) {
        this.addAll(customSet);
        return this;
    }
}