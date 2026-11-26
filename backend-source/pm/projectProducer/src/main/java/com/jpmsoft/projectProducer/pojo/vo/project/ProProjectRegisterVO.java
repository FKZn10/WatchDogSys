package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegister;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegisterUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 项目登记详情，包含参建单位、附件分组及附件列表。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProProjectRegisterVO extends ProProjectRegister {

    /**
     * 按排序号、ID排列的参建单位。
     */
    private List<ProProjectRegisterUnit> unitList;
    /**
     * 单据附件分组ID。
     */
    private String fileRelationId;
    /**
     * 单据附件。
     */
    private List<FileResponse> files;
    /**
     * 批复文件附件。
     */
    private List<FileResponse> approvalFiles;
    /**
     * 项目效果图附件。
     */
    private List<FileResponse> renderingFiles;
}

