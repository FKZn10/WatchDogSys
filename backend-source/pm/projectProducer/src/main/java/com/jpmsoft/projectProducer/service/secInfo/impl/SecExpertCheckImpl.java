package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertCheckModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertCheckMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertCheckDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheck;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheckDetail;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertCheckVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:33
 */
@Slf4j
@Service
public class SecExpertCheckImpl extends FileImpl<SecExpertCheckMapper, SecExpertCheck, SecExpertCheckDTO> implements ISecExpertCheckService {

    @Resource
    private ISecExpertCheckDtlService secExpertCheckDtlService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private BpmFeign bpmFeign;
    // 专家抽取表
    @Resource
    private ISecSelectionRecordService secSelectionRecordService;
    // 专家选取记录详情表 专家信息
    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private ISecInfoService secInfoService;

    @Resource
    private ISecExpertCheckDetailService secExpertCheckDetailService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISecExpertPauseService secExpertPauseService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecExpertCheck(SecExpertCheckDTO request) {
        SecExpertCheck entity = new SecExpertCheck();
        createOrUpdateFileEntity(entity, request);

        List<SecSelectionRecordDtlModel> secSelectionRecordDtlModels = request.getSecSelectionRecordDtls();

        // 修复逻辑错误：原代码中使用 isEmpty 判断但执行遍历操作
        if (ValidateUtil.isNotEmpty(secSelectionRecordDtlModels)) {
            // 批量处理：收集所有需要更新的 SecExpertCheckDetail 对象
            List<SecExpertCheckDetail> detailsToUpdate = new ArrayList<>();

            // 收集所有 scdId 用于批量查询
            List<String> scdIds = secSelectionRecordDtlModels.stream()
                    .filter(model -> ValidateUtil.isNotEmpty(model.getScdId()))
                    .map(SecSelectionRecordDtlModel::getScdId)
                    .collect(Collectors.toList());

            if (!scdIds.isEmpty()) {
                // 批量查询所有相关的 SecExpertCheckDetail 对象
                List<SecExpertCheckDetail> existingDetails = secExpertCheckDetailService.listByIds(scdIds);

                // 创建 scdId 到 detail 的映射，提高查找效率
                Map<String, SecExpertCheckDetail> detailMap = existingDetails.stream()
                        .collect(Collectors.toMap(SecExpertCheckDetail::getId, detail -> detail));

                // 匹配并更新需要修改的 detail 对象
                for (SecSelectionRecordDtlModel model : secSelectionRecordDtlModels) {
                    if (ValidateUtil.isNotEmpty(model.getScdId())) {

                        SecExpertCheckDetail detail = detailMap.get(model.getScdId());
                        if (detail != null) {
                            detail.setEcId(entity.getId());
                            detailsToUpdate.add(detail);
                        }
                    }
                }

                // 批量更新所有需要更新的 detail 对象
                if (!detailsToUpdate.isEmpty()) {
                    secExpertCheckDetailService.updateBatchById(detailsToUpdate);
                }
            }
        }

        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertCheckList(SecExpertCheckDTO request, LogUserInfoVo user) {
        String authSql = sysUserService.getAuthSql("Sec_Expert_Check", user.getId(), "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<SecExpertCheckDTO> queryMap = new QueryMap<>(request);
        queryMap.put("authSql", authSql).put("state").put("createUname")
                .put("exName").put("secName").put("pName").put("pCode").put("srId");
        IPage<SecExpertCheckVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertCheckVO> data = baseMapper.getSecExpertCheckList(page, queryMap.getQuery());
        data.forEach(d->{
            d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
        });
        return BaseResponse.ok(data, Integer.parseInt(Long.valueOf(page.getTotal()).toString()));
    }

    @Override
    public BaseResponse getSecExpertCheckById(String id) {
        SecExpertCheckModel data = baseMapper.getSecExpertCheckById(id);
        List<SecSelectionRecordDtlModel> selectionRecordDtl = baseMapper.getSecdById(id);
        if (ValidateUtil.isNotNull(selectionRecordDtl)) {
            data.setSecSelectionRecordDtls(selectionRecordDtl);
            if (ValidateUtil.isNotEmpty(data.getSecSelectionRecordDtls())) {
                data.setSecSelectionRecordDtls(data.getSecSelectionRecordDtls().stream().map(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                    return d;
                }).collect(Collectors.toList()));
            }
        }

/*        if (ValidateUtil.isNotEmpty(data.getSecSelectionRecordDtls())) {
            data.setSecSelectionRecordDtls(data.getSecSelectionRecordDtls().stream().map(d -> {
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                return d;
            }).collect(Collectors.toList()));
        }*/
/*        if (ValidateUtil.isNotEmpty(data.getExIdNumber())) {
            data.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getExIdNumber())));
        }*/

        return BaseResponse.ok(data);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getSecEXpertCheckSendToDo() {
        // 一标一评文档编码
        String tCode = "SecExpertCheck";

        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();

        // 获取前一天的日期
        LocalDate yesterday = now.toLocalDate().minusDays(1);

        // 设置时间为 00:00:00 和 23:59:59
        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
        LocalDateTime endOfYesterday = yesterday.atTime(LocalTime.MAX);

        LambdaQueryWrapper<SecSelectionRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecSelectionRecord::getState, 7)
                .and(wrapper -> wrapper.ne(SecSelectionRecord::getSrSend, "01")
                        .or()
                        .isNull(SecSelectionRecord::getSrSend))
                .between(SecSelectionRecord::getSrCreateTime, startOfYesterday, endOfYesterday);

        List<SecSelectionRecord> data = secSelectionRecordService.list(queryWrapper);

        if (ValidateUtil.isEmpty(data)) {
            return BaseResponse.ok("没有代办");
        }

        List<BatchhandlingTaskListDto> taskListDtos = new ArrayList<>();

        FileTemplateTree fileTemplateByCode = userProducerFeign.getFileTemplateByCode(tCode);
        if (fileTemplateByCode == null) {
            return BaseResponse.fail("找不到文档模板");
        }
        for (SecSelectionRecord entity : data) {
            if (ValidateUtil.isEmpty(entity.getSrCreateUid())) {
                log.error("未找到专家抽取的创建人id，无法生成代办 + srId" + entity.getSrId());
                continue;
            }
            SecExpertCheck secExpertCheck = new SecExpertCheck();
            secExpertCheck.setId(GenerateUUID.getUUID());
            secExpertCheck.setSrId(entity.getSrId());
            secExpertCheck.setSecId(entity.getSecId());
            secExpertCheck.setPId(entity.getPId());
            secExpertCheck.setExamineUid(entity.getSrCreateUid());
            secExpertCheck.setSeMode(entity.getSrMode());
            secExpertCheck.setEcAssessTime(entity.getSrBidTime());
            secExpertCheck.setEcTime(entity.getSrCreateTime());
            secExpertCheck.setCreateTime(new Date());
            secExpertCheck.setCreateUid(entity.getSrCreateUid());

            PreProject preProject = preProjectService.getById(entity.getPId());
            SecInfo secInfo = secInfoService.getById(entity.getSecId());
            SysUser sysUser = sysUserService.getById(entity.getSrCreateUid());
            if (preProject != null) {
                secExpertCheck.setPName(preProject.getpName());
            } else {
                secExpertCheck.setPName("");
            }
            if (secInfo != null) {
                secExpertCheck.setSecName(secInfo.getSecName());
            } else {
                secExpertCheck.setSecName("");
            }
            if (sysUser != null) {
                secExpertCheck.setExamineUname(sysUser.getUName());
                secExpertCheck.setCreateUname(sysUser.getUName());
            } else {
                secExpertCheck.setExamineUname("");
            }
            sysFileService.saveFile(secExpertCheck.getId(), fileTemplateByCode.gettId(), secExpertCheck.getState() + "", secExpertCheck.getCreateUid(), secExpertCheck.getCreateUname()
                    , secExpertCheck.getCreateTime(), secExpertCheck.getPId(),fileTemplateByCode.gettName(), tCode, "create",
                    "", null, tCode, null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(""), secExpertCheck.getId());
            save(secExpertCheck);

            BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
            if (ValidateUtil.isEmpty(entity.getSrCreateUid())) {
                throw new JPMException(ResMsg.actError + " 接收人ID为空");
            }

            tkl.setFileId(secExpertCheck.getId())
                    .setUId(secExpertCheck.getExamineUid())
                    .setProcessed(secExpertCheck.getExamineUid())
                    .setOperator(secExpertCheck.getExamineUid());
            taskListDtos.add(tkl);
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                BatchhandlingTaskDto task = new BatchhandlingTaskDto();
                task.setCurrentNode("createNode");
                task.setOnlineConditions(String.valueOf(true));
                task.settCode(tCode);
//                task.setTeId(ValidateUtil.isEmpty((String) null) ? "All" : user.getTeId());
                task.setTeId("All");
                task.setSendList(taskListDtos);

                BaseResponse taskRes = bpmFeign.batchSendTask(task,null);
                if (taskRes == null || !taskRes.getSuccess()) {
                    throw new JPMException(ResMsg.taskCreateFail);
                }

                // 发送成功后更新 SecSelectionRecord 的 srSend 字段为 "01"
                List<String> srIds = data.stream().map(SecSelectionRecord::getSrId).collect(Collectors.toList());
                LambdaUpdateWrapper<SecSelectionRecord> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.in(SecSelectionRecord::getSrId, srIds)
                        .set(SecSelectionRecord::getSrSend, "01");
                secSelectionRecordService.update(updateWrapper);
            }
        });
        return BaseResponse.ok("代办发送成功！");
    }

    @Override
    public BaseResponse getSecExpertCheckBySrId(String srId, String uid) {
        SecExpertCheckModel data = baseMapper.getSecExpertCheckBySrId(srId);
        List<SecSelectionRecordDtlModel> selectionRecordDtl = baseMapper.getSelectionRecordDtl(srId);
        if (ValidateUtil.isNotNull(selectionRecordDtl)) {
            data.setSecSelectionRecordDtls(selectionRecordDtl);
            if (ValidateUtil.isNotEmpty(data.getSecSelectionRecordDtls())) {
                data.setSecSelectionRecordDtls(data.getSecSelectionRecordDtls().stream().map(d -> {
                    d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
                    return d;
                }).collect(Collectors.toList()));
            }
        }

        return BaseResponse.ok(data,0);
    }

    @Override
    public BaseResponse getSecExpertCheckTaskTimeout(SecExpertCheckDTO request) {
        QueryMap<SecExpertCheckDTO> queryMap = new QueryMap<>(request);
        queryMap.put("tCode").put("taskName").put("actName");
        List<SecExpertCheckDTO> datas = baseMapper.getSecExpertCheckTaskTimeout(queryMap.getQuery());
        List<SecExpertCheckDTO> data = new ArrayList<>(datas.stream()
                .filter(Objects::nonNull)
                .filter(dto -> dto.getSrId() != null)
                .collect(Collectors.toMap(
                        SecExpertCheckDTO::getSrId,
                        dto -> dto,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                )).values());

        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse syncSecExpertPause(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertCheck check = this.getById(id);
        if (ValidateUtil.isNull(check)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(check.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        List<SecSelectionRecordDtlModel> selectionRecordDtl = baseMapper.getSecdById(id);
        if (ValidateUtil.isEmpty(selectionRecordDtl)) {
            return BaseResponse.ok();
        }
        // 处理专家的评价
        // 专家id
        // List<String> exIds = selectionRecordDtl.stream()
        //         .map(SecSelectionRecordDtlModel::getExId)
        //         .filter(Objects::nonNull)
        //         .collect(Collectors.toList());
        // 查询专家当年的评价
        // List<SecSelectionRecordDtl> list = secSelectionRecordDtlService.getListByExIds(exIds);
        // 获取扣分和时间的对应配置
        String config = sysConfigService.getConfigByCode("secExpertCheckScore");
        if (ValidateUtil.isEmpty(config)) {
            return BaseResponse.fail(ResMsg.confError);
        }
        // 转换配置为 Map
        Map<String, Integer> scoreConfig;
        try {
            scoreConfig = JSONUtil.toBean(config, Map.class);
        } catch (Exception e) {
            log.error("配置转换错误", e);
            return BaseResponse.fail(ResMsg.confError);
        }
        // 判断专家的扣分对应的时间
        Map<String, Integer> exIdToDays = new HashMap<>();
        for (SecSelectionRecordDtlModel model : selectionRecordDtl) {
            Integer days = matchDaysByScoreFloor(scoreConfig, model.getTotalDeduction());
            if (days != null) {
                exIdToDays.put(model.getExId(), days);
            }
        }
        // 交给专家暂停申请处理
        secExpertPauseService.handleExpertPause(exIdToDays);
        return BaseResponse.ok();
    }

    private Integer matchDaysByScoreFloor(Map<String, Integer> scoreConfig, BigDecimal expertScore) {
        if (scoreConfig == null || scoreConfig.isEmpty() || expertScore == null) {
            return null;
        }

        Integer bestDays = null;
        BigDecimal bestScore = null;

        for (Map.Entry<String, Integer> entry : scoreConfig.entrySet()) {
            Integer days = entry.getValue();
            if (days == null) {
                continue;
            }

            BigDecimal cfgScore;
            try {
                cfgScore = new BigDecimal(entry.getKey());
            } catch (Exception e) {
                continue;
            }

            if (cfgScore.compareTo(expertScore) <= 0
                    && (bestScore == null || cfgScore.compareTo(bestScore) > 0)) {
                bestScore = cfgScore;
                bestDays = days;
            }
        }

        return bestDays;
    }
}
