package me.tony.java8.lambda;

import me.tony.java8.Base;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * lambda表达式和functional接口
 * Created by tony on 2016/10/24.
 */
public class LambdaExamples extends Base {

    static void invokeMyLambda(MyLambda lambda, int a, int b, int c) {
        logger.info(String.valueOf(lambda.get(a, b, c)));
    }

    /**
     * 第一个lambda表达式，Runnable接口实现
     */
    @Test
    public void lambdaRunnable() {
        Thread t = new Thread(() -> logger.info("hello lambda"));
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("hello lambda")
//            }
//        });
        t.start();
    }

    /**
     * lambda和匿名内部类的区别
     */
    @Test
    public void typeOfLambda() {
        Runnable runnable = () -> {
        };
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
        logger.info(runnable.getClass().getName());

    }

    /**
     * lambda表达式的作用域
     */
    @Test
    public void testScope() {
        ScopeTest st = new ScopeTest();
        st.c1.accept(logger);
        st.c2.accept(logger);
        st.c3.accept(logger);
        st.c4.accept(logger);
    }

    /**
     * 变换花样使用IntFunction&lt;Integer&gt;
     * 方法引用
     */
    @Test
    public void lambdaIntFunction() {
        showInteger((int integer) -> {
            return Math.abs(integer);
        }, -1);
        showInteger(integer -> Math.abs(integer), -1);
        showInteger(Math::abs, -1);
        showInteger(integer -> integer * 2, 128);
        showInteger(haha -> haha + 100, 99);
    }

    /**
     * 自定义一个Functional接口并且以lambda的形式调用
     */
    @Test
    public void testMyLambda() {
        invokeMyLambda((a, b, c) -> a + b + c, 1, 10, 100);
    }


    /**
     * 坑：方法重载
     *
     * @throws Exception
     */
    @Test
    public void overloadingProblem() throws Exception {
        logger.info(task((Callable<String>) () -> "Callable:hello lambda"));
        logger.info(task((Supplier<String>) () -> "Supplier:hello lambda"));
//        logger.info(task(()->"hello lambda"));
    }

}


class ScopeTest {
    Consumer<Logger> c1 = logger -> logger.info("{}", this);
    Consumer<Logger> c2 = new Consumer<Logger>() {
        @Override
        public void accept(Logger logger) {
            logger.info("{}", this);
        }

        @Override
        public String toString() {
            return "hello world in consumer";
        }
    };
    Consumer<Logger> c3 = logger -> logger.info(toString());
    Consumer<Logger> c4 = new Consumer<Logger>() {
        @Override
        public void accept(Logger logger) {
            logger.info(toString());
        }

        @Override
        public String toString() {
            return "hello world in consumer";
        }
    };

    @Override
    public String toString() {
        return "hello world";
    }
}