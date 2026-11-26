package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectRiskRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectRiskResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectRiskMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectLib;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectRisk;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectLibService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectRiskService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Service
public class OutlineProjectRiskServiceImpl extends ServiceImpl<OutlineProjectRiskMapper, OutlineProjectRisk> implements IOutlineProjectRiskService {
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据项目管理大纲体系id查询风险指标
     *
     * @param oplId
     * @return
     */
    @Override
    public List<OutlineProjectRiskResponse> getOutlineProjectRiskByOplId(String oplId) {
        if (ValidateUtil.isNull(oplId)) {
            return Collections.emptyList();
        }
        List<OutlineProjectRiskResponse> data = baseMapper.getOutlineProjectRiskByOplIds(Collections.singletonList(oplId));
        if (ValidateUtil.isNotEmpty(data)) {
            return data;
        }
        return Collections.emptyList();
    }

    /**
     * 根据项目管理大纲管理id查询风险指标
     *
     * @param opmId
     * @return
     */
    @Override
    public List<OutlineProjectRiskResponse> getAllOutlineProjectRiskByOpmId(String opmId) {
        List<OutlineProjectLib> projectLibs = outlineProjectLibService.list(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getOpmId, opmId));
        if (ValidateUtil.isNotEmpty(projectLibs)) {
            List<String> oplIds = projectLibs.stream().map(OutlineProjectLib::getId).collect(Collectors.toList());
            List<OutlineProjectRiskResponse> libRisks = baseMapper.getOutlineProjectRiskByOplIds(oplIds);
            if (ValidateUtil.isNotEmpty(libRisks)) {
                return libRisks;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 新增项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse createOutlineProjectRisk(OutlineProjectRiskRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectRisk entity = new OutlineProjectRisk();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateTime(new Date());
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        Integer sort = baseMapper.selectSort(request.getOplId());
        entity.setOprSort(sort == null ? 0 : sort + 1);
        baseMapper.insert(entity);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(request.getOplId(), user);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除项目管理大纲体系管理风险指标
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    public BaseResponse deleteOutlineProjectRiskById(String id, String token) {
        OutlineProjectRisk risk = this.getById(id);
        if (ValidateUtil.isNull(risk)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(risk.getOplId(), user);
        return BaseResponse.ok();
    }

    /**
     * 修改项目管理大纲体系管理风险指标
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse updateOutlineProjectRisk(OutlineProjectRiskRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getId() == null || !riskExist(request.getRiskId(), request.getOplId())) {
            return BaseResponse.ok(ResMsg.infoNotExist);
        }
        OutlineProjectRisk entity = new OutlineProjectRisk();
        BeanUtils.copyProperties(request, entity);
        Integer sort = baseMapper.selectSort(request.getOplId());
        entity.setOprSort(sort == null ? 0 : sort + 1);
        baseMapper.updateById(entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(request.getOplId(), user);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 根据riskId和oplId判断项目风险指标是否存在
     *
     * @param riskId
     * @param oplId
     * @return
     */
    @Override
    public Boolean riskExist(String riskId, String oplId) {
        LambdaQueryWrapper<OutlineProjectRisk> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutlineProjectRisk::getRiskId, riskId).eq(OutlineProjectRisk::getOplId, oplId);
        return baseMapper.exists(wrapper);
    }

    /**
     * 查询项目管理大纲体系管理风险指标
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectRiskById(String id) {
        return BaseResponse.ok(baseMapper.getOutlineProjectRiskById(id));
    }

    /**
     * 分页查询项目管理大纲体系管理风险指标
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectRiskPage(OutlineProjectRiskRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineProjectRisk> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineProjectRiskResponse> datas = baseMapper.getOutlineProjectRiskPage(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public List<OutlineProjectRiskResponse> getProjectRiskLedgerByOpmIds(List<String> opmIds) {
        return baseMapper.getProjectRiskLedgerByOpmIds(opmIds);
    }
}
