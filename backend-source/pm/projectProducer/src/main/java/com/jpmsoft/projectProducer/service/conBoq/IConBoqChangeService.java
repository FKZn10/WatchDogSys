package com.jpmsoft.projectProducer.service.conBoq;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqChangeDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqChange;

/**
 * 合同工程量清单变更主表业务接口
 */
public interface IConBoqChangeService extends IService<ConBoqChange> {
    /**
     * 新增或修改变更清单主表及子表数据
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    BaseResponse createOrUpdateConBoqChange(ConBoqChangeDto request);

    /**
     * 获取变更清单列表
     *
     * @param request 变更清单数据
     * @return 操作结果
     */
    BaseResponse getConBoqChangeList(ConBoqChangeDto request);

    /**
     * 根据id获取变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    BaseResponse getConBoqChangeById(String id);

    /**
     * 根据id删除变更清单主表及子表数据
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    BaseResponse deleteConBoqChangeById(String id);

    /**
     * 同步变更清单
     *
     * @param id 变更主表ID
     * @return 操作结果
     */
    BaseResponse syncConBoqChange(String id);
}

