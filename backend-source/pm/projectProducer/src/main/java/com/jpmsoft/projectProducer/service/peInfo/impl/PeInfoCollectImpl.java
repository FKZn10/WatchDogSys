package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectRequest;
import com.jpmsoft.common.model.response.PeInfoCollectDtlResponse;
import com.jpmsoft.common.model.response.PeInfoCollectResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeInfoCollectMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollect;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoCollectDtlService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoCollectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeInfoCollectImpl extends ServiceImpl<PeInfoCollectMapper, PeInfoCollect> implements IPeInfoCollectService {

    @Resource
    private IPeInfoCollectDtlService peInfoCollectDtlService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePeInfoCollect(PeInfoCollectRequest request) {
        PeInfoCollect pojo = baseMapper.selectById(request.getPecId());
        PeInfoCollect entity = new PeInfoCollect();
        BeanUtils.copyProperties(request, entity);
        entity.setPecFlowDate(null);
        if (pojo == null) {//新增
            if (request.getPeIds() == null)
                return BaseResponse.fail(ResMsg.selectPeInfoCollectDtl);
            entity.setPecDate(new Date());
            baseMapper.insert(entity);
            peInfoCollectDtlService.createOrUpdateCollectToCreateDtl(request.getPeIds(), entity.getPecId());
            sysFileService.FileHelper(entity, request.gettId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.gettCode());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPecId());

            return BaseResponse.ok(entity.getPecId());
        } else {
            PeInfoCollect model = baseMapper.selectById(request.getPecId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getPecFlowState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            if (request.getPecFlowState() != null)
                entity.setPecFlowDate(new Date());
            entity.setPecDate(pojo.getPecDate());
            baseMapper.updateById(entity);
            peInfoCollectDtlService.createOrUpdateCollectToCreateDtl(request.getPeIds(), entity.getPecId());
            sysFileService.FileHelper(entity, request.gettId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), request.gettCode());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPecId());

            return BaseResponse.ok(entity.getPecId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePeInfoCollectByPecId(String pecId) {
        PeInfoCollect pojo = baseMapper.selectById(pecId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.isApprovedState(pojo.getPecFlowState()))
            return BaseResponse.fail(ResMsg.stateNotDel);
        baseMapper.deleteById(pecId);
        peInfoCollectDtlService.deleteByPecId(pecId);
        sysFileService.deleteFileById(pecId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updatePeInfoCollectFlowState(PeInfoCollectRequest request) {
        if (ValidateUtil.isEmpty(request.getPecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeInfoCollect pojo = baseMapper.selectById(request.getPecId());
        SysFile filePojo = sysFileService.getById(request.getPecId());
        if (pojo == null || filePojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.nonModifyState(pojo.getPecFlowState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        pojo.setPecFlowState(Integer.parseInt(filePojo.getFileflowstatus()));
        baseMapper.updateById(pojo);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPeInfoCollectList(PeInfoCollectRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("pecYear", ValidateUtil.isEmpty(request.getPecYear()) ? null : request.getPecYear());
        params.put("pecQuarter", ValidateUtil.isEmpty(request.getPecQuarter()) ? null : request.getPecQuarter());
        params.put("pecType", ValidateUtil.isEmpty(request.getPecType()) ? null : request.getPecType());
        params.put("pecConstructionUnitId", ValidateUtil.isEmpty(request.getPecConstructionUnitId()) ? null : request.getPecConstructionUnitId());
        params.put("pecUname", ValidateUtil.isEmpty(request.getPecUname()) ? null : request.getPecUname());
        params.put("pecUnitId", ValidateUtil.isEmpty(request.getPecUnitId()) ? null : request.getPecUnitId());
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? null : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? null : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("pecDepartment", !JudgeUtil.checkObject(request.getPecDepartment()) ? request.getPecDepartment() : null);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("pecConstructionUnitName", ValidateUtil.isEmpty(request.getPecConstructionUnitName()) ? null : request.getPecConstructionUnitName());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        List<PeInfoCollectResponse> datas = baseMapper.getPeInfoCollectList(params);
        Integer total = baseMapper.getPeInfoCollectListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getPeInfoCollectByPecId(String pecId) {
        PeInfoCollectResponse data = baseMapper.getPeInfoCollectByPecId(pecId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPeInfoCollectExportByPecId(String pecId, String uId) {
        //根据考核评分做鉴权
        //String authSql = sysUserService.getAuthSql("Pe_Info_Collect_Dtl", uId, null, OutUserAuthEnum.Unit_Auth.getIndex());
        //if (ValidateUtil.isEmpty(authSql))
        //    return BaseResponse.fail(ResMsg.noPermission);
        PeInfoCollectResponse data = baseMapper.getPeInfoCollectByPecId(pecId);
        if (data == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        List<PeInfoCollectDtlResponse> peInfoCollectDtls = peInfoCollectDtlService.getPeInfoCollectDtlListWhenExport(null, pecId);
        data.setPeInfoCollectDtls(peInfoCollectDtls);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse deletePeInfoCollectDtlWhenBackByPecId(String pecId) {
        PeInfoCollect pojo = baseMapper.selectById(pecId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (pojo.getPecFlowState() != ApprovalStatusEnum.Returned_Initiator.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        peInfoCollectDtlService.deleteByPecId(pecId);
        return BaseResponse.ok();
    }
}
