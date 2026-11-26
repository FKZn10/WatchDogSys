package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectDtlRequest;
import com.jpmsoft.common.model.response.PeInfoCollectDtlResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.peInfo.PeInfoCollectDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollectDtl;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoCollectDtlService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeInfoCollectDtlImpl extends ServiceImpl<PeInfoCollectDtlMapper, PeInfoCollectDtl> implements IPeInfoCollectDtlService {


    @Override
    public BaseResponse getPeInfoNotGenerateCollectDtl(PeInfoCollectDtlRequest request, String uId) {
        ////根据考核评分做鉴权
        //String authSql = sysUserService.getAuthSql("Pe_Info_Collect_Dtl", uId, null, OutUserAuthEnum.Unit_Auth.getIndex());
        //if (ValidateUtil.isEmpty(authSql))
        //    return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        //params.put("authSql", authSql);
        params.put("peType", ValidateUtil.isEmpty(request.getPeType()) ? null : request.getPeType());
        params.put("pConstructionUnit", ValidateUtil.isEmpty(request.getpConstructionUnit()) ? null : request.getpConstructionUnit());
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? null : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? null : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("peUnitId", ValidateUtil.isEmpty(request.getPeUnitId()) ? null : request.getPeUnitId());
        params.put("peYear", ValidateUtil.isEmpty(request.getPeYear()) ? null : request.getPeYear());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("peQuarter", ValidateUtil.isEmpty(request.getPeQuarter()) ? null : request.getPeQuarter());
        params.put("conPartyAId", ValidateUtil.isEmpty(request.getConPartyAId()) ? null : request.getConPartyAId());
        params.put("pecDepartment", ValidateUtil.isEmpty(request.getPecDepartment()) ? null : request.getPecDepartment());
        List<PeInfoCollectDtlResponse> datas = baseMapper.getPeInfoNotGenerateCollectDtl(params);
        Integer total = baseMapper.getPeInfoNotGenerateCollectDtlCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getPeInfoCollectDtlList(PeInfoCollectDtlRequest request, String uId) {
        //根据考核评分做鉴权
        //String authSql = sysUserService.getAuthSql("Pe_Info_Collect_Dtl", uId, null, OutUserAuthEnum.Unit_Auth.getIndex());
        //if (ValidateUtil.isEmpty(authSql))
        //    return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        //params.put("authSql", authSql);
        //数据类型
        params.put("peState", !ValidateUtil.isEmpty(request.getPeState()) ? request.getPeState() : null);
        //乙方单位
        params.put("peCompanyId", !ValidateUtil.isEmpty(request.getPeCompanyId()) ? request.getPeCompanyId() : null);
        //合同类型
        params.put("conType", !ValidateUtil.isEmpty(request.getConType()) ? request.getConType() : null);
        //项目名称
        params.put("pName", !ValidateUtil.isEmpty(request.getpName()) ? request.getpName() : null);
        params.put("pId", !ValidateUtil.isEmpty(request.getpId()) ? request.getpId() : null);
        //合同名称
        params.put("conName", !ValidateUtil.isEmpty(request.getConName()) ? request.getConName() : null);
        //乙方单位名称
        params.put("peCompanyName", !ValidateUtil.isEmpty(request.getPeCompanyName()) ? request.getPeCompanyName() : null);
        //考核汇总ID
        params.put("pecId", ValidateUtil.isEmpty(request.getPecId()) ? null : request.getPecId());
        //年度
        params.put("peYear", ValidateUtil.isEmpty(request.getPeYear()) ? null : request.getPeYear());
        //季度
        params.put("peQuarter", ValidateUtil.isEmpty(request.getPeQuarter()) ? null : request.getPeQuarter());
        //评价类型
        params.put("peType", ValidateUtil.isEmpty(request.getPeType()) ? null : request.getPeType());
        //建设单位 主管单位
        params.put("pConstructionUnit", ValidateUtil.isEmpty(request.getpConstructionUnit()) ? null : request.getpConstructionUnit());
        //评价单位
        params.put("peUnitId", ValidateUtil.isEmpty(request.getPeUnitId()) ? null : request.getPeUnitId());
        //是否为结束状态(结束状态 上会确认共用此数据)
        params.put("approval", request.getApproval() != null && request.getApproval() == true ? true : false);
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? null : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? null : request.getSortType());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        IPage<PeInfoCollectDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PeInfoCollectDtlResponse> datas = baseMapper.getPeInfoCollectDtlList(page, params);
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public void createOrUpdateCollectToCreateDtl(List<String> peIds, String pecId) {
        deleteByPecId(pecId);
        List<PeInfoCollectDtl> list = new ArrayList<>();
        peIds.forEach(peId -> {
            PeInfoCollectDtl entity = new PeInfoCollectDtl();
            entity.setPeId(peId);
            entity.setPecdId(GenerateUUID.getUUID());
            entity.setPecId(pecId);
            list.add(entity);
        });
        this.saveBatch(list);
    }

    @Override
    public void deleteByPecId(String pecId) {
        baseMapper.deleteByPecId(pecId);
    }

    @Override
    public void deleteByPeId(String peId) {
        baseMapper.deleteByPeId(peId);
    }

    @Override
    public List<PeInfoCollectDtlResponse> getPeInfoCollectDtlListWhenExport(String authSql, String pecId) {
        return baseMapper.getPeInfoCollectDtlListWhenExport(authSql, pecId);
    }

}
