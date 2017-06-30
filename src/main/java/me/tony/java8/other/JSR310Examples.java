package me.tony.java8.other;

import me.tony.java8.Base;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 新的时间api使用
 */
public class JSR310Examples extends Base {

    /**
     * {@link java.time.LocalDate}的使用
     */
    @Test
    public void localDateExample() {
        LocalDate now = LocalDate.now();
        LOGGER.info("it is:{}", now);

        LocalDate nowInLA = LocalDate.now(ZoneId.of("America/Los_Angeles"));
        LOGGER.info("it is:{} in LA", nowInLA);

        LocalDate myBirthday = LocalDate.of(1989, 6, 18);
        LOGGER.info("I was born on:{}", myBirthday);

        LocalDate whenIWasTen = myBirthday.plus(10, ChronoUnit.YEARS);
        LOGGER.info("{} is my tenth birthday", whenIWasTen);
    }

    /**
     * {@link java.time.LocalTime}的使用
     */
    @Test
    public void localTimeExample() {
        LocalTime now = LocalTime.now();
        LOGGER.info("it is:{} now", now);

        LocalTime nowInLA = LocalTime.now(ZoneId.of("America/Los_Angeles"));
        LOGGER.info("it is:{} now in LA", nowInLA);

        LocalTime timeForLunch = LocalTime.of(12, 30);
        LOGGER.info("we have lunch at {}", timeForLunch);

        LocalTime timeForSiesta = timeForLunch.plus(30, ChronoUnit.MINUTES);
        LOGGER.info("we have a nap at {}", timeForSiesta);
    }

    /**
     * {@link java.time.LocalDateTime}的使用
     */
    @Test
    public void localDateTimeExample() {
        LocalDateTime now = LocalDateTime.now();
        LOGGER.info("it is:{} now", now);
        LocalDateTime nowInLA = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
        LOGGER.info("it is:{} now in LA", nowInLA);

        LocalDate dateOfDoomsday = LocalDate.of(2012, 12, 21);
        LocalTime timeOfDoomsday = LocalTime.of(12, 0, 0);
        LocalDateTime doomsday = LocalDateTime.of(dateOfDoomsday, timeOfDoomsday);
        LOGGER.info("{} is the doomsday date in Mayan calendar", doomsday);

    }

    /**
     * {@link java.time.Instant}的使用
     */
    @Test
    public void instantExample() {
        Instant now = Instant.now();
        LOGGER.info("it is:{} in instant style now", now.toEpochMilli());
    }

    /**
     * 时间间隔
     */
    @Test
    public void zoneExample() {
        LocalDate iphone7 = LocalDate.of(2016, 9, 8);
        LocalDate mbp = LocalDate.of(2016, 10, 28);
        Period period = Period.between(iphone7, mbp);
        LOGGER.info("days between iphone7 and new macbook pro is {} months and {} days", period.getMonths(), period.getDays());

    }

    /**
     * 时间格式化
     */
    @Test
    public void formatToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        LOGGER.info(now.format(formatter));
    }

    /**
     * 将符合pattern的String转换为时间
     */
    @Test
    public void parseFromString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2016-10-28 16:00:00", formatter);
        LOGGER.info("{}", localDateTime);
    }

    /**
     * 将{@link java.time.LocalDateTime}转换成{@link java.util.Date}
     */
    @Test
    public void localDateTimeToDate() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date date = Date.from(instant);
        LOGGER.info("now in java.util.Date is:{}", date);
    }

    /**
     * 将{@link java.util.Date}转换成{@link java.time.LocalDateTime}
     */
    @Test
    public void dateToLocalDateTime() {
        Date now = new Date();
        Instant instant = now.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        LOGGER.info("now in java.time.LocalDateTime is:{}", localDateTime);
    }

}
