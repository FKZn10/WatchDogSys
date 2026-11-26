package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/18 11:00
 */
@Data
public class DepartmentPCjConfDTO {
    /**
     * 项目审批通过需要导入的项目组织机构模板名称(需唯一 否则同名随机取第一个)
     */
    private String depTemp = "组织机构报备模板";
    /**
     * 集团本部项目部门编码
     */
    private String jtCode = "JTBB";
    /**
     * 项目管理单位部门编码
     */
    private String gldwCode = "QGCXMGL";
    /**
     * 集团本部工程部分管部长岗位编码
     */
    private String jtLd = "GCBFGBZ";
    /**
     * 集团本部工程部经办人岗位编码
     */
    private String jtJbr = "GCBJBR";
    /**
     * 项目管理单位项目经理岗位编码
     */
    private String gldwXmjl = "XMJL";
    /**
     * 项目管理单位项目分管领导岗位编码
     */
    private String gldwFgld = "XMFGLD";

    /**
     * 业主单位1部门编码
     */
    private String yzdwoCode = "YZDW1";

    /**
     * 业主单位1项目负责人岗位编码
     */
    private String xmfzoCode = "XMFZR1";

    /**
     * 业主单位1项目分管领导岗位编码
     */
    private String xmfgoCode = "XMFGLD1";

    /**
     * 业主单位2部门编码
     */
    private String yzdwtCode = "YZDW2";

    /**
     * 业主单位2项目负责人岗位编码
     */
    private String xmfztCode = "XMFZR2";

    /**
     * 业主单位2项目分管领导岗位编码
     */
    private String xmfgtCode = "XMFGLD2";

    /**
     * 项目建设模式（不必填）
     */
    @JsonProperty("pBuildModel")
    private String pBuildModel;

    /**
     * 参建单位部门编码
     */
    private String cjdwCode = "CJDW";

    /**
     * 建设单位/代建单位
     */
    private String jsdwCode = "JSDW";

    /**
     * 建设单位项目负责人
     */
    private String jsXmfzrCode = "JSXMFZR";

    /**
     * 项目副经理/驻地经理
     */
    private String xmfjlCode = "XMFJL";

    /**
     * 项目管理人员
     */
    private String xmglryCode = "XMGLRY";

    /**
     * 监理单位
     */
    private String jldwCode = "JLDW";

    /**
     * 总监理工程师
     */
    private String zjlgcsCode = "ZJLGCS";

    /**
     * 专业监理工程师
     */
    private String zyjlgcsCode = "ZYJLGCS";

    /**
     * 监理员
     */
    private String jlyCode = "JLY";

    /**
     * 施工单位
     */
    private String sgdwCode = "SGDW";

    /**
     * 施工单位项目经理
     */
    private String sgxmjlCode = "SGXMJL";

    /**
     * 项目副经理 / 生产经理
     */
    private String scjlCode = "SCJL";

    /**
     * 技术负责人（总工）
     */
    private String jsfzrCode = "JSFZR";

    /**
     * 安全负责人
     */
    private String aqfzrCode = "AQFZR";

    /**
     * 现场管理人员
     */
    private String xcglyCode = "XCGLY";

    /**
     * 勘查单位
     */
    private String kcdwCode = "KCDW";

    /**
     * 项目负责人
     */
    private String kcxmfzrCode = "KCXMFZR";

    /**
     * 现场技术人员
     */
    private String xcjsyCode = "XCJSY";

    /**
     * 设计单位
     */
    private String sjdwCode = "SJDW";

    /**
     * 设计单位项目负责人
     */
    private String sjxmfzrCode = "SJXMFZR";

    /**
     * 专业负责人（建筑、结构、给排水、电、暖通、其他）
     */
    private String zyfzrCode = "ZYFZR";

    /**
     * 跟踪审计单位/造价咨询单位
     */
    private String gzsjdwCode = "GZSJDW";

    /**
     * 项目审计负责人
     */
    private String xmsjfzrCode = "XMSJFZR";

    /**
     * 项目现场主审
     */
    private String xczsCode = "XCZS";

    /**
     * 专业审计工程师（与设计单位专业负责人对应）
     */
    private String sjgcsCode = "SJGCS";
}
