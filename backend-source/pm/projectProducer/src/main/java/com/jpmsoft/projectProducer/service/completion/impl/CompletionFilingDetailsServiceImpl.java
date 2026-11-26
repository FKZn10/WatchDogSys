package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Completion.CompletionFilingDetailsRequest;
import com.jpmsoft.common.model.response.Completion.CompletionFilingDetailsResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.completion.CompletionFilingDetailsMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionFilingDetails;
import com.jpmsoft.projectProducer.service.completion.ICompletionFilingDetailsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CompletionFilingDetailsServiceImpl extends ServiceImpl<CompletionFilingDetailsMapper, CompletionFilingDetails> implements ICompletionFilingDetailsService {

    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public void createiCompletionFilingDetails(List<CompletionFilingDetailsRequest> completionFilingDetails,String cfId,String type) {
        if(type.equals("delete")){
            deleteCompletionFilingDetails(cfId);
        }
        List<CompletionFilingDetails> detailsList  = new ArrayList<>();
        for (CompletionFilingDetailsRequest detail : completionFilingDetails) {
            CompletionFilingDetails entity = new CompletionFilingDetails();
            BeanUtils.copyProperties(detail,entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setCfId(cfId);
            detailsList.add(entity);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(entity.getCfdFile()),
                    Arrays.asList(detail.getCfdFileIds()), entity.getId());
        }
        if(!CollectionUtils.isEmpty(detailsList)){
            saveBatch(detailsList);
        }
    }

    @Override
    public List<CompletionFilingDetailsResponse> getCompletionFilingDetailsByCfid(QueryWrapper<CompletionFilingDetails> queryWrapper) {

        List<CompletionFilingDetails> detailsList = baseMapper.selectList(queryWrapper);
        List<CompletionFilingDetailsResponse> res=new ArrayList<>();
        if (!CollectionUtils.isEmpty(detailsList))
        {
            detailsList.forEach(e->{
                CompletionFilingDetailsResponse r=new CompletionFilingDetailsResponse();
                BeanUtils.copyProperties(e,r);
                res.add(r);
            });
        }
        return res;
    }

    @Override
    public void deleteCompletionFilingDetails(String cfId) {
        LambdaQueryWrapper<CompletionFilingDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompletionFilingDetails::getCfId,cfId);
        baseMapper.delete(queryWrapper);
    }
}
