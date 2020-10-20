package com.company;

import com.company.hashFunction.HashFunction;
import com.company.hashFunction.feistel.Feistel;
import com.company.hashFunction.sensor.Sensor;
import com.company.hashFunction.sensor.Tests;

import java.util.BitSet;

public class Main {
    final static int MOST_BIGGEST_INT = 1000000000;

    public static void main(String[] args) {
        BitSet key = new BitSet();
        BitSet IV = new BitSet();


        IV.set(0, 64, true);
        IV.set(1, 5, false);
        IV.set(10, 12, false);

        key.set(0, 16, true);
        key.set(1, 5, false);
        key.set(10, 12, false);


        HashFunction hashFunction = new HashFunction(IV, key, 9);

       double[] arr = getHashFunctionArr(hashFunction);

       int[] arr1 = Tests.getFrequencyDistribution(arr);

        System.out.println();
        System.out.println("Математическое ожидание: "+ Tests.getExpectedValue(arr));
        System.out.println("Дисперсия: "+ Tests.getVariance(arr));
        System.out.print("Частотное распределение: ");
          for (int v : arr1) {
            System.out.print(v+ " ");
        }
        System.out.println();
        System.out.println("Хи-распределение: "+ Tests.khiDistribution(arr));

    }

    public static double[] getHashFunctionArr(HashFunction hashFunction) {
        double[] result = new double[100];
        Sensor sensor = new Sensor(0.011, 0);
        for (int i = 0; i < 100; i++) {
            double n = hashFunctionToDouble(hashFunction, getMessageBlocks(3, sensor));
            result[i] =  n < 1 ? n : n/10;
        }
        return result;
    }

    public static double hashFunctionToDouble(HashFunction hashFunction, BitSet[] text) {
        BitSet bitSet = hashFunction.getHashFunction(text);
        double number = Integer.parseInt(Feistel.toBinaryView(bitSet.get(17, 50), 30).toString(), 2);
        return number / MOST_BIGGEST_INT;
    }


    public static BitSet[] getMessageBlocks(int numberOfBlocks, Sensor sensor) {
        BitSet[] text = new BitSet[numberOfBlocks];


        for (int i = 0; i < numberOfBlocks; i++) {
            BitSet bitSet = new BitSet();
            for (int j = 0; j < 64; j++) {
                bitSet.set(j, 64 * sensor.getNumber() < 32);
            }
            text[i] = bitSet;
        }

        return text;
    }
}
