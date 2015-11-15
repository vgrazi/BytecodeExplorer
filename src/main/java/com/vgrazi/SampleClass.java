package com.vgrazi;

import java.io.Serializable;

/**
 * Created by vgrazi on 8/12/15.
 */
public class SampleClass implements Serializable, Cloneable, SampleInterface {
    private int myAge=12345;

    protected SampleInterface mySampleInterface = new SampleClass();
    public float myWeight = 175f;
    public float myHeight = 6.5f;
    public SampleClass() {

    }
    public static void main(String[] args) {
        new SampleClass().launch();
    }

    private void launch() {
        System.out.println("Hello, World");
    }

    @Override
    public double fibonacci(int n) {
        if (n <= 1) return
            n;
        else return
            fibonacci(n - 1) + fibonacci(n - 2);
    }

    private void theEnd() {
        int a = 1;
        System.out.println(
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" +
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" +
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789" +
            "-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789-123456789"
        );
        System.out.println("The end");
    }
}
