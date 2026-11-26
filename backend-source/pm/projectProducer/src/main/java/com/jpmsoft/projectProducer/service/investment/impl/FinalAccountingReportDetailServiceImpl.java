package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.FinalAccountingReportDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingReportDetail;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportDetailVo;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingReportDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinalAccountingReportDetailServiceImpl extends ServiceImpl<FinalAccountingReportDetailMapper, FinalAccountingReportDetail> implements FinalAccountingReportDetailService {
    @Override
    public void saveReportDetail(List<FinalAccountingReportDetailVo> detailList, String id, int type) {
        if(type == 1){
            deleteReportId(id);
        }
        if(!CollectionUtils.isEmpty(detailList)){
            List<FinalAccountingReportDetail> details = new ArrayList<>();
            for (FinalAccountingReportDetailVo vo : detailList) {
                FinalAccountingReportDetail detail = new FinalAccountingReportDetail();
                BeanUtils.copyProperties(vo,detail);
                detail.setId(GenerateUUID.getUUID());
                detail.setFarId(id);
                details.add(detail);
            }
            saveBatch(details);
        }
    }

    @Override
    public void deleteReportId(String id) {
        LambdaQueryWrapper<FinalAccountingReportDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinalAccountingReportDetail::getFarId,id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<FinalAccountingReportDetail> getReportDetailId(String id) {
        LambdaQueryWrapper<FinalAccountingReportDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinalAccountingReportDetail::getFarId,id);
       return baseMapper.selectList(queryWrapper);
    }
}
