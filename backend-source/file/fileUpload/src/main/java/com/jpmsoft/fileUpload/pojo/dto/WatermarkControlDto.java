package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/11/29 18:14
 */
@Data
public class WatermarkControlDto implements Serializable {
    /**
     * pdf预览是否开启水印
     */
    private boolean pdfViewEnable;
    /**
     * 水印字体大小
     */
    private int pdfViewFontSize = 10;
    /**
     * 水印内容
     */
    private List<Map<String, Object>> contents;
    /**
     * 透明度
     */
    private float webAlpha = 0.5f;
    /**
     * 旋转角度
     */
    private float rotation = -40;
    /**
     * 字体颜色RGB
     */
    private String colorRgb;


//    private int pdfViewFontSize = 10;
//    private int pdfViewFontSize = 10;

}
