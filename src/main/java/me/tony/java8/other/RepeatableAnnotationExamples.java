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
 * Created by tony on 2016/10/27.
 */
public class RepeatableAnnotationExamples extends Base {

    @Test
    public void testRepeatableAnnotation() {
        Prop[] propArray = UseRepeatableAnnotation.class.getAnnotationsByType(Prop.class);
        Arrays.stream(propArray).forEach(prop -> logger.info("prop value is:{}", prop.value()));

        Props props = UseRepeatableAnnotation.class.getAnnotation(Props.class);
        Arrays.stream(props.value()).forEach(prop -> logger.info("prop value is:{}", prop.value()));
    }

    @Test
    public void useContainer() {
        Prop[] propArray = UseContainerAnnotation.class.getAnnotationsByType(Prop.class);
        Arrays.stream(propArray).forEach(prop -> logger.info("prop value is:{}", prop.value()));
        
        Props props = UseRepeatableAnnotation.class.getAnnotation(Props.class);
        Arrays.stream(props.value()).forEach(prop -> logger.info("prop value is:{}", prop.value()));
    }
}