package me.tony.java8learning;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by tony on 2016/10/18.
 */
public class LambdaAndStream {

    private static final Logger logger = LoggerFactory.getLogger(LambdaAndStream.class);

    static List<Integer> integers = new ArrayList<>(1000000);

    static {
        for (int i = 0; i < 1000000; i++) {
            integers.add((int) (Math.random() * 1000));
        }
    }

    @Test
    public void firstLambda() {
        Thread t = new Thread(() -> System.out.println("hello lambda"));
        t.start();
    }

    @Test
    public void lambdaInSwing() {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Test
    public void testStream() {
        List<Integer> list = IntStream.range(1, 1000000).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        long start = System.currentTimeMillis();
        Optional<Integer> max = list.stream().max((Integer x, Integer y) -> x - y);
        long end = System.currentTimeMillis();
        logger.info("max is:{} and cost time:{}", max.isPresent() ? max.get() : "null", end - start);
    }

    @Test
    public void testParalStream() {
        List<Integer> list = IntStream.range(1, 1000000).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        long start = System.currentTimeMillis();
        Optional<Integer> max = list.parallelStream().max((x, y) -> x - y);
        long end = System.currentTimeMillis();
        logger.info("max is:{} and cost time:{}", max.isPresent() ? max.get() : "null", end - start);
    }
}
