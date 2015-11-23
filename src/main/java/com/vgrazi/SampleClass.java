package com.vgrazi;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by vgrazi on 8/12/15.
 */
public class SampleClass implements Serializable, Cloneable, SampleInterface {
    public static int myZip=11223;

    protected SampleInterface mySampleInterface = new SampleClass();
    public float myWeight = 175f;
    public float myHeight = -6.5f;
    public Method myMethod;
    public SampleClass() throws NoSuchMethodException {

        myMethod = SampleClass.class.getMethod("fibonacci", int.class);
    }
    public static void main(String[] args) throws NoSuchMethodException {
        new SampleClass().launch();
    }

    private static void launch() {
        System.out.println("Hello, World");
    }

    @Override
    public double fibonacci(int n) {
        if (n <= 1) return
            n;
        else return
            fibonacci(n - 1) + fibonacci(n - 2);
    }

    public void theEnd() {
        int a = 1;
        System.out.println("Myage:" + myZip);
        System.out.println(
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" +
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" +
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" +
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789"
        );
        System.out.println("The end");
    }
}
