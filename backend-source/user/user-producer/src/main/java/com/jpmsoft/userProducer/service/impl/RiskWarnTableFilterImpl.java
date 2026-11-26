package com.jpmsoft.userProducer.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableFilterRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.RiskWarnTableFilterMapper;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTableFilter;
import com.jpmsoft.userProducer.service.IRiskWarnTableFilterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskWarnTableFilterImpl extends ServiceImpl<RiskWarnTableFilterMapper, RiskWarnTableFilter> implements IRiskWarnTableFilterService {

    @Override
    public void createOrUpdataRiskWarnTableFilter(String RwiId, String RwtId, List<RiskWarnTableFilterRequest> TableFilterList) {
        QueryWrapper<RiskWarnTableFilter> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwt_Id", RwtId);
        List<RiskWarnTableFilter> entity = baseMapper.selectList(wrapper);
        if (entity != null && entity.size() > 0) {
            //修改
            List<String> newidlist = TableFilterList.stream().map(RiskWarnTableFilterRequest::getRwtfId).collect(Collectors.toList());
            List<String> oldidlist = entity.stream().map((RiskWarnTableFilter::getRwtfId)).collect(Collectors.toList());
            //获取要删除的数据
            List<RiskWarnTableFilter> removelist = entity.stream().filter(n -> !newidlist.contains(n.getRwtfId())).collect(Collectors.toList());
            if (removelist.size() > 0) {
                List<String> removeid = removelist.stream().map(RiskWarnTableFilter::getRwtfId).collect(Collectors.toList());
                baseMapper.deleteBatchIds(removeid);
            }
            //计算要添加的数据
            List<RiskWarnTableFilterRequest> addlist = TableFilterList.stream().filter(n -> !oldidlist.contains(n.getRwtfId())).collect(Collectors.toList());
            if (addlist.size() > 0) {
                List<RiskWarnTableFilter> pojos = addTableFilter(addlist, RwiId, RwtId);
                if (pojos.size() > 0)
                    saveBatch(pojos);
            }
            //获取要修改的数据
            List<RiskWarnTableFilterRequest> updatalist = TableFilterList.stream().filter(n -> oldidlist.contains(n.getRwtfId())).collect(Collectors.toList());
            if (updatalist.size() > 0) {
                List<RiskWarnTableFilter> pojos = addTableFilter(updatalist, RwiId, RwtId);
                saveOrUpdateBatch(pojos);
            }
        } else {
            List<RiskWarnTableFilter> pojos = addTableFilter(TableFilterList, RwiId, RwtId);
            if (pojos.size() > 0)
                saveBatch(pojos);
        }

    }


    @Override
    public void createOrUpdataRiskWarnTableFilter(String RwiId, List<RiskWarnTableFilter> TableFilterList) {
        QueryWrapper<RiskWarnTableFilter> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", RwiId);
        List<RiskWarnTableFilter> entity = baseMapper.selectList(wrapper);
        if (entity != null && entity.size() > 0) {
            //修改
            List<String> newidlist = TableFilterList.stream().map(RiskWarnTableFilter::getRwtfId).collect(Collectors.toList());
            List<String> oldidlist = entity.stream().map((RiskWarnTableFilter::getRwtfId)).collect(Collectors.toList());
            //获取要删除的数据
            List<RiskWarnTableFilter> removelist = entity.stream().filter(n -> !newidlist.contains(n.getRwtfId())).collect(Collectors.toList());
            if (removelist.size() > 0) {
                List<String> removeid = removelist.stream().map(RiskWarnTableFilter::getRwtfId).collect(Collectors.toList());
                baseMapper.deleteBatchIds(removeid);
            }
            //计算要添加的数据
            List<RiskWarnTableFilter> addlist = TableFilterList.stream().filter(n -> !oldidlist.contains(n.getRwtfId())).collect(Collectors.toList());
            if (addlist.size() > 0) {
                if (TableFilterList.size() > 0)
                    saveBatch(TableFilterList);
            }
            //获取要修改的数据
            List<RiskWarnTableFilter> updatalist = TableFilterList.stream().filter(n -> oldidlist.contains(n.getRwtfId())).collect(Collectors.toList());
            if (updatalist.size() > 0) {
                saveOrUpdateBatch(updatalist);
            }
        } else {
            if (TableFilterList.size() > 0)
                saveBatch(TableFilterList);
        }

    }

    private List<RiskWarnTableFilter> addTableFilter(List<RiskWarnTableFilterRequest> TableFilter, String RwiId, String RwtId) {
        List<RiskWarnTableFilter> newlist = new ArrayList<>();
        TableFilter.forEach(data -> {
            RiskWarnTableFilter newobj = new RiskWarnTableFilter();
            newobj.setRwiId(RwiId);
            newobj.setRwtfAssociationTable(data.getRwtfAssociationTable());
            newobj.setRwtfComparator(data.getRwtfComparator());
            newobj.setRwtfComparedProcessingCover(data.getRwtfComparedProcessingCover());
            newobj.setRwtfComparedValue(data.getRwtfComparedValue());
            newobj.setRwtfComparisonProcessing(data.getRwtfComparisonProcessing());
            newobj.setRwtfComparisonPercentage(data.getRwtfComparisonPercentage());
            newobj.setRwtfComparisonPercentageCover(data.getRwtfComparisonPercentageCover());
            newobj.setRwtfComparisonType(data.getRwtfComparisonType());
            newobj.setRwtfConditionType(data.getRwtfConditionType());
            if (ValidateUtil.isNull(data.getRwtfCreateTime())) {
                newobj.setRwtfCreateTime(new Date());
            } else {
                newobj.setRwtfCreateTime(data.getRwtfCreateTime());
            }

            if (ValidateUtil.isEmpty(data.getRwtfId())) {
                newobj.setRwtfId(GenerateUUID.getUUID());
            } else {
                newobj.setRwtfId(data.getRwtfId());
            }

            newobj.setRwtfTableField(data.getRwtfTableField());
            newobj.setRwtId(RwtId);
            newlist.add(newobj);
        });
        return newlist;
    }

    @Override
    public void deleteRiskWarnTableMap(String id) {
        QueryWrapper<RiskWarnTableFilter> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", id);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getRiskWarnTableFilter(String id) {
        List<RiskWarnTableFilterRequest> newlist = new ArrayList<>();
        QueryWrapper<RiskWarnTableFilter> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", id);
        List<RiskWarnTableFilter> list = baseMapper.selectList(wrapper);
//        list.forEach(data->{
//            RiskWarnTableFilterRequest newobj=new  RiskWarnTableFilterRequest();
//            newobj.setRwiId(data.getRwiId());
//            newobj.setRwtfAssociationTable(data.getRwtfAssociationTable());
//            newobj.setRwtfComparator(data.getRwtfComparator());
//            newobj.setRwtfComparedProcessingCover(data.getRwtfComparedProcessingCover());
//            newobj.setRwtfComparedValue(data.getRwtfComparedValue());
//            newobj.setRwtfComparisonPercentage(data.getRwtfComparisonPercentage());
//            newobj.setRwtfComparisonPercentageCover(data.getRwtfComparisonPercentageCover());
//            newobj.setRwtfComparisonType(data.getRwtfComparisonType());
//            newobj.setRwtfConditionType(data.getRwtfConditionType());
//            newobj.setRwtfCreateTime(data.getRwtfCreateTime());
//            newobj.setRwtfId(data.getRwtfId());
//            newobj.setRwtfTableField(data.getRwtfTableField());
//            newobj.setRwtId(data.getRwtId());
//            newlist.add(newobj);
//        });
        return BaseResponse.ok(list, list.size());
    }
}
