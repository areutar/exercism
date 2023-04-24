import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

class Buckets {
    private final Set<Bucket> bucketSet;

    public Buckets(Set<Bucket> bucketSet) {
        this.bucketSet = bucketSet;
    }

    public Set<Bucket> getBucketSet() {
        return new HashSet<>(bucketSet);
    }

    public static Buckets mixBuckets(Buckets buckets, Bucket bucket1, Bucket bucket2) {
        Set<Bucket> bucketsCopy = buckets.getBucketSet();
        Bucket bucket1New = new Bucket(min(bucket1.getActual() +
                bucket2.getActual(), bucket1.getTotal()),
                bucket1.getTotal(), bucket1.getName());
        Bucket bucket2New = new Bucket(max(bucket2.getActual() -
                bucket1.getTotal() +
                bucket1.getActual(), 0),
                bucket2.getTotal(), bucket2.getName());
        bucketsCopy.remove(bucket1);
        bucketsCopy.remove(bucket2);
        bucketsCopy.add(bucket1New);
        bucketsCopy.add(bucket2New);
        return new Buckets(bucketsCopy);
    }

    public static Buckets fillBucket(Buckets buckets, Bucket bucket, int newSize) {
        Set<Bucket> bucketsCopy = buckets.getBucketSet();
        Bucket newBucket = Bucket.fillBucket(bucket, newSize);
        bucketsCopy.remove(bucket);
        bucketsCopy.add(newBucket);
        return new Buckets(bucketsCopy);
    }

    public static Bucket findBucket(Buckets buckets, int size) {
        return buckets.getBucketSet().stream()
                .filter(bucket -> bucket.getActual() == size)
                .findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buckets buckets1 = (Buckets) o;
        return bucketSet.equals(buckets1.bucketSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucketSet);
    }

    @Override
    public String toString() {
        return bucketSet.toString();
    }
}