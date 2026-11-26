package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSelectionRecordDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheckDtl;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertCheckDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordDtlService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SecSelectionRecordDtlImpl extends ServiceImpl<SecSelectionRecordDtlMapper, SecSelectionRecordDtl> implements ISecSelectionRecordDtlService {

    @Resource
    private ISecExpertCheckDtlService secExpertCheckDtlService;


    @Override
    public void createOrUpdateSecSelectionRecord(List<SecSelectionRecordDtlModel> datas, String srId) {
        deleteAllBySrId(srId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecSelectionRecordDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecSelectionRecordDtl entity = new SecSelectionRecordDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setSrdId(ValidateUtil.isEmpty(entity.getSrdId()) ? GenerateUUID.getUUID() : entity.getSrdId());
                entity.setSrId(srId);
                entity.setSrdTime(ValidateUtil.isNull(data.getSrdTime()) ? new Date() : data.getSrdTime());
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }


    @Override
    public void createOrUpdateSecSelectionRecordDtl(List<SecSelectionRecordDtlModel> secSelectionRecordDtls, String ecId, String srId) {
        deleteAllByecId(ecId);
        if (ValidateUtil.isNotEmpty(secSelectionRecordDtls)) {
            List<SecSelectionRecordDtl> entitys = new ArrayList<>();
            List<SecExpertCheckDtl> secExpertCheckDtls = new ArrayList<>();
            secSelectionRecordDtls.forEach(data -> {

                SecSelectionRecordDtl entity = new SecSelectionRecordDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setEcId(ecId);
                entity.setSrdId(ValidateUtil.isEmpty(entity.getSrdId()) ? GenerateUUID.getUUID() : entity.getSrdId());
                entity.setSrdTime(ValidateUtil.isNull(data.getSrdTime()) ? new Date() : data.getSrdTime());
                entity.setSrId(srId);
                entitys.add(entity);
                if (ValidateUtil.isNotEmpty(data.getSecExpertCheckDtls())) {
                    data.getSecExpertCheckDtls().forEach(secExpertCheckDtlDTO -> {
                        SecExpertCheckDtl secExpertCheckDtl = new SecExpertCheckDtl();
                        BeanUtils.copyProperties(secExpertCheckDtlDTO, secExpertCheckDtl);
                        secExpertCheckDtl.setId(ValidateUtil.isEmpty(secExpertCheckDtl.getId()) ? GenerateUUID.getUUID() : secExpertCheckDtl.getId());
                        secExpertCheckDtl.setEcId(ecId);
                        secExpertCheckDtl.setSrdId(entity.getSrdId());
                        secExpertCheckDtls.add(secExpertCheckDtl);
                    });
                }
            });
            if (entitys.size() > 0) {
                saveBatch(entitys);
            }
            if (secExpertCheckDtls.size() > 0) {
                secExpertCheckDtlService.saveBatch(secExpertCheckDtls);
            }
        }

    }

    private void deleteAllByecId(String ecId) {
        QueryWrapper<SecSelectionRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("ec_id", ecId);
        baseMapper.delete(wrapper);
    }


    @Override
    public void deleteAllBySrId(String srId) {
        QueryWrapper<SecSelectionRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Id", srId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getSelectionRecordDtlCount(String exId) {
        Integer count = baseMapper.getSelectionRecordDtlCount(exId);
        return BaseResponse.ok(count == null ? 1 : count + 1);
    }

    @Override
    public BaseResponse updateSecSelectionRecordDtl(SecSelectionRecordDtlModel request) {
        if (ValidateUtil.isEmpty(request.getSrdId())) return BaseResponse.fail(ResMsg.notId);
        SecSelectionRecordDtl data=baseMapper.selectById(request.getSrdId());
        data.setSrdAttend(request.getSrdAttend());
        data.setSrdAvoid(request.getSrdAvoid());
        data.setSrdConfirmTime(request.getSrdConfirmTime());
        baseMapper.updateById(data);
        return BaseResponse.ok();
    }

    @Override
    public Integer getExpertCount(String exId) {
        return baseMapper.getExpertCount(exId);
    }

    @Override
    public boolean expertSelectionWithTime(String exId, Date time) {
        if (baseMapper.expertSelectionWithTime(exId, DateUtil.Date2String(time, "yyyy-MM-dd 00:00:00")) > 0)
            return true;
        return false;
    }

    @Override
    public void updateAttend(String exId, String srId, Integer attend) {
        baseMapper.updateAttend(exId, srId, attend);
    }

    @Override
    public void updateVacation(String exId, String srId, Integer attend, Integer vacation) {
        baseMapper.updateVacation(exId, srId, attend, vacation);
    }

    @Override
    public List<SecSelectionRecordDtl> getBySrId(String srId) {
        QueryWrapper<SecSelectionRecordDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Id", srId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SecSelectionRecordDtlModel> getSelectionRecordDtlList(SecSelectionRecordDtlDto request) {
        QueryMap<SecSelectionRecordDtlDto> params = new QueryMap<>(request);
        params.put("exId").put("srId").put("secId").put("sameBidTime")
                .put("notSecIdList").put("srdAttend");
        List<SecSelectionRecordDtlModel> selectionRecordDtlList = baseMapper.getSelectionRecordDtlList(params.getQuery());
        return selectionRecordDtlList;
    }

    @Override
    public BaseResponse getSelectionRecordDtlListByExId(SecSelectionRecordDtlDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<SecSelectionRecordDtlModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<SecSelectionRecordDtlDto> params = new QueryMap<>(request);
        params.put("exId").put("srId").put("secId");
        List<SecSelectionRecordDtlModel> datas = baseMapper.getSelectionRecordDtlListByExId(page, params.getQuery());
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public List<SecSelectionRecordDtl> getListByExIds(List<String> exIds) {
        if (ValidateUtil.isNotEmpty(exIds)) {
            return baseMapper.getListByExIds(exIds);
        }
        return Collections.emptyList();
    }
}
