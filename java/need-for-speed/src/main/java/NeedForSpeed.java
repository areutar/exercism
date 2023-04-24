class NeedForSpeed {
    private final int speed;
    private final int batteryDrain;
    private int batteryCharge;
    private int metersDriven;
    private static final int NITRO_SPEED = 50;
    private static final int NITRO_DRAIN = 4;
    public static final int MAX_BATTERY_CHARGE = 100;

    public NeedForSpeed(int speed, int batteryDrain) {
        this.speed = speed;
        this.batteryDrain = batteryDrain;
        batteryCharge = MAX_BATTERY_CHARGE;
        metersDriven = 0;
    }

    public int getSpeed() {
        return speed;
    }

    public int getBatteryDrain() {
        return batteryDrain;
    }

    public boolean batteryDrained() {
        return batteryCharge <= 0;
    }

    public int distanceDriven() {
        return metersDriven;
    }

    public void drive() {
        batteryCharge -= batteryDrain;
        if (batteryCharge >= 0) {
            metersDriven += speed;
        }
    }

    public static NeedForSpeed nitro() {
        return new NeedForSpeed(NITRO_SPEED, NITRO_DRAIN);
    }
}

class RaceTrack {
    private final int distance;

    public RaceTrack(int distance) {
        this.distance = distance;
    }

    public boolean tryFinishTrack(NeedForSpeed car) {
        int canDrive = NeedForSpeed.MAX_BATTERY_CHARGE / car.getBatteryDrain();
        return distance <= car.getSpeed() * canDrive;
    }
}
