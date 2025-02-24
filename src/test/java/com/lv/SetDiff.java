package com.lv;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * @projectName: wangzai
 * @package: com.lv
 * @className: Test002
 * @author: dus
 * @description: 比较Set<String>
 * @date: 2024/12/30 16:32
 * @version: 1.0
 */
public class SetDiff {

    /**
     * 判断两个 Set 是否内容一致，并返回第一个集合存在但第二个集合不存在的元素。
     *
     * @param set1 第一个集合
     * @param set2 第二个集合
     * @return 一个包含第一个集合存在但第二个集合不存在的元素的 Set
     */
    public static Set<String> compareSetsAndGetDifference(Set<String> set1, Set<String> set2) {
        // 判断两个 Set 是否内容一致
        boolean areEqual = set1.equals(set2);

        System.out.println("两个 Set 的内容是否一致: " + areEqual);

        if (areEqual) {
            return new HashSet<>(); // 如果相等，返回空集合
        }

        // 获取第一个集合存在但第二个集合不存在的元素
        Set<String> difference = new HashSet<>(set1);
        difference.removeAll(set2);

        return difference;
    }

   /* public static void main(String[] args) {
        // 创建两个 Set
        Set<String> set1 = new HashSet<>(List.of("apple", "banana", "orange", "grape"));
        Set<String> set2 = new HashSet<>(List.of("banana", "orange", "apple"));

        // 调用方法并输出结果
        Set<String> result = compareSetsAndGetDifference(set1, set2);
        System.out.println("第一个集合存在但第二个集合不存在的元素: " + result);
    }
*/

}
