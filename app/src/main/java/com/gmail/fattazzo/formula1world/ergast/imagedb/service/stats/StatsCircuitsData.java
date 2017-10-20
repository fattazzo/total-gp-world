package com.gmail.fattazzo.formula1world.ergast.imagedb.service.stats;

/**
 * @author fattazzo
 *         <p/>
 *         date: 09/10/17
 */
public class StatsCircuitsData {

    private int id;
    private String name;

    private int count;

    private int driverId;
    private String driverName;

    private int constructorId;
    private String constructorName;
    private String constructorRef;

    private Type type;

    public StatsCircuitsData(int id, String name, int driverId, String driverName, int constructorId, String constructorName, String constructorRef, Type type) {
        this.id = id;
        this.name = name;

        this.driverId = driverId;
        this.driverName = driverName;

        this.constructorId = constructorId;
        this.constructorName = constructorName;
        this.constructorRef = constructorRef;

        this.type = type;
    }

    public StatsCircuitsData(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.type = Type.COUNT;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getWinnerName() {
        switch (type) {
            case CONSTRUCTORS_WINNER:
                return constructorName;
            case DRIVERS_WINNER:
                return driverName;
            default:
                return "";
        }
    }

    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getConstructorId() {
        return constructorId;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public String getConstructorRef() {
        return constructorRef;
    }

    public enum Type {COUNT, DRIVERS_WINNER, CONSTRUCTORS_WINNER}
}
