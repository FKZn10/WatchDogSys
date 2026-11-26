package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Judge_Extraction_Detail")
public class JudgeExtractionDetail {

    @TableId(type = IdType.INPUT)
    private String id;                      // 主键
    private String extractionId;           // 抽取表ID
    private String exId;                   // 专家ID
    private String cId;                    // 关联单位ID
    private String cName;                  // 关联单位名称
    private String exName;                 // 专家姓名
    private String exRemark;               // 专家备注
    private String exDId;                  // 专家部门ID
    private String exDName;                // 专家部门名称
    private String exSex;                  // 性别
    private Date exBirthday;          // 出生日期
    private String exFamily;               // 民族
    private String exIdType;               // 证件类型
    private String exIdNumber;             // 证件号码
    private String exLocation;             // 所在地
    private String exPolitics;             // 政治面貌
    private String exEducation;            // 学历
    private String exMajor;                // 专业
    private String exSchool;               // 毕业院校
    private String exWorkState;            // 工作状态
    private Integer exProfessionalYears;   // 从业年限
    private String exWorkUnit;             // 工作单位
    private String exPost;                 // 职务
    private String exTitle;                // 职称
    private String exTitleNumber;          // 职称证书编号
    private String exProfessionalCategory; // 专业类别
    private String exIndustry;             // 行业
    private String exWorkAddress;          // 工作地址
    private String exCellphoneNumber;      // 手机号码
    private String exPostcode;             // 邮编
    private String exFax;                  // 传真
    private String exHomePhone;            // 家庭电话
    private String exEmail;                // 电子邮箱
    private String exMailAddress;          // 通讯地址
    private String exBidMajor;            // 评标专业
    private String exPracticeQualification;// 执业资格
    private String exBidArea;              // 评标区域
    private String exPersonalResearch;     // 个人研究
    private String exProjects;             // 项目经验
    private String exAvoidanceUnit;        // 回避单位
    private String exCreateUid;           // 创建人ID
    private String exCreateUname;         // 创建人姓名
    private Date exCreateTime;       // 创建时间
    private Integer exState;              // 状态
    private Date exStateTime;        // 状态变更时间
    private String exCategory;            // 专家类别
    private Date exStartDate;        // 开始日期
    private Date exTilteDate;        // 职称日期
    private Date exPracticeDate;     // 执业日期
    private String exPracticeCode;        // 执业证书编号
    private String exMainWork;            // 主要工作
    private String exPersonalResult;      // 个人成果
    private Integer exStatus;             // 状态
    private String exType;                // 专家类型
    private String exQualification;       // 资格
    private String exDiplomaMajor;        // 学历专业
    private Date exWorkTime;         // 工作时间
    private Integer exWorkYear;           // 工作年限(年)
    private Integer exWorkMon;            // 工作年限(月)
    private String exCpy;                 // 现工作单位
    private String exCpyPost;             // 现职务
    private Date exFinishDate;            // 完成日期
    private BigDecimal exPostNum;         // 职务数量
    private BigDecimal exMajorNum;        // 专业数量
    private String exBankAccount;         // 银行账号
    private String exBank;                // 开户银行
    private String exCardFile;            // 证件文件
    private String exDegree;              // 学位
    private String exPerformFile;         // 业绩文件
    private String exTitleFile;           // 职称文件
    private String exPromiseFile;         // 承诺文件
    private String exAvoidFile;           // 回避文件
    private String exOtherFile;           // 其他文件
    private String exPlaintextNum;        // 明文编号
    private String teId;                  // 关联ID
    private Short exIsAvoidance;          // 是否回避(0否1是)
    private BigDecimal exSysWorkYear;      // 系统计算工作年限
    private String exDeclareMajorOne;     // 申报专业一
    private String exDeclareMajorTwo;     // 申报专业二
    private String exAvoid;               // 回避说明
    private String exApplicantId;         // 申请人ID
    private String personnelState;        // 人员状态
    private Date personnelTimeStr;        // 人员开始时间
    private Date personnelTimeEnd;        // 人员结束时间
    private String inLibrary ;   // 是否入库
    /**
     * 是否能参会
     */
    private Integer srdAttend;


    // "01" 表示内部专家  "02" 表示外部专家
    private String experType;

    private Date extractTime;
}