package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

//建管专家 excel类
@Data
public class JGExpertExcelDTO {

    //姓名
    private String exName;

    //证件号码
    private String exIdNumber;

    //家庭电话（手机号）
    private String exHomePhone;

    //专业技术职称
    private String exTitle;

    //专家类别   JPMsoft_JGZJLX
    private String exCategory;

    //技术类专家申报专业1  JPMsoft_JGZJSBZYA
    private String exDeclareMajorOne;
    //技术类专家申报专业2 JPMsoft_JGZJSBZYA
    private String exDeclareMajorTwo;
    //经济类专家申报专业1 JPMsoft_JGZJSBZYB
    private String exEconomicMajorOne;
    //经济类专家申报专业2 JPMsoft_JGZJSBZYB
    private String exEconomicMajorTwo;

}
