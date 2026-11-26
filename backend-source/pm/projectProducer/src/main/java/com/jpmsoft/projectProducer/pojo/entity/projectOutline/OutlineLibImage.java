package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 目录图片对应
 *
 * @author tianhelin
 * @date 2025/08/25
 */
@Data
@TableName("Outline_Lib_Image")
public class OutlineLibImage {
    @TableId(type = IdType.INPUT)
    private String id;
    private String libId;
    private String fileId;
}