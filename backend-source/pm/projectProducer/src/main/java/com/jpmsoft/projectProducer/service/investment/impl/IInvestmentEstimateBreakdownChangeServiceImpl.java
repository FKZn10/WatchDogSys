package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownChangeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownChangeDetailsMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvestmentEstimateBreakdownChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownChange;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentEstimateBreakdownChangeDetails;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentEstimateBreakdownChangeVo;
import com.jpmsoft.projectProducer.service.investment.IInvestmentEstimateBreakdownChangeService;
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
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IInvestmentEstimateBreakdownChangeServiceImpl extends BaseFileImpl<InvestmentEstimateBreakdownChangeMapper, InvestmentEstimateBreakdownChange, InvestmentEstimateBreakdownChangeModel> implements IInvestmentEstimateBreakdownChangeService {

    @Autowired
    private InvestmentEstimateBreakdownChangeDetailsMapper investmentEstimateBreakdownChangeDetailsMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateInvestmentEstimateBreakdownChange(InvestmentEstimateBreakdownChangeModel model) {
        String primaryId = model.getId();
        InvestmentEstimateBreakdownChange entity;
        if (primaryId == null) {
            entity = new InvestmentEstimateBreakdownChange();
            BeanUtil.copyProperties(model, entity);
            primaryId = GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            createInvestmentEstimateBreakdownChangeDetails(model, primaryId, entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), entity.getId());
            //创建流程
            if (sysFileService.FileHelper(entity, model, "create", model.getFileRelationId())) {

            } else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            if (entity == null){
                entity = new InvestmentEstimateBreakdownChange();
            }
            BeanUtil.copyProperties(model, entity);
            saveOrUpdate(entity);
            //删除原有详情
            investmentEstimateBreakdownChangeDetailsMapper.deleteByIsbChangeId(primaryId);
            createInvestmentEstimateBreakdownChangeDetails(model, primaryId, entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    private void createInvestmentEstimateBreakdownChangeDetails(InvestmentEstimateBreakdownChangeModel model, String primaryId, InvestmentEstimateBreakdownChange entity) {
        List<InvestmentEstimateBreakdownChangeDetailsModel> list = model.getDetails();
        for (InvestmentEstimateBreakdownChangeDetailsModel detailsModel : list) {
            InvestmentEstimateBreakdownChangeDetails entity2 = new InvestmentEstimateBreakdownChangeDetails();
            detailsModel.setId(GenerateUUID.getUUID());
            detailsModel.setIsbChangeId(primaryId);
            BeanUtil.copyProperties(detailsModel, entity2);
            investmentEstimateBreakdownChangeDetailsMapper.insert(entity2);
        }
    }

    @Override
    public BaseResponse getInvestmentEstimateBreakdownChangeList(InvestmentEstimateBreakdownChangeModel model) {
        log.info("request:{}", JacksonUtils.toJSONString(model));
        IPage<InvestmentEstimateBreakdownChangeModel> page = new Page<>(model.getPage(), model.getSize());
        QueryMap<InvestmentEstimateBreakdownChangeModel> params = new QueryMap<>(model);
        //未发起流程仅发起人可见，审批中、已作废流程仅发起人、审批人可见
        List<InvestmentEstimateBreakdownChangeVo> investmentEstimateBreakdownList
                = baseMapper.getInvestmentEstimateBreakdownChangeList(params.getDto(), page);

        log.info("response:{}", JacksonUtils.toJSONString(investmentEstimateBreakdownList));
        return BaseResponse.ok(investmentEstimateBreakdownList, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvestmentEstimateBreakdownChangeById(String id) {
        InvestmentEstimateBreakdownChangeVo investmentEstimateBreakdownVo = baseMapper.getInvestmentEstimateBreakdownChangeById(id);
        if (investmentEstimateBreakdownVo == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(investmentEstimateBreakdownVo);
    }

}
