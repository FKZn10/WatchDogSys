package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Oa.OaWorkorderRequest;
import com.jpmsoft.common.model.response.Oa.OaWorkorderResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaWorkorderMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkorder;
import com.jpmsoft.projectProducer.service.oa.IOaWorkorderService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
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
public class OaWorkorderImpl extends ServiceImpl<OaWorkorderMapper, OaWorkorder> implements IOaWorkorderService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "woId", codeParam = "woCode", method = "setWoCode", codeRule = "OaWorkorder", beanClass = OaWorkorderRequest.class)
    public BaseResponse createOrUpdateOaWorkorder(OaWorkorderRequest request) {
        OaWorkorder entity = new OaWorkorder();
        if (ValidateUtil.isEmpty(request.getWoCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getWoId())) {
            entity.setWoCreateTime(new Date());
//            entity.setWoCode(createWoCode());
            entity.setWoId(GenerateUUID.getUUID());
            entity.setWoState(ValidateUtil.isEmpty(request.getWoState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getWoState());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getWoId(), request.gettId(), entity.getWoState() + "", entity.getWoCreateUid(), entity.getWoCreateUname()
                    , entity.getWoCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getWoId());

        } else {
            OaWorkorder model = baseMapper.selectById(request.getWoId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getWoState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setWoState(model.getWoState());
            entity.setWoStateTime(model.getWoStateTime());
            entity.setWoCreateTime(model.getWoCreateTime());
            entity.setWoCreateUid(model.getWoCreateUid());
            entity.setWoCreateUname(model.getWoCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getWoId(), request.gettId(), entity.getWoState() + "", entity.getWoCreateUid(), entity.getWoCreateUname()
                    , entity.getWoCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getWoId());

        }
        return BaseResponse.ok(entity.getWoId());
    }

    @Override
    public BaseResponse getOaWorkorderList(OaWorkorderRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("OA_WorkOrder", uId, "Wo_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Wo_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<OaWorkorderRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("woType").put("woTitle").put("pId")
                .put("woCode").put("woPriority").put("woState").put("woResolvedState");
        IPage<OaWorkorderResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OaWorkorderResponse> datas = baseMapper.getOaWorkorderList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getOaWorkorderById(String wrId) {
        OaWorkorderResponse data = baseMapper.getOaWorkorderById(wrId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse updateReplyInfo(OaWorkorderRequest request) {
        OaWorkorder model = baseMapper.selectById(request.getWoId());
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        model.setWoReplyContent(request.getWoReplyContent());
        model.setWoReplyUname(request.getWoReplyUname());
        model.setWoReplyReason(request.getWoReplyReason());
        model.setWoReplyTime(request.getWoReplyTime());
        model.setWoReplyUid(request.getWoReplyUid());
        model.setWoReplySolution(request.getWoReplySolution());
        model.setWoResolvedState(request.getWoResolvedState());
        model.setWoActualTime(request.getWoActualTime());
        baseMapper.updateById(model);
        return BaseResponse.ok(model.getWoId());
    }


//    private String createWoCode() {
//        QueryWrapper<OaWorkorderPojo> wrapper = new QueryWrapper<>();
//        Integer count = baseMapper.selectCount(wrapper);
//        String code = (count + 1) + "";
//        if (code.length() < 8) {
//            for (int i = 0; i < (8 - count.toString().length()); i++) {
//                code = "0" + code;
//            }
//            return code;
//        } else
//            return (count + 1) + "";
//    }

}
