package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaNoticeRequest;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNotice;

public interface IOaNoticeService extends IService<OaNotice> {

    BaseResponse createOrUpdateOaNotice(OaNoticeRequest request);

    BaseResponse getOaNoticeByNid(String nId);

    BaseResponse getOaNoticeList(OaNoticeRequest request);

    BaseResponse getUserOaNoticeList(OaNoticeRequest request);

    BaseResponse deleteOaNoticeByNid(String nId);

    /**
     * 收发文 管理  添加查看记录
     *
     * @author:zhangl
     */
    BaseResponse createOrUpdateViewRecords(RequestBean requestBean);

    /**
     * 获取分页列表 查看记录
     */
    BaseResponse getViewRecordsById(RequestBean requestBean);
}
