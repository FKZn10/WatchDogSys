package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SignSafetyResponDTO;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SignSafetyResponMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SignSafetyRespon;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.oa.IReportedPersonnelService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISignSafetyResponService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 安全责任书签订
 */
@Slf4j
@Service
public class SignSafetyResponImpl extends ServiceImpl<SignSafetyResponMapper, SignSafetyRespon> implements ISignSafetyResponService {

    /**
     * 安全责任书文件模板编码
     */
    private static final String T_CODE = "signSafeRespon";

    @Resource
    private IReportedPersonnelService reportedPersonnelService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private BpmFeign bpmFeign;

    /**
     * 根据人员申报表发起安全责任书签订并发送待办
     *
     * @param rpId  人员申报表主键
     * @param token 登录token
     * @return 安全责任书主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createSignSafetyResponByReported(String rpId, String token) {
        if (ValidateUtil.isEmpty(rpId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ReportedPersonnel personnel = reportedPersonnelService.getById(rpId);
        if (ValidateUtil.isNull(personnel)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 仅人员申报表审批通过（状态7）后才允许发起安全责任书
        if (!JudgeUtil.isApprovedState(personnel.getRpState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        LogUserInfoVo userInfo = UserUtils.getUserInfo();
        SignSafetyRespon entity = new SignSafetyRespon();
        entity.setSaId(GenerateUUID.getUUID());
        entity.setSaName(personnel.getRpApplicant());
        entity.setCompanyId(personnel.getRpUnitId());
        entity.setSaPostId(personnel.getRpChangePosition());
        entity.setSaPost(personnel.getRpChangePosition());
        entity.setPId(personnel.getPId());
        entity.setConId(personnel.getConId());
        entity.setRepPersonId(rpId);
        entity.setState(0);
        // 签订时间取人员申报表的创建日期，与前端“选择人员申报表后自动赋值”一致
        entity.setSaSignTime(personnel.getRpCreateTime());
        entity.setCreateUid(userInfo.getId());
        entity.setCreateUname(userInfo.getName());
        entity.setCreateTime(new Date());
        entity.setDeleted(0);
        save(entity);

        // 生成流程文件信息
        FileTemplateTree template = userProducerFeign.getFileTemplateByCode(T_CODE);
        if (template != null) {
            sysFileService.saveFile(entity.getSaId(), template.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(),
                    entity.getCreateTime(), entity.getPId(), "安全责任书签订", T_CODE, "create",
                    null, null, T_CODE, entity.getConId());
        }

        // 待办需在事务提交后发送，否则bpm服务读不到刚写入的sys_file记录
        final String saId = entity.getSaId();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                sendSignTask(saId, personnel, userInfo, token);
            }
        });
        return BaseResponse.ok(saId);
    }

    /**
     * 发送安全责任书签订待办给申报人
     *
     * @param saId      安全责任书主键
     * @param personnel 人员申报表
     * @param userInfo  当前登录用户
     * @param token     登录token
     */
    private void sendSignTask(String saId, ReportedPersonnel personnel, LogUserInfoVo userInfo, String token) {
        if (ValidateUtil.isEmpty(personnel.getRpApplicantId())) {
            log.info("人员申报表申报人为空，跳过待办发送，rpId:{}", personnel.getRpId());
            return;
        }
        List<BatchhandlingTaskListDto> taskListDtos = new ArrayList<>();
        BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
        tkl.setFileId(saId)
                .setUId(userInfo.getId())
                .setProcessed(personnel.getRpApplicantId())
                .setOperator(userInfo.getId());
        taskListDtos.add(tkl);

        BatchhandlingTaskDto task = new BatchhandlingTaskDto();
        task.setCurrentNode("createNode");
        task.setOnlineConditions(String.valueOf(false));
        task.settCode(T_CODE);
        task.setTeId(ValidateUtil.isEmpty(userInfo.getTeId()) ? "All" : userInfo.getTeId());
        task.setSendList(taskListDtos);
        BaseResponse taskRes = bpmFeign.batchSendTask(task, token);
        log.info("安全责任书待办发送结果:{}", taskRes);
        if (taskRes == null || !taskRes.getSuccess()) {
            throw new JPMException(ResMsg.taskCreateFail);
        }
    }

    /**
     * 获取安全责任书签订列表
     *
     * @param request 筛选条件
     * @return 安全责任书签订列表
     */
    @Override
    public BaseResponse getSignSafetyResponList(SignSafetyResponDTO request) {
        if (ValidateUtil.isNull(request)) {
            request = new SignSafetyResponDTO();
        }
        IPage<SignSafetyResponDTO> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<SignSafetyResponDTO> params = new QueryMap<>(request);
        params.put("saId").put("repPersonId").put("pId").put("conId").put("companyId")
                .put("saName").put("saPost").put("state").put("createUid").put("createUname")
                .put("pName").put("conName").put("cName")
                .put(request.getSaSignTimes(), "saSignTimeStart", "saSignTimeEnd")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        List<SignSafetyResponDTO> data = baseMapper.getSignSafetyResponList(page, params.getQuery());
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取安全责任书签订详情
     *
     * @param id 主键
     * @return 安全责任书签订详情
     */
    @Override
    public BaseResponse getSignSafetyResponById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        return BaseResponse.ok(baseMapper.getSignSafetyResponById(id));
    }
}
