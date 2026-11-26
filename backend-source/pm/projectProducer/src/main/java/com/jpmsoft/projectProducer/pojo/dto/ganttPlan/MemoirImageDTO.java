package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import lombok.Data;

import java.io.Serializable;

/**
 * 大事记图片
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:14
 */
@Data
public class MemoirImageDTO implements Serializable {

    private static final long serialVersionUID = 5046976599373488354L;

    private String id;
    private String miId;
    private String miImg;
    private String miCompressImg;
    private String miUrl;
    private String miCompressUrl;
    private Boolean miCarousel;
    private Integer miSort;

    private String miImgIds;
}
