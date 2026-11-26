package com.jpmsoft.projectProducer.service.supervision.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Supervisor.ConSupDisclosureRequest;
import com.jpmsoft.common.model.response.Supervisor.ConSupDisclosureResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.supervision.ConSupDisclosureMapper;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupDisclosure;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.supervision.IConSupDisclosureService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ConSupDisclosureImpl extends ServiceImpl<ConSupDisclosureMapper, ConSupDisclosure> implements IConSupDisclosureService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConSupDisclosure(ConSupDisclosureRequest request) {
        ConSupDisclosure entity = new ConSupDisclosure();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getDisId())) {
            entity.setDisId(GenerateUUID.getUUID());
            entity.setDisCreateTime(new Date());
            entity.setDisStateTime(null);
            entity.setDisState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getDisId(), request.gettId(), entity.getDisState() + "", entity.getDisCreateUid(), entity.getDisCreateUname()
                    , entity.getDisCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getDisFile()), entity.getDisId());

        } else {
            ConSupDisclosure model = baseMapper.selectById(request.getDisId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getDisState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setDisCreateTime(model.getDisCreateTime());
            entity.setDisCreateUid(model.getDisCreateUid());
            entity.setDisCreateUname(model.getDisCreateUname());
            entity.setDisState(model.getDisState());
            entity.setDisStateTime(model.getDisStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getDisId(), request.gettId(), entity.getDisState() + "", entity.getDisCreateUid(), entity.getDisCreateUname()
                    , entity.getDisCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getDisFile()),
                    Arrays.asList(request.getFileIds(), request.getDisFileIds()), entity.getDisId());

        }
        return BaseResponse.ok(entity.getDisId());
    }

    @Override
    public BaseResponse getConSupDisclosureList(ConSupDisclosureRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Sup_Disclosure", uId, "Dis_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Dis_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSupDisclosureRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("disType").put("pName").put("conName").put("pId");
        IPage<ConSupDisclosureResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConSupDisclosureResponse> datas = baseMapper.getConSupDisclosureList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConSupDisclosureById(String disId) {
        ConSupDisclosureResponse data = baseMapper.getConSupDisclosureById(disId);
        return BaseResponse.ok(data);
    }
}
