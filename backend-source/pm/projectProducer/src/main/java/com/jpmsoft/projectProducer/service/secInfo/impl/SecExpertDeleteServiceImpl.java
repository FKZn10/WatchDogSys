package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertDeleteModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.other.TaskMapper;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertDeleteMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.other.FlowActivity;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.Task;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAudit;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertDelete;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAuditService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertDeleteService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 专家删除申请
 */
@Service
public class SecExpertDeleteServiceImpl extends ServiceImpl<SecExpertDeleteMapper, SecExpertDelete> implements ISecExpertDeleteService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private ISecExpertAuditService secExpertAuditService;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;
    @Resource
    private BpmFeign bpmFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecExpertDelete(SecExpertDeleteModel request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (secExpertAuditService.count(Wrappers.<SecExpertAudit>lambdaQuery()
                .eq(SecExpertAudit::getYear, request.getYear())
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                .eq(SecExpertAudit::getSeaCid, ValidateUtil.isEmpty(request.getCId()) ? request.getcId() : request.getCId())
        ) > 0) {
            return BaseResponse.fail("企业管理员已提交审批，无法修改或新增！");
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        SecExpertDelete entity;
        if (ValidateUtil.isEmpty(request.getId())) {
            // 创建
            // 去重验证
            if (this.count(Wrappers.<SecExpertDelete>lambdaQuery().eq(SecExpertDelete::getExId, request.getExId())
                    .ne(SecExpertDelete::getExState, ApprovalStatusEnum.Invalid.getIndex())
            ) > 0) {
                return BaseResponse.fail("存在变更记录，请勿重复提交！");
            }
            entity = new SecExpertDelete();
            BeanUtils.copyProperties(request, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setExCreateTime(new Date());
            entity.setExCreateUid(user.getId());
            entity.setExCreateUname(user.getName());
            sysFileService.saveFile(entity.getId(), request.gettId(), request.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), entity.getId());
            if (ValidateUtil.isNotEmpty(entity.getExIdNumber())) {
                entity.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
            }
            secExpertService.updateChangeStatus(entity.getExId(), "04");
            this.save(entity);
        } else {
            // 更新
            entity = this.getById(request.getId());
            if (ValidateUtil.isNull(entity)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            BeanUtils.copyProperties(request, entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), request.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExDegree(), request.getExPerformFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExAvoidFile(), request.getExOtherFile(), request.getFileRelationId()), Arrays.asList(request.getExCardFileIds(), request.getExDegreeIds(), request.getExPerformFileIds(), request.getExTitleFileIds(), request.getExPromiseFileIds(), request.getExAvoidFileIds(), request.getExOtherFileIds(), request.getFileIds()), entity.getId());
            if (ValidateUtil.isNotEmpty(entity.getExIdNumber())) {
                entity.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
            }
            this.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertDeleteList(SecExpertDeleteModel request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<SecExpertDeleteModel> queryMap = new QueryMap<>(request);
        Page<SecExpertDeleteModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertDeleteModel> datas = baseMapper.getSecExpertDeleteList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getSecExpertDeleteById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertDeleteModel model = baseMapper.getSecExpertDeleteById(id);
        if (ValidateUtil.isNull(model)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isNotEmpty(model.getExIdNumber())) {
            model.setExPlaintextNum(apiUtilsService.SelfSm2Decrypt(model.getExIdNumber()));
        }
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse syncSecExpertDeleteData(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertDelete secExpertDelete = this.getById(id);
        if (ValidateUtil.isNull(secExpertDelete)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(secExpertDelete.getExState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        SecExpert expert = secExpertService.getById(secExpertDelete.getExId());
        if (ValidateUtil.isNull(expert)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // expert.setExState(ApprovalStatusEnum.Invalid.getIndex());
        expert.setExStatus(0);
        secExpertService.updateById(expert);
        return BaseResponse.ok(expert.getExId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse invalidProcessData(List<String> exIds) {
        if (ValidateUtil.isEmpty(exIds)) {
            return BaseResponse.ok();
        }

        List<String> cleanExIds = exIds.stream()
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(cleanExIds)) {
            return BaseResponse.ok();
        }

        List<String> invalidIds = this.list(Wrappers.<SecExpertDelete>lambdaQuery()
                        .select(SecExpertDelete::getId)
                        .in(SecExpertDelete::getExId, cleanExIds)
                        .ne(SecExpertDelete::getExState, ApprovalStatusEnum.Already_Over.getIndex())
                        .ne(SecExpertDelete::getExState, ApprovalStatusEnum.Invalid.getIndex()))
                .stream()
                .map(SecExpertDelete::getId)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(invalidIds)) {
            return BaseResponse.ok();
        }

        this.update(Wrappers.<SecExpertDelete>lambdaUpdate()
                .set(SecExpertDelete::getExState, ApprovalStatusEnum.Invalid.getIndex())
                .in(SecExpertDelete::getId, invalidIds));
        // 作废
        sysFileService.update(Wrappers.<SysFile>lambdaUpdate()
                .set(SysFile::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                .in(SysFile::getFileid, invalidIds));
        // 删除待办
        taskMapper.delete(Wrappers.<Task>lambdaQuery()
                .in(Task::getTaskentityid, invalidIds));
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询原来的数据
        SecExpertDelete entity = this.getById(request.getFileId());
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        entity.setExState(ApprovalStatusEnum.Returned_Initiator.getIndex());
        this.updateById(entity);
        SysFile sysFile = sysFileService.getById(entity.getExId());
        sysFileService.saveFile(entity.getExId(), sysFile.gettId(), ApprovalStatusEnum.Returned_Initiator.getIndex() + "", entity.getExCreateUid(), entity.getExCreateUname()
                , entity.getExCreateTime(), null, sysFile.getFiletitle(), sysFile.getFilecode(),
                "update", sysFile.getFileRelationId(), sysFile.getFilesectionid(), sysFile.gettCode(), sysFile.getFilecontract());
        // 生成一条已办
        // 获取当前节点
        FlowActivity flowActivity = taskProcessinMapper.getCurrentNode(sysFile.getFlowId());
        LogUserInfoVo userInfoByToken = redisUtils.getUserInfoByToken(token);
        TaskProcessin his = new TaskProcessin();
        his.setTpId(GenerateUUID.getUUID());
        his.setActname(flowActivity.getActname());
        his.setTpDotime(new Date());
        his.setTpExplain(request.getOpinion());
        his.setTpFlowid(sysFile.getFlowId());
        his.setTpProject(sysFile.getFileproject());
        his.setTpId(GenerateUUID.getUUID());
        his.setTpResult("退回");
        his.setTpEntityid(entity.getExId());
        his.setTpName(sysFile.getFiletitle());
        his.setTpUser(userInfoByToken.getId());
        his.setTpType("退回");
        his.setTpActtype("userTask");
        his.setTpActhour(flowActivity.getActhour() != null ? Float.parseFloat(flowActivity.getActhour() + "") : null);
        his.setTpFrom(userInfoByToken.getId());
        his.setTpFileid(entity.getExId());
        his.setTpSend(entity.getExCreateUid());
        his.setTpTemplatecodeo(sysFile.gettCode());
        his.setTpActid(flowActivity.getActid());
        his.setTpSigntime(new Date());
        his.setTpDotime(new Date());
        if (!"All".equals(sysFile.getTeId())) {
            his.setTeId(sysFile.getTeId());
        }
        taskProcessinMapper.insert(his);

        // 生成一条待办
        String uid = redisUtils.getUid(token);
        BatchhandlingTaskDto batchhandlingTaskDto = new BatchhandlingTaskDto();
        BatchhandlingTaskListDto batchhandlingTaskListDto = new BatchhandlingTaskListDto();
        batchhandlingTaskListDto.setFileId(entity.getExId());
        batchhandlingTaskListDto.setProcessed(entity.getExCreateUid());
        // batchhandlingTaskListDto.setOpinion(request.getOpinion());
        batchhandlingTaskListDto.setUId(uid);
        batchhandlingTaskListDto.setOperator(uid);
        batchhandlingTaskDto.settCode(sysFile.gettCode());
        batchhandlingTaskDto.setSendList(Collections.singletonList(batchhandlingTaskListDto));
        batchhandlingTaskDto.setTeId("All");
        batchhandlingTaskDto.setCurrentNode("returnStartNode");
        batchhandlingTaskDto.setOnlineConditions("false");
        batchhandlingTaskDto.setNotGenerateTask("1");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                bpmFeign.batchSendTask(batchhandlingTaskDto, token);
            }
        });
        secExpertService.updateChangeStatus(entity.getExId(), "04");
        return BaseResponse.ok(entity.getExId());
    }

    @Override
    public BaseResponse updateChangeStatus(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertDelete expertDelete = this.getById(id);
        if (ValidateUtil.isNull(expertDelete)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(expertDelete.getExState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        secExpertService.updateChangeStatus(expertDelete.getExId(), "03");
        return BaseResponse.ok();
    }
}
