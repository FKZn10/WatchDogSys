package com.jpmsoft.common.utils;

import com.jpmsoft.common.model.JpmLicenseDTO;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class JpmCheckUtil {

    public static void check(String xmlFilePath) {
        try {
            File xmlFile = new File(xmlFilePath);
            // 2. 调用工具类转换为实体类
            JpmLicenseDTO license = Xml2ObjectUtil.xmlFile2Object(xmlFile, JpmLicenseDTO.class);
            System.out.println("License参数：" + license);
            if (license == null) {
                exit("License无效！");
            }
            if (ValidateUtil.isAnObjEmpty(license.getCode(), license.getCompany(), license.getExpiredDate())) {
                exit("License无效,参数缺失！");
            }
            if (!license.getExpiredDate().equals("0")) {
                Date expiredDate = DateUtils.parseDate(license.getExpiredDate(), "yyyy/MM/dd");
                if (expiredDate == null) exit("License无效,无效时间！");
                if (calculateDayDiffIgnoreTime(new Date(), expiredDate) < 0L) exit("License无效,已过期！");
            }
            StringBuilder sb = new StringBuilder();
            sb.append(license.getExpiredDate()).append("|").append(license.getExpiredDate()).append("|").append(ProMachineCodeUtil.getProUniqueMachineCode());
            if (!license.getCode().equals(ProMachineCodeUtil.sha256Encrypt(sb.toString()))) exit("License无效,无效码！");
            System.out.println("License验证通过！");
            System.out.println("所属单位：" + license.getCompany());
            System.out.println("有效期限:" + license.getExpiredDate());
        } catch (Exception e) {
            exit("License无效,校验异常！");
        }
    }

    public static String getCode(JpmLicenseDTO license) throws NoSuchAlgorithmException {
        return ProMachineCodeUtil.sha256Encrypt(license.getExpiredDate() + "|" + license.getExpiredDate() + "|" + license.getCode());
    }

    private static void exit(String msg) {
        System.out.println(msg);
        System.exit(1);
    }

    /**
     * 核心方法：计算两个Date天数差（忽略时分秒、支持负数）
     *
     * @param date1 第一个日期（被减数）
     * @param date2 第二个日期（减数）
     * @return 天数差：date1 - date2；负数=date1早于date2，正数=date1晚于date2，0=同一天
     * @throws NullPointerException 当date1/date2为null时抛出
     */
    public static long calculateDayDiffIgnoreTime(Date date1, Date date2) {
        // 1. 指定时区（推荐显式指定，避免不同环境时区不一致导致计算错误）
        // 东八区：Asia/Shanghai，其他时区可参考ZoneId.getAvailableZoneIds()
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");

        // 2. Date转换为LocalDate（自动忽略时分秒，仅保留年月日）
        // 转换逻辑：Date → Instant（时间戳）→ ZonedDateTime（带时区）→ LocalDate（纯日期）
        LocalDate localDate1 = date1.toInstant().atZone(zoneId).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(zoneId).toLocalDate();

        // 3. 计算天数差（不做绝对值，保留正负）
        // between(a,b) 底层逻辑：a - b；与参数顺序严格一致
        return ChronoUnit.DAYS.between(localDate1, localDate2);
    }
}
