package com.jpmsoft.jpbpm.config;

public class RequestDataHelper {
    /**
     * 请求参数存取
     */
    private static final ThreadLocal<String> REQUEST_DATA = new ThreadLocal<>();

    /**
     * 设置请求参数
     *
     * @param code 分表编码
     */
    public static void setRequestData(String code) {
        REQUEST_DATA.set(code);
    }


    /**
     * 获取请求参数
     *
     * @return 请求参数 MAP 对象
     */
    public static String getRequestData() {
        return REQUEST_DATA.get();
    }

    /**
     * clear
     */
    public static void clear() {
        REQUEST_DATA.remove();
    }
}
