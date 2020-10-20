package com.company.hashFunction;

import com.company.hashFunction.feistel.CBC;
import com.company.hashFunction.feistel.Feistel;
import com.company.hashFunction.sensor.Sensor;

import java.util.BitSet;

public class HashFunction {
    private BitSet IV;
    private final BitSet key;
    private final int steps;

    public HashFunction(BitSet IV, BitSet key, int steps) {
        Sensor sensor = new Sensor((double) 1 / (2 + steps), (double) 1 / (5 + steps));
        this.IV = IV;
        this.key = CycleShifts.leftCycleBitShift(key, (int) (64 * sensor.getNumber()), 64);
        this.steps = steps;
    }

    public BitSet getHashFunction(BitSet[] text) {
        CBC cbc = new CBC(text, new Feistel(key, steps), IV);
        BitSet[] result = cbc.encrypt();
        return result[result.length - 1];
    }
}
