package com.lv.utils;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @projectName: wangzai
 * @package: com.lv.utils
 * @className: JsonDeepCleaner
 * @author: dus
 * @description:
 * @date: 2025/2/24 9:59
 * @version: 1.0
 */
public class JsonDeepCleaner {

    public static void main(String[] args) {
        // 示例数据
        Map<String, Object> data = new HashMap<>();
        Map<String, String> businessAcceptanceExplanation = new HashMap<>();
        businessAcceptanceExplanation.put("受理说明", "");

        Map<String, String> businessCancellation = new HashMap<>();
        businessCancellation.put("取消方式-网上营业厅", "");
        businessCancellation.put("取消方式-H5链接", "H5链接");
        businessCancellation.put("取消方式-业务客户端", "");
        businessCancellation.put("取消方式-手机营业厅客户端", "手机营业厅客户端");

        Map<String, Object> map = new HashMap<>();
        map.put("业务受理-说明", businessAcceptanceExplanation);
        map.put("业务受理-取消", businessCancellation);
        map.put("业务受理-呼入营销", new HashMap().put("呼入营销", ""));
        map.put("知识要点", new HashMap().put("要点说明", ""));

        System.out.println("原始数据: " + map);

        // 清理空值
        cleanMap(map);

        System.out.println("清理后的数据: " + map);
    }

    /**
     * 递归清理 Map 中值为空的键值对
     *
     * @param map 输入的 Map
     */
    public static void cleanMap(Map<String, Object> map) {
        if (map == null) return;

        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object value = entry.getValue();

            // 如果值是 Map，递归清理
            if (value instanceof Map) {
                cleanMap((Map<String, Object>) value);
                if (((Map<?, ?>) value).isEmpty()) {
                    iterator.remove(); // 如果子 Map 为空，则移除该键值对
                }
            }
            // 如果值是字符串且为空，移除该键值对
            else if (value instanceof String && ((String) value).trim().isEmpty()) {
                iterator.remove();
            }
            // 如果值为 null，移除该键值对
            else if (value == null) {
                iterator.remove();
            }
        }
    }
}
