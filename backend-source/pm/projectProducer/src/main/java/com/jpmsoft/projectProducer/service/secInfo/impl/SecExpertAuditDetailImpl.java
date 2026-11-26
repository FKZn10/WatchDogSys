package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertAuditDetailMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAuditDetailVO;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:20
 */
@Service
public class SecExpertAuditDetailImpl extends ServiceImpl<SecExpertAuditDetailMapper, SecExpertAuditDetail> implements ISecExpertAuditDetailService {
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete2Create(List<SecExpertAuditDetailDTO> data, String seaId) {
        deleteBySeaId(seaId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecExpertAuditDetail> entities = new ArrayList<>();
            for (SecExpertAuditDetailDTO d : data) {
                if (ValidateUtil.isNotEmpty(d.getExId())) {
                    SecExpertAuditDetail entity = new SecExpertAuditDetail();
                    BeanUtils.copyProperties(d, entity);
                    entity.setId(GenerateUUID.getUUID());
                    entity.setSeaId(seaId);
                    entities.add(entity);
                }
            }
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySeaId(String seaId) {
        QueryWrapper<SecExpertAuditDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Sea_Id", seaId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<String> getExIdList(String seaId) {
        List<SecExpertAuditDetail> data = selectBySeaId(seaId);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(SecExpertAuditDetail::getExId).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<SecExpertAuditDetailVO> getDetailList(String seaId) {
        List<SecExpertAuditDetail> data = selectBySeaId(seaId);
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<String> exIds = data.stream()
                .map(SecExpertAuditDetail::getExId)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        Map<String, String> exIdNameMap = data.stream()
                .collect(Collectors.toMap(
                        SecExpertAuditDetail::getExId,
                        SecExpertAuditDetail::getKeyExpert,
                        (a, b) -> a
                ));
        List<SecExpertModel> exms = secExpertService.getExpertListByIds(exIds);
        Map<String, SecExpertModel> expertMap = CollectionUtils.isEmpty(exms)
                ? Collections.emptyMap()
                : exms.stream().collect(Collectors.toMap(
                SecExpertModel::getExId,
                e -> e,
                (a, b) -> a
        ));
        return data.stream().map(d -> {
            SecExpertAuditDetailVO vo = new SecExpertAuditDetailVO();
            BeanUtils.copyProperties(d, vo);
            SecExpertModel exm = expertMap.get(d.getExId());
            if (exm != null) {
                BeanUtils.copyProperties(exm, vo);
                // 处理身份证号码
                vo.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(vo.getExIdNumber())));
            }
            if (exIdNameMap.containsKey(d.getExId())) {
                vo.setKeyExpert(exIdNameMap.get(d.getExId()));
            }
            return vo;
        }).collect(Collectors.toList());
    }

    private List<SecExpertAuditDetail> selectBySeaId(String seaId) {
        QueryWrapper<SecExpertAuditDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Sea_Id", seaId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SecExpertAuditDetailVO> getSecExpertAuditDetailListGroup(Integer year) {
        if (ValidateUtil.isNull(year)) {
            year = DateUtil.getTimeYear(new Date());
        }
        return baseMapper.getSecExpertAuditDetailListGroup(year);
    }

    @Override
    public SecExpertAudit getParentByCId(String cId) {
        return baseMapper.getParentByCId(cId);
    }
}
