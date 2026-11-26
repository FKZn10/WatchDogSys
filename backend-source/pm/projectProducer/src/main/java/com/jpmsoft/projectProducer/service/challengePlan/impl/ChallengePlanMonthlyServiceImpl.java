package com.jpmsoft.projectProducer.service.challengePlan.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyPageRequest;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyRequest;
import com.jpmsoft.common.model.response.challengePlan.ChallengePlanMonthlyResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.challengePlan.ChallengePlanMonthlyMapper;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanAnnual;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanMonthly;
import com.jpmsoft.projectProducer.pojo.entity.investment.OperationRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.challengePlan.IChallengePlanAnnualService;
import com.jpmsoft.projectProducer.service.challengePlan.IChallengePlanMonthlyService;
import com.jpmsoft.projectProducer.service.investment.IOperationRecordService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Service
public class ChallengePlanMonthlyServiceImpl extends ServiceImpl<ChallengePlanMonthlyMapper, ChallengePlanMonthly> implements IChallengePlanMonthlyService {
    @Resource
    private IChallengePlanAnnualService challengePlanAnnualService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IOperationRecordService operationRecordService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * 创建当前年份的月度计划
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public BaseResponse createChallengePlanMonthly(ChallengePlanAnnualRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("参数错误");
        }
        ChallengePlanAnnual planAnnual = challengePlanAnnualService.getById(request.getId());
        if (!Objects.equals(planAnnual.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            return BaseResponse.fail("当前流程状态不允许此操作");
        }
        Long count = lambdaQuery().eq(ChallengePlanMonthly::getCpaId, request.getId()).count();
        if (count > 0) {
            return BaseResponse.fail("当前年度计划的月度计划已存在");
        }
        List<ChallengePlanMonthly> monthlyList = new ArrayList<>();

        for (int i = 1; i < 13; i++) {
            ChallengePlanMonthly monthly = new ChallengePlanMonthly();
            monthly.setId(GenerateUUID.getUUID());
            monthly.setCreateTime(new Date());
            monthly.setState(1);
            monthly.setCpaId(request.getId());
            monthly.setDeclareYear(request.getDeclareYear());
            monthly.setDeclareMonth(String.valueOf(i));
            monthlyList.add(monthly);
        }
        if (ValidateUtil.isNotEmpty(monthlyList) && monthlyList.size() == 12) {
            saveBatch(monthlyList);
            return BaseResponse.ok();
        }
        return BaseResponse.fail("创建失败");
    }

