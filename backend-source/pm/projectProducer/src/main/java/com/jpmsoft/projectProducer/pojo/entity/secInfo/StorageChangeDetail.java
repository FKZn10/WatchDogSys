package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("STORAGE_CHANGE_DETAIL")
public class StorageChangeDetail {

    @TableId(type = IdType.INPUT)
    private String id;
    private String scId; // 关联storage_change表的id
    private String exApplicantId;
    private String exName;
    private String idNumber;
    private String exId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("dName")
    private String dName;
    private String exPracticeCode;
    private String exTitle;
    private String changeType;


    @TableField(exist = false)
    @JsonProperty("cId")
    private String cId; // 所属单位ID
    @JsonProperty("cName")
    @TableField(exist = false)
    private String cName; // 所属单位名称
    @TableField(exist = false)
    private String exRemark; // 专家备注
    @TableField(exist = false)
    private String exDId; // 所属部门ID
    @TableField(exist = false)
    private String exDName;
    @TableField(exist = false)
    private String exSex; // 性别
    @TableField(exist = false)
    private Date exBirthday; // 出生日期
    @TableField(exist = false)
    private String exFamily; // 家庭情况
    @TableField(exist = false)
    private String exIdType; // 证件类型
    @TableField(exist = false)
    private String exIdNumber; // 证件号码
    @TableField(exist = false)
    private String exLocation; // 所在地区
    @TableField(exist = false)
    private String exPolitics; // 政治面貌
    @TableField(exist = false)
    private String exEducation; // 教育程度
    @TableField(exist = false)
    private String exMajor; // 专业
    @TableField(exist = false)
    private String exSchool; // 毕业院校
    @TableField(exist = false)
    private String exWorkState; // 工作状态
    @TableField(exist = false)
    private Integer exProfessionalYears; // 专业年限
    @TableField(exist = false)
    private String exWorkUnit; // 工作单位
    @TableField(exist = false)
    private String exPost; // 职位
    @TableField(exist = false)
    private String exTitleNumber; // 职称编号
    @TableField(exist = false)
    private String exProfessionalCategory; // 专业类别
    @TableField(exist = false)
    private String exIndustry; // 行业
    @TableField(exist = false)
    private String exWorkAddress; // 工作地址
    @TableField(exist = false)
    private String exCellphoneNumber; // 手机号码
    @TableField(exist = false)
    private String exPostcode; // 邮政编码
    @TableField(exist = false)
    private String exFax; // 传真
    @TableField(exist = false)
    private String exHomePhone; // 家庭电话
    @TableField(exist = false)
    private String exEmail; // 电子邮箱
    @TableField(exist = false)
    private String exMailAddress; // 邮寄地址
    @TableField(exist = false)
    private String exBidMajor; // 招标专业
    @TableField(exist = false)
    private String exPracticeQualification; // 执业资格
    @TableField(exist = false)
    private String exBidArea; // 招标区域
    @TableField(exist = false)
    private String exPersonalResearch; // 个人研究
    @TableField(exist = false)
    private String exProjects; // 项目经验
    @TableField(exist = false)
    private String exAvoidanceUnit; // 回避单位
    @TableField(exist = false)
    private String exCreateUid; // 创建人ID
    @TableField(exist = false)
    private String exCreateUname; // 创建人姓名
    @TableField(exist = false)
    private Date exCreateTime; // 创建时间
    @TableField(exist = false)
    private Integer exState; // 状态
    @TableField(exist = false)
    private Date exStateTime; // 状态时间
    @TableField(exist = false)
    private String exCategory; // 分类
    @TableField(exist = false)
    private Date exStartDate; // 开始日期
    @TableField(exist = false)
    private Date exTilteDate; // 职称日期
    @TableField(exist = false)
    private Date exPracticeDate; // 执业日期
    @TableField(exist = false)
    private String exMainWork; // 主要工作
    @TableField(exist = false)
    private String exPersonalResult; // 个人成果
    @TableField(exist = false)
    private Integer exStatus; // 状态
    @TableField(exist = false)
    private String exType; // 类型
    @TableField(exist = false)
    private String exQualification; // 资格
    @TableField(exist = false)
    private String exDiplomaMajor; // 学历专业
    @TableField(exist = false)
    private Date exWorkTime; // 工作时间
    @TableField(exist = false)
    private Integer exWorkYear; // 工作年限
    @TableField(exist = false)
    private Integer exWorkMon; // 工作月份
    @TableField(exist = false)
    private String exCpy; // 公司
    @TableField(exist = false)
    private String exCpyPost; // 公司职位
    @TableField(exist = false)
    private Date exFinishDate; // 完成日期/入库时间
    @TableField(exist = false)
    private BigDecimal exPostNum; // 职位数量
    @TableField(exist = false)
    private BigDecimal exMajorNum; // 专业数量
    @TableField(exist = false)
    private String exBankAccount; // 银行账号
    @TableField(exist = false)
    private String exBank; // 银行名称
    @TableField(exist = false)
    private String exCardFile; // 身份证文件
    @TableField(exist = false)
    private String exDegree; // 学位
    @TableField(exist = false)
    private String exPerformFile; // 表现文件
    @TableField(exist = false)
    private String exTitleFile; // 职称文件
    @TableField(exist = false)
    private String exPromiseFile; // 承诺文件
    @TableField(exist = false)
    private String exAvoidFile; // 回避文件
    @TableField(exist = false)
    private String exOtherFile; // 其他文件
    @TableField(exist = false)
    private String exPlaintextNum; // 明文编号
    @TableField(exist = false)
    private String teId; // 相关ID
    @TableField(exist = false)
    private Integer exIsAvoidance; // 是否回避
    @TableField(exist = false)
    private BigDecimal exSysWorkYear; // 系统工作年限
    @TableField(exist = false)
    private String exDeclareMajorOne; // 申报专业一
    @TableField(exist = false)
    private String exDeclareMajorTwo; // 申报专业二
    @TableField(exist = false)
    private String exAvoid; // 回避内容
    @TableField(exist = false)
    private String personnelState; // 人员状态
    @TableField(exist = false)
    private Date personnelTimeStr; // 人员开始时间
    @TableField(exist = false)
    private Date personnelTimeEnd; // 人员结束时间
}
