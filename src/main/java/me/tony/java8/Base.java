package me.tony.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.function.IntFunction;
import java.util.function.Supplier;


/**
 * Created by tony on 2016/10/24.
 */
public class Base {
    protected static transient final Logger LOGGER = LoggerFactory.getLogger("Java8Practice");

    protected static void showInteger(IntFunction<Integer> function, int value) {
        LOGGER.info(String.valueOf(function.apply(value)));
    }

    protected static String task(Callable<String> callable) throws Exception {
        return callable.call();
    }

    protected static String task(Supplier<String> supplier) throws Exception {
        return supplier.get();
    }

    protected static boolean isOddInteger(int i) {
        return i % 2 == 1;
    }

    protected static boolean isEvenInteger(int i) {
        return i % 2 == 0;
    }
}
