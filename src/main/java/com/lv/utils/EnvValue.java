package com.lv.utils;

/**
 * @projectName: wangzai
 * @package: com.lv.utils
 * @className: EnvValue
 * @author: dus
 * @description:
 * @date: 2025/1/9 17:22
 * @version: 1.0
 */
public class EnvValue {

    private static final String ENV_KEY_CMOS_CENTER_ID = "cmos.center.code";

    public EnvValue() {
    }

    public static String getEnvCenterId() {
        String envCenterId = null;
        envCenterId = System.getProperty("cmos.center.code");

        try {
            if (envCenterId == null) {
                return envCenterId;
            } else {
                Long centerId = Long.parseLong(envCenterId);
                envCenterId = String.format("%02d", centerId);
                if (centerId >= 0L && centerId <= 99L) {
                    return envCenterId;
                } else {
                    throw new RuntimeException("从环境变量获取中心编码数据超出取值范围：【中心编码：" + centerId + "】");
                }
            }
        } catch (NumberFormatException var2) {
            throw new RuntimeException("从环境变量获取中心编码或实例编码格式错误，【中心编码：" + envCenterId + "】");
        }
    }

}
