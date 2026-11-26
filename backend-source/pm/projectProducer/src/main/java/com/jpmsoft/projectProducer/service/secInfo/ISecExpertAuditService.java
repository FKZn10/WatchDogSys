package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:19
 */
public interface ISecExpertAuditService extends IService<SecExpertAudit> {

    BaseResponse createOrUpdateSecExpertAudit(SecExpertAuditDTO request, LogUserInfoVo user);

    /**
     * 定制 生成名称
     * 申报单位+批次
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSecExpertAuditDz(SecExpertAuditDTO request, LogUserInfoVo user);

    BaseResponse getSecExpertAuditList(SecExpertAuditDTO request);

    BaseResponse getSecExpertAuditById(String id);

    BaseResponse approve2SyncExpertState(String seaId);

    BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token);

    BaseResponse getSecExpertAuditDetailListGroup();

    BaseResponse getSecExpertAuditByYear(Integer year);

    BaseResponse invalidExpertProcessData(String id);

    BaseResponse getSecExpertAuditChangeStatusInfoByYear(String exApplicantCId, Integer year);
}
