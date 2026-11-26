package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAnnualPlanChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanChangeDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanChange;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaAnnualPlanChangeVo;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanChangeDtlService;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanChangeService;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanDtlService;
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
public class OaAnnualPlanChangeImpl extends BaseFileImpl<OaAnnualPlanChangeMapper, OaAnnualPlanChange, OaAnnualPlanChangeDto>
        implements IOaAnnualPlanChangeService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOaAnnualPlanChangeDtlService oaAnnualPlanChangeDtlService;
    @Resource
    private IOaAnnualPlanDtlService oaAnnualPlanDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaAnnualPlanChange(OaAnnualPlanChangeDto request) {
        OaAnnualPlanChange entity = new OaAnnualPlanChange();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        oaAnnualPlanChangeDtlService.createOrUpdateOaAnnualPlanChangeDtl(request.getOaAnnualPlanChangeDtls(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getOaAnnualPlanChangeList(OaAnnualPlanChangeDto request) {
        QueryMap<OaAnnualPlanChangeDto> params = new QueryMap<>(request);
        params.put("apId").put("apTitle").put("apYear")
              .put("apRemark").put("state").put("createUname");
        IPage<OaAnnualPlanChangeVo> page = new Page<>(request.getPage(), request.getSize());
        List<OaAnnualPlanChangeVo> datas = baseMapper.getOaAnnualPlanChangeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getOaAnnualPlanChangeById(String id) {
        return BaseResponse.ok(baseMapper.getOaAnnualPlanChangeById(id));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approval2SyncOaAnnualPlan(String id) {
        OaAnnualPlanChangeVo data = baseMapper.getOaAnnualPlanChangeById(id);
        if (ValidateUtil.isNull(data)) return BaseResponse.fail(ResMsg.infoNotExist);
        if (data.getState() != ApprovalStatusEnum.Already_Over.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        oaAnnualPlanDtlService.approval2RefershAnnualPlan(data.getOaAnnualPlanChangeDtls(), data.getApId());
        return BaseResponse.ok();
    }


}
