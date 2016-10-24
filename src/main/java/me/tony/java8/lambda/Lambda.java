package me.tony.java8.lambda;

import me.tony.java8.Base;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.function.Supplier;


/**
 * lambda表达式和functional接口
 * Created by tony on 2016/10/24.
 */
public class Lambda extends Base {

    static void testMyLambda(MyLambda lambda) {
        logger.info(String.valueOf(lambda.get(1, 10, 100)));
    }

    /**
     * 第一个lambda表达式，Runnable接口实现
     */
    @Test
    public void lambdaRunnable() {
        Thread t = new Thread(() -> logger.info("hello lambda"));
        t.start();
    }

    @Test
    public void lambdaIntFunction() {
        showInteger((integer) -> {
            return Math.abs(integer);
        }, -1);
        showInteger(integer -> Math.abs(integer), -1);
        showInteger(Math::abs, -1);
        showInteger(integer -> integer * 2, 128);
        showInteger(haha -> haha + 100, 99);
    }

    @Test
    public void overloadingProblem() throws Exception {
        logger.info(task((Callable<String>) () -> "Callable:hello lambda"));
        logger.info(task((Supplier<String>) () -> "Supplier:hello lambda"));
//        logger.info(task(()->"hello lambda"));
    }

    @Test
    public void testMyLambda() {
        Lambda.testMyLambda((a, b, c) -> a + b + c);
    }
}
