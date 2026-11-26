package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * word模板渲染
 *
 * @Author: 李文涛 aaron
 * @date: 2024/5/14 13:45
 */
@Data
public class WordTempRenderingDTO implements Serializable {
    private static final long serialVersionUID = 4162332152145748991L;
    //基础配置模板文件
    private String fileConfCode;
    //数据源  图片需要转为ID  比如模板内key img1 值则对应附件ID
    private WordTempRenderingDataDTO data;
    //附件的ID
    private List<String> fileIds;
    //图片宽度
    private double imgWidth = 375d;
    //图片高度
    private double imgHeight = 225d;

    private String pdf;

}
