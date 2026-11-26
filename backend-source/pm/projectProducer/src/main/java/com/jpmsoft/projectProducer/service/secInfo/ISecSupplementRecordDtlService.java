package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupplementRecordDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupplementRecordDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:36
 */
public interface ISecSupplementRecordDtlService extends IService<SecSupplementRecordDtl> {

    void createOrUpdate(List<SecSupplementRecordDtlDTO> data, String sesId);

    void deleteBySesId(String sesId);

    /**
     * 查询补抽的专家
     * Srd_Mark='01'
     * @param sesId
     * @return
     */
    List<SecSupplementRecordDtl> getBySesId(String sesId);
}
