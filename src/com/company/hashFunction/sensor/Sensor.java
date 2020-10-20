package com.company.hashFunction.sensor;

public class Sensor {
    private double z;
    private double R;
    private final double n = 8;

    public Sensor(double z, double R) {
        this.z = z;
        this.R = R;
    }

    public double getNumber() {
        this.z = this.z + Math.pow(10, -n);
        this.R = (this.R / z + Math.PI) % 1;
        return R;
    }


}
