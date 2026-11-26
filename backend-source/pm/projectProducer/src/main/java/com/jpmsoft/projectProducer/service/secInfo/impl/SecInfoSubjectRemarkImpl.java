package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoSubjectRemarkMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectRemark;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoSubjectRemarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 招标计划信息 合同外工程及费用 备注表 服务实现类
 */
@Service
public class SecInfoSubjectRemarkImpl extends ServiceImpl<SecInfoSubjectRemarkMapper, SecInfoSubjectRemark> implements ISecInfoSubjectRemarkService {
    /**
     * 新增或修改备注表
     *
     * @param remarkList
     * @param mainId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecInfoSubjectRemark(List<SecInfoSubjectRemarkDTO> remarkList, String mainId) {
        this.remove(Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, mainId));
        if (ValidateUtil.isEmpty(remarkList)) {
            return BaseResponse.ok();
        }
        List<SecInfoSubjectRemark> secInfoSubjectRemarks = BeanUtil.copyToList(remarkList, SecInfoSubjectRemark.class);
        for (SecInfoSubjectRemark secInfoSubjectRemark : secInfoSubjectRemarks) {
            secInfoSubjectRemark.setId(GenerateUUID.getUUID());
            secInfoSubjectRemark.setMainId(mainId);
            secInfoSubjectRemark.setCreateTime(new Date());
        }
        this.saveOrUpdateBatch(secInfoSubjectRemarks);
        return BaseResponse.ok();
    }

    /**
     * 从旧的mainId同步到新的mainId
     *
     * @param oldMainId
     * @param newMainId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approval2UpdateSecInfoSubjectRemark(String oldMainId, String newMainId) {
        this.remove(Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, newMainId));
        List<SecInfoSubjectRemark> remarkList = this.list(Wrappers.<SecInfoSubjectRemark>lambdaQuery().eq(SecInfoSubjectRemark::getMainId, oldMainId));
        if (ValidateUtil.isEmpty(remarkList)) {
            return BaseResponse.ok();
        }
        for (SecInfoSubjectRemark secInfoSubjectRemark : remarkList) {
            secInfoSubjectRemark.setId(GenerateUUID.getUUID());
            secInfoSubjectRemark.setMainId(newMainId);
            secInfoSubjectRemark.setCreateTime(new Date());
        }
        this.saveOrUpdateBatch(remarkList);
        return BaseResponse.ok();
    }
}
