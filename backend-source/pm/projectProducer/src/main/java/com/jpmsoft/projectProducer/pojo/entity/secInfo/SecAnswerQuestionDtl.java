package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 答疑补遗清单
 */
@Data
@TableName("Sec_Answer_Question_Dtl")
public class SecAnswerQuestionDtl {

    @TableId(type = IdType.INPUT)
    private String aqdId;
    private String aqId;
    //答疑单位
    private String aqdCompany;
    //答疑内容
    private String aqdContent;
    //答疑时间
    private Date aqdTime;
    //答疑回复
    private String aqdReply;
    //回复时间
    private Date aqdReplyTime;
    //创建时间
    private Date aqdCreateTime;
    //提疑内容
    private String aqdQuestion;
    //答疑类型
    private String aqdType;
    //排序
    private Integer aqdSort;
}
