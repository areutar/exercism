import java.util.Arrays;
import java.util.List;

public class TestTrack {

    public static void race(RemoteControlCar car) {
        car.drive();
    }

    public static List<ProductionRemoteControlCar> getRankedCars(ProductionRemoteControlCar prc1,
                                                                 ProductionRemoteControlCar prc2) {
        List<ProductionRemoteControlCar> listCars = Arrays.asList(prc1, prc2);
        listCars.sort(ProductionRemoteControlCar::compareTo);
        return listCars;
    }
}
