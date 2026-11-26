package com.jpmsoft.userProducer.utils;


import java.math.BigDecimal;
import java.util.*;

/**
 * IoT工具类 - 钢筋检测仪数据解析
 * 解析案例代码
 */
public class IotUtils {

    private StringBuilder boxing = new StringBuilder();
    private boolean cszt = false;

    /**
     * 小端序转换：将十六进制字符串按字节反转
     *
     * @param hexStr 十六进制字符串
     * @return 反转后的十六进制字符串
     */
    private String littleEndianHex(String hexStr) {
        StringBuilder result = new StringBuilder();
        for (int i = hexStr.length() - 2; i >= 0; i -= 2) {
            result.append(hexStr.substring(i, i + 2));
        }
        return result.toString();
    }

    /**
     * 解析十六进制数据
     */
    public Map<String, Object> parseDataFN(List<Object> args) {
        // 测试数据
        // hexStr = "484341000000594759373158003952440165003131393236303330333130002a0003001500120004001a0010484347593733343400100d0606000000001e0e0c10030000000c000000030000000005000c00ca000e01720010"
        // JGJ = "4843410000005f475937315800a05244016500313139323630333033313000250039000f00130004001a001648434759373435310010070000000000001e3f3e3f0600000012000000020000003e403f0000000000003d3e3e000000000000"
        // BX = "4843410000019747593731580076524401650031313932363033303331300013003b000f00130004001a001348434759373435320010070000000000001e3d3c3ea50000004a0100000200000000020002000200020002000200020002000200020004000400070009000a000d000e00100013001500150018001a001d002000220024002600280029002b002d002e002f003100320033003500360036003800380039003a003a003a003a3e3a003a003a003a0039003800370036003400340032002f002e002b0029002700230020001c00160012000e000a0005000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000200020002000400060009000b000f0013001500170019001d0021002300260027002a002d002e003000320034003600380038003a003b003b003c003d003d003d003d003d3c3d003d003d003d003c003c003b003a003900380037003600330031002e002a0025001f001a001448434100000013475937315800c25244024f4b"
        String hexStr = args.get(0) + "";
        String result = hexStr;
        if (boxing.length() > 0) {
            result = boxing.toString();
        }

        // 跳过前34个字符
        result = result.substring(34);

        // 版本号
        String iVervisons = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 仪器编号
        String yiQiBianHao = result.substring(0, 24);
        result = result.substring(24);

        // 秒
        String sec = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 分
        String min = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 时
        String hour = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 日
        String day = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 月
        String month = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 年
        String year = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 类型
        String type = result.substring(0, 2);
        result = result.substring(20);

        // 直径1（1字节 不换）
        String ucDiam1 = result.substring(0, 2);
        result = result.substring(2);

        // 直径2（1字节 不换）
        String ucDiam2 = result.substring(0, 2);
        result = result.substring(2);

        // 间距1（1字节 不换）
        String ucDistance1 = result.substring(0, 2);
        result = result.substring(2);

        // 间距2（1字节 不换）
        String ucDistance2 = result.substring(0, 2);
        result = result.substring(2);

        // 直径3 8位 → 高低位反转
        String iDiam3 = littleEndianHex(result.substring(0, 8));
        result = result.substring(8);

        // 设计保护层（1字节）
        String ucDesignCover = result.substring(0, 2);
        result = result.substring(2);

        // 平均保护层（1字节）
        String ucCoverAvg = result.substring(0, 2);
        result = result.substring(2);

        // 最小保护层（1字节）
        String ucCoverMin = result.substring(0, 2);
        result = result.substring(2);

        // 最大保护层（1字节）
        String ucCoverMax = result.substring(0, 2);
        result = result.substring(2);

        // 存储点数 4位 → 高低位反转
        String iSaveDotCnt = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 合格点数 4位 → 高低位反转
        String iPassCoverCnt = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 数据长度 8位 → 高低位反转
        String dataLenth = littleEndianHex(result.substring(0, 8));
        result = result.substring(8);

        // 钢筋根数 4位 → 高低位反转
        String iGangJingNumber = littleEndianHex(result.substring(0, 4));
        result = result.substring(4);

        // 构件类型（1字节）
        String ucGJType = result.substring(0, 2);
        result = result.substring(2);

        // 保留字节（1字节）
        String ucNull = result.substring(0, 2);
        result = result.substring(2);

        // 测点数据
        List dtlist = new ArrayList();
        List<String> boxingTypes = Arrays.asList("13", "1B", "1C", "1D", "1E", "20");
        String dtType = "";

        if (boxingTypes.contains(type)) {
            dtType = "boXing";
        } else {
            dtType = "none";
        }

        if ("boXing".equals(dtType)) {
            cszt = true;
            if (!boxing.toString().contains("5244024f4b")) {
                boxing.append(hexStr);
            }
            if (boxing.toString().contains("5244024f4b")) {
                result = result + hexStr;
                boxing = new StringBuilder();
                cszt = false;
            }
        }

        System.err.println(boxing);

        if (cszt) {
            return null;
        }

        int saveDotCnt = Integer.parseInt(iSaveDotCnt, 16);
        for (int i = 0; i < saveDotCnt; i++) {
            Map obj = new HashMap();
            if ("boXing".equals(dtType)) {
                obj.put("houdu", Integer.parseInt(result.substring(0, 2), 16));
                obj.put("height", Integer.parseInt(result.substring(2, 4), 16));
                result = result.substring(4);
            } else if ("16".equals(type)) {
                obj.put("houdu1", Integer.parseInt(result.substring(0, 2), 16)); // a1
                obj.put("houdu2", Integer.parseInt(result.substring(2, 4), 16)); // a2
                obj.put("houdu", Integer.parseInt(result.substring(4, 6), 16)); // a3
                obj.put("height", 0);
                result = result.substring(6);
            } else {
                obj.put("height", Integer.parseInt(result.substring(0, 4), 16));
                System.err.println(result.substring(0, 8));
                obj.put("houdu", Integer.parseInt(result.substring(6, 8), 16));
                result = result.substring(8);
            }
            dtlist.add(obj);
        }

        if ("boXing".equals(dtType)) {
            List filteredList = new ArrayList();
            for (int i = 0; i < dtlist.size(); i++) {
                Map item = (Map) dtlist.get(i);
                int houdu = ((Number) item.get("houdu")).intValue();
                if (houdu != 0) {
                    item.put("index", i);
                    filteredList.add(item);
                }
            }
            dtlist = filteredList;
        }

        double avgJiange = 0;
        if (dtlist.size() > 1) {
            if ("boXing".equals(dtType)) {
                int lastIndex = ((Number) ((Map) dtlist.get(dtlist.size() - 1)).get("index")).intValue();
                int firstIndex = ((Number) ((Map) dtlist.get(0)).get("index")).intValue();
                avgJiange = (double) (lastIndex - firstIndex) / (dtlist.size() - 1) * 2;
                System.err.println(avgJiange);
                avgJiange = Math.round(avgJiange * 100.0) / 100.0;
            } else {
                int lastHeight = ((Number) ((Map) dtlist.get(dtlist.size() - 1)).get("height")).intValue();
                int firstHeight = ((Number) ((Map) dtlist.get(0)).get("height")).intValue();
                avgJiange = (double) (lastHeight - firstHeight) / (dtlist.size() - 1);
                System.err.println(avgJiange);
                avgJiange = Math.round(avgJiange * 100.0) / 100.0;
            }
        }
        double avgboGao = 0;
        if ("boXing".equals(dtType)) {
            double sum = 0;
            for (int i = 0; i < dtlist.size(); i++) {
                Map item = (Map) dtlist.get(i);
                sum += ((Number) item.get("height")).intValue();
            }
            avgboGao = sum / dtlist.size();
        }

        // 构建返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ucCoverAvg", Integer.parseInt(ucCoverAvg, 16));
        resultMap.put("avgJiange", avgJiange);
        resultMap.put("key", "GY71X");
        resultMap.put("avgboGao", String.format("%.2f", avgboGao));
        return resultMap;
    }
}
