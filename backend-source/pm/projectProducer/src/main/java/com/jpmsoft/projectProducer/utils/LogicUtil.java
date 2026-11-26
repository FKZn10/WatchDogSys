package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.common.utils.ValidateUtil;

import java.util.List;
import java.util.Map;

/**
 * 逻辑处理工具
 * created by:Aaron
 * created time:2021-10-29
 */
public class LogicUtil {


    public static void putParamToMap(Map<String, Object> params, List<String> times, String startKey, String endKey) {
        if (times != null && times.size() == 2) {
            params.put(startKey, ValidateUtil.isEmpty(times.get(0)) ? null : times.get(0));
            params.put(endKey, ValidateUtil.isEmpty(times.get(1)) ? null : times.get(1));
        }
    }
}
