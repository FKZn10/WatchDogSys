package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAnnualPlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlan;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanVo;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanDtlService;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:11
 */
@Service
public class OaAnnualPlanImpl extends BaseFileImpl<OaAnnualPlanMapper, OaAnnualPlan, OaAnnualPlanDto>
        implements IOaAnnualPlanService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOaAnnualPlanDtlService oaAnnualPlanDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaAnnualPlan(OaAnnualPlanDto request) {
        if (ValidateUtil.isEmpty(request.getId()) && yearExists(request.getApYear()))
            return BaseResponse.fail(ResMsg.thisYearHavingAnnualPlan);
        OaAnnualPlan entity = new OaAnnualPlan();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        oaAnnualPlanDtlService.createOrUpdateOaAnnualPlanDtl(request.getOaAnnualPlanDtls(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getOaAnnualPlanList(OaAnnualPlanDto request) {
        QueryMap<OaAnnualPlanDto> params = new QueryMap<>(request);
        params.put("apTitle").put("apYear").put("apRemark").put("state").put("createUname");
        IPage<OaAnnualPlanVo> page = new Page<>(request.getPage(), request.getSize());
        List<OaAnnualPlanVo> datas = baseMapper.getOaAnnualPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private boolean yearExists(String year) {
        QueryWrapper<OaAnnualPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("Ap_Year", year);
        wrapper.ne("State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }


    @Override
    public BaseResponse getOaAnnualPlanById(String id) {
        return BaseResponse.ok(baseMapper.getOaAnnualPlanById(id));
    }
}
