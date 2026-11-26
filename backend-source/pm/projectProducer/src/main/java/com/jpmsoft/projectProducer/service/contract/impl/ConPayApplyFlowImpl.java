package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConPayApplyFlowMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowSumDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvProjectPlan;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConPayApplyFlowVo;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IConPayApplyFlowService;
import com.jpmsoft.projectProducer.service.investment.IInvProjectPlanService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConPayApplyFlowImpl extends ServiceImpl<ConPayApplyFlowMapper, ConPayApplyFlow> implements IConPayApplyFlowService {
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IInvProjectPlanService invProjectPlanService;
    @Resource
    private IConContractService contractService;

    @Override
    public BaseResponse getConPayApplyFlowList(ConPayApplyFlowDto request) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Cpaf_Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("cpaId", ValidateUtil.isEmpty(request.getCpaId()) ? null : request.getCpaId());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        List<ConPayApplyFlowDto> list = baseMapper.getConPayApplyFlowList(params);
        return BaseResponse.ok(list, list.size());
    }

    @Override
    public BaseResponse getConPayApplyFlowById(String id) {
        ConPayApplyFlowDto obj = baseMapper.getConPayApplyFlowById(id);
        if (obj != null) {
            if (!ValidateUtil.isEmpty(obj.getConIdSup())) {
                String[] conIds = obj.getConIdSup().split(",");
                QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
                wrapper.in("Con_Id", conIds);
                List<ConContract> conList = contractService.list(wrapper);
                if (conList.size() > 0) {
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    conList.forEach(n -> {
                        Map<String, Object> datas = new HashMap<>();
                        datas.put("conId", n.getConId());
                        datas.put("conName", n.getConName());
                        datas.put("conMoneyTotal", n.getConMoneyTotal());
                        datas.put("conSupIsChangeMoney", n.getConSupIsChangeMoney());
                        datas.put("conSupChangeMonType", n.getConSupChangeMonType());
                        datas.put("conSupChangeMoney", n.getConSupChangeMoney());
                        dataList.add(datas);
                    });
                    obj.setConList(dataList);
                }
            }
        }
        return BaseResponse.ok(obj, 1);
    }

    @Override
    public BaseResponse getConPayApplyFlowByConId(String conId) {
        return BaseResponse.ok(baseMapper.getConPayApplyFlowByConId(conId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConPayApplyFlow(ConPayApplyFlowDto request) {
        if (ValidateUtil.isEmpty(request.getCpafId())) {
            ConPayApplyFlow entity = new ConPayApplyFlow();
            BeanUtils.copyProperties(request, entity);
            String codeRule = null;
            entity.setCpafSerialNumber(apiUtilsService.generateCodeByCustomized("ConPayApplyFlow", request.getCpafId(), request.getConId(), "", request));
            if (ValidateUtil.isEmpty(entity.getCpafSerialNumber())) return BaseResponse.fail(ResMsg.codeGenerError);
            codeRule = "ConPayApplyFlow";
            entity.setCpafId(GenerateUUID.getUUID());
            entity.setCpafCreateTime(new Date());
            if (entity.getState() == null) {
                entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            }

            //entity.setStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getCpafId(), request.gettId(), entity.getState() + "", entity.getCpafCreateUid(), entity.getCpafApplyUname()
                    , entity.getCpafCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getCpafId());
            if (ValidateUtil.isNotEmpty(codeRule))
                apiUtilsService.refershAutoNoByCustomized("ConPayApplyFlow", request.getCpafId(), request.getConId(), "", request);
            return BaseResponse.ok(entity.getCpafId());
        } else {
            ConPayApplyFlow entity = new ConPayApplyFlow();
            BeanUtils.copyProperties(request, entity);
            //以下参数不可更改
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getCpafId(), request.gettId(), entity.getState() + "", entity.getCpafCreateUid(), entity.getCpafApplyUname()
                    , entity.getCpafCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()), Arrays.asList(request.getFileIds()), entity.getCpafId());
            return BaseResponse.ok(entity.getCpafId());
        }
    }

    @Override
    public BaseResponse getConPayApplyFlowSumById(ConPayApplyFlowSumDto request) {
        QueryWrapper<ConPayApplyFlow> conPayApplyFlowWrapper = new QueryWrapper<>();
        conPayApplyFlowWrapper.eq("P_Id", request.getpId());
        conPayApplyFlowWrapper.ne("State", "0");
        conPayApplyFlowWrapper.ne("State", "3");
        List<ConPayApplyFlow> list = baseMapper.selectList(conPayApplyFlowWrapper);
        BigDecimal applicationTotal = BigDecimal.ZERO;
        BigDecimal conApplicationTotal = BigDecimal.ZERO;
        BigDecimal contractMoneyTotal = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(list)) {
            List<ConPayApplyFlow> conList = list.stream().filter(n -> StrUtil.equals(n.getConId(), request.getConId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(conList)) {
                List<ConPayApplyFlow> cpaList = conList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpaId()) && n.getCpaId().equals(request.getCpaId()) && n.getState() == 7).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(cpaList)) {
                    applicationTotal = cpaList.stream().map(ConPayApplyFlow::getCpafApplication).filter(ValidateUtil::isNotNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                    contractMoneyTotal = cpaList.stream().map(ConPayApplyFlow::getCpafContractMoney).filter(ValidateUtil::isNotNull).reduce(BigDecimal.ZERO, BigDecimal::add);
                }
                conApplicationTotal = conList.stream().filter(n -> n.getState() == 7).map(ConPayApplyFlow::getCpafApplication).filter(ValidateUtil::isNotNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }
        List<String> pIds = new ArrayList<>();
        pIds.add(request.getpId());
//        Map<String, Object> params = new HashMap<>();
//        params.put("pIds", pIds);
        List<InvProjectPlan> invProjectPlanList = invProjectPlanService.getInvProjectPlanListByPids(pIds);

        Map<String, Object> data = new HashMap<>();
        data.put("applicationTotal", applicationTotal);
        data.put("conApplicationTotal", conApplicationTotal);
        data.put("contractMoneyTotal", contractMoneyTotal);
        data.put("invProjectPlan", invProjectPlanList.stream().filter(n -> n.getIppYear().equals(request.getYear())));
        data.put("conPayApplyFlow", list);

        return BaseResponse.ok(data, 1);
    }

    @Override
    public BaseResponse getConPayApplyFlowSumByConId(String conId) {
        List<ConPayApplyFlow> list = baseMapper.getConPayApplyFlowSum(conId);
        BigDecimal total = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(list)) {
            total = list.stream().map(k -> k.getCpafApplication()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return BaseResponse.ok(total, 1);
    }


    @Override
    public BaseResponse getConPayApplyFlowDataList(ConPayApplyFlowVo vo) {

        if (vo.getStateTimes() != null && vo.getStateTimes().size() > 0) {
            if (vo.getStateTimes().get(0) != null && !vo.getStateTimes().get(0).equals("")) {
                vo.setDateStr(vo.getStateTimes().get(0));
            }
            if (vo.getStateTimes().get(1) != null && !vo.getStateTimes().get(1).equals("")) {
                vo.setDateEnd(vo.getStateTimes().get(1));
            }
        }

        IPage<ConPayApplyFlowVo> data = baseMapper.getConPayApplyFlowDataList(new Page<>(vo.getPage(), vo.getSize()), vo);
        return BaseResponse.ok(data.getRecords(), (int) data.getTotal());
    }

    @Override
    public BaseResponse getConPayApplyFlowStatsList(ConPayApplyFlowVo vo) {
        if (vo.getStateTimes() != null && vo.getStateTimes().size() > 0) {
            if (vo.getStateTimes().get(0) != null && !vo.getStateTimes().get(0).equals("")) {
                vo.setDateStr(vo.getStateTimes().get(0));
            }
            if (vo.getStateTimes().get(1) != null && !vo.getStateTimes().get(1).equals("")) {
                vo.setDateEnd(vo.getStateTimes().get(1));
            }
        }
        IPage<ConPayApplyFlowVo> data = baseMapper.getConPayApplyFlowDataList(new Page<>(vo.getPage(), vo.getSize()), vo);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data.getRecords());
        Map<String, BigDecimal> stats = baseMapper.statsPayApplyFlow(vo);
        if (stats == null) stats = new HashMap<>();
        if (!stats.containsKey("cpafApplicationSum")) stats.put("cpafApplicationSum", BigDecimal.ZERO);
        if (!stats.containsKey("cpafRemainingAmountSum")) stats.put("cpafRemainingAmountSum", BigDecimal.ZERO);
        BigDecimal payApprovalSum = baseMapper.statsConPayApproval(vo);
        stats.put("payApprovalSum", ValidateUtil.isNull(payApprovalSum) ? BigDecimal.ZERO : payApprovalSum);
        res.put("stats", stats);
        return BaseResponse.ok(res, (int) data.getTotal());
    }


    @Override
    public List<ConPayApplyFlow> getConPayById(String payId) {
        LambdaQueryWrapper<ConPayApplyFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPayApplyFlow::getPayId, payId);
        return baseMapper.selectList(queryWrapper);
    }
}
