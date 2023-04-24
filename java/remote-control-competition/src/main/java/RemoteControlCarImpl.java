public abstract class RemoteControlCarImpl implements RemoteControlCar {
    private int distanceTravelled;
    private int buildInDistanceTravelled;

    public RemoteControlCarImpl() {
        setDistanceTravelled(0);
    }

    @Override
    public void drive() {
        distanceTravelled += buildInDistanceTravelled;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(int distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public void setBuildInDistanceTravelled(int buildInDistanceTravelled) {
        this.buildInDistanceTravelled = buildInDistanceTravelled;
    }
}
