package com.hm.libb.dsource.util;

/**
 * Created by huangming on 2017/6/22.
 */
public class CalUtils {

    public static float EMA(float[] arr, int day) {
        return EMA_INNER(arr, day, 0);
    }

    public static float DIF(float[] arr, int day1, int day2) {
        return DIF_INNER(arr, day1, day2, 0);
    }

    public static float DEA(float[] arr, int day1, int day2, int c) {
        return DEA_INNER(arr, day1, day2, c, 0);
    }

    public static float BAR(float[] arr, int day1, int day2, int c) {
        return BAR_INNER(arr, day1, day2, c);
    }

    private static float EMA_INNER(float arr[], int day, int index) {
        return index == arr.length ? 0 : arr[index] * (2f / (day + 1)) + ((day - 1f) / (day + 1f)) * EMA_INNER(arr, day, index + 1);
    }

    private static float DIF_INNER(float[] arr, int day1, int day2, int index) {
        return index == arr.length - 1 ? 0 : (EMA_INNER(arr, day1, index) - EMA_INNER(arr, day2, index));
    }

    private static float DEA_INNER(float[] arr, int day1, int day2, int c, int index) {
        return index > c ? 0 : DIF_INNER(arr, day1, day2, index) * (2f / (c + 1f)) + (c - 1f) / (c + 1f) * DEA_INNER(arr, day1, day2, c, index + 1);
        // return index == arr.length - 1 ? 0 : DIF_INNER(arr, day1, day2, c, index) * (2f / (c + 1f)) + ((c - 1f) / (c + 1f)) * DEA_INNER(arr, day1, day2, c, index + 1);
    }

    private static float BAR_INNER(float[] arr, int day1, int day2, int c) {
        return (DIF_INNER(arr, day1, day2, 0) - DEA_INNER(arr, day1, day2, c, 0)) * 2f;
    }
}

