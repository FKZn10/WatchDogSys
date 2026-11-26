package com.jpmsoft.fileUpload.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目信息填报实体类
 * @author jjz
 */
@Data
@TableName("t_information_filling")
public class TInformationFilling {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 项目名称
     */


    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 项目分类
     */
    private String classification;

    /**
     * 细项分类
     */
    private String itemclassification;

    /**
     * 建设性质
     */
    private String typeofConstruction;

    /**
     * 规模值
     */
    private BigDecimal scalekey;

    /**
     * 单位
     */
    private String unit;

    /**
     * 建设周期（月）
     */
    private String constructioncycle;

    /**
     * 建设内容
     */
    private String constructioncontent;

    /**
     * 总投资
     */
    private BigDecimal invest;

    /**
     * 匡算
     */
    private BigDecimal roughestimate;

    /**
     * 估算
     */
    private BigDecimal reckon;

    /**
     * 概算
     */
    private BigDecimal budestimate;

    /**
     * 预算
     */
    private BigDecimal budget;

    /**
     * 计划开工时间
     */
    private String projectstart;

    /**
     * 开工时间
     */
    private String startwork;

    /**
     * 竣工时间
     */
    private String becompleted;

    /**
     * BIM技术，确认项目是否使用BIM技术
     */
    private String BIMskill;

    /**
     * 智慧工地，确认项目是否使用智慧工地
     */
    private String wisdomsite;

    /**
     * 工程项目管理信息系统，确认该项目是否使用工程项目管理信息系统进行项目管理
     */
    private String projectmanagement;

    /**
     * 新技术，目是否运用新的建造技术，如有运用可在备注栏进行内容说明
     */
    private String newtechnolony;

    /**
     * 新材料，确认项目建造是否有运用高新材料，如有运用可在备注栏进行内容说明
     */
    private String thenewmaterial;

    /**
     * 新工艺，确认项目建造是否有运用新型工艺，如有运用可在备注栏进行内容说明
     */
    private String thenewprocess;

    /**
     * 节能，确认项目是否具节能特色
     */
    private String energysaving;

    /**
     * 节地，确认项目是否符合节地设计特色
     */
    private String inrhythmic;

    /**
     * 节水，确认项目是否具节水设计特色
     */
    private String watersaving;

    /**
     * 节材，确认项目是否具节材设计特色
     */
    private String savingmaterial;

    /**
     * 装配式建造，确认项目是否采用装配置建造
     */
    private String fabricatedconstruction;

    /**
     * 数字建造，确认项目是否采用数字建造
     */
    private String digitalconstruction;

    /**
     * 绿色建造，确认项目是否采用绿色建造
     */
    private String greenbuilding;

    /**
     * 是否集中建设，确认是否集中建设采用
     */
    private String focusontheconstruction;

    /**
     * 工程总承包，确认是否采用工程总承包
     */
    private String generalprojectcontracting;

    /**
     * 是否全过程工程咨询，确认是否采用全过程工程咨询
     */
    private String engineeringconsulting;

    /**
     * 是否建筑师负责制，确认是否采用建筑师负责制
     */
    private String architectincharge;

    @ExcelValid
    @ExcelProperty(index = 1)
    private String name;
    /**
     * 上报年份
     */
    @ExcelValid
    @ExcelProperty(index = 2)
    private String reportingYear;

    /**
     * 上报月份
     */
    @ExcelValid
    @ExcelProperty(index = 3)
    private String reportingMonth;

    /**
     * 上季度计划
     */
    @ExcelValid
    @ExcelProperty(index = 4)
    private BigDecimal plannedforlastquarter;

    /**
     * 本季度计划
     */
    @ExcelValid
    @ExcelProperty(index = 5)
    private BigDecimal planforthisquarter;

    /**
     * 上季度完成
     */
    @ExcelValid
    @ExcelProperty(index = 6)
    private BigDecimal completedlastquarter;

    /**
     * 本季度完成
     */
    @ExcelValid
    @ExcelProperty(index = 7)
    private BigDecimal completethequarter;

    /**
     * 上季度支付
     */
    @ExcelValid
    @ExcelProperty(index = 8)
    private BigDecimal paylastquarter;

