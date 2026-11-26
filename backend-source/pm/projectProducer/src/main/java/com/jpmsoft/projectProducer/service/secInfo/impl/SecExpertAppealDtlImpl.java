package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertAppealDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAppealDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAppealDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAppealDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCheckDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:47
 */
@Service
public class SecExpertAppealDtlImpl extends ServiceImpl<SecExpertAppealDtlMapper, SecExpertAppealDtl> implements ISecExpertAppealDtlService {

    @Resource
    private ISecExpertCheckDtlService secExpertCheckDtlService;


    @Override
    public void createOrUpdateSecExpertAppealDtl(List<SecExpertAppealDtlDTO> data, String seaId) {
        deleteBySeaId(seaId);
        if (!CollectionUtils.isEmpty(data)) {
            List<SecExpertAppealDtl> entities = data.stream().map(d -> {
                SecExpertAppealDtl e = new SecExpertAppealDtl();
                BeanUtils.copyProperties(d, e);
                e.setSeaId(seaId);
                e.setId(GenerateUUID.getUUID(d.getId()));
                return e;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(data)) saveBatch(entities);
        }
    }

    @Override
    public void deleteBySeaId(String seaId) {
        QueryWrapper<SecExpertAppealDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sea_Id", seaId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void approveSecExpertAppealById(String seaId) {
        QueryWrapper<SecExpertAppealDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sea_Id", seaId);
        wrapper.eq("Sead_Is_Agree", Boolean.TRUE);
        List<SecExpertAppealDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            secExpertCheckDtlService.updateResultByIds(data.stream().map(SecExpertAppealDtl::getSecdId).collect(Collectors.toList()), 0);
        }
    }

    @Override
    public BaseResponse updateSecExpertAppealDtl(SecExpertAppealDtlDTO request) {
        SecExpertAppealDtl data = baseMapper.selectById(request.getId());
        BeanUtils.copyProperties(data, request);
        baseMapper.updateById(data);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateSecExpertAppealDtlBath(List<SecExpertAppealDtlDTO> request) {
        List<String> ids = request.stream().map(SecExpertAppealDtlDTO::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) return BaseResponse.fail(ResMsg.notId);
        QueryWrapper<SecExpertAppealDtl> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        List<SecExpertAppealDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            data.forEach(d -> {
                List<SecExpertAppealDtlDTO> reqs = request.stream().filter(n -> ValidateUtil.isNotEmpty(n.getId()) && n.getId().equals(d.getId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(reqs)) {
                    BeanUtils.copyProperties(reqs.get(0), d);
                }
            });
            updateBatchById(data);
        }
        return BaseResponse.ok();
    }
}
