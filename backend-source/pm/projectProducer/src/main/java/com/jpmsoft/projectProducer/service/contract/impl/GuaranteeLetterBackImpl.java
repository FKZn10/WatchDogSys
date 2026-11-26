package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterBackRequest;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterBackResponse;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterSignResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.GuaranteeLetterBackMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterBack;
import com.jpmsoft.projectProducer.service.contract.IGuaranteeLetterBackService;
import com.jpmsoft.projectProducer.service.contract.IGuaranteeLetterSignService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class GuaranteeLetterBackImpl extends ServiceImpl<GuaranteeLetterBackMapper, GuaranteeLetterBack> implements IGuaranteeLetterBackService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IGuaranteeLetterSignService guaranteeLetterSignService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGuaranteeLetterBack(GuaranteeLetterBackRequest request) {
        GuaranteeLetterBack entity = new GuaranteeLetterBack();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getGlbId())) {
            entity.setGlbId(GenerateUUID.getUUID());
            entity.setGlbCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getGlbId(), request.gettId(), entity.getGlbState() + "", entity.getGlbCreateUid(), entity.getGlbCreateUname()
                    , entity.getGlbCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getGlbId());

        } else {
            GuaranteeLetterBack model = baseMapper.selectById(request.getGlbId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setGlbCreateTime(model.getGlbCreateTime());
            entity.setGlbCreateUid(model.getGlbCreateUid());
            entity.setGlbCreateUname(model.getGlbCreateUname());
            entity.setGlbState(model.getGlbState());
            entity.setGlbStateTime(model.getGlbStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getGlbId(), request.gettId(), entity.getGlbState() + "", entity.getGlbCreateUid(), entity.getGlbCreateUname()
                    , entity.getGlbCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getGlbId());

        }
        return BaseResponse.ok(entity.getGlbId());
    }

    @Override
    public BaseResponse getGuaranteeLetterBackList(GuaranteeLetterBackRequest request, String uId) {
        //根据保函签收做鉴权
        String authSql = sysUserService.getAuthSql("Guarantee_Letter_Back", uId, "Glb_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GuaranteeLetterBackRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName").put("secName")
                .put("glsCode").put("glbCompanyName").put("glbCompletion");
        IPage<GuaranteeLetterBackResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GuaranteeLetterBackResponse> datas = baseMapper.getGuaranteeLetterBackList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getGuaranteeLetterBackByglbId(String glbId) {
        return BaseResponse.ok(baseMapper.getGuaranteeLetterBackByglbId(glbId));
    }

    @Override
    public BaseResponse getGuaranteeLetterBackAndSignByglbId(String glbId) {
        GuaranteeLetterBackResponse data = baseMapper.getGuaranteeLetterBackByglbId(glbId);
        if (data != null) {
            GuaranteeLetterSignResponse model = guaranteeLetterSignService.getGuaranteeLetterSignById(data.getGlsId());
            if (model != null)
                model.setGuaranteeLetterBack(data);
            return BaseResponse.ok(model);
        } else
            return BaseResponse.ok(data);
    }

    @Override
    public GuaranteeLetterBack getGuaranteeLetterBackByglsId(String glsId) {
        QueryWrapper<GuaranteeLetterBack> wrapper = new QueryWrapper<>();
        wrapper.ne("Glb_State", ApprovalStatusEnum.Invalid.getIndex());
        wrapper.eq("Gls_Id", glsId);
        wrapper.orderByDesc("Glb_Create_Time");
        List<GuaranteeLetterBack> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0)
            return datas.get(0);
        else
            return null;
    }

    @Override
    public List<GuaranteeLetterBack> getGuaranteeLetterBackByConId(String conId) {
        return baseMapper.getGuaranteeLetterBackByConId(conId);
    }
}
