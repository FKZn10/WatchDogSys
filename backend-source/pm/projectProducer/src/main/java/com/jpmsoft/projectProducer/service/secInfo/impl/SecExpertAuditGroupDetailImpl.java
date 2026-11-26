package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertAuditGroupDetailMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditGroupDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditGroupDetail;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditGroupDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 集团专家评审
 */
@Service
public class SecExpertAuditGroupDetailImpl extends ServiceImpl<SecExpertAuditGroupDetailMapper, SecExpertAuditGroupDetail> implements ISecExpertAuditGroupDetailService {
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete2Create(List<SecExpertAuditGroupDetailDTO> secExpertAuditDetails, String id) {
        deleteBySeaId(id);
        if (!CollectionUtils.isEmpty(secExpertAuditDetails)) {
            List<SecExpertAuditGroupDetail> entities = new ArrayList<>();
            for (SecExpertAuditGroupDetailDTO d : secExpertAuditDetails) {
                if (ValidateUtil.isNotEmpty(d.getExId())) {
                    SecExpertAuditGroupDetail entity = new SecExpertAuditGroupDetail();
                    BeanUtils.copyProperties(d, entity);
                    entity.setId(GenerateUUID.getUUID());
                    entity.setSeaId(id);
                    entities.add(entity);
                }
            }
            if (!CollectionUtils.isEmpty(entities)) {
                saveBatch(entities);
            }
        }
    }

    @Override
    public void deleteBySeaId(String seaId) {
        LambdaQueryWrapper<SecExpertAuditGroupDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecExpertAuditGroupDetail::getSeaId, seaId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecExpertAuditGroupDetailDTO> getDetailList(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return new ArrayList<>();
        }
        List<SecExpertAuditGroupDetailDTO> data = baseMapper.getDetailList(id);
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        data.forEach(d -> {
            // 处理身份证号码
            if (ValidateUtil.isNotEmpty(d.getExIdNumber())) {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            }
        });
        return data;
    }

    private List<SecExpertAuditGroupDetail> selectBySeaId(String seaId) {
        LambdaQueryWrapper<SecExpertAuditGroupDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecExpertAuditGroupDetail::getSeaId, seaId);
        return baseMapper.selectList(wrapper);
    }
}
