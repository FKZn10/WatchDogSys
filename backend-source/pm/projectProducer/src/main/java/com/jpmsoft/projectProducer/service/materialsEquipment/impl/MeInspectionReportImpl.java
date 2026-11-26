package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.MeEnter.MeInspectionReportRequest;
import com.jpmsoft.common.model.response.MeEnter.MeInspectionReportResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeInspectionReportMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeInspectionReport;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeInspectionReportService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MeInspectionReportImpl extends ServiceImpl<MeInspectionReportMapper, MeInspectionReport> implements IMeInspectionReportService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMeInspectionReport(MeInspectionReportRequest request) {
        MeInspectionReport entity = new MeInspectionReport();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getIrId())) {
            entity.setIrCreateTime(new Date());
            entity.setIrId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIrId(), request.gettId(), entity.getIrState() + "", entity.getIrCreateUid(), entity.getIrCreateUname()
                    , entity.getIrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getIrId());

        } else {
            MeInspectionReport model = baseMapper.selectById(request.getIrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getIrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setIrState(model.getIrState());
            entity.setIrStateTime(model.getIrStateTime());
            entity.setIrCreateTime(model.getIrCreateTime());
            entity.setIrCreateUid(model.getIrCreateUid());
            entity.setIrCreateUname(model.getIrCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIrId(), request.gettId(), entity.getIrState() + "", entity.getIrCreateUid(), entity.getIrCreateUname()
                    , entity.getIrCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getIrId());

        }
        return BaseResponse.ok(entity.getIrId());
    }

    @Override
    public BaseResponse getMeInspectionReportList(MeInspectionReportRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Inspection_Report", uId, "Ir_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ir_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeInspectionReportRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conName").put("pId").put("pName")
                .put("conId").put("wiUnitId").put("metName").put("conPartyBId");
        IPage<MeInspectionReportResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeInspectionReportResponse> datas = baseMapper.getMeInspectionReportList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeInspectionReportByIrId(String irId) {
        MeInspectionReportResponse data = baseMapper.getMeInspectionReportByIrId(irId);
        return BaseResponse.ok(data);
    }
}
