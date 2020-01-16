package com.bowie.notes.features;

import org.junit.Test;

import java.time.*;
import java.util.Date;

/**
 * Created by Bowie on 2020/1/16 14:03
 **/
public class DateUtils {

    @Test
    public void testClock() {
        final Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());
    }

    @Test
    public void testLocal() {
        final Clock clock = Clock.systemUTC();
        LocalDate date = LocalDate.now();
        LocalDate dateFromClock = LocalDate.now(clock);

        System.out.println(date);
        System.out.println(dateFromClock);

        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now(clock);

        System.out.println(time);
        System.out.println(timeFromClock);

        //LocalDateTime类包含了LocalDate和LocalTime的信息，
        // 但是不包含ISO-8601日历系统中的时区信息。
        final LocalDateTime datetime = LocalDateTime.now();
        final LocalDateTime datetimeFromClock = LocalDateTime.now(clock);

        System.out.println(datetime);
        System.out.println(datetimeFromClock);

    }

    @Test
    public void testZoneDateTime() {
        //需要特定时区的data/time信息，则可以使用ZoneDateTime，
        // 它保存有ISO-8601日期系统的日期和时间，而且有时区信息。
        final Clock clock = Clock.systemUTC();
        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now(clock);
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));

        System.out.println(zonedDatetime);
        System.out.println(zonedDatetimeFromClock);
        System.out.println(zonedDatetimeFromZone);

    }

    @Test
    public void testDuration() {
        // Get duration between two dates
        final LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0);
        final LocalDateTime to = LocalDateTime.of(2015, Month.APRIL, 16, 23, 59, 59);

        final Duration duration = Duration.between(from, to);
        System.out.println("Duration in days: " + duration.toDays());
        System.out.println("Duration in hours: " + duration.toHours());
    }

    @Test
    public void testDateToLocalDateTime() {
        //Date转换为LocalDateTime
        //1.
        Date date = new Date();
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTime1 = instant.atZone(zoneId).toLocalDateTime();
        System.out.println("Date = " + date);
        System.out.println("LocalDateTime1 = " + localDateTime1);

        //2.
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(date.toInstant(), zoneId);
        System.out.println("localDateTime2 = " + localDateTime2);

        //将LocalDateTime转换为Date
        ZoneId zoneId1 = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId1);

        Date date1 = Date.from(zdt.toInstant());

        System.out.println("LocalDateTime = " + localDateTime);
        System.out.println("Date = " + date1);




    }
}
