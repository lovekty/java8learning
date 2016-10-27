package me.tony.java8.other;

import me.tony.java8.Base;
import org.junit.Test;

import java.time.*;

/**
 * 新的时间api使用
 * Created by tony on 2016/10/27.
 */
public class JSR310Examples extends Base {

    @Test
    public void localDateExample() {
        LocalDate now = LocalDate.now();
        logger.info("it is:{}", now);

        LocalDate nowInLA = LocalDate.now(ZoneId.of("America/Los_Angeles"));
        logger.info("it is:{} in LA", nowInLA);

        LocalDate myBirthday = LocalDate.of(1989, 6, 18);
        logger.info("I was born on:{}", myBirthday);
    }

    @Test
    public void localTimeExample() {
        LocalTime now = LocalTime.now();
        logger.info("it is:{} now", now);

        LocalTime nowInLA = LocalTime.now(ZoneId.of("America/Los_Angeles"));
        logger.info("it is:{} now in LA", nowInLA);
    }

    @Test
    public void localDateTimeExample() {
        LocalDateTime now = LocalDateTime.now();
        logger.info("it is:{} now", now);
        LocalDateTime nowInLA = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
        logger.info("it is:{} now in LA", nowInLA);

        LocalDate dateOfDoomsday = LocalDate.of(2012, 12, 21);
        LocalTime timeOfDoomsday = LocalTime.of(12, 0, 0);
        LocalDateTime doomsday = LocalDateTime.of(dateOfDoomsday, timeOfDoomsday);
        logger.info("{} is the doomsday date in Mayan calendar", doomsday);

    }

    @Test
    public void instantExample() {
        Instant now = Instant.now();
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("it is:{} in instant style now {}", now.toEpochMilli(), currentTimeMillis);
    }

}
