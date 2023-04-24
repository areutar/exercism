import java.util.Objects;

class Bucket{
    private final int actual;
    private final int total;
    private final String name;

    public Bucket(int actual, int total, String name) {
        this.actual = actual;
        this.total = total;
        this.name = name;
    }

    public static Bucket fillBucket(Bucket bucket, int newSize) {
        return new Bucket(newSize, bucket.total, bucket.name);
    }

    public int getTotal() {
        return total;
    }

    public int getActual() {
        return actual;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "(" + actual + "," + total + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bucket bucket = (Bucket) o;
        return total == bucket.total && actual == bucket.actual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, actual);
    }
}