package com.lv.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @projectName: wangzai
 * @package: com.lv.utils
 * @className: SequenceUtils
 * @author: dus
 * @description:
 * @date: 2025/1/9 17:14
 * @version: 1.0
 */
@Component
public class SequenceUtils {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        SequenceUtils.redisTemplate = redisTemplate;
    }


    public SequenceUtils() {
    }

    public static String getSequence(String key, int length) throws Exception {
        long timestamp = System.currentTimeMillis();
        String id = null;
        String incrSequence = getSequenceOnlyIncr(key, length);
        String datePattern = "yyyyMMddHHmmssSSS";
        if (StringUtils.isNotEmpty(EnvValue.getEnvCenterId())) {
            id = DateFormatUtils.format(timestamp, datePattern) + EnvValue.getEnvCenterId() + incrSequence;
        } else {
            id = DateFormatUtils.format(timestamp, datePattern) + incrSequence;
        }

        return id;
    }

    public static Long getLogicSeq(String key) throws Exception {
        long timestamp = System.currentTimeMillis();
        String datePattern = "yyyyMMddHHmmssSSS";
        String id = null;
        Long seqId = null;
        String incrSequence = getSequenceOnlyIncr(key, 5);
        if (StringUtils.isNotEmpty(EnvValue.getEnvCenterId())) {
            id = DateFormatUtils.format(timestamp, datePattern).substring(2, datePattern.length() - 3) + EnvValue.getEnvCenterId() + incrSequence;
        } else {
            id = DateFormatUtils.format(timestamp, datePattern).substring(2, datePattern.length() - 3) + incrSequence;
        }

        seqId = Long.parseLong(id);
        return seqId;
    }

    private static String getSequenceOnlyIncr(String key, int length) throws Exception {
        int baseLength = 4;
        StringBuffer baseNums = new StringBuffer();
        String uniqueNums = null;
        if (length <= baseLength) {
            throw new RuntimeException("序列长度不满足唯一标识生成需求！");
        } else if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("redis键值不能为空！");
        } else {
            String id = null;

            for(int i = 0; i < length; ++i) {
                baseNums.insert(0, "9");
            }

            try {
                ValueOperations valueOperations = redisTemplate.opsForValue();
                Long redisValue = valueOperations.increment(key);
                if (redisValue - Long.valueOf(baseNums.toString()) > 0L) {
                    String transValue = splits(redisValue.toString(), length);
                    id = transValue;
                } else {
                    uniqueNums = addPosition(redisValue.toString(), length);
                    id = uniqueNums;
                }
            } catch (Exception var8) {
                id = getRandomStringByLength(length);
            }

            return id;
        }
    }


    private static String getRandomStringByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    private static String splits(String temp, int length) {
        String value = temp.substring(temp.length() - length);
        return value;
    }

    private static String addPosition(String temp, int length) {
        StringBuffer value = new StringBuffer();

        for(int i = 0; i < length - temp.length(); ++i) {
            value.insert(0, "0");
        }

        value.append(temp.toString());
        return value.toString();
    }


    public static void main(String[] args) {

        try {
            System.out.println(getSequence("test", 5));
            System.out.println(getLogicSeq("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
