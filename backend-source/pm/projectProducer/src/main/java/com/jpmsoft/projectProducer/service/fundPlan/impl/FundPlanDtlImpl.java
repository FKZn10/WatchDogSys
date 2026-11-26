package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundPlanDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtl;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtlCheck;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanDtlVo;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlCheckService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:06
 */
@Service
public class FundPlanDtlImpl extends ServiceImpl<FundPlanDtlMapper, FundPlanDtl> implements IFundPlanDtlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IFundPlanDtlCheckService fundPlanDtlCheckService;
    @Resource
    private IFundPlanService fundPlanService;

    @Override
    public void createOrUpdateFundPlanDtl(List<FundPlanDtlDto> fundPlanDtls,List<FundPlanDtlDto> fundPlanDtls2,List<FundPlanDtlDto> fundPlanDtls3, String fpId, String pId,String fpType) {
        if (!CollectionUtils.isEmpty(fundPlanDtls)) {
            List<FundPlanDtl> olds = getFundPlanDtlByFpId1(fpId,fpType);
            List<String> oldIds = olds.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList());
            List<String> delIds = oldIds.stream().filter(n ->
                            !fundPlanDtls.stream().map(FundPlanDtlDto::getFpdId).collect(Collectors.toList()).contains(n))
                    .collect(Collectors.toList());

            List<FundPlanDtl> adds = fundPlanDtls.stream()
                    .filter(n -> ValidateUtil.isEmpty(n.getFpdId()) || !oldIds.contains(n.getFpdId()))
                    .map(dtl -> {
                        FundPlanDtl add = new FundPlanDtl();
                        BeanUtils.copyProperties(dtl, add);
                        add.setFpId(fpId);
                        add.setPId(pId);
                        add.setFpdId(GenerateUUID.getUUID());
                        //新增时赋值最终值
                        add.setFpdDemandFinal(dtl.getFpdDemand());
                        add.setFpdDemandRatioTotalFinal(dtl.getFpdDemandRatioTotal());
                        add.setFpdDemandTotalFinal(dtl.getFpdDemandTotal());
                        sysFileAnnexService.addFormRefreshRelationFile(add.getFpdFile(), add.getFpdId());
                        return add;
                    }).collect(Collectors.toList());

            List<FundPlanDtl> updates = fundPlanDtls.stream()
                    .filter(n -> !ValidateUtil.isEmpty(n.getFpdId()) && oldIds.contains(n.getFpdId()))
                    .map(dtl -> {
                        FundPlanDtl upd = new FundPlanDtl();
                        BeanUtils.copyProperties(dtl, upd);
                        upd.setFpId(fpId);
                        upd.setPId(pId);
                        sysFileAnnexService.refreshRelationFile(upd.getFpdFile(), dtl.getFpdFileIds(), upd.getFpdId());
                        return upd;
                    }).collect(Collectors.toList());
            delFundPlanDtlByIds(delIds);
            if (adds.size() > 0) saveBatch(adds);
            if (updates.size() > 0) updateBatchById(updates);
        } else
            delFundPlanDtlByFpID1(fpId);

        if (!CollectionUtils.isEmpty(fundPlanDtls2)) {
            List<FundPlanDtl> olds = getFundPlanDtlByFpId2(fpId,fpType);
            List<String> oldIds = olds.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList());
            List<String> delIds = oldIds.stream().filter(n ->
                            !fundPlanDtls2.stream().map(FundPlanDtlDto::getFpdId).collect(Collectors.toList()).contains(n))
                    .collect(Collectors.toList());

            List<FundPlanDtl> adds = fundPlanDtls2.stream()
                    .filter(n -> ValidateUtil.isEmpty(n.getFpdId()) || !oldIds.contains(n.getFpdId()))
                    .map(dtl -> {
                        FundPlanDtl add = new FundPlanDtl();
                        BeanUtils.copyProperties(dtl, add);
                        add.setFpId(fpId);
                        add.setPId(pId);
                        add.setFpdId(GenerateUUID.getUUID());
                        //新增时赋值最终值
                        add.setFpdDemandFinal(dtl.getFpdDemand());
                        add.setFpdDemandRatioTotalFinal(dtl.getFpdDemandRatioTotal());
                        add.setFpdDemandTotalFinal(dtl.getFpdDemandTotal());
                        sysFileAnnexService.addFormRefreshRelationFile(add.getFpdFile(), add.getFpdId());
                        return add;
                    }).collect(Collectors.toList());

            List<FundPlanDtl> updates = fundPlanDtls2.stream()
                    .filter(n -> !ValidateUtil.isEmpty(n.getFpdId()) && oldIds.contains(n.getFpdId()))
                    .map(dtl -> {
                        FundPlanDtl upd = new FundPlanDtl();
                        BeanUtils.copyProperties(dtl, upd);
                        upd.setFpId(fpId);
                        upd.setPId(pId);
                        sysFileAnnexService.refreshRelationFile(upd.getFpdFile(), dtl.getFpdFileIds(), upd.getFpdId());
                        return upd;
                    }).collect(Collectors.toList());
            delFundPlanDtlByIds(delIds);
            if (adds.size() > 0) saveBatch(adds);
            if (updates.size() > 0) updateBatchById(updates);
        } else
            delFundPlanDtlByFpID2(fpId);


        if (!CollectionUtils.isEmpty(fundPlanDtls3)) {
            List<FundPlanDtl> olds = getFundPlanDtlByFpId3(fpId,fpType);
            List<String> oldIds = olds.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList());
            List<String> delIds = oldIds.stream().filter(n ->
                            !fundPlanDtls3.stream().map(FundPlanDtlDto::getFpdId).collect(Collectors.toList()).contains(n))
                    .collect(Collectors.toList());

            List<FundPlanDtl> adds = fundPlanDtls3.stream()
                    .filter(n -> ValidateUtil.isEmpty(n.getFpdId()) || !oldIds.contains(n.getFpdId()))
                    .map(dtl -> {
                        FundPlanDtl add = new FundPlanDtl();
                        BeanUtils.copyProperties(dtl, add);
                        add.setFpId(fpId);
                        add.setPId(pId);
                        add.setFpdId(GenerateUUID.getUUID());
                        //新增时赋值最终值
                        add.setFpdDemandFinal(dtl.getFpdDemand());
                        add.setFpdDemandRatioTotalFinal(dtl.getFpdDemandRatioTotal());
                        add.setFpdDemandTotalFinal(dtl.getFpdDemandTotal());
                        sysFileAnnexService.addFormRefreshRelationFile(add.getFpdFile(), add.getFpdId());
                        return add;
                    }).collect(Collectors.toList());

            List<FundPlanDtl> updates = fundPlanDtls3.stream()
                    .filter(n -> !ValidateUtil.isEmpty(n.getFpdId()) && oldIds.contains(n.getFpdId()))
                    .map(dtl -> {
                        FundPlanDtl upd = new FundPlanDtl();
                        BeanUtils.copyProperties(dtl, upd);
                        upd.setFpId(fpId);
                        upd.setPId(pId);
                        sysFileAnnexService.refreshRelationFile(upd.getFpdFile(), dtl.getFpdFileIds(), upd.getFpdId());
                        return upd;
                    }).collect(Collectors.toList());
            delFundPlanDtlByIds(delIds);
            if (adds.size() > 0) saveBatch(adds);
            if (updates.size() > 0) updateBatchById(updates);
        } else
            delFundPlanDtlByFpID3(fpId);

    }

    private List<FundPlanDtl> getFundPlanDtlByFpId3(String fpId, String fpType) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        List<FundPlanDtl>  obj= baseMapper.selectList(wrapper);
        if(ValidateUtil.isEmpty(fpType)||fpType.equals("03")||fpType.equals("")) {
            obj = obj.stream().filter(n -> ValidateUtil.isEmpty(n.getFpdType())|| n.getFpdType().equals("03")).collect(Collectors.toList());
        }
        else
        {
            obj = obj.stream().filter(n -> n.getFpdType().equals("03")).collect(Collectors.toList());
        }
        return obj;
    }

    @Override
    public void delFundPlanDtlByFpID(String fpId) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        List<FundPlanDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            delFundPlanDtlByIds(data.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList()));
    }

    @Override
    public void delFundPlanDtlByFpID1(String fpId) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        wrapper.eq("Fpd_Type", "01");
        List<FundPlanDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            delFundPlanDtlByIds(data.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList()));
    }

    @Override
    public void delFundPlanDtlByFpID2(String fpId) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        wrapper.eq("Fpd_Type", "02");
        List<FundPlanDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            delFundPlanDtlByIds(data.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList()));
    }

    @Override
    public void delFundPlanDtlByFpID3(String fpId) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        wrapper.eq("Fpd_Type", "03");
        List<FundPlanDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            delFundPlanDtlByIds(data.stream().map(FundPlanDtl::getFpdId).collect(Collectors.toList()));
    }

    @Override
    public void delFundPlanDtlByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        fundPlanDtlCheckService.delFundPlanDtlCheckByFpdIds(ids);
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateFinalInfo(FundPlanDtlCheck check) {
        FundPlanDtl dtl = baseMapper.selectById(check.getFpdId());
        if (dtl == null) return;
        dtl.setFpdDemandFinal(check.getFpdcDemand());
        dtl.setFpdDemandRatioTotalFinal(check.getFpdcDemandRatioTotal());
        //前期已付资金+本期资金需求【最终】=累计申请金额最终
        dtl.setFpdDemandTotalFinal(dtl.getFpdPayTotal().add(dtl.getFpdDemandFinal()));
        baseMapper.updateById(dtl);
        fundPlanService.updateFinalInfo(dtl.getFpId());
    }

    @Override
    public BaseResponse getFundPlanDtlList(FundPlanDtlDto request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<FundPlanDtlDto> params = new QueryMap<>(request);
        params.put("conId").put("pId");
        IPage<FundPlanDtlVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundPlanDtlVo> datas = baseMapper.getFundPlanDtlList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public List<FundPlanDtlVo> getAllFundPlanDtlByFpId(String fpId) {
        List<FundPlanDtlVo> datas = baseMapper.getAllFundPlanDtlByFpId(fpId);
        return datas;
    }

    @Override
    public List<FundPlanDtl> getFundPlanDtlByFpId(String fpId) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FundPlanDtl> getFundPlanDtlByFpId1(String fpId, String fpType) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        List<FundPlanDtl>  obj= baseMapper.selectList(wrapper);
        if(ValidateUtil.isEmpty(fpType)||fpType.equals("01")||fpType.equals("")) {
            obj = obj.stream().filter(n -> ValidateUtil.isEmpty(n.getFpdType())|| n.getFpdType().equals("01")).collect(Collectors.toList());
        }
        else
        {
            obj = obj.stream().filter(n -> n.getFpdType().equals("01")).collect(Collectors.toList());
        }
        return obj;
    }
    @Override
    public List<FundPlanDtl> getFundPlanDtlByFpId2(String fpId, String fpType) {
        QueryWrapper<FundPlanDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fp_Id", fpId);
        List<FundPlanDtl> obj= baseMapper.selectList(wrapper);
        if(fpType.equals("02")) {
            obj = obj.stream().filter(n -> ValidateUtil.isEmpty(n.getFpdType())|| n.getFpdType().equals("02")).collect(Collectors.toList());
        }
        else
        {
            obj = obj.stream().filter(n -> n.getFpdType().equals("02")).collect(Collectors.toList());
        }
        return obj;
    }
}
