public class Lasagna {
    private final int expectedMinutesInOven = 40;

    // TODO: define the 'expectedMinutesInOven()' method
    public int expectedMinutesInOven() {
        return expectedMinutesInOven;
    }

    // TODO: define the 'remainingMinutesInOven()' method
    public int remainingMinutesInOven(int minutesPassed) {
        return expectedMinutesInOven - minutesPassed;
    }

    // TODO: define the 'preparationTimeInMinutes()' method
    public int preparationTimeInMinutes(int numberLayers) {
        return numberLayers * 2;
    }

    // TODO: define the 'totalTimeInMinutes()' method
    public int totalTimeInMinutes(int numberLayers, int minutesPassed) {
        return preparationTimeInMinutes(numberLayers) + minutesPassed;
    }
}
