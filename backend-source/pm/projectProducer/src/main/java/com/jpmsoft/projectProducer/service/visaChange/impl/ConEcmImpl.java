package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.DesignChange.ConEcmRequest;
import com.jpmsoft.common.model.response.DesignChange.ConEcmResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConEcmMapper;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEcm;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConEcmService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ConEcmImpl extends ServiceImpl<ConEcmMapper, ConEcm> implements IConEcmService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConEcm(ConEcmRequest request) {
        ConEcm entity = new ConEcm();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getEcmId())) {
            entity.setEcmId(GenerateUUID.getUUID());
            entity.setEcmCreateTime(new Date());
            entity.setEcmStateTime(null);
            entity.setEcmState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getEcmId(), request.gettId(), entity.getEcmState() + "", entity.getEcmCreateUid(), entity.getEcmCreateUname()
                    , entity.getEcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getEcmId());

            return BaseResponse.ok(entity.getEcmId());
        } else {
            ConEcm model = baseMapper.selectById(request.getEcmId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getEcmState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setEcmCreateTime(model.getEcmCreateTime());
            entity.setEcmCreateUid(model.getEcmCreateUid());
            entity.setEcmCreateUname(model.getEcmCreateUname());
            entity.setEcmState(model.getEcmState());
            entity.setEcmStateTime(model.getEcmStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getEcmId(), request.gettId(), entity.getEcmState() + "", entity.getEcmCreateUid(), entity.getEcmCreateUname()
                    , entity.getEcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getEcmId());

            return BaseResponse.ok(entity.getEcmId());
        }
    }

    @Override
    public BaseResponse getConEcmList(ConEcmRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Ecm", uId, "Ecm_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ecm_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return queryList(request, authSql);
    }

    @Override
    public BaseResponse getConEcmListNoAuth(ConEcmRequest request) {
        return queryList(request, null);
    }

    private BaseResponse queryList(ConEcmRequest request, String authSql) {
        QueryMap<ConEcmRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("ecmYesNo")
                .put("ecmDepName").put("dcoMajor").put("enisCode")
                .put("ecmReason").put("ecmContent")
                .put("pId").put("pName").put("ecmIsHistory")
                .put("ecmName").put("ecmType").put("ecmDep")
                .put("ecmState").put("ecmCreateUname")
                .put(request.getEcmAmounts(), "ecmAmountStart", "ecmAmountEnd")
                .put(request.getEcmCreateTimes(), "ecmCreateTimeStart", "ecmCreateTimeEnd");
        IPage<ConEcmResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcmResponse> datas = baseMapper.getConEcmList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcmById(String ecmId) {
        ConEcmResponse data = baseMapper.getConEcmById(ecmId);
        return BaseResponse.ok(data);
    }

    @Override
    public ConEcm getLastEcmByDcoId(String dcoId) {
        QueryWrapper<ConEcm> wr = new QueryWrapper<>();
        wr.eq("Dco_Id", dcoId);
        wr.eq("Ecm_State", ApprovalStatusEnum.Already_Over.getIndex());
        wr.orderByDesc("Ecm_Create_Time");
        wr.last(" limit 1");
        return baseMapper.selectOne(wr);
    }
}
