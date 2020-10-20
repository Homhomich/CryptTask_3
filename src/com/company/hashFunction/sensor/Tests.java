package com.company.hashFunction.sensor;

import java.util.Arrays;

public class Tests {
    private static final int amountOfParts = 20;


    /*Мат ожидание в квадрате*/
    private static double getExpectedValueInSquare(double[] arr) {
        var expectedValue = 0.0;
        for (double v : arr) {
            expectedValue += Math.pow(v, 2);
        }
        expectedValue /= arr.length;
        return expectedValue;
    }

    /*Мат ожидание*/
    public static double getExpectedValue(double[] arr) {
        var expectedValue = 0.0;
        for (double v : arr) {
            expectedValue += v;
        }
        expectedValue = expectedValue / arr.length;
        return expectedValue;
    }

    /*Дисперсия*/

    public static double getVariance(double[] arr) {
        return getExpectedValueInSquare(arr)
                - Math.pow(getExpectedValue(arr), 2);
    }

    /*Частотное распредление*/
    public static int[] getFrequencyDistribution(double[] arr) {
        var freqDist = new int[amountOfParts];
        Arrays.fill(freqDist, 0);
        for (double v : arr) {
            var index = (int) (v * freqDist.length);
            freqDist[index] += 1;
        }
        return freqDist;
    }

    /*Хи распредление*/
    public static double khiDistribution(double[] arr) {
        var freqDist = getFrequencyDistribution(arr);
        var khiDistribution = 0.0;

        var theoreticalAmount = (double) arr.length / freqDist.length;

        for (int amount : freqDist) {
            khiDistribution += Math.pow((amount - theoreticalAmount), 2) / theoreticalAmount;
        }
        return khiDistribution;
    }


}
