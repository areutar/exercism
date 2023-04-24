public class ElonsToyCar {
    private int drivenMeters;
    private int batteryCharge;

    public ElonsToyCar() {
        this.batteryCharge = 100;
        this.drivenMeters = 0;
    }

    public static ElonsToyCar buy() {
        return new ElonsToyCar();
    }

    public int getDrivenMeters() {
        return drivenMeters;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public String distanceDisplay() {
        return String.format("Driven %d meters", getDrivenMeters());
    }

    public String batteryDisplay() {
        return batteryCharge == 0 ?
                "Battery empty" :
                String.format("Battery at %d%%", getBatteryCharge());
    }

    public void drive() {
        if (batteryCharge > 0) {
            drivenMeters += 20;
            batteryCharge -= 1;
        }
    }
}
