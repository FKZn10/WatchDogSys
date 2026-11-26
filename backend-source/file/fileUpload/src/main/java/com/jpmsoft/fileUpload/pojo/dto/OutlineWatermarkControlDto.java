package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OutlineWatermarkControlDto implements Serializable {
    /**
     * 是否开启水印
     */
    private boolean enable = true;
    /**
     * 水印字体
     */
    private String font = "Times New Roman";
    /**
     * 水印字体大小
     */
    private int fontSize = 12;
    /**
     * 透明度
     */
    private float alpha = 0.5f;
    /**
     * 旋转角度
     */
    private float rotation = -5;
    /**
     * 字体颜色RGB
     */
    private String colorRgb = "255,248,220";
    /**
     * 水印内容
     */
    private List<Map<String, Object>> contents;
}
