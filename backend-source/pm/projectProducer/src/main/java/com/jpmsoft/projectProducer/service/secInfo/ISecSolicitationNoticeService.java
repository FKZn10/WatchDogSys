package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSolicitationNoticeDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSolicitationNotice;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:30
 */
public interface ISecSolicitationNoticeService extends IService<SecSolicitationNotice> {

    BaseResponse createOrUpdateSecSolicitationNotice(SecSolicitationNoticeDto request);

    BaseResponse getSecSolicitationNoticeList(SecSolicitationNoticeDto request);

    BaseResponse getSecSolicitationNoticeById(String id);

    BaseResponse getSecSolicitationNoticeLastApprovedBySecId(String secId);
}
