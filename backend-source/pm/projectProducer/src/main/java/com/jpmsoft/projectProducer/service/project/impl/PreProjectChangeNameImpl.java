package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.project.PreProjectChangeNameRequest;
import com.jpmsoft.common.model.response.Project.PreProjectChangeNameResponse;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreProjectChangeNameMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChangeName;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectChangeNameService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PreProjectChangeNameImpl extends ServiceImpl<PreProjectChangeNameMapper, PreProjectChangeName> implements IPreProjectChangeNameService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateProChangeName(PreProjectChangeNameRequest request) {
        PreProjectChangeName entity;
        if (ValidateUtil.isEmpty(request.getPcnId())) entity = new PreProjectChangeName();
        else {
            entity = baseMapper.selectById(request.getPcnId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        }
        entity.setPcnOldName(request.getPcnOldName());
        entity.setPcnNewName(request.getPcnNewName());
        entity.setPcnModifyReason(request.getPcnModifyReason());
        entity.setPcnAttachment(request.getPcnAttachment());
        entity.setpId(request.getpId());
        entity.setPcnUserId(request.getPcnUserId());
        if (ValidateUtil.isEmpty(request.getPcnId())) {
            entity.setPcnCreateUid(request.getPcnCreateUid());
            entity.setPcnCreateUname(request.getPcnCreateUname());
            entity.setPcnCreateTime(new Date());
            entity.setPcnId(GenerateUUID.getUUID());
            entity.setPcnFlowstate(request.getPcnFlowstate());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPcnId(), request.gettId(), entity.getPcnFlowstate() + "", entity.getPcnCreateUid(), entity.getPcnCreateUname(), entity.getPcnCreateTime(), entity.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPcnAttachment()), entity.getPcnId());
        } else {
//            entity.setPcnUserId(request.getPcnUserId());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPcnId(), request.gettId(), entity.getPcnFlowstate() + "", entity.getPcnCreateUid(), entity.getPcnCreateUname(), entity.getPcnCreateTime(), entity.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPcnAttachment()), Arrays.asList(request.getFileIds(), request.getPcnAttachmentIds()), entity.getPcnId());
        }
        return BaseResponse.ok(entity.getPcnId());
    }

    @Override
    public BaseResponse getPreProjectChangeNameByPage(PreProjectChangeNameRequest request) {
        if (ValidateUtil.isEmpty(request.getPcnName())) request.setPcnName(null);
        List<PreProjectChangeNameResponse> data = baseMapper.getPreProjectChangeNameByPage(request.getPcnName(), request.getpId(), request.getTeId(), request.getSortType(), request.getSortFiled(), request.getPageStart(), request.getSize());
        Integer total = baseMapper.getPreProjectChangeNameCount(request.getPcnName(), request.getpId(), request.getTeId());
        return BaseResponse.ok(data, total);
    }

    @Override
    public BaseResponse getPreProjectChangeNameById(String pcnId) {
        PreProjectChangeNameResponse data = baseMapper.getPreProjectChangeNameById(pcnId);
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProChangeNameById(PreProjectChangeNameRequest request) {
        PreProjectChangeName entity;
        if (ValidateUtil.isEmpty(request.getPcnId())) return BaseResponse.fail(ResMsg.notReqParam);
        else {
            entity = baseMapper.selectById(request.getPcnId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        }
        baseMapper.deleteById(entity.getPcnId());
        sysFileService.deleteFileById(entity.getPcnId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateProChangeNameFlowState(PreProjectChangeNameRequest request) {
        if (JudgeUtil.checkObject(request.getPcnId())) return BaseResponse.fail(ResMsg.notReqParam);
        else {
            PreProjectChangeName entity = baseMapper.selectById(request.getPcnId());
            SysFile filePojo = sysFileService.getById(request.getPcnId());
            if (entity == null || filePojo == null) return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setPcnFlowstate(Integer.parseInt(filePojo.getFileflowstatus()));
            entity.setPcnFlowdate(new Date());
            baseMapper.updateById(entity);
            if (Objects.equals(entity.getPcnFlowstate(), ApprovalStatusEnum.Already_Over.getIndex()))
                refreshProjectName(entity.getPcnId());
            return BaseResponse.ok();
        }
    }

    @Override
    public BaseResponse getProjectNameUsedBeforeListById(String pId) {
        QueryWrapper<PreProjectChangeName> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("PCN_FlowState", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.orderByDesc("Pcn_Create_Time");
        List<PreProjectChangeName> datas = baseMapper.selectList(wrapper);
        PreProjectResponse projectPojo = preProjectService.selectById(pId);
        Map<String, Object> map = new HashMap<>();
        map.put("project", projectPojo);
        map.put("changeNameList", datas);
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse approve2RefreshProjectName(String pcnId) {
        refreshProjectName(pcnId);
        return BaseResponse.ok();
    }

    private void refreshProjectName(String pcnId) {
        PreProjectChangeName pcn = baseMapper.selectById(pcnId);
        if (pcn == null) throw new JPMException(ResMsg.infoNotExist);
        if (!Objects.equals(pcn.getPcnFlowstate(), ApprovalStatusEnum.Already_Over.getIndex()))
            throw new JPMException(ResMsg.stateCannotAct);
        PreProject project = preProjectService.getById(pcn.getpId());
        if (project == null) throw new JPMException(ResMsg.infoNotExist);
        String oldName = ValidateUtil.isNotEmpty(project.getpNameHistory()) ? project.getpNameHistory() + ";" + project.getpName() : project.getpName();
        project.setpName(pcn.getPcnNewName());
        project.setpNameHistory(oldName);
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
        preProjectService.updateById(project);
    }
}
