package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvControlManageModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvControlManageMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvControlManage;
import com.jpmsoft.projectProducer.service.investment.IInvControlManageService;
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
public class InvControlManageImpl extends ServiceImpl<InvControlManageMapper, InvControlManage> implements IInvControlManageService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvControlManage(InvControlManageModel request) {
        InvControlManage entity = new InvControlManage();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getIcmId())) {
            entity.setIcmCreateTime(new Date());
            entity.setIcmId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIcmId(), request.gettId(), entity.getIcmState() + "", entity.getIcmCreateUid(), entity.getIcmCreateUname()
                    , entity.getIcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getIcmId());

        } else {
            InvControlManage model = baseMapper.selectById(request.getIcmId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getIcmState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setIcmState(model.getIcmState());
            entity.setIcmStateTime(model.getIcmStateTime());
            entity.setIcmCreateTime(model.getIcmCreateTime());
            entity.setIcmCreateUid(model.getIcmCreateUid());
            entity.setIcmCreateUname(model.getIcmCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIcmId(), request.gettId(), entity.getIcmState() + "", entity.getIcmCreateUid(), entity.getIcmCreateUname()
                    , entity.getIcmCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getIcmId());

        }
        return BaseResponse.ok(entity.getIcmId());
    }

    @Override
    public BaseResponse getInvControlManageList(InvControlManageModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Inv_Control_Manage", uId, "Icm_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Icm_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvControlManageModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("icmLegalPersonName").put("icmPlanType")
                .put("icmMajorType").put("icmCreateUname").put("icmState").put("icmPName").put("icmYear");
        IPage<InvControlManageModel> page = new Page<>(request.getPage(), request.getSize());
        List<InvControlManageModel> datas = baseMapper.getInvControlManageList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvControlManageById(String icmId) {
        InvControlManageModel datas = baseMapper.getInvControlManageById(icmId);
        return BaseResponse.ok(datas);
    }
}
