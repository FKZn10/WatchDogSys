package com.jpmsoft.projectProducer.service.conSupNotice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNotice;

public interface IConSupNoticeService extends IService<ConSupNotice> {
    /**
     * 新增或者修改
     *
     * @param requestBean
     * @return
     */
    BaseResponse createOrUpdateConSupNotice(RequestBean requestBean,String teId);

    /**
     * 获取监理通知单列表
     *
     * @param page
     * @return
     */
    BaseResponse getConSupNoticeList(Page page,String teId,String uId);

    /**
     * @param requestBean
     * @return
     */
    BaseResponse getConSupNoticeById(RequestBean requestBean);

    /**
     * 审核
     *
     * @param requestBean
     * @return
     */
    BaseResponse updateSnState(RequestBean requestBean);

    /**
     * 根据监理报表生成监理通知单并给报表提交人发送待办
     *
     * @param wrId  监理报表主键
     * @param token 登录token
     * @return 监理通知单主键
     */
    BaseResponse createConSupNoticeByWorkReport(String wrId, String token);

}
