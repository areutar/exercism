class ProductionRemoteControlCar extends RemoteControlCarImpl implements Comparable<ProductionRemoteControlCar>{
    private static final int productionDistanceTravelled = 10;

    private int numberOfVictories;

    public ProductionRemoteControlCar() {
        super();
        setBuildInDistanceTravelled(productionDistanceTravelled);
    }

    public int getNumberOfVictories() {
        return numberOfVictories;
    }

    public void setNumberOfVictories(int numberOfVictories) {
        this.numberOfVictories = numberOfVictories;
    }

    @Override
    public int compareTo(ProductionRemoteControlCar o) {
        return getNumberOfVictories() - o.numberOfVictories;
    }
}
