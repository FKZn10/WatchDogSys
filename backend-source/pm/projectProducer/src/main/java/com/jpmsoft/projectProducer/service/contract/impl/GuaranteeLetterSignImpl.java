package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Contract.GuaranteeLetterSignRequest;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterBackResponse;
import com.jpmsoft.common.model.response.Contract.GuaranteeLetterSignResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.GuaranteeLetterSignMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterBack;
import com.jpmsoft.projectProducer.pojo.entity.contract.GuaranteeLetterSign;
import com.jpmsoft.projectProducer.service.contract.IGuaranteeLetterBackService;
import com.jpmsoft.projectProducer.service.contract.IGuaranteeLetterSignService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class GuaranteeLetterSignImpl extends ServiceImpl<GuaranteeLetterSignMapper, GuaranteeLetterSign> implements IGuaranteeLetterSignService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IGuaranteeLetterBackService guaranteeLetterBackService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "glsId", codeParam = "glsCode", method = "setGlsCode", codeRule = "GuaranteeLetterSign", beanClass = GuaranteeLetterSignRequest.class)
    public BaseResponse createOrUpdateGuaranteeLetterSign(GuaranteeLetterSignRequest request) {
        String codeRule = null;
        String numCode = apiUtilsService.getAutoCode("GuaranteeLetterSignNum", request);
        if (ValidateUtil.isNotEmpty(numCode)) {
            request.setGlsNum(numCode);
            codeRule = "GuaranteeLetterSignNum";
        }
        GuaranteeLetterSign entity = new GuaranteeLetterSign();
        if (ValidateUtil.isEmpty(request.getGlsCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<GuaranteeLetterSign> wrapper = new QueryWrapper<>();
        wrapper.eq("Gls_Code", request.getGlsCode());
        wrapper.ne("Gls_State", ApprovalStatusEnum.Invalid.getIndex());
        if (!ValidateUtil.isEmpty(request.getGlsId()))
            wrapper.ne("Gls_Id", request.getGlsId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(request.getGlsId())) {
            entity.setGlsId(GenerateUUID.getUUID());
            entity.setGlsCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getGlsId(), request.gettId(), entity.getGlsState() + "", entity.getGlsCreateUid(), entity.getGlsCreateUname()
                    , entity.getGlsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getGlsId());

        } else {
            GuaranteeLetterSign model = baseMapper.selectById(request.getGlsId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setGlsCreateTime(model.getGlsCreateTime());
            entity.setGlsCreateUid(model.getGlsCreateUid());
            entity.setGlsCreateUname(model.getGlsCreateUname());
            entity.setGlsState(model.getGlsState());
            entity.setGlsStateTime(model.getGlsStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getGlsId(), request.gettId(), entity.getGlsState() + "", entity.getGlsCreateUid(), entity.getGlsCreateUname()
                    , entity.getGlsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getGlsId());

        }
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, request);
        return BaseResponse.ok(entity.getGlsId());
    }

    @Override
    public BaseResponse getGuaranteeLetterSignList(GuaranteeLetterSignRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Guarantee_Letter_Sign", uId, "Gls_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Gls_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GuaranteeLetterSignRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("glsIsrenewal").put("duwSoon").put("overDue")
                .put("haveBack").put("conId").put("glsType").put("pName").put("pId").put("conName")
                .put("secName").put("glsState").put("glsCode").put("glsCreateUname")
                .put("glsGuarantor").put("glsWarranteeName").put("conCode")
                .put(request.getGlsIssuanceDates(), "glsIssuanceStart", "glsIssuanceEnd")
                .put(request.getGlsDueDates(), "glsDueStart", "glsDueEnd")
                .put(request.getGlsCreateTimes(), "glsCreateStart", "glsCreateEnd")
                .put(request.getGlsMoneys(), "glsMoneyStart", "glsMoneyEnd").put("glsJlxs").put("glsBeneficiaryName").put("companyPartyAName")
                .put("companyPartybName").put("glbCompanyName").put("glbState");
        IPage<GuaranteeLetterSignResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GuaranteeLetterSignResponse> datas = baseMapper.getGuaranteeLetterSignList(page, params.getQuery());
        datas.forEach(d->{
            d.setGlsWarranteeTel(VoInfoUtil.phoneHandle(d.getGlsWarranteeTel()));
        });
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getGuaranteeLetterSignByglsId(String glsId) {
        GuaranteeLetterSignResponse data = baseMapper.geteGuaranteeLetterSignByglsId(glsId);
        if (data != null) {
            GuaranteeLetterBack child = guaranteeLetterBackService.getGuaranteeLetterBackByglsId(data.getGlsId());
            GuaranteeLetterBackResponse node = new GuaranteeLetterBackResponse();
            if (child != null)
                BeanUtils.copyProperties(child, node);
            data.setGuaranteeLetterBack(node);
            data.setGlsWarranteeTel(VoInfoUtil.phoneHandle(data.getGlsWarranteeTel()));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getGuaranteeLetterSignByContract(String conId) {
        GuaranteeLetterSignResponse data = baseMapper.getGuaranteeLetterSignByContract(conId);
        if (data != null) {
            GuaranteeLetterBack child = guaranteeLetterBackService.getGuaranteeLetterBackByglsId(data.getGlsId());
            GuaranteeLetterBackResponse node = new GuaranteeLetterBackResponse();
            if (child != null)
                BeanUtils.copyProperties(child, node);
            data.setGuaranteeLetterBack(node);
            data.setGlsWarranteeTel(VoInfoUtil.phoneHandle(data.getGlsWarranteeTel()));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public GuaranteeLetterSignResponse getGuaranteeLetterSignById(String glsId) {
        GuaranteeLetterSignResponse data=baseMapper.geteGuaranteeLetterSignByglsId(glsId);
        if (data!=null)
            data.setGlsWarranteeTel(VoInfoUtil.phoneHandle(data.getGlsWarranteeTel()));
        return data;
    }

    @Override
    public List<GuaranteeLetterSign> getGuaranteeLetterSignByConId(String conId) {
        QueryWrapper<GuaranteeLetterSign> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.ne("Gls_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BaseResponse getGuaranteeLetterExistence(String conId) {
        if(ValidateUtil.isEmpty(conId)) return BaseResponse.fail("缺少必填参数");
        LambdaQueryWrapper<GuaranteeLetterSign> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GuaranteeLetterSign::getConId,conId);
        queryWrapper.eq(GuaranteeLetterSign::getGlsJlxs,"01");
        queryWrapper.eq(GuaranteeLetterSign::getGlsState,7);
        queryWrapper.ge(GuaranteeLetterSign::getGlsDueDate, LocalDateTime.now());
         Long aLong = baseMapper.selectCount(queryWrapper);
        return aLong > 0 ? BaseResponse.ok() : BaseResponse.fail("该合同未存在保函信息或已过期,不可支付");
    }
}
