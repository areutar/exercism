import java.util.*;

class TwoBucket {
    private final int desiredLiters;
    private final Buckets startBuckets;
    private final Buckets endBuckets;
    private final String startName;
    private int moves = 0;

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startName) {
        this.desiredLiters = desiredLiters;
        this.startName = startName;
        startBuckets = new Buckets(Set.of(new Bucket(0, bucketOneCap, "one"),
                new Bucket(0, bucketTwoCap, "two"))
        );

        Queue<Buckets> toExplore = new LinkedList<>();
        toExplore.add(startBuckets);
        Map<Buckets, Buckets> pathMap = new HashMap<>();
        Set<Buckets> visited = new HashSet<>();

        Buckets current = null;
        while (!toExplore.isEmpty()) {
            current = toExplore.poll();
            if (checkBucketsAreFound(current)) {
                break;
            }
            // add neighbours of the current pair
            for (Buckets buckets : getNeighbours(current)) {
                if (!visited.contains(buckets) && !isWrongBuckets(buckets)) {
                    visited.add(buckets);
                    pathMap.put(buckets, current);
                    toExplore.add(buckets);
                }
            }
        }
        endBuckets = current;
        // reconstruct the path
        Deque<Buckets> path = new LinkedList<>();
        while (current != startBuckets) {
            path.addFirst(current);
            current = pathMap.get(current);
            moves++;
        }
        path.addFirst(startBuckets);
        System.out.println(path);
    }

    public static Set<Buckets> getNeighbours(Buckets buckets) {
        Set<Buckets> neighbours = new HashSet<>();
        for (Bucket bucket1 : buckets.getBucketSet()) {
            for (Bucket bucket2 : buckets.getBucketSet()) {
                if (bucket1 != bucket2) {
                    neighbours.add(Buckets.mixBuckets(buckets, bucket1, bucket2));
                }
            }
            neighbours.add(Buckets.fillBucket(buckets, bucket1, bucket1.getTotal()));
            neighbours.add(Buckets.fillBucket(buckets, bucket1, 0));
        }
        return neighbours;
    }

    private boolean isWrongBuckets(Buckets buckets) {
        Set<Bucket> bucketSet = buckets.getBucketSet();
        boolean oneFullFilling = bucketSet.stream()
                .filter(bucket -> bucket.getActual() == bucket.getTotal()).count() == 1;
        boolean allButOneEmpty = bucketSet.stream()
                .filter(bucket -> bucket.getActual() == 0).count() == bucketSet.size() - 1;
        if (oneFullFilling && allButOneEmpty) {
            Bucket startBucket = bucketSet.stream()
                    .filter(bucket -> bucket.getActual() != 0).findFirst().get();
            return !startBucket.getName().equals(startName);
        }
        return false;
    }

    private boolean checkBucketsAreFound(Buckets current) {
        return current.getBucketSet().stream()
                .anyMatch(bucket -> bucket.getActual() == desiredLiters);
    }

    int getTotalMoves() {
        return moves;
    }

    String getFinalBucket() {
        return Buckets.findBucket(endBuckets, desiredLiters).getName();
    }

    int getOtherBucket() {
        return endBuckets.getBucketSet().stream()
                .filter(bucket -> !bucket.getName().equals(getFinalBucket()))
                .findFirst().orElseThrow(NullPointerException::new).getActual();
    }

// https://www.codingninjas.com/codestudio/library/solving-water-jug-problem-using-bfs
}
