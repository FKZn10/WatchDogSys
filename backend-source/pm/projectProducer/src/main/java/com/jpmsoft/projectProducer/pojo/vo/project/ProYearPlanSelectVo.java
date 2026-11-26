package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.requestAndResponse.Project.ProConstructionPlanListModel;
import lombok.Data;

/**
 * 年度申报计划选择清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:10
 */
@Data
public class ProYearPlanSelectVo extends ProConstructionPlanListModel {
    private String id;
    private String cplId;
    private String ycpId;
}
