package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineLibRiskRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineLibRiskResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineLibRiskMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineLibRisk;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineLibRiskService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineSystemLibService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Service
public class OutlineLibRiskServiceImpl extends ServiceImpl<OutlineLibRiskMapper, OutlineLibRisk> implements IOutlineLibRiskService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;

    @Override
    public BaseResponse createOutlineLibRisk(OutlineLibRiskRequest request, String token) {
        if (riskExist(request.getRiskId(), request.getOslId())) return BaseResponse.fail(ResMsg.recordExist);
        OutlineLibRisk entity = new OutlineLibRisk();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateTime(new Date());
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        Integer sort = baseMapper.selectSort(request.getOslId());
        entity.setOlrSort(sort == null ? 0 : sort + 1);
        baseMapper.insert(entity);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(request.getOslId(), user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public void deleteByOslId(String oslId) {
        baseMapper.deleteByOslId(oslId);
    }

    @Override
    public void deleteByOsmId(String osmId) {
        baseMapper.deleteByOsmId(osmId);
    }

    @Override
    public Boolean riskExist(String riskId, String oslId) {
        LambdaQueryWrapper<OutlineLibRisk> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutlineLibRisk::getRiskId, riskId).eq(OutlineLibRisk::getOslId, oslId);
        return baseMapper.exists(wrapper);
    }

    /**
     * 根据体系库id获取体系库风险
     *
     * @param oslId
     * @return
     */
    @Override
    public List<OutlineLibRiskResponse> getOutlineLibRiskByOslId(String oslId) {
        List<OutlineLibRiskResponse> risk = baseMapper.getOutLineLibRiskByOslIds(Collections.singletonList(oslId));
        if (ValidateUtil.isEmpty(risk)) {
            return Collections.emptyList();
        }
        return risk;
    }

    @Override
    public BaseResponse getOutlineLibRiskById(String id) {
        OutlineLibRisk libRisk = getById(id);
        if (ValidateUtil.isNull(libRisk)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineLibRiskResponse response = new OutlineLibRiskResponse();
        BeanUtils.copyProperties(libRisk, response);
        return BaseResponse.ok(response);
    }

    @Override
    public BaseResponse getOutlineLibRiskPage(OutlineLibRiskRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineLibRiskResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineLibRiskResponse> datas = baseMapper.getOutlineLibRiskPage(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据体系库id获取体系库风险
     *
     * @param oslIds
     * @return
     */
    @Override
    public List<OutlineLibRiskResponse> getAllOutlineLibRiskByOslIds(List<String> oslIds) {
        if (ValidateUtil.isNotEmpty(oslIds)) {
            List<OutlineLibRiskResponse> libRisks = baseMapper.getOutLineLibRiskByOslIds(oslIds);
            if (ValidateUtil.isNotEmpty(libRisks)) {
                return libRisks;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public BaseResponse updateOutlineLibRisk(OutlineLibRiskRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getId() == null || !riskExist(request.getRiskId(), request.getOslId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineLibRisk entity = new OutlineLibRisk();
        BeanUtils.copyProperties(request, entity);
        Integer sort = baseMapper.selectSort(request.getOslId());
        entity.setOlrSort(sort == null ? 0 : sort + 1);
        baseMapper.updateById(entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(request.getOslId(), user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse deleteOutlineLibRiskById(String id, String token) {
        OutlineLibRisk risk = this.getById(id);
        if (ValidateUtil.isNull(risk)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(risk.getOslId(), user);
        return BaseResponse.ok();
    }
}
