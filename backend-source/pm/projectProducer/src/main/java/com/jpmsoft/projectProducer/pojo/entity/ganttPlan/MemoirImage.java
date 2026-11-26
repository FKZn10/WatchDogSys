package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 大事记图片
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:14
 */
@Data
@TableName("memoir_image")
public class MemoirImage {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    @TableField(value = "mi_id")
    private String miId;
    @TableField(value = "mi_img")
    private String miImg;
    @TableField(value = "mi_compress_img")
    private String miCompressImg;
    @TableField(value = "mi_url")
    private String miUrl;
    @TableField(value = "mi_compress_url")
    private String miCompressUrl;
    @TableField(value = "mi_carousel")
    private Boolean miCarousel;
    @TableField(value = "mi_sort")
    private Integer miSort;

}
