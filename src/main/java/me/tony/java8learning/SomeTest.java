package me.tony.java8learning;

import org.junit.Test;

/**
 * Created by tony on 2016/10/21.
 */
public class SomeTest {

    @Test
    public void test() {
        Class c = Integer.TYPE;
        Class<Integer> ci = Integer.class;
        System.out.println(c.getName());
    }
}
