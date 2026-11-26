package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConBoqEcchangeDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtl;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConBoqEcchangeDtl;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEc;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareDtl;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;
import com.jpmsoft.projectProducer.service.conBoq.IConBoqDtlService;
import com.jpmsoft.projectProducer.service.contract.IConBoqEcchangeDtlService;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/24 9:43
 */
@Service
public class ConBoqEcchangeDtlImpl extends ServiceImpl<ConBoqEcchangeDtlMapper, ConBoqEcchangeDtl>
        implements IConBoqEcchangeDtlService {

    @Resource
    private IConEcService conEcService;
    @Resource
    private IConMeasureRecordService conMeasureRecordService;
    @Resource
    private IConBoqDtlService conBoqDtlService;
    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;


    @Override
    public BaseResponse createOrUpdateBoqEcChange(List<ConBoqDtlDto> request, String ecId) {
        deleteByEcId(ecId);
        if (!CollectionUtils.isEmpty(request)) {
            List<ConBoqEcchangeDtl> entities = request.stream().map(d -> {
                ConBoqEcchangeDtl entity = new ConBoqEcchangeDtl();
                BeanUtil.copyProperties(d, entity);
                entity.setEcId(ecId);
                entity.setId(GenerateUUID.getUUID());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getBoqEcChangeDtlByEcId(String ecId) {
        QueryWrapper<ConBoqEcchangeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ec_Id", ecId);
        List<ConBoqEcchangeDtl> data = baseMapper.selectList(wrapper);
        List<String> boqIds = data.stream().map(e -> e.getBoqId()).collect(Collectors.toList());
        List<String> boqdParIds = data.stream().map(e -> e.getBoqdParentId()).collect(Collectors.toList());
        List<ConBoqDtl> conBoqDtlList = conBoqDtlService.getBoQdName(boqIds, boqdParIds);

        if (!CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(data.stream().map(d -> {
                ConBoqDtlDto e = new ConBoqDtlDto();
                BeanUtil.copyProperties(d, e);
                Optional<ConBoqDtl> matchingBoqDtl =
                        conBoqDtlList.stream().filter(f -> f.getBoqId().equals(e.getBoqId()) && f.getBoqdId().equals(e.getBoqdParentId())).findFirst();
                matchingBoqDtl.ifPresent(f -> e.setBoqdParentName(ValidateUtil.isNotEmpty(f.getBoqdName()) ? f.getBoqdName() : ""));
                if (ValidateUtil.isEmpty(e.getBoqdBddId())) {//如果子表没存则去工程量清单查询
                    Optional<ConBoqDtl> currentBoqd =
                            conBoqDtlList.stream().filter(f -> f.getBoqId().equals(e.getBoqId()) && f.getBoqdId().equals(e.getBoqdId())).findFirst();
                    currentBoqd.ifPresent(f -> e.setBoqdBddId(f.getBoqdBddId()));
                }
                if (ValidateUtil.isNotEmpty(e.getBoqdBddId())) {
                    InvBudgetDeclareDtl bdd = invBudgetDeclareDtlService.getById(e.getBoqdBddId());
                    if (bdd != null)
                        e.setBddTotal(bdd.getBddTotal());
                }
                return e;
            }).collect(Collectors.toList()));
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getBoqEcChangeDtlByBoqdId(String boqdId) {
        QueryWrapper<ConBoqEcchangeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("boqd_id", boqdId);
        List<ConBoqEcchangeDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<ConEc> coEcs = conEcService.selectApprovedByIds(data.stream().map(ConBoqEcchangeDtl::getEcId).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(coEcs)) {
                List<ConBoqDtlDto> res = new ArrayList<>();
                coEcs.forEach(ec -> {
                    res.addAll(data.stream().filter(f -> f.getEcId().equals(ec.getEcId())).map(d -> {
                        ConBoqDtlDto r = new ConBoqDtlDto();
                        BeanUtil.copyProperties(d, r);
                        return r;
                    }).collect(Collectors.toList()));
                });
                return BaseResponse.ok(res);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse can2Change(ConBoqDtlDto request) {
        if (ValidateUtil.isAnObjEmpty(request.getPageType()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (request.getPageType().equals("02") || request.getPageType().equals("03")) {
            if (ValidateUtil.isNull(request.getBoqdChangeAmount()) ||
                    ValidateUtil.isNull(request.getBoqdAmount()))
                return BaseResponse.fail(ResMsg.notReqParam);
            List<ConMeasureRecordVo> cmrs = conMeasureRecordService.getApprovedData(Arrays.asList(request.getBoqdId()));
            if (CollectionUtils.isEmpty(cmrs)) return BaseResponse.ok(true);
            if (request.getPageType().equals("02")) {//变更
                if (request.getBoqdChangeAmount().compareTo(request.getBoqdAmount()) < 0) {//减少工程量
                    Float ybs = 0F;
                    for (ConMeasureRecordVo c : cmrs) {
                        ybs = MathUtil.floatAddFloat(ybs, c.getMearFinalAmount());
                    }
                    if (request.getBoqdChangeAmount().compareTo(ybs) < 0)
                        return BaseResponse.ok("已报审工程量大于变更后工程量，无法变更！", false);
                }
            }
            if (request.getPageType().equals("03")) {//删除
                if (!CollectionUtils.isEmpty(cmrs))
                    return BaseResponse.ok("已报审，无法删除！", false);
            }
        }
        return BaseResponse.ok(true);
    }

    @Override
    public void approved2RefreshConBoqDtl(String ecId) {
        ConEc ec = conEcService.getById(ecId);
        if (ec != null && ec.getEcState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            QueryWrapper<ConBoqEcchangeDtl> wrapper = new QueryWrapper<>();
            wrapper.eq("Ec_Id", ecId);
            List<ConBoqEcchangeDtl> data = baseMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(data)) {
//                List<String> boqdIds = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getBoqdId())).map(ConBoqEcchangeDtl::getBoqdId).collect(Collectors.toList());
//                List<ConMeasureRecordVo> cmrs = conMeasureRecordService.getApprovedData(boqdIds);
//                List<ConBoqDtl> boqDtls = conBoqDtlService.listByIds(boqdIds);
                List<ConBoqDtl> adds = new ArrayList<>();
                List<ConBoqDtl> updates = new ArrayList<>();
//                List<ConBoqDtl> deletes = new ArrayList<>();
                for (ConBoqEcchangeDtl d : data) {
                    ConBoqDtl boq = new ConBoqDtl();
                    BeanUtil.copyProperties(d, boq);
                    //region 新增
                    if (d.getPageType().equals("01")) {
                        boq.setBoqdId(GenerateUUID.getUUID());
                        adds.add(boq);
                    }
                    //endregion
                    //region 变更
                    if (d.getPageType().equals("02"))//变更
                    {
                        if (d.getBoqdChangeAmount().compareTo(d.getBoqdAmount()) > 0) {//增加工程量
                            //原工程量清单标记变更
                            ConBoqDtl add = new ConBoqDtl();
                            BeanUtil.copyProperties(boq, add);
                            add.setPageType("02");
                            updates.add(add);
                            //新增量标记新增
                            boq.setBoqdAmount(MathUtil.floatSubtractFloat(d.getBoqdChangeAmount(), d.getBoqdAmount()));
                            boq.setBoqdChangePreId(boq.getBoqdId());
                            boq.setBoqdId(GenerateUUID.getUUID());
                            boq.setPageType("01");
                            adds.add(boq);
                        }
                        if (d.getBoqdChangeAmount().compareTo(d.getBoqdAmount()) < 0) {//减少工程量
                            boq.setBoqdAmount(d.getBoqdChangeAmount());
                            updates.add(boq);
                        }
                    }
                    //endregion
                    //region 新增删除
                    if (d.getPageType().equals("03")) {
                        boq.setPageType(d.getPageType());
                        updates.add(boq);
                    }
                    //endregion
                }
                if (!CollectionUtils.isEmpty(adds)) conBoqDtlService.saveBatch(adds);
                if (!CollectionUtils.isEmpty(updates)) conBoqDtlService.updateBatchById(updates);
            }
        } else throw new JPMException(ResMsg.stateCannotAct);
    }

    @Override
    public void deleteByEcId(String ecId) {
        QueryWrapper<ConBoqEcchangeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Ec_Id", ecId);
        baseMapper.delete(wrapper);
    }
}
