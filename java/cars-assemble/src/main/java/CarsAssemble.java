public class CarsAssemble {
    public static final int PRODUCTION_EACH_HOUR = 221;
    public static final int FIRST_SUCCESS_RATE = 100;
    public static final int SECOND_SUCCESS_RATE = 90;
    public static final int THIRD_SUCCESS_RATE = 80;
    public static final int FOURTH_SUCCESS_RATE = 77;

    public double productionRatePerHour(int speed) {
        double probability;
        switch (speed) {
            case 1:
            case 2:
            case 3:
            case 4: {
                probability = FIRST_SUCCESS_RATE / 100.0;
                break;
            }
            case 5:
            case 6:
            case 7:
            case 8: {
                probability = SECOND_SUCCESS_RATE / 100.0;
                break;
            }
            case 9: {
                probability = THIRD_SUCCESS_RATE / 100.0;
                break;
            }
            case 10: {
                probability = FOURTH_SUCCESS_RATE / 100.0;
                break;
            }
            default:
                probability = 0.0;
        }
        return speed * PRODUCTION_EACH_HOUR * probability;
    }

    public int workingItemsPerMinute(int speed) {
        return (int) (productionRatePerHour(speed) / 60);
    }
}