    /**
     * 更新月度计划
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateChallengePlanMonthly(ChallengePlanMonthlyRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isNull(request.getId())) {
            return BaseResponse.fail("参数错误");
        }
        ChallengePlanMonthly monthly = this.getById(request.getId());
        if (ValidateUtil.isNull(monthly)) {
            return BaseResponse.fail("该月度计划不存在");
        }
        if (Objects.equals(request.getState(), 7) && "01".equalsIgnoreCase(request.getExecutionStatus())) {
            List<ChallengePlanMonthly> monthlies = baseMapper.getLaterChallengePlanMonthly(monthly.getCpaId(), monthly.getDeclareYear(), monthly.getDeclareMonth());
            if (ValidateUtil.isNotEmpty(monthlies)) {
                monthlies.forEach(item -> BeanUtils.copyProperties(request, item, "id", "cpaId", "declareYear", "declareMonth"));
                this.updateBatchById(monthlies);
            }
        } else {
            BeanUtils.copyProperties(request, monthly, "id", "cpaId", "declareYear", "declareMonth");
            this.updateById(monthly);
        }
        sysFileAnnexService.refreshRelationFile(Collections.singletonList(request.getFileRelationId()),
                Collections.singletonList(request.getFileIds()), monthly.getId());
        return BaseResponse.ok(monthly.getId());
    }

    /**
     * 获取月度计划列表
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getChallengePlanMonthlyList(ChallengePlanMonthlyPageRequest request, String uId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("参数错误");
        }
        String authSql = sysUserService.getAuthSql("challenge_plan_monthly", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        // 将鉴权的mn.P_ID改成p.P_ID
        authSql = authSql.replace("mn.P_ID", "p.P_ID");
        // 计算偏差
        Integer page = request.getPage();
        Integer offset = (page - 1) * request.getSize();

        List<ChallengePlanMonthlyResponse> data = baseMapper.getChallengePlanMonthlyList(request, offset, authSql);
        if (CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        Integer total = baseMapper.getChallengePlanMonthlyCount(request, authSql);
        return BaseResponse.ok(data, total);
    }

    /**
     * 获取月度计划信息
     *
     * @param request
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getChallengePlanMonthlyClassInfo(ChallengePlanMonthlyPageRequest request, String uId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("参数错误");
        }
        String authSql = sysUserService.getAuthSql("challenge_plan_monthly", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        // 将鉴权的mn.P_ID改成p.P_ID
        authSql = authSql.replace("mn.P_ID", "p.P_ID");
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(ChallengePlanMonthlyPageRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            List<Map<String, Object>> rawResult = baseMapper.getAllValues(selectClause, request, authSql);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(rawResult, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getChallengePlanMonthlyClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    /**
     * 根据id获取月度计划信息
     *
     * @param id
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getChallengePlanMonthlyById(String id, String uId) {
        String authSql = sysUserService.getAuthSql("challenge_plan_monthly", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        // 将鉴权的mn.P_ID改成p.P_ID
        authSql = authSql.replace("mn.P_ID", "p.P_ID");
        ChallengePlanMonthlyResponse response = baseMapper.getChallengePlanMonthlyById(id, authSql);
        return BaseResponse.ok(response);
    }

    /**
     * 批量更新状态
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchUpdateStatus(ChallengePlanMonthlyRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail("参数错误");
        }
        List<ChallengePlanMonthly> updateEntityList = new ArrayList<>();
        List<ChallengePlanMonthly> challengePlanMonthlies = listByIds(request.getIds());
        if (ValidateUtil.isEmpty(challengePlanMonthlies)) {
            return BaseResponse.fail("月度计划不存在");
        }
        LogUserInfoVo uid = redisUtils.getUserInfoByToken(token);
        List<OperationRecord> operationRecords = new ArrayList<>();
        challengePlanMonthlies.forEach(monthly -> {
            operationRecords.add(operationRecordService.entity(monthly.getId(), uid, "ChallengePlanMonthly", monthly.getState() + "", request.getState() + "", request.getOrRemarks()));
            monthly.setState(request.getState());
            monthly.setStateTime(new Date());
            updateEntityList.add(monthly);
            if (Objects.equals(request.getState(), 7) && "01".equalsIgnoreCase(monthly.getExecutionStatus())) {
                List<ChallengePlanMonthly> monthlies = baseMapper.getLaterChallengePlanMonthly(monthly.getCpaId(), monthly.getDeclareYear(), monthly.getDeclareMonth());
                if (ValidateUtil.isNotEmpty(monthlies)) {
                    monthlies.forEach(item -> {
                        operationRecords.add(operationRecordService.entity(item.getId(), uid, "ChallengePlanMonthly", item.getState() + "", monthly.getState() + "", request.getOrRemarks()));
                        BeanUtils.copyProperties(monthly, item, "id", "cpaId", "declareYear", "declareMonth");
                    });
                    updateEntityList.addAll(monthlies);
                }
            }
        });
        if (!CollectionUtils.isEmpty(updateEntityList)) this.updateBatchById(updateEntityList);
        if (!CollectionUtils.isEmpty(operationRecords)) operationRecordService.saveBatch(operationRecords);
        if (operationRecords.size() == 1) {
            return BaseResponse.ok(operationRecords.get(0).getId());
        }
        return BaseResponse.ok();
    }
}
