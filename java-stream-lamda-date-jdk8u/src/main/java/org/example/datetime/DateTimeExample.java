package org.example.datetime;



import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeExample {

    public static void main(String[] args) {
        getLocalDateTime();
        getLocalDatePlus();
        createZonedDateTime();

        getPeriodAndDuration();
        parseTimeAndDate();
    }

    private static void getLocalDateTime(){
        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前日期时间: " + now);
    }

    private static void getLocalDatePlus(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);
        System.out.println("下周的同一时刻: " + nextWeek);

        LocalDateTime lastMonth = now.minusMonths(1);
        System.out.println("上个月的同一时刻: " + lastMonth);
    }

    private static void createZonedDateTime(){
        // 获取当前时区的日期时间
        ZonedDateTime zonedNow = ZonedDateTime.now();
        System.out.println("当前时区的日期时间: " + zonedNow);

        // 指定时区的日期时间
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2025, 12, 25, 14, 30, 0, 0, ZoneId.of("America/New_York"));
        System.out.println("指定时区的日期时间: " + zonedDateTime);

        ZonedDateTime utcTime = zonedNow.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println("UTC时间: " + utcTime);

        ZonedDateTime nextYearInParis = zonedDateTime.plusYears(1).withZoneSameInstant(ZoneId.of("Europe/Paris"));
        System.out.println("明年此刻在巴黎: " + nextYearInParis);
    }


    private static void getPeriodAndDuration(){

        LocalTime start = LocalTime.of(14, 0);
        LocalTime end = LocalTime.of(15, 30);

        Duration duration = Duration.between(start, end);
        System.out.println("持续时间: " + duration.toMinutes() + " 分钟");
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        Period period = Period.between(startDate, endDate);
        System.out.println("两个日期之间的差距: " + period.getMonths() + " 个月 " + period.getDays() + " 天");


    }

    private static void parseTimeAndDate(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(formatter);
        System.out.println("格式化后的日期: " + formattedDate);
        String dateStr = "2025-12-25";
        LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
        System.out.println("解析后的日期: " + parsedDate);


    }
}
