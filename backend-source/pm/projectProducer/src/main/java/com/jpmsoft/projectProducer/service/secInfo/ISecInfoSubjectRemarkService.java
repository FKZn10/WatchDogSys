package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectRemark;

import java.util.List;

/**
 * 招标计划信息 合同外工程及费用 备注表 服务类
 */
public interface ISecInfoSubjectRemarkService extends IService<SecInfoSubjectRemark> {
    /**
     * 新增或修改备注表
     *
     * @param remarkList
     * @param mainId
     * @return
     */
    BaseResponse createOrUpdateSecInfoSubjectRemark(List<SecInfoSubjectRemarkDTO> remarkList, String mainId);

    /**
     * 从旧的mainId同步到新的mainId
     *
     * @param oldMainId
     * @param newMainId
     * @return
     */
    BaseResponse approval2UpdateSecInfoSubjectRemark(String oldMainId, String newMainId);
}
