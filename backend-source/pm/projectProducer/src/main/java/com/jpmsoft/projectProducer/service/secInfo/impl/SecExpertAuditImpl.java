package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.other.TaskProcessinMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertAuditMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAuditDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.other.FlowActivity;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAuditDetailVO;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAuditVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:20
 */
@Service
public class SecExpertAuditImpl extends FileImpl<SecExpertAuditMapper, SecExpertAudit, SecExpertAuditDTO> implements ISecExpertAuditService {
    @Resource
    private ISecExpertAuditDetailService secExpertAuditDetailService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TaskProcessinMapper taskProcessinMapper;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private ISecExpertPauseService secExpertPauseService;
    @Resource
    private ISecExpertDeleteService secExpertDeleteService;
    @Resource
    private ISecExpertChangeService secExpertChangeService;
    @Resource
    private ISecExpertAuditGroupService secExpertAuditGroupService;

    @Override
    public BaseResponse createOrUpdateSecExpertAudit(SecExpertAuditDTO request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request.getYear())) {
            request.setYear(DateUtil.getTimeYear(new Date()));
        }
        SecExpertAudit entity = new SecExpertAudit();
        entity.setSeaCid(user.getCId());
        createOrUpdateFileEntity(entity, request);
        secExpertAuditDetailService.delete2Create(request.getSecExpertAuditDetails(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse createOrUpdateSecExpertAuditDz(SecExpertAuditDTO request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request.getCreateTime())) {
            request.setCreateTime(new Date());
        }
        Integer year = DateUtil.getTimeYear(request.getCreateTime());
        if (ValidateUtil.isEmpty(request.getId())) {
            request.setSeaName(user.getCName() + year.toString() + "年评委入库审核表");
        }
        if (secExpertAuditGroupService.count(Wrappers.<SecExpertAuditGroup>lambdaQuery()
                .ne(SecExpertAuditGroup::getState, ApprovalStatusEnum.Invalid.getIndex())
                .ne(SecExpertAuditGroup::getState, ApprovalStatusEnum.Not_Started.getIndex())
                .eq(SecExpertAuditGroup::getYear, year)) > 0
        ) {
            return BaseResponse.fail("集团已提交入库申请，无法创建新的入库申请！");
        }
        if (this.count(Wrappers.<SecExpertAudit>lambdaQuery().eq(SecExpertAudit::getSeaName, request.getSeaName())
                .ne(ValidateUtil.isNotEmpty(request.getId()), SecExpertAudit::getId, request.getId())
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())) > 0) {
            return BaseResponse.fail("当前企业已存在入库申请，请勿重复创建！");
        }
        List<SecExpertAuditDetailDTO> secExpertAuditDetails = request.getSecExpertAuditDetails();
        if (CollectionUtil.isNotEmpty(secExpertAuditDetails)) {
            List<String> exIdList = secExpertAuditDetails.stream().filter(s -> s.getExId() != null).map(e -> e.getExId()).collect(Collectors.toList());
            secExpertService.checkExpertWorkTime(exIdList);
        }

        return createOrUpdateSecExpertAudit(request, user);
    }

    @Override
    public BaseResponse getSecExpertAuditList(SecExpertAuditDTO request) {
        QueryMap<SecExpertAuditDTO> params = new QueryMap<>(request);
        params.put("seaName").put("createUname").put("state").put("year").put("seaCid");
        IPage<SecExpertAuditVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertAuditVO> data = baseMapper.getSecExpertAuditList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecExpertAuditById(String id) {
        SecExpertAudit sea = baseMapper.selectById(id);
        if (sea == null) return BaseResponse.fail(ResMsg.infoNotExist);
        SecExpertAuditVO data = new SecExpertAuditVO();
        BeanUtils.copyProperties(sea, data);
        data.setSecExpertAuditDetails(secExpertAuditDetailService.getDetailList(id));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approve2SyncExpertState(String seaId) {
        SecExpertAudit sea = baseMapper.selectById(seaId);
        if (sea == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(sea.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        List<String> ids = secExpertAuditDetailService.getExIdList(seaId);
        secExpertService.updateExpertStateBath(ids, sea.getState());
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询原来的数据，这里的fileId是cid
        SecExpertAudit entity = secExpertAuditDetailService.getParentByCId(request.getFileId());
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        entity.setState(ApprovalStatusEnum.Returned_Initiator.getIndex());
        this.updateById(entity);
        SysFile sysFile = sysFileService.getById(entity.getId());
        sysFileService.saveFile(entity.getId(), sysFile.gettId(), ApprovalStatusEnum.Returned_Initiator.getIndex() + "", entity.getCreateUid(), entity.getCreateUname()
                , entity.getCreateTime(), null, sysFile.getFiletitle(), sysFile.getFilecode(),
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
        his.setTpEntityid(entity.getId());
        his.setTpName(sysFile.getFiletitle());
        his.setTpUser(userInfoByToken.getId());
        his.setTpType("退回");
        his.setTpActtype("userTask");
        his.setTpActhour(flowActivity.getActhour() != null ? Float.parseFloat(flowActivity.getActhour() + "") : null);
        his.setTpFrom(userInfoByToken.getId());
        his.setTpFileid(entity.getId());
        his.setTpSend(entity.getCreateUid());
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
        batchhandlingTaskListDto.setFileId(entity.getId());
        batchhandlingTaskListDto.setProcessed(entity.getCreateUid());
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
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 查询结束审核的专家列表给集团审核使用
     */
    @Override
    public BaseResponse getSecExpertAuditDetailListGroup() {
        List<SecExpertAuditDetailVO> data = secExpertAuditDetailService.getSecExpertAuditDetailListGroup(DateUtil.getTimeYear(new Date()));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecExpertAuditByYear(Integer year) {
        if (ValidateUtil.isNull(year)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertAudit sea = this.getOne(Wrappers.<SecExpertAudit>lambdaQuery().eq(SecExpertAudit::getYear, year)
                .ne(BaseEntity::getState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(BaseEntity::getCreateTime).last("limit 1")
        );
        if (sea == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        SecExpertAuditVO data = new SecExpertAuditVO();
        BeanUtils.copyProperties(sea, data);
        data.setSecExpertAuditDetails(secExpertAuditDetailService.getDetailList(sea.getId()));
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse invalidExpertProcessData(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecExpertAudit entity = this.getById(id);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (Objects.equals(entity.getState(), ApprovalStatusEnum.Invalid.getIndex()) ||
                Objects.equals(entity.getState(), ApprovalStatusEnum.Not_Started.getIndex())
        ) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 查询子表
        List<SecExpertAuditDetail> details = secExpertAuditDetailService.list(Wrappers.<SecExpertAuditDetail>lambdaQuery().eq(SecExpertAuditDetail::getSeaId, id));
        List<String> exIds = details.stream().map(SecExpertAuditDetail::getExId).collect(Collectors.toList());
        // 作废各个专家的在流程中的暂停、删除、变更数据以及同企业专家新增在流程中的数据
        if (ValidateUtil.isNotEmpty(entity.getSeaCid())) {
            secExpertService.invalidProcessData(entity.getSeaCid());
        }
        if (ValidateUtil.isNotEmpty(exIds)) {
            secExpertPauseService.invalidProcessData(exIds);
            secExpertDeleteService.invalidProcessData(exIds);
            secExpertChangeService.invalidProcessData(exIds);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecExpertAuditChangeStatusInfoByYear(String exApplicantCId, Integer year) {
        if (ValidateUtil.isNull(year)) {
            year = DateUtil.getTimeYear(new Date());
        }

        SecExpertModel model = new SecExpertModel();
        model.setExApplicantCId(exApplicantCId);
        model.setYear(year);
        model.setExStates(Arrays.asList("0", "2", "7"));
        model.setPage(1);
        model.setSize(Integer.MAX_VALUE);
        model.setExChangeStatus("04");

        BaseResponse secExpertList = secExpertService.getSecExpertList(model);
        if (!secExpertList.isSuccess() || ValidateUtil.isEmpty(secExpertList.getData())) {
            return BaseResponse.ok(new HashMap<String, String>());
        }

        List<SecExpertModel> data = (List<SecExpertModel>) secExpertList.getData();
        List<String> exIds = data.stream()
                .map(SecExpertModel::getExId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtil.isEmpty(exIds)) {
            return BaseResponse.ok(new HashMap<String, String>());
        }

        List<SecExpertChange> changeList = secExpertChangeService.list(
                Wrappers.<SecExpertChange>lambdaQuery()
                        .in(SecExpertChange::getExId, exIds)
                        .orderByDesc(SecExpertChange::getExCreateTime)
        );

        List<SecExpertDelete> deleteList = secExpertDeleteService.list(
                Wrappers.<SecExpertDelete>lambdaQuery()
                        .in(SecExpertDelete::getExId, exIds)
                        .orderByDesc(SecExpertDelete::getExCreateTime)
        );

        Map<String, SecExpertChange> latestChangeMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(changeList)) {
            for (SecExpertChange c : changeList) {
                if (c == null || ValidateUtil.isEmpty(c.getExId())) {
                    continue;
                }
                SecExpertChange old = latestChangeMap.get(c.getExId());
                if (old == null || (c.getExCreateTime() != null && old.getExCreateTime() != null && c.getExCreateTime().after(old.getExCreateTime()))
                        || (c.getExCreateTime() != null && old.getExCreateTime() == null)) {
                    latestChangeMap.put(c.getExId(), c);
                }
            }
        }

        Map<String, SecExpertDelete> latestDeleteMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(deleteList)) {
            for (SecExpertDelete d : deleteList) {
                if (d == null || ValidateUtil.isEmpty(d.getExId())) {
                    continue;
                }
                SecExpertDelete old = latestDeleteMap.get(d.getExId());
                if (old == null || (d.getExCreateTime() != null && old.getExCreateTime() != null && d.getExCreateTime().after(old.getExCreateTime()))
                        || (d.getExCreateTime() != null && old.getExCreateTime() == null)) {
                    latestDeleteMap.put(d.getExId(), d);
                }
            }
        }

        Map<String, String> result = new HashMap<>(Math.max(16, data.size() * 2));

        for (SecExpertModel expert : data) {
            if (expert == null || ValidateUtil.isEmpty(expert.getExId())) {
                continue;
            }

            SecExpertChange change = latestChangeMap.get(expert.getExId());
            SecExpertDelete delete = latestDeleteMap.get(expert.getExId());

            Integer expertState = expert.getExState();
            Integer changeState = (change == null ? null : change.getExState());
            Integer deleteState = (delete == null ? null : delete.getExState());

            boolean expertApproved = JudgeUtil.isApprovedState(expertState);
            boolean changeApproved = JudgeUtil.isApprovedState(changeState);
            boolean deleteApproved = JudgeUtil.isApprovedState(deleteState);

            boolean expertNotStarted = Objects.equals(expertState, ApprovalStatusEnum.Not_Started.getIndex());
            boolean changeNotStarted = Objects.equals(changeState, ApprovalStatusEnum.Not_Started.getIndex());
            boolean deleteNotStarted = Objects.equals(deleteState, ApprovalStatusEnum.Not_Started.getIndex());

            List<String> msgParts = new ArrayList<>(3);

            if (!expertApproved) {
                msgParts.add(expertNotStarted ? "新增未提交" : "新增未审批");
            } else if (ValidateUtil.isNull(change) && ValidateUtil.isNull(delete)) {
                msgParts.add("专家今年未提交");
            }

            if (ValidateUtil.isNotNull(change)) {
                if (!changeApproved) {
                    msgParts.add(changeNotStarted ? "修改未提交" : "修改未审批");
                }
            }
            if (ValidateUtil.isNotNull(delete)) {
                if (!deleteApproved) {
                    msgParts.add(deleteNotStarted ? "删除未提交" : "删除未审批");
                }
            }

            if (!msgParts.isEmpty()) {
                result.put(expert.getExName(), String.join("，", msgParts));
            }
        }

        return BaseResponse.ok(result);
    }
}
