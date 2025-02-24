package com.lv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @projectName: wangzai
 * @package: com.lv
 * @className: PreviousDayExample
 * @author: dus
 * @description:
 * @date: 2025/1/14 11:02
 * @version: 1.0
 */
public class PreviousDayExample {


    public static void main(String[] args) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 获取当前日期的前一天，并设置时间为当天开始时间00:00
        LocalDateTime previousDayStart = now.minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        // 定义输出格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化输出
        String formattedPreviousDay = previousDayStart.format(formatter);

        System.out.println(formattedPreviousDay); // 输出类似：2025-01-13 00:00:00

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(calendar.getTime());
    }
}
