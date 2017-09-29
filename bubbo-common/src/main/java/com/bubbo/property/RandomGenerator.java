package com.bubbo.property;

import java.util.Random;

/**
 * Created by DELL on 2017/9/28.
 */
public class RandomGenerator {

    public static Integer randInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("maximum value must be greater than min");
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
