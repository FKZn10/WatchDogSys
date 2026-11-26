package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentBudgetBreakdownChangeDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentBudgetBreakdownChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownChange;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdownChangeDetails;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentBudgetBreakdownChangeVo;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownChangeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IInvestmentBudgetBreakdownChangeServiceImpl extends BaseFileImpl<InvestmentBudgetBreakdownChangeMapper, InvestmentBudgetBreakdownChange, InvestmentBudgetBreakdownChangeModel> implements IInvestmentBudgetBreakdownChangeService {
    @Autowired
    private InvestmentBudgetBreakdownChangeDetailsMapper investmentBudgetBreakdownChangeDetailsMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateInvestmentBudgetBreakdownChange(InvestmentBudgetBreakdownChangeModel request) {
        String primaryId = request.getId();
        InvestmentBudgetBreakdownChange entity;
        if (primaryId == null) {
            entity = new InvestmentBudgetBreakdownChange();
            BeanUtil.copyProperties(request, entity);
            primaryId = GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            createInvestmentBudgetBreakdownChangeDetails(request, primaryId, entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getProjectBudgetFile()), entity.getId());
            //创建流程
            if (sysFileService.FileHelper(entity, request, "create", request.getFileRelationId())) {

            } else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            if (entity == null){
                entity = new InvestmentBudgetBreakdownChange();
            }
            BeanUtil.copyProperties(request, entity);
            baseMapper.updateById(entity);
            //删除原有详情
            investmentBudgetBreakdownChangeDetailsMapper.deleteByIsChangebId(primaryId);
            createInvestmentBudgetBreakdownChangeDetails(request, primaryId, entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getProjectBudgetFile()), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    private void createInvestmentBudgetBreakdownChangeDetails(InvestmentBudgetBreakdownChangeModel request, String primaryId, InvestmentBudgetBreakdownChange entity) {
        List<InvestmentBudgetBreakdownChangeDetailsModel> list = request.getDetails();
        for (InvestmentBudgetBreakdownChangeDetailsModel detailsModel : list) {
            InvestmentBudgetBreakdownChangeDetails entity2 = new InvestmentBudgetBreakdownChangeDetails();
            detailsModel.setId(GenerateUUID.getUUID());
            detailsModel.setIsbChangeId(primaryId);
            BeanUtil.copyProperties(detailsModel, entity2);
            investmentBudgetBreakdownChangeDetailsMapper.insert(entity2);
        }
    }

    @Override
    public BaseResponse getInvestmentBudgetBreakdownChangeList(InvestmentBudgetBreakdownChangeModel request) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<InvestmentBudgetBreakdownChangeModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<InvestmentBudgetBreakdownChangeModel> params = new QueryMap<>(request);
        //未发起流程仅发起人可见，审批中、已作废流程仅发起人、审批人可见
        List<InvestmentBudgetBreakdownChangeVo> investmentBudgetBreakdownList
                = baseMapper.getInvestmentBudgetBreakdownChangeList(params.getDto(), page);

        log.info("response:{}", JacksonUtils.toJSONString(investmentBudgetBreakdownList));
        return BaseResponse.ok(investmentBudgetBreakdownList, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvestmentBudgetBreakdownChangeById(String id) {
        InvestmentBudgetBreakdownChangeVo investmentBudgetBreakdownVo = baseMapper.getInvestmentBudgetBreakdownChangeById(id);
        if (investmentBudgetBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(investmentBudgetBreakdownVo);
    }

}
