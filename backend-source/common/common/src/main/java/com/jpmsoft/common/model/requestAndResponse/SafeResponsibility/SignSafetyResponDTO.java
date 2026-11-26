package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 安全责任书签订
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SignSafetyResponDTO extends BaseFileDto {

    /**
     * 主键
     */
    private String saId;

    /**
     * 姓名，取人员申报表的申报人
     */
    private String saName;

    /**
     * 责任单位id，取人员申报表的申报单位
     */
    private String companyId;
    /**
     * 责任单位名称，列表展示用，由 companyId 关联SYS_Company得到
     */
    private String cName;

    /**
     * 岗位id
     */
    private String saPostId;
    /**
     * 岗位名称
     */
    private String saPostName;

    /**
     * 项目id
     */
    private String pId;
    /**
     * 项目名称
     */
    private String pName;

    /**
     * 合同id
     */
    private String conId;
    /**
     * 合同名称
     */
    private String conName;

    /**
     * 签订时间
     */
    private Date saSignTime;

    /**
     * 签订时间范围查询条件，0位开始时间1位结束时间
     */
    private List<String> saSignTimes;

    /**
     * 创建时间范围查询条件，0位开始时间1位结束时间
     */
    private List<String> createTimes;

    /**
     * 签订附件
     */
    private String saSignAttach;
    /**
     * 签订附件文件id集合
     */
    private String saSignAttachIds;

    /**
     * 岗位（冗余岗位名称），取人员申报表的变更后岗位
     */
    private String saPost;

    /**
     * 关联的人员申报表id
     */
    private String repPersonId;

    /**
     * 流程状态
     */
    private Integer state;
}
