package com.vgrazi;

/**
 * Created by vgrazi on 8/23/15.
 */
public interface SampleInterface {
    @Deprecated @SuppressWarnings("myannotation")
    String myName = "Henry";

    double fibonacci(int n);
}
