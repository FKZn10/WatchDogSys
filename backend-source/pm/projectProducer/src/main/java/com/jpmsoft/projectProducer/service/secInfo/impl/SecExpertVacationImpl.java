package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertVacationMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSupplementDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupplementRecordDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertVacation;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecord;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordDtl;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertVacationVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertSupplementService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertVacationService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSelectionRecordService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:08
 */
@Service
public class SecExpertVacationImpl extends FileImpl<SecExpertVacationMapper, SecExpertVacation, SecExpertVacationDTO> implements ISecExpertVacationService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;
    @Resource
    private ISecExpertSupplementService secExpertSupplementService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private ISecSelectionRecordService secSelectionRecordService;

    @Override
    public BaseResponse createOrUpdateSecExpertVacation(SecExpertVacationDTO request) {
        if (ValidateUtil.isNull(request.getSevTime())) request.setSevTime(new Date());
        if (ValidateUtil.isAnObjEmpty(request.getExId(), request.getSrId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        SecExpertVacation entity = new SecExpertVacation();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertVacationList(SecExpertVacationDTO request, LogUserInfoVo user) {
        String authSql = sysUserService.onlyShowCancel(user.getId(), "Create_Uid", "State");
        QueryMap<SecExpertVacationDTO> queryMap = new QueryMap<>(request);
        queryMap.put("authSql", authSql).put("exName").put("state").put("createUname").put("pName").put("pCode").put("secName").put("secCode");
        IPage<SecExpertVacationVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertVacationVO> data = baseMapper.getSecExpertVacationList(page, queryMap.getQuery());
        data.forEach(d -> {
            d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
        });
        return BaseResponse.ok(data, Integer.parseInt(Long.valueOf(page.getTotal()).toString()));
    }

    @Override
    public BaseResponse getSecExpertVacationById(String id) {
        SecExpertVacationVO data = baseMapper.getSecExpertVacationById(id);
        if (data != null)
            data.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getExIdNumber())));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approveSecExpertVacationById(String id, String token) {
        SecExpertVacation sev = baseMapper.selectById(id);
        if (sev == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNull(sev.getState()) || !Objects.equals(sev.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        //需要选取记录中是否按时参加和是否请假标记
        secSelectionRecordDtlService.updateVacation(sev.getExId(), sev.getSrId(), 0, 1);
        //region 生成专家抽取记录以及待办消息
        //region 生成专家抽取业务表单信息
        FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("SecExpertSupplement");
        if (temp != null) {
            SecExpertSupplementDTO ses = new SecExpertSupplementDTO();
            ses.settCode(temp.gettCode());
            ses.settId(temp.gettId());
            ses.setFileCode(temp.gettCode());
            ses.setFileTitle(temp.gettName());
            ses.setSrId(sev.getSrId());
            ses.setSevId(sev.getId());
            ses.setState(ApprovalStatusEnum.Not_Started.getIndex());
            ses.setCreateTime(new Date());
            ses.setStateTime(new Date());
            ses.setCreateUid(user.getId());
            ses.setCreateUname(user.getName());
            ses.setTeId(user.getTeId());
            List<SecSelectionRecordDtl> records = secSelectionRecordDtlService.getBySrId(sev.getSrId());
            if (!CollectionUtils.isEmpty(records)) {
                ses.setSecSupplementRecordDtls(records.stream().map(r -> {
                    SecSupplementRecordDtlDTO d = new SecSupplementRecordDtlDTO();
                    BeanUtils.copyProperties(r, d);
                    d.setSrdId(GenerateUUID.getUUID());
                    d.setSrdMark("02");
                    return d;
                }).collect(Collectors.toList()));
            }
            BaseResponse res = secExpertSupplementService.createOrUpdate(ses);
            //endregion
            //region 生成待办
            if (res != null && res.getSuccess()) {
                SecSelectionRecord sr = secSelectionRecordService.getById(ses.getSrId());
                if (sr != null || ValidateUtil.isEmpty(sr.getSrCreateUid())) {
                    BatchhandlingTaskDto task = new BatchhandlingTaskDto();
                    task.setCurrentNode("startNode");
                    task.setOnlineConditions("false");
                    task.setTeId(ValidateUtil.isEmpty(user.getTeId()) ? "All" : user.getTeId());
                    task.settCode(temp.gettCode());
                    BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
                    tkl.setFileId(res.getData().toString()).setUId(ses.getCreateUid()).setProcessed(sr.getSrCreateUid());
                    task.setSendList(Arrays.asList(tkl));
                    BaseResponse taskRes = bpmFeign.batchSendTask(task, token);
                    if (taskRes == null || !taskRes.getSuccess()) throw new JPMException(ResMsg.taskCreateFail);
                }
            }
            //endregion
        }
        //endregion
        return BaseResponse.ok();
    }


}
