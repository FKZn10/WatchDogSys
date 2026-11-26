package com.jpmsoft.projectProducer.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/19 14:14
 */
public class MoneyUtil {

    private static final char[] CN_UPPER_NUMBER = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    private static final char[] CN_UPPER_UNIT = {'仟', '佰', '拾'};
    private static final char[] CN_GROUP = {'圆', '万', '亿'};

    /**
     * 金额转大写中文
     * 可转换1分-1万亿以内的金额
     *
     * @param i_money 单位元的金额可带角分位
     * @return
     */
    public static String money2Chinese(BigDecimal i_money) {
        if (i_money.equals(BigDecimal.ZERO)) {
            return "零圆整";
        }
        Double max = 1000000000000D;
        Double min = 0.01D;
        if (i_money.doubleValue() >= max || i_money.doubleValue() < min) {
            return "大于1万亿或小于1分了";
        }
        i_money = i_money.setScale(2, RoundingMode.HALF_UP);
        String numStr = i_money.toString();
        int pointPos = numStr.indexOf('.');
        //整数部分
        String s_int = null;
        //小数部分
        String s_point = null;
        if (pointPos >= 0) {
            s_int = numStr.substring(0, pointPos);
            s_point = numStr.substring(pointPos + 1);
        } else {
            s_int = numStr;
        }
        StringBuilder sb = new StringBuilder();
        if (!"0".equals(s_int)) {
            int groupCount = (int) Math.ceil(s_int.length() / 4.0);
            for (int group = 0; group < groupCount; group++) {
                boolean zeroFlag = true;
                boolean noZeroFlag = false;
                int start = (s_int.length() % 4 == 0 ? 0 : (s_int.length() % 4 - 4)) + 4 * group;
                for (int i = 0; i < 4; i++) {
                    if (i + start >= 0) {
                        int value = s_int.charAt(i + start) - '0';
                        if (value > 0) {
                            sb.append(CN_UPPER_NUMBER[value]);
                            if (i < 3) {
                                sb.append(CN_UPPER_UNIT[i]);
                            }
                            zeroFlag = true;
                            noZeroFlag = true;
                        } else if (zeroFlag) {
                            sb.append('零');
                            zeroFlag = false;
                        }
                    }
                }
                if (sb.charAt(sb.length() - 1) == '零') {
                    sb.deleteCharAt(sb.length() - 1);
                }
                if (noZeroFlag || groupCount - group == 1) {
                    sb.append(CN_GROUP[groupCount - group - 1]);
                }
            }
        }
        if (s_point == null || "00".equals(s_point)) {
            sb.append('整');
        } else {
            int j = s_point.charAt(0) - '0';
            int f = s_point.charAt(1) - '0';
            if (j > 0) {
                sb.append(CN_UPPER_NUMBER[j]).append('角');
                if (f != 0) {
                    sb.append(CN_UPPER_NUMBER[f]).append('分');
                }
            } else if ("0".equals(s_int)) {
                sb.append(CN_UPPER_NUMBER[f]).append('分');
            } else {
                sb.append('零').append(CN_UPPER_NUMBER[f]).append('分');
            }
        }
        return sb.toString();
    }


}
