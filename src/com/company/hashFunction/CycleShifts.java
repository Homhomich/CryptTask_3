package com.company.hashFunction;

import java.util.BitSet;

public class CycleShifts {

    public static BitSet rightCycleBitShift(BitSet set, int step, int itemLength) {
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

    public static BitSet leftCycleBitShift(BitSet set, int step, int itemLength) {
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
}
