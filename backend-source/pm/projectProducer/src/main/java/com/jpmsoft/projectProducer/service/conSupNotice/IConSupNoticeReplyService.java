package com.jpmsoft.projectProducer.service.conSupNotice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeReply;

public interface IConSupNoticeReplyService extends IService<ConSupNoticeReply> {

    /**
     * 新增或者修改  回复得通知单
     *
     * @param
     * @return
     */
    BaseResponse createOrUpdateConSupNoticeReply(RequestBean requestBean,String teId);


    BaseResponse updateSnrState(RequestBean requestBean);

    BaseResponse getConSupNoticeReplyPageList(Page page,String teId,String uId);


    BaseResponse getConSupNoticeReplyById(RequestBean requestBean);

    /**
     * 获取回复次序
     *
     * @param pId
     * @param snId
     * @return
     */
    BaseResponse getReplyOrder(String pId, String snId);
}
