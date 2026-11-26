package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.oa.OaAnnualPlanDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanDtl;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanChangeDtlVo;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanDtlVo;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:10
 */
@Service
public class OaAnnualPlanDtlImpl extends ServiceImpl<OaAnnualPlanDtlMapper, OaAnnualPlanDtl>
        implements IOaAnnualPlanDtlService {

    @Override
    public void createOrUpdateOaAnnualPlanDtl(List<OaAnnualPlanDtlDto> datas, String apId) {
        delOaAnnualPlanDtlByApId(apId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<OaAnnualPlanDtl> entitys = datas.stream()
                    .filter(n -> ValidateUtil.isNotEmpty(n.getPId())).map(data -> {
                        OaAnnualPlanDtl entity = new OaAnnualPlanDtl();
                        BeanUtils.copyProperties(data, entity);
                        entity.setApId(apId);
                        entity.setId(GenerateUUID.getUUID(data.getId()));
                        return entity;
                    }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entitys))
                saveBatch(entitys);
        }
    }

    @Override
    public void delOaAnnualPlanDtlByApId(String apId) {
        QueryWrapper<OaAnnualPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ap_Id", apId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void approval2RefershAnnualPlan(List<OaAnnualPlanChangeDtlVo> dtls, String apId) {
        if (!CollectionUtils.isEmpty(dtls)) {
            List<String> delPids = dtls.stream().filter(n -> ValidateUtil.isNotEmpty(n.getApdType())
                    && n.getApdType().equals("2")).map(OaAnnualPlanChangeDtlVo::getpId).collect(Collectors.toList());
            delOaAnnualPlanDtlByPids(delPids, apId);

            Integer sort = baseMapper.getCurrentSort(apId);
            if (sort == null) sort = 0;
            List<OaAnnualPlanChangeDtlVo> addDtls = dtls.stream().filter(n -> ValidateUtil.isNotEmpty(n.getApdType())
                    && n.getApdType().equals("1")).collect(Collectors.toList());
            List<OaAnnualPlanDtl> adds = new ArrayList<>();
            for (OaAnnualPlanChangeDtlVo dtl : addDtls) {
                if (ValidateUtil.isNotEmpty(dtl.getpId())) {
                    OaAnnualPlanDtl entity = new OaAnnualPlanDtl();
                    entity.setId(GenerateUUID.getUUID());
                    entity.setPId(dtl.getpId());
                    entity.setApdSort(sort += 1);
                    entity.setApId(apId);
                    entity.setApdCheckType(dtl.getApdCheckType());
                    adds.add(entity);
                }
            }
            if (!CollectionUtils.isEmpty(adds)) saveBatch(adds);
        }
    }

    @Override
    public BaseResponse getOaAnnualPlanDtlList(OaAnnualPlanDto request) {
        QueryMap<OaAnnualPlanDto> query = new QueryMap<>(request);
        query.put("apYear").put("state").put("apdCheckType");
//        IPage<OaAnnualPlanDtlVo> page = new Page<>(request.getPage(), request.getSize());
//        List<OaAnnualPlanDtlVo> datas = baseMapper.getOaAnnualPlanDtlList(page, query.getQuery());
        List<OaAnnualPlanDtlVo> datas = baseMapper.getOaAnnualPlanDtlList(query.getQuery());
        return BaseResponse.ok(datas);
    }

    @Override
    public List<OaAnnualPlanDtl> getOaAnnualPlanDtlByYearAndPids(List<String> pIds, Integer year) {
        return baseMapper. getOaAnnualPlanDtlByYearAndPids(pIds,year) ;
    }


    private void delOaAnnualPlanDtlByPids(List<String> pIds, String apId) {
        if (CollectionUtils.isEmpty(pIds)) return;
        QueryWrapper<OaAnnualPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ap_Id", apId);
        wrapper.in("P_Id", pIds);
        baseMapper.delete(wrapper);
    }
}
