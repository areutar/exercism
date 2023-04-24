import java.util.Objects;

public class TeamResults implements Comparable<TeamResults>{
    private final String name;
    private int mp;
    private int w;
    private int d;
    private int l;
    private int p;

    public TeamResults(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamResults that = (TeamResults) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%-30s |  %d |  %d |  %d |  %d |  %d\n",
                name, mp, w, d, l, p);
    }

    @Override
    public int compareTo(TeamResults o) {
        if ( o.p - p != 0) {
            return o.p - p;
        }
        return this.name.compareTo(o.name);
    }

    enum Result {
        WIN, DRAW, LOSS;

        public static Result findResult(String name) {
            for (Result result : Result.values()) {
                if (result.name().toLowerCase().equals(name)) {
                    return result;
                }
            }
            throw new IllegalArgumentException("No such result found!");
        }
    }
}
