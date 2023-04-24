import java.util.List;

public class Knapsack {
    private List<Item> items;

    public int maximumValue(int weight, List<Item> items) {
        this.items = items;
        return maxValue(items.size(), weight);
    }

    private int maxValue(int i, int maxWeight) {
        if (i == 0) {
            return 0;
        }

        Item currItem = items.get(i - 1);
        if (currItem.getWeight() > maxWeight) {
            return maxValue(i - 1, maxWeight);
        } else {
            return Math.max(maxValue(i - 1, maxWeight),
                    maxValue(i - 1, maxWeight - currItem.getWeight()) + currItem.getValue());
        }
    }
}