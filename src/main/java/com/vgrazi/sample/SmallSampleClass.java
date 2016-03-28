package com.vgrazi.sample;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by vgrazi on 8/12/15.
 */
public class SmallSampleClass {
    public static void main(String[] args) throws NoSuchMethodException {
        new SmallSampleClass().launch();
    }

    private static void launch() {
        System.out.println("Hello, World");
    }
}
