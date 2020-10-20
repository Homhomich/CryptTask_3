package com.company.hashFunction.feistel;

import com.company.hashFunction.sensor.Sensor;

import java.util.BitSet;

public class Feistel {
    private final BitSet key;
    private final int steps;


    public Feistel(BitSet key, int steps) {
        this.key = key;
        this.steps = steps;
    }


    public BitSet encrypt(BitSet message) {
        BitSet encryptMessage = message.get(0, 64);
        for (int i = 1; i < steps; i++) {
            encryptMessage = encryptStep(encryptMessage, i);
        }
        System.gc();
        return encryptMessage;
    }

    public BitSet decrypt(BitSet message) {
        BitSet decryptMessage = message.get(0, 64);
        for (int i = steps - 1; i > 0; i--) {
            decryptMessage = decryptionStep(decryptMessage, i);
        }

        System.gc();
        return decryptMessage;
    }

    public BitSet rightCycleBitShift(BitSet set, int step, int itemLength) {
        BitSet newSet = set.get(0, itemLength);
        int k = 0;
        for (int i = step; i < itemLength; i++) {
            newSet.set(i, set.get(i - step));
        }

        for (int i = step; i > 0; i--) {
            newSet.set(k, set.get(itemLength - i));
            k++;
        }
        return newSet;
    }

    public BitSet leftCycleBitShift(BitSet set, int step, int itemLength) {
        BitSet newSet = set.get(0, itemLength);
        int k = 0;
        for (int i = 0; i < itemLength - step; i++) {
            newSet.set(i, set.get(i + step));
        }

        for (int i = itemLength - step; i < itemLength; i++) {
            newSet.set(i, set.get(k));
            k++;
        }
        return newSet;
    }

    public BitSet function(BitSet set) {
        BitSet setAfterBitShift = rightCycleBitShift(set.get(0, 8), 3, 8);
        BitSet setAfterSelection = set.get(8, 16);
        setAfterSelection.flip(0, 8);

        BitSet result = new BitSet();
        for (int i = 0; i < 8; i++) {
            result.set(i, setAfterBitShift.get(i));
            result.set(i + 8, setAfterSelection.get(i));
        }

        return result;
    }

    public BitSet createKey(int step) {
        BitSet keyAfterBitShift = leftCycleBitShift(this.key, step, 64);

        BitSet keyWithOddBits = new BitSet();
        keyWithOddBits.set(0, keyAfterBitShift.get(0));
        for (int i = 0; i < 32; i++) {
            keyWithOddBits.set(i, keyAfterBitShift.get(2 * i));
        }
        return keyWithOddBits.get(0, 16);
    }

    public BitSet decryptionStep(BitSet message, int step) {
        BitSet b0 = message.get(0, 16);
        BitSet b1 = message.get(16, 32);
        BitSet b2 = message.get(32, 48);
        BitSet b3 = message.get(48, 64);

        BitSet a0 = b2.get(0, 16);

        BitSet a1 = createKey(step);
        a1.xor(b3.get(0, 16));

        BitSet a2 = b0.get(0, 16);
        a2.xor(b2.get(0, 16));

        BitSet a3 = b1.get(0, 16);
        BitSet functionInput = b3.get(0, 16);
        a3.xor(function(functionInput));


        BitSet result = new BitSet();
        for (int i = 0; i < 16; i++) {
            result.set(i, a0.get(i));
            result.set(i + 16, a1.get(i));
            result.set(i + 32, a2.get(i));
            result.set(i + 48, a3.get(i));
        }
        return result;
    }


    public BitSet encryptStep(BitSet message, int step) {
        BitSet a0 = message.get(0, 16);
        BitSet a1 = message.get(16, 32);
        BitSet a2 = message.get(32, 48);
        BitSet a3 = message.get(48, 64);

        BitSet b0 = a0.get(0, 16);
        b0.xor(a2);

        BitSet b1 = a3.get(0, 16);
        BitSet functionInput = a1.get(0, 16);
        functionInput.xor(createKey(step));
        b1.xor(function(functionInput));

        BitSet b2 = a0.get(0, 16);

        BitSet b3 = a1.get(0, 16);
        b3.xor(createKey(step));

        BitSet result = new BitSet();
        for (int i = 0; i < 16; i++) {
            result.set(i, b0.get(i));
            result.set(i + 16, b1.get(i));
            result.set(i + 32, b2.get(i));
            result.set(i + 48, b3.get(i));
        }
        return result;
    }

    public static StringBuilder toBinaryView(BitSet set, int setLength) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < setLength; i++) {
            result.append(set.get(i) ? "1" : "0");
        }
        return result;
    }
}
