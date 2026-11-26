package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 地质勘察
 */
@Data
@TableName("Design_Geology")
public class DesignGeology {

    @TableId(type = IdType.INPUT)
    private String dgId;
    //项目
    private String pId;
    //合同
    private String conId;
    //勘察内容
    private String dgSurveyContent;
    //开始时间
    private Date dgStart;
    //结束时间
    private Date dgEnd;
    private Integer State;
    private Date stateTime;
    private Date dgCreateTime;
    private String dgCreateUid;
    private String dgCreateUname;
    //勘察名称
    private String dgName;
    //报告版本编号
    private String dgCode;
    //勘察单位
    private String dgExplorationUnit;
    //是否符合勘察任务书
    private Integer dgDesignOrder;
    private String teId;
    //勘察单位联系方式
    private String dgExplorationUnitTel;

}
