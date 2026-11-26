package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Contract.ConVisaFeeRequest;
import com.jpmsoft.common.model.response.Contract.ConVisaFeeResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConVisaFeeMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaFee;
import com.jpmsoft.projectProducer.service.contract.IConVisaFeeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ConVisaFeeImpl extends ServiceImpl<ConVisaFeeMapper, ConVisaFee> implements IConVisaFeeService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "vfId", codeParam = "vfCode", method = "setVfCode", codeRule = "ConVisaFee", beanClass = ConVisaFeeRequest.class)
    public BaseResponse createOrUpdateConVisaFee(ConVisaFeeRequest request) {
        ConVisaFee entity = new ConVisaFee();
        if (ValidateUtil.isEmpty(request.getVfCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);

        QueryWrapper<ConVisaFee> wrapper = new QueryWrapper<>();
        wrapper.eq("Vf_Code", request.getVfCode());
        if (!ValidateUtil.isEmpty(entity.getVfId()))
            wrapper.ne("Vf_Id", request.getVfId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);

        if (ValidateUtil.isEmpty(request.getVfId())) {
//            ConVisaRecordPojo recordPojo = conVisaRecordService.getById(request.getVrId());
//            if (recordPojo == null)
//                return  BaseResponse.fail(ResMsg.notConVisa);
//            entity.setConId(recordPojo.getConId());
            entity.setVfCreateTime(new Date());
            entity.setVfId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getVfId(), request.gettId(), entity.getVfState() + "", entity.getVfCreateUid(), entity.getVfCreateUname()
                    , entity.getVfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getVfFileContact(), request.getVfFileEstimate()), entity.getVfId());
        } else {
            ConVisaFee model = baseMapper.selectById(request.getVfId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getVfState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setVfCreateTime(model.getVfCreateTime());
            entity.setVfCreateUid(model.getVfCreateUid());
            entity.setVfCreateUname(model.getVfCreateUname());
            entity.setVfState(model.getVfState());
            entity.setVfStateTime(model.getVfStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getVfId(), request.gettId(), entity.getVfState() + "", entity.getVfCreateUid(), entity.getVfCreateUname()
                    , entity.getVfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getVfFileContact(), request.getVfFileEstimate()),
                    Arrays.asList(request.getFileIds(), request.getVfFileContactIds(), request.getVfFileEstimateIds()), entity.getVfId());
        }
        return BaseResponse.ok(entity.getVfId());
    }

    @Override
    public BaseResponse getConVisaFeeList(ConVisaFeeRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Visa_Fee", uId, "Vf_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Vf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConVisaFeeRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conId").put("pId").put("pName").put("conName").put("vfCode").put("svisCode").put("svisSubCode")
                .put("vfContractor").put("vfState").put("vfAmountInvolved").put("vfVisaCategory").put("conWhere").put("svisProjectName");
        IPage<ConVisaFeeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConVisaFeeResponse> datas = baseMapper.getConVisaFeeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConVisaFeeByVfId(String vfId, Integer conWhere) {
        conWhere = ValidateUtil.isNull(conWhere) ? 0 : conWhere;
        ConVisaFeeResponse data = baseMapper.getConVisaFeeByVfId(vfId, conWhere);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getVisaAmt(String conId) {
        LambdaQueryWrapper<ConVisaFee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConVisaFee::getConId,conId);
        queryWrapper.eq(ConVisaFee::getVfState,7);
         List<ConVisaFee> conVisaFees = baseMapper.selectList(queryWrapper);
         Map<String,Object> map = new HashMap<>();
         if (ValidateUtil.isNotNull(conVisaFees)){
             //增加的金额
              BigDecimal increaseAmt = conVisaFees.stream().filter(e ->ValidateUtil.isNotNull(e.getVfMoneyType())  && e.getVfMoneyType().equals("1")).map(ConVisaFee::getVfMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
              //减少的金额
             BigDecimal reduceAmt = conVisaFees.stream().filter(e -> ValidateUtil.isNotNull(e.getVfMoneyType())  &&  e.getVfMoneyType().equals("0")).map(ConVisaFee::getVfMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
             BigDecimal subtrAmt =  MathUtil.decSubtract(increaseAmt,reduceAmt);
             map.put("increaseAmt",increaseAmt);
             map.put("reduceAmt",reduceAmt);
             map.put("subtrAmt",subtrAmt);
         }

        return BaseResponse.ok(map);
    }
}
