package com.jpmsoft.projectProducer.service.conBoq;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqDtlChangeDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtlChange;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * 合同工程量清单变更子表业务接口
 */
public interface IConBoqDtlChangeService extends IService<ConBoqDtlChange> {
    /**
     * 按 childNodes 树全量替换变更明细，重新生成节点ID及父子关联。
     *
     * @param dtlList 变更明细根节点集合，为空时清空原有明细
     * @param user    当前操作用户
     * @param boqcId  变更主表ID
     */
    void createConBoqDtlChange(List<ConBoqDtlChangeDto> dtlList, LogUserInfoVo user, String boqcId);

    /**
     * 获取变更主表下的平铺明细，父子关系由 boqdcParentId 表示。
     *
     * @param boqcId 变更主表ID
     * @return 变更明细集合
     */
    List<ConBoqDtlChangeDto> getConBoqDtlChangeList(String boqcId);

    /**
     * 删除指定变更主表下的全部明细。
     *
     * @param id 变更主表ID
     */
    void deleteByBoqcId(String id);

    /**
     * 按新增、变更、删除状态同步正式明细，并转换父节点ID。
     *
     * @param id    变更主表ID
     * @param boqId 正式清单主表ID
     */
    void syncConBoqChangeDtl(String id, String boqId);
}

