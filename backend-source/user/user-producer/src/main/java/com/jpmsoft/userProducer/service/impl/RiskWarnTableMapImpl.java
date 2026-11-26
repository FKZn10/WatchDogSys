package com.jpmsoft.userProducer.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableMapRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.RiskWarnTableMapMapper;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTableMap;
import com.jpmsoft.userProducer.service.IRiskWarnTableMapService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskWarnTableMapImpl extends ServiceImpl<RiskWarnTableMapMapper, RiskWarnTableMap> implements IRiskWarnTableMapService {

    @Override
    public void createOrUpdataRiskWarnTableMap(String id, List<RiskWarnTableMapRequest> TableMapList) {
        QueryWrapper<RiskWarnTableMap> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", id);
        List<RiskWarnTableMap> entity = baseMapper.selectList(wrapper);
        if (entity != null && entity.size() > 0) {
            //修改
            List<String> newidlist = TableMapList.stream().map(n -> n.getRwtmId()).collect(Collectors.toList());
            List<String> oldidlist = entity.stream().map((n -> n.getRwtmId())).collect(Collectors.toList());
            //获取要删除的数据
            List<RiskWarnTableMap> removelist = entity.stream().filter(n -> !newidlist.contains(n.getRwtmId())).collect(Collectors.toList());
            if (removelist.size() > 0) {
                List<String> removeid = removelist.stream().map(n -> n.getRwtmId()).collect(Collectors.toList());
                baseMapper.deleteBatchIds(removeid);
            }
            //计算要添加的数据
            List<RiskWarnTableMapRequest> addlist = TableMapList.stream().filter(n -> !oldidlist.contains(n.getRwtmId())).collect(Collectors.toList());
            if (addlist.size() > 0) {
                List<RiskWarnTableMap> pojos = addTableMap(addlist, id);
                if (pojos.size() > 0)
                    saveBatch(pojos);
            }
            //获取要修改的数据
            List<RiskWarnTableMapRequest> updatalist = TableMapList.stream().filter(n -> oldidlist.contains(n.getRwtmId())).collect(Collectors.toList());
            if (updatalist.size() > 0) {
                List<RiskWarnTableMap> pojos = addTableMap(updatalist, id);
                saveOrUpdateBatch(pojos);
            }
        } else {
            List<RiskWarnTableMap> pojos = addTableMap(TableMapList, id);
            if (pojos.size() > 0)
                saveBatch(pojos);
        }
    }

    private List<RiskWarnTableMap> addTableMap(List<RiskWarnTableMapRequest> Map, String RwiId) {
        List<RiskWarnTableMap> pojos = new ArrayList<>();
        Map.forEach(data -> {
            RiskWarnTableMap newobj = new RiskWarnTableMap();
            newobj.setRwiId(RwiId);
            newobj.setRwtmCreateTime(new Date());
            newobj.setRwtmField(data.getRwtmField());
            if (ValidateUtil.isEmpty(data.getRwtmId())) {
                newobj.setRwtmId(GenerateUUID.getUUID());
            } else {
                newobj.setRwtmId(data.getRwtmId());
            }
            newobj.setRwtmMappingValue(data.getRwtmMappingValue());
            newobj.setRwtmType(data.getRwtmType());
            pojos.add(newobj);
        });
        return pojos;

    }


    @Override
    public void deleteRiskWarnTableMap(String id) {
        QueryWrapper<RiskWarnTableMap> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", id);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getRiskWarnTableMap(String id) {
        List<RiskWarnTableMapRequest> newlist = new ArrayList<>();
        QueryWrapper<RiskWarnTableMap> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", id);
        List<RiskWarnTableMap> list = baseMapper.selectList(wrapper);
//        list.forEach(data->{
//            RiskWarnTableMapRequest newobj=new RiskWarnTableMapRequest();
//            newobj.setRwiId(data.getRwiId());
//            newobj.setRwtmCreateTime(data.getRwtmCreateTime());
//            newobj.setRwtmField(data.getRwtmField());
//            newobj.setRwtmId(data.getRwtmId());
//            newobj.setRwtmMappingValue(data.getRwtmField());
//            newobj.setRwtmType(data.getRwtmType());
//            newlist.add(newobj);
//        });
        return BaseResponse.ok(list, list.size());
    }
}
