package com.jpmsoft.projectProducer.service.conBoq;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoq;

public interface IConBoqService extends IService<ConBoq> {
    /**
     * 首页查询
     */
    BaseResponse getBoqList(Page page,String teId);

    /**
     * 新建
     */
    BaseResponse createBoq(RequestBean requestBean,String teId);

    /**
     * 工程清单查看
     */
    BaseResponse getBoqById(RequestBean requestBean);

    /**
     * 工程清单修改
     */
    BaseResponse updateBop(RequestBean requestBean);

    /**
     * 工程清单审批
     */
    BaseResponse updateBoqState(RequestBean requestBean);


    BaseResponse createOrUpdateConBoq(ConBoqDto request);

    BaseResponse getConBoqList(ConBoqDto request, String uId);

    BaseResponse getConBoqById(String boqId, Boolean queryDtl);
}
