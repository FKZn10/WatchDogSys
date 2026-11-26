package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专家库（建管）
 */
@Data
@TableName("Sec_JG_Expert")
public class SecJGExpert {

    @TableId(type = IdType.INPUT)
    private String id;
    //所属单位ID（内部专家才有的属性 ）
    private String cId;
    /**
     * 是否内部专家 0：外部 1、内部
     */
    private Integer exInsideType;
    /**
     * （内部专家才有的属性 ）是否进场0：非进场 1、进场
     */
    private Integer exEntry;
    //姓名
    private String exName;
    //性别
    private String exSex;
    //出生日期
    private Date exBirthday;
    //民族
    private String exFamily;
    //证件类别
    private String exIdType;
    //证件号码
    private String exIdNumber;
    //所在地区
    private String exLocation;
    //政治面貌
    private String exPolitics;
    //最高学历
    private String exEducation;
    //所学专业
    private String exMajor;
    //毕业院校
    private String exSchool;
    //工作状态
    private String exWorkState;
    //从事专业年限
    private Integer exProfessionalYears;
    //工作单位
    private String exWorkUnit;
    //职务
    private String exPost;
    //专业技术职称  JPMsoft_ZYJSZC
    private String exTitle;
    //职称编号
    private String exTitleNumber;
    //从事专业类别
    private String exProfessionalCategory;
    //所属行业
    private String exIndustry;
    //工作地址
    private String exWorkAddress;
    //手机1
    private String exCellphoneNumber1;
    //手机2
    private String exCellphoneNumber2;
    //邮编
    private String exPostcode;
    //传真
    private String exFax;
    //家庭电话
    private String exHomePhone;
    //email
    private String exEmail;
    //通信地址
    private String exMailAddress;
    //评标专业
    private String exBidMajor;
    //执业资格
    private String exPracticeQualification;
    //评标区域
    private String exBidArea;
    //个人研究及专业成就
    private String exPersonalResearch;
    //曾参与过的主要评审项目
    private String exProjects;
    //回避单位
    private String exAvoidanceUnit;
    //创建人ID
    private String exCreateUid;
    //创建人姓名
    private String exCreateUname;
    //创建时间
    private Date exCreateTime;
    //审批状态
    private Integer exState;
    //审批时间
    private Date exStateTime;
    //专家类别 JPMsoft_JGZJLX
    private String exCategory;
    //从事专业起始年月
    private Date exStartDate;
    //获得技术职称时间
    private Date exTilteDate;
    //取得执业资格时间
    private Date exPracticeDate;
    //执业证书编号
    private String exPracticeCode;
    //主要工作经历
    private String exMainWork;
    //专业学术成果
    private String exPersonalResult;
    //状态
    private Integer exStatus;
    //专家类型
    private String exType;
    //北京工行 获取职业资格时间 格式 yyyy-MM/yyyy-MM
    private String exQualification;

    /**
     * 毕业证书专业
     */
    private String exDiplomaMajor;
    /**
     * 首次参加工作时间
     */
    private Date exWorkTime;
    /**
     * 工作年限(年)
     */
    private Integer exWorkYear;
    /**
     * 工作年限(月)
     */
    private Integer exWorkMon;
    /**
     * 所在单位
     */
    private String exCpy;
    /**
     * 所在单位职务
     */
    private String exCpyPost;
    //毕业时间
    private Date exFinishDate;
    //从事现岗位年数
    private BigDecimal exPostNum;
    //从事现专业年数
    private BigDecimal exMajorNum;
    //银行账号
    private String exBankAccount;
    //开户行
    private String exBank;
    //身份证（附件）
    private String exCardFile;
    //学历学位证书
    private String exDegree;
    //获奖证书、专业业绩、执业资格证书等证明自身能力的材料
    private String exPerformFile;
    //专业技术职称证书或者具有同等专业水平的证明材料
    private String exTitleFile;
    //《承诺书》（格式详见附件2）
    private String exPromiseFile;
    //本人认为需要申请回避的信息
    private String exAvoidFile;
    //其他
    private String exOtherFile;

    /**
     * 仅用于存储明文身份证号码
     * 用于逻辑操作 不响应于前端
     */
    private String exPlaintextNum;
    private String teId;
    //有无回避单位
    private Boolean exIsAvoidance;
    //申请人
    private String exApplicantId;
    //参加集团系统工作年份
    private Integer exSysWorkYear;
    //技术类专家申报专业1  JPMsoft_JGZJSBZYA
    private String exDeclareMajorOne;
    //技术类专家申报专业2 JPMsoft_JGZJSBZYA
    private String exDeclareMajorTwo;
    //经济类专家申报专业1 JPMsoft_JGZJSBZYB
    private String exEconomicMajorOne;
    //经济类专家申报专业2 JPMsoft_JGZJSBZYB
    private String exEconomicMajorTwo;
    //本人认为需要回避的信息
    private String exAvoid;

    private String exUnit;
}
