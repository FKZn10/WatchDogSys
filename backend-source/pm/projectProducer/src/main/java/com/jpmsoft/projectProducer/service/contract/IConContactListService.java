package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContactListRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContactList;

public interface IConContactListService extends IService<ConContactList> {

    /**
     * 新增或修改工程联系单
     *
     */
    BaseResponse createOrUpdateContactList(ConContactListRequest request);

    /**
     * ID删除
     *
     * @param clId
     * @return
     */
    BaseResponse deleteContactLisByClId(String clId);

    BaseResponse getContactLisByClId(String clId);

    /**
     * 列表分页查询
     *
     * @param request
     * @return
     */
    BaseResponse getConContactList(ConContactListRequest request, String uId);


    BaseResponse getConContactListNoAuth(ConContactListRequest request);


    ConContactList getConContactListByConId(String conId);

    void processSyn(String uuid);
}
