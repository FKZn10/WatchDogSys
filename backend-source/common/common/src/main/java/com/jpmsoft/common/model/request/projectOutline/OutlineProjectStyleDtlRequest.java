package com.jpmsoft.common.model.request.projectOutline;

import lombok.Data;

/**
 * 样式详情
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Data
public class OutlineProjectStyleDtlRequest {
    private String id;
    private String category;
    private String opsId;
    private String font;
    private String fontEng;
    private Double fontSize;
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
