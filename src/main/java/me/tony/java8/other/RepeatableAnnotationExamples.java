package me.tony.java8.other;

import me.tony.java8.Base;
import org.junit.Test;

import java.lang.annotation.*;
import java.util.Arrays;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Props.class)
@interface Prop {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Props {
    Prop[] value();
}

@Prop("hello")
@Prop("world")
class UseRepeatableAnnotation {

}

@Props({@Prop("hello"), @Prop("world")})
class UseContainerAnnotation {

}

/**
 * 重复注解
 */
public class RepeatableAnnotationExamples extends Base {

    /**
     * 使用repeatable注解
     */
    @Test
    public void testRepeatableAnnotation() {
        Prop[] propArray = UseRepeatableAnnotation.class.getAnnotationsByType(Prop.class);
        Arrays.stream(propArray).forEach(prop -> LOGGER.info("prop value is:{}", prop.value()));

        Props props = UseRepeatableAnnotation.class.getAnnotation(Props.class);
        Arrays.stream(props.value()).forEach(prop -> LOGGER.info("prop value is:{}", prop.value()));
    }

    /**
     * java1.7以前的做法
     */
    @Test
    public void useContainer() {
        Prop[] propArray = UseContainerAnnotation.class.getAnnotationsByType(Prop.class);
        Arrays.stream(propArray).forEach(prop -> LOGGER.info("prop value is:{}", prop.value()));
        
        Props props = UseContainerAnnotation.class.getAnnotation(Props.class);
        Arrays.stream(props.value()).forEach(prop -> LOGGER.info("prop value is:{}", prop.value()));
    }
}