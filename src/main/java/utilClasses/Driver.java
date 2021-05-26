package utilClasses;

public class Driver {
    private int id;
    private String driverName;
    private vehicles vehicle;
    public enum vehicles {CAR, BIKE, SCOOTER, MOTORBIKE}

    public Driver(String driverName, String vehicle) {
        this.driverName = driverName;
        this.vehicle = vehicles.valueOf(vehicle);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public vehicles getVehicle() {
        return vehicle;
    }

    public void setVehicle(vehicles vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", driverName='" + driverName + '\'' +
                ", vehicle=" + vehicle +
                '}';
    }
}