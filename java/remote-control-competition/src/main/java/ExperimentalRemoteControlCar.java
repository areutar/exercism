public class ExperimentalRemoteControlCar extends RemoteControlCarImpl {
    private static final int experimentalDistanceTravelled = 20;

    public ExperimentalRemoteControlCar() {
        super();
        setBuildInDistanceTravelled(experimentalDistanceTravelled);
    }
}
