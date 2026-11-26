package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 样式详情
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Data
@TableName("Outline_Project_Style_Dtl")
public class OutlineProjectStyleDtl {
    @TableId(type = IdType.INPUT)
    private String id;
    private String category;
    private String opsId;
    private String font;
    private Double fontSize;
    private String fontEng;
    private Double paragraphBefore;
    private Double paragraphAfter;
    private String lineSpacingType;
    private Double lineSpacingValue;
    private Double indentLeft;
    private Double indentRight;
    private String indentSpecial;
    private Double indentSpecialValue;
    private String align;
    private String margins;
    private Double marginsTop;
    private Double marginsBottom;
    private Double marginsLeft;
    private Double marginsRight;
    private Boolean bold;
    private Boolean italic;
    private Double tableBorderWidth;
    private String tableAlign;
    private Double headerUpperMargin;
    private Boolean headerUnderline;
    private Double footerBottomMargin;
    private Boolean footerPageNumber;
    private Boolean footerPageNumberCover;
}