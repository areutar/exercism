import java.util.Map;

class SpaceAge {
    private static final Map<String, Double> periods = Map.ofEntries(
            Map.entry("Mercury", 0.2408467),
            Map.entry("Venus", 0.61519726),
            Map.entry("Earth", 1.0),
            Map.entry("Mars", 1.8808158),
            Map.entry("Jupiter", 11.862615),
            Map.entry("Saturn", 29.447498),
            Map.entry("Uranus", 84.016846),
            Map.entry("Neptune", 164.79132)
    );
    private final double years;

    SpaceAge(double seconds) {
        this.years = seconds / 31557600;
    }

    double onPlanet(double period) {
        return years / period;
    }

    double onMercury() {
        return onPlanet(periods.get("Mercury"));
    }

    double onVenus() {
        return onPlanet(periods.get("Venus"));
    }

    double onEarth() {
        return onPlanet(periods.get("Earth"));
    }

    double onMars() {
        return onPlanet(periods.get("Mars"));
    }

    double onJupiter() {
        return onPlanet(periods.get("Jupiter"));
    }

    double onSaturn() {
        return onPlanet(periods.get("Saturn"));
    }

    double onUranus() {
        return onPlanet(periods.get("Uranus"));
    }

    double onNeptune() {
        return onPlanet(periods.get("Neptune"));
    }
}
