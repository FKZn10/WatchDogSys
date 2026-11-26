package com.jpmsoft.common.utils;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProMachineCodeUtil {


    // 系统信息工具（oshi核心）
    private static final SystemInfo SYSTEM_INFO = new SystemInfo();
    private static final HardwareAbstractionLayer HAL = SYSTEM_INFO.getHardware();
    private static final String salt = "jpm8888";


    /**
     * 生产级唯一机器码：MAC地址+CPU序列号+主板序列号 → SHA256加密（64位）
     */
    public static String getProUniqueMachineCode() throws Exception {
        // 1. 获取核心硬件标识
//        String macStr = getValidMacAddress(); // 有效MAC地址（方案一的方法）
        String cpuSerial = getCpuSerialNumber(); // CPU序列号
        String boardSerial = getMotherboardSerialNumber(); // 主板序列号
        System.out.println("CPU序列号：" + cpuSerial);
        System.out.println("主板序列号：" + cpuSerial);
        // 2. 拼接所有标识（分隔符避免不同标识拼接后冲突）
        StringBuilder sb = new StringBuilder();
        sb.append(salt).append("|")
                .append(cpuSerial).append("|")
                .append(boardSerial);

        if (sb.toString().contains("null")) {
            throw new RuntimeException("未授权设备信息！");
        }

        // 3. SHA256加密（比MD5更安全，64位固定长度）
        return sha256Encrypt(sb.toString());
    }
//
//    /**
//     * 复用方案一：获取有效MAC地址（过滤虚拟/回环/未启用网卡）
//     */
//    private static String getValidMacAddress() {
//        StringBuilder macSb = new StringBuilder();
//        try {
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            while (networkInterfaces.hasMoreElements()) {
//                NetworkInterface ni = networkInterfaces.nextElement();
//                if (ni.isVirtual() || ni.isLoopback() || !ni.isUp()) {
//                    continue;
//                }
//                byte[] macBytes = ni.getHardwareAddress();
//                if (macBytes == null || macBytes.length == 0) {
//                    continue;
//                }
//                for (byte b : macBytes) {
//                    macSb.append(String.format("%02X", b & 0xFF));
//                }
//                // 取第一个有效物理网卡的MAC即可（多网卡取主网卡，保证唯一性）
//                break;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("获取MAC地址失败", e);
//        }
//        return macSb.length() == 0 ? "unknown_mac" : macSb.toString();
//    }

    /**
     * 获取CPU序列号（跨平台，硬件固化，无法修改）
     */
    private static String getCpuSerialNumber() {
        CentralProcessor cpu = HAL.getProcessor();
        // 获取CPU序列号（oshi封装，跨Windows/Linux/macOS）
        String cpuSerial = cpu.getProcessorIdentifier().getProcessorID();
        return Util.isBlank(cpuSerial) ? "unknown_cpu" : cpuSerial.trim();
    }

    /**
     * 获取主板序列号（跨平台，硬件固化，无法修改）
     */
    private static String getMotherboardSerialNumber() {
        Baseboard baseboard = HAL.getComputerSystem().getBaseboard();
        String boardSerial = baseboard.getSerialNumber();
        return Util.isBlank(boardSerial) ? "unknown_board" : boardSerial.trim();
    }

    /**
     * SHA256加密工具（64位十六进制字符串，比MD5更安全）
     */
    public static String sha256Encrypt(String content) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(content.getBytes());
        StringBuilder sha256Sb = new StringBuilder();
        for (byte b : digest) {
            sha256Sb.append(String.format("%02x", b & 0xFF));
        }
        return sha256Sb.toString();
    }
}
