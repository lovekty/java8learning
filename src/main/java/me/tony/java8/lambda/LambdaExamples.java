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
        LOGGER.info(String.valueOf(lambda.get(a, b, c)));
    }

    /**
     * 第一个lambda表达式，Runnable接口实现
     */
    @Test
    public void lambdaRunnable() {
        Thread t = new Thread(() -> LOGGER.info("hello lambda"));
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LOGGER.info("hello lambda");
//            }
//        });
        t.start();
    }

    /**
     * lambda和匿名内部类的区别
     * getClass()不一样，二进制文件也不一样，并不是语法糖
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
        LOGGER.info(runnable.getClass().getName());

    }

    /**
     * lambda和匿名内部类的区别
     * lambda表达式的作用域 与外部环境一样
     * 内部类则创建了一个新的作用域
     */
    @Test
    public void testScope() {
        ScopeTest st = new ScopeTest();
        st.printThis.accept(LOGGER);
        st.printToString.accept(LOGGER);
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
//        Supplier<List> listSupplier = ArrayList::new;
//        Function<Integer, String[]> stringArrayFunction = String[]::new;

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
        LOGGER.info(task((Callable<String>) () -> "Callable:hello lambda"));
        LOGGER.info(task((Supplier<String>) () -> "Supplier:hello lambda"));
//        LOGGER.info(task(()->"hello lambda"));
    }

}


class ScopeTest {
    Consumer<Logger> printThis = logger -> logger.info("{}", this);
    Consumer<Logger> printToString = logger -> logger.info("{}", toString());
//    Consumer<Logger> printThis = new Consumer<Logger>() {
//        @Override
//        public void accept(Logger LOGGER) {
//            LOGGER.info("{}", this);
//        }
//    };
//    Consumer<Logger> printToString = new Consumer<Logger>() {
//        @Override
//        public void accept(Logger LOGGER) {
//            LOGGER.info("{}", toString());
//        }
//    };

    @Override
    public String toString() {
        return "hello world";
    }
}