    /**
     * 本季度支付
     */
    @ExcelValid
    @ExcelProperty(index = 9)
    private BigDecimal payforthequarter;

    /**
     * 形象进度
     */
    @ExcelValid
    @ExcelProperty(index = 10)
    private String imageprogress;

    /**
     * 项目产出
     */
    @ExcelValid
    @ExcelProperty(index = 11)
    private BigDecimal projectoutput;

    /**
     * 施工面积
     */
    @ExcelValid
    @ExcelProperty(index = 12)
    private BigDecimal theconstructionareaof;

    /**
     * 施工面积单位
     */
    @ExcelValid
    @ExcelProperty(index = 13)
    private String conunit;

    /**
     * 新开工面积
     */
    @ExcelValid
    @ExcelProperty(index = 14)
    private BigDecimal newstartarea;

    /**
     * 新开工面积单位
     */
    @ExcelValid
    @ExcelProperty(index = 15)
    private String newsunit;

    /**
     * 人员数量
     */
    @ExcelValid
    @ExcelProperty(index = 16)
    private Integer numberofpersonnel;

    /**
     * 完成产值
     */
    @ExcelValid
    @ExcelProperty(index = 17)
    private BigDecimal completionofthevalue;

    /**
     * 完成面积
     */
    @ExcelValid
    @ExcelProperty(index = 18)
    private BigDecimal completionarea;

    /**
     * 完成面积单位
     */
    @ExcelValid
    @ExcelProperty(index = 19)
    private String comunit;

    /**
     * 完成金额
     */
    @ExcelValid
    @ExcelProperty(index = 20)
    @TableField("completedAmount")
    private String completedAmount;

    /**
     * 合同总金额
     */
    @ExcelValid
    @ExcelProperty(index = 21)
    private BigDecimal totalcontractamount;

    /**
     * 结转合同金额
     */
    @ExcelValid
    @ExcelProperty(index = 22)
    private BigDecimal carryforwardthecontractamount;

    /**
     * 新签合同金额
     */
    @ExcelValid
    @ExcelProperty(index = 23)
    private BigDecimal newcontractamount;

    /**
     * 一季度目标
     */
    @ExcelValid
    @ExcelProperty(index = 24)
    @TableField("firstQuarterTarget")
    private BigDecimal firstQuarterTarget;

    /**
     * 二季度目标
     */
    @ExcelValid
    @ExcelProperty(index = 25)
    @TableField("secondQuarterTarget")
    private BigDecimal secondQuarterTarget;

    /**
     * 三季度目标
     */
    @ExcelValid
    @ExcelProperty(index = 26)
    @TableField("thirdQuarterTarget")
    private BigDecimal thirdQuarterTarget;

    /**
     * 四季度目标
     */
    @ExcelValid
    @ExcelProperty(index = 27)
    @TableField("fourthQuarterTarget")
    private BigDecimal fourthQuarterTarget;

    /**
     * 国家级
     */
    @ExcelValid
    @ExcelProperty(index = 28)
    private String nationallevel;

    /**
     * 省级
     */
    @ExcelValid
    @ExcelProperty(index = 29)
    private String provinciallevel;

    /**
     * 市级
     */
    @ExcelValid
    @ExcelProperty(index = 30)
    private String citylevel;

    /**
     * 其他级别
     */
    @ExcelValid
    @ExcelProperty(index = 31)
    private String otherlevel;

    /**
     * 备注
     */
    @ExcelValid
    @ExcelProperty(index = 32)
    private String note;

    /**
     * 简要分析报告
     */
    @ExcelValid
    @ExcelProperty(index = 33)
    private String briefanalysisreport;

    /**
     * 单位编号
     */
    private String oid;

    /**
     * 区域编号
     */
    private String did;

    /**
     * 权限码
     */
    private String rang;


    private Date creattime;

    /**
     * 来源类型
     */
    private Integer sourceType;

    /**
     * 父ID
     */
    private String pId;

    /**
     * 创建用户ID
     */
    private String createUid;

    /**
     * 创建用户名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 状态时间
     */
    private Date stateTime;


    @TableField("uuid")
    private String uuid;
}
