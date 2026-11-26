package com.jpmsoft.common.model.request.SecInfo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsExpertRequest extends BaseFileRequest {


    private String exId; // 专家ID
    @JsonProperty("cId")
    private String cId; // 所属单位ID
    @JsonProperty("cName")
    private String cName; // 所属单位名称
    private String exApplicantId;
    private String exName; // 专家姓名
    private String exRemark; // 专家备注
    private String exDId; // 所属部门ID
    private String exDName;
    private String exIdNumber;
    private String exIdNumberEncrypt;
    private String exCellphoneNumber; // 手机号码
    private String exTitle; // 职称
    private String exPracticeCode; // 工程注册证书
    private String exAvoid; // 回避内容
    /**
     * 01 集团专家
     * 02 小城投专家
     * 缺省01
     */
    private String exCategory = "01"; // 分类
    private String exMainWork;  //工作经历
    private Integer exWorkYear; //工作年限
    private String exOtherFile; // 其他文件
    private String exOtherFileIds;
    private String exPromiseFile; // 承诺文件
    private String exPromiseFileIds; // 承诺文件
    private String exTitleFile; // 职称文件
    private String exTitleFileIds; // 职称文件
    private String exCardFile; // 身份证文件
    private String exCardFileIds; // 身份证文件
    private String inLibrary; // 是否在库
    private String exCreateUid;  // 创建人ID
    private String exCreateUname;  // 创建人姓名


    private String exSex; // 性别
    private Date exBirthday; // 出生日期
    private String exFamily; // 家庭情况
    private String exIdType; // 证件类型
    private String exLocation; // 所在地区
    private String exPolitics; // 政治面貌
    private String exEducation; // 教育程度
    private String exMajor; // 专业
    private String exSchool; // 毕业院校
    private String exWorkState; // 工作状态
    private Integer exProfessionalYears; // 专业年限
    private String exWorkUnit; // 工作单位
    private String exPost; // 职位
    private String exTitleNumber; // 职称编号
    private String exProfessionalCategory; // 专业类别
    private String exIndustry; // 行业
    private String exWorkAddress; // 工作地址
    private String exPostcode; // 邮政编码
    private String exFax; // 传真
    private String exHomePhone; // 家庭电话
    private String exEmail; // 电子邮箱
    private String exMailAddress; // 邮寄地址
    private String exBidMajor; // 招标专业
    private String exPracticeQualification; // 执业资格
    private String exBidArea; // 招标区域
    private String exPersonalResearch; // 个人研究
    private String exProjects; // 项目经验
    private String exAvoidanceUnit; // 回避单位
    private Date exCreateTime; // 创建时间
    private Integer exState; // 状态
    private Date exStateTime; // 状态时间
    private Date exStartDate; // 开始日期
    private Date exTilteDate; // 职称日期
    private Date exPracticeDate; // 执业日期
    private String exPersonalResult; // 个人成果
    private Integer exStatus; // 状态
    private String exType; // 类型
    private String exQualification; // 资格
    private String exDiplomaMajor; // 学历专业
    private Date exWorkTime; // 工作时间
    private Integer exWorkMon; // 工作月份
    private String exCpy; // 公司
    private String exCpyPost; // 公司职位
    private Date exFinishDate; // 完成日期/入库时间
    private BigDecimal exPostNum; // 职位数量
    private BigDecimal exMajorNum; // 专业数量
    private String exBankAccount; // 银行账号
    private String exBank; // 银行名称
    private String exDegree; // 学位
    private String exPlaintextNum; // 明文编号
    private String teId; // 相关ID
    private Boolean exIsAvoidance; // 是否回避
    private BigDecimal exSysWorkYear; // 系统工作年限
    private String exDeclareMajorOne; // 申报专业一
    private String exDeclareMajorTwo; // 申报专业二
    private String personnelState; // 人员状态
    private Date personnelTimeStr; // 人员开始时间
    private Date personnelTimeEnd; // 人员结束时间

    /**
     * 是否能参会 1 0
     */
    private Integer srdAttend;


    // "01" 表示内部专家  "02" 表示外部专家
    private String experType;

    private Date extractTime;

}
