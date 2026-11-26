package com.jpmsoft.projectProducer.pojo.entity.evaluate;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约评价指标库(ZNPJIndicator)实体类
 *
 * @author makejava
 * @since 2022-08-15 18:34:32
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("znpj_indicator")
public class ZNPJIndicator implements Serializable {
    private static final long serialVersionUID = -79163854386016353L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 指标名称
     */
    private String name;
    /**
     * 指标编号（唯一值自动生成）
     */
    private String code;

    /**
     * 指标版本id
     */
    private String indicatorVersionId;

    /**
     * 主版本号0-99
     */
    private Integer majorVersion;

    /**
     * 副版本0-999
     */
    private Integer subVersion;

    /**
     * 评价对象：1、履约（合同）2、项目3、单位
     */
    private Integer targetType;

    /**
     * 数据来源类型 1、系统录入 2、第三方平台录入 3、系统功能（直接选择四版的功能。比如人员更换，存具体的职位）
     */
    private String sourceType;

    /**
     * 系统功能模块：
     */
    private Integer sysModule;

    //接口库id
    private String interfaceId;


    /**
     * 指标分类
     */
    private String categoryId;

    /**
     * 类型：1、线性 2、单选 3、分段 4、多选 5、多选系数 6、无函数（填值类）7、登记值 8、登记得分 9、发生事件（添加多个自定义标签，后面用户填写数据）10、主动打分
     * 1-6类型，是在考核评分时填值或者选值
     * 7、8、9类型，是对应信息录入功能，通过指标关联
     * 10类型 指标得分由用户填写
     */
    private Integer type;

    /**
     * 分值类型：1、系数 2、分值
     */
    private Integer scoreType;

    /**
     * 是否是百分比（系数类型）：1、百分比（例如：履约率 80%）2、系数（例如：1）
     */
    private Integer coefficientType;

    /**
     * 加扣分类型： 1、加分 2、扣分 3、额外加分 4、直接评定 5、额外扣分
     */
    private Integer plusMinusScoreType;

    /**
     * 加扣分逻辑类型：
     * 1、普通加扣分：（例如：每更换一次项目经理扣5分） 发生次数、条件、分值（传值到occurNum、occurCondition、score）
     * 2、逻辑加扣分：（例如：单项技术用量比在 70%以上加 5 分，60%以上的加 3 分，50% 以上的加 2 分。） 传值到plusMinusScoreLogicValue
     * 基础值 70、60、50；条件、分值
     * 超过70 加 5分、超过60 加 3分、超过50 加 1分
     */
    private Integer plusMinusScoreLogicType;

    /**
     * 加扣分逻辑值：
     * [
     *  {
     *     "occurNum":70,//基础值
     *     "occurCondition":"大于",//条件
     *     "score":"5"//每项分值
     *  },
     *  {
     *     "occurNum":60,
     *     "occurCondition":"大于",
     *     "score":"3"
     *  },
     *  {
     *     "occurNum":50,
     *     "occurCondition":"大于",
     *     "score":"1"
     *  }
     * ]
     */
    private String plusMinusScoreLogicValue;

    /**
     * 发生次数
     * type为7时
     */
    private Integer occurNum;

    /**
     * 发生条件（大于、小于、等于、大于等于、小于等于）
     *  type为7时
     */
    private String occurCondition;

    /**
     * 每项分值
     */
    private String score;
    /**
    * 是否允许人为打分  0 否  1 是
    */
    private Integer isScoreArtificial;
    private Integer isPrintShow;

    /**
     * 指标考核要素
     */
    private String assessmentElementsName;

    /**
     * 考核描述
     */
    private String assessmentDes;

    /**
     * 业务管理部门id
     */
    private String businessManageDepId;
    /**
     * 业务管理部门
     */
    private String businessManageDep;
    /**
     * 管理制度文件（现改为存制度文件模块的文件id）
     */
    private String manageFileName;
    /**
     * 考核标准
     */
    private String assessmentCriteria;
    /**
     * 升降标准
     */
    private String liftingStandard;
    /**
     * 考核佐证
     */
    private String examinationEvidence;
    /**
     * 考核管理部门
     */
    private String assessmentManageDep;
    /**
     * 考核管理部门id
     */
    private String assessmentManageDepId;
    /**
     * 考核主体（部门id）id
     */
    private String assessmentSubjectId;
    /**
     * 考核主体
     */
    private String assessmentSubject;
    /**
     * 考核数据
     */
    private String assessmentData;

    /*****************一次性获取多个指标的来源****************/
    /**
     * 考核数据来源(存id)
     */
    private String assessmentDataSource;

    /**
     * 考核数据来源
     */
    private String assessmentDataSourceStr;

    /*****************单条指标配置的来源****************/
    /**
     * 考核数据来源地址
     */
    private String sourceUrl;

    /**
     * 请求方式 GET\POST
     */
    private String requestMethod;

    /**
     * 考核数据来源参数（入参）
     *  存参数库id
     */
    private String sourceParams;

    /**
     * 考核数据来源参数（入参）
     *  存展示的文本
     */
    private String sourceParamsStr;

    /**
     * 提取路径/判定规则
     */
    private String extractionPath;

    /**
     * 数据频率
     */
    private String dataFrequency;

    /**
     * 抓取时间
     */
    private String captureTime;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 单位
     */
    private String unit;

    /**
     * 指标标签ids
     */
    private String labelParam;


    private String labelParamStr;


    /**
     * 数据验证接口地址
     */
    private String dataValidationInterfaceAddress;

    /**
     * 数据验证页面地址
     */
    private String dataValidationPageAddress;

    /**
     * 请求方式 GET、POST
     */
    private String dataValidationRequestMethod;

    /**
     * 数据验证参数
     */
    private String dataValidationParams;

    /**
     * 数据验证参数
     */
    private String dataValidationParamsStr;


    //创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    //更新时间
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除状态：1正常 2删除
     */
    private Integer delFlag;
    /**
     * 状态：1 有效，2 禁用
     */
    private Integer status;




}