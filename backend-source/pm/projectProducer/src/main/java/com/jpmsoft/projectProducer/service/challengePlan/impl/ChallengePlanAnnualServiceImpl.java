package com.jpmsoft.projectProducer.service.challengePlan.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.response.challengePlan.ChallengePlanAnnualResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.challengePlan.ChallengePlanAnnualMapper;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanAnnual;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.challengePlan.IChallengePlanAnnualService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Service
public class ChallengePlanAnnualServiceImpl extends ServiceImpl<ChallengePlanAnnualMapper, ChallengePlanAnnual> implements IChallengePlanAnnualService {
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;

    /**
     * 创建或更新年度计划
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateChallengePlanAnnual(ChallengePlanAnnualRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("参数错误");
        }
        if (ValidateUtil.isNotNull(request.getId()) && Objects.equals(request.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            return BaseResponse.fail("该年度计划已提交，不能编辑");
        }
        PreProject preProject = preProjectService.getById(request.getPId());
        if (ValidateUtil.isNull(preProject) ||
                Objects.equals(preProject.getState(), ApprovalStatusEnum.Invalid.getIndex()) ||
                (preProject.getDeleted() && Objects.equals(preProject.getpStatus(), "0"))
        ) {
            return BaseResponse.fail("项目不存在或已作废");
        }
        preProject.setpBuildYear(request.getPBuildYear());
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        preProject.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), preProject));
        preProjectService.updateById(preProject);
        if (ValidateUtil.isNull(request.getId())) {
            long count = count(Wrappers.<ChallengePlanAnnual>lambdaQuery().eq(ChallengePlanAnnual::getPId, request.getPId()));
            if (count > 0) {
                return BaseResponse.fail("该项目已存在年度计划");
            }
        }
        ChallengePlanAnnual challengePlanAnnual = new ChallengePlanAnnual();
        BeanUtils.copyProperties(request, challengePlanAnnual);
        if (ValidateUtil.isNotNull(request.getId())) {
            this.updateById(challengePlanAnnual);
            sysFileService.saveFile(challengePlanAnnual.getId(), request.gettId(), challengePlanAnnual.getState() + "", challengePlanAnnual.getCreateUid(), challengePlanAnnual.getCreateUname()
                    , challengePlanAnnual.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), challengePlanAnnual.getId());
            return BaseResponse.ok(challengePlanAnnual.getId());
        } else {
            challengePlanAnnual.setId(GenerateUUID.getUUID());
            challengePlanAnnual.setCreateTime(new Date());
            boolean save = this.save(challengePlanAnnual);
            if (save) {
                sysFileService.saveFile(challengePlanAnnual.getId(), request.gettId(), challengePlanAnnual.getState() + "", challengePlanAnnual.getCreateUid(), challengePlanAnnual.getCreateUname()
                        , challengePlanAnnual.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                        "create", request.getFileRelationId(), null, request.gettCode(), null);
                sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), challengePlanAnnual.getId());
                return BaseResponse.ok(challengePlanAnnual.getId());
            } else {
                return BaseResponse.fail("创建失败");
            }
        }
    }

    /**
     * 删除年度计划
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteChallengePlanAnnualById(ChallengePlanAnnualRequest request) {
        if (request == null || ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail("参数错误");
        }
        ChallengePlanAnnual planAnnual = this.getById(request.getId());
        if (ValidateUtil.isNull(planAnnual)) {
            return BaseResponse.fail("该年度计划不存在");
        }
        if (Objects.equals(planAnnual.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            return BaseResponse.fail("该年度计划流程已结束，不能删除");
        }
        this.removeById(planAnnual);
        return BaseResponse.ok();
    }

    /**
     * 获取年度计划列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getChallengePlanAnnualList(ChallengePlanAnnualRequest request) {
        if (request == null) {
            return BaseResponse.fail("参数错误");
        }
        if (ValidateUtil.isEmpty(request.getDeclareYear())) {
            request.setDeclareYear(DateFormatUtil.dateToString(new Date(), "yyyy"));
        }
        // 计算偏差
        Integer page = request.getPage();
        Integer offset = (page - 1) * request.getSize();

        List<ChallengePlanAnnualResponse> list = baseMapper.getChallengePlanAnnualList(request, offset);
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        long total = this.count(Wrappers.<ChallengePlanAnnual>lambdaQuery().eq(ChallengePlanAnnual::getDeclareYear, request.getDeclareYear()));
        return BaseResponse.ok(list, Math.toIntExact(total));
    }

    /**
     * 获取年度计划详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getChallengePlanAnnualById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail("参数错误");
        }
        ChallengePlanAnnualResponse response = baseMapper.getChallengePlanAnnualByid(id);
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.fail("该年度计划不存在");
        }
        return BaseResponse.ok(response);
    }
}
