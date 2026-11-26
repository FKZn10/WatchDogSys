package com.jpmsoft.userProducer.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.RiskWarnTableMapper;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTable;
import com.jpmsoft.userProducer.service.IRiskWarnTableFilterService;
import com.jpmsoft.userProducer.service.IRiskWarnTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskWarnTableImpl extends ServiceImpl<RiskWarnTableMapper, RiskWarnTable> implements IRiskWarnTableService {
    @Resource
    IRiskWarnTableFilterService riskWarnTableFilterService;

    @Override
    public void createOrUpdataRiskWarnTable(String RwiId, List<RiskWarnTableRequest> TableList) {
        QueryWrapper<RiskWarnTable> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", RwiId);
        List<RiskWarnTable> entity = baseMapper.selectList(wrapper);
        if (entity != null && entity.size() > 0) {
            //修改
            List<String> newidlist = TableList.stream().map(RiskWarnTableRequest::getRwtId).collect(Collectors.toList());
            List<String> oldidlist = entity.stream().map((RiskWarnTable::getRwtId)).collect(Collectors.toList());
            //获取要删除的数据
            List<RiskWarnTable> removelist = entity.stream().filter(n -> !newidlist.contains(n.getRwtId())).collect(Collectors.toList());
            if (removelist.size() > 0) {
                List<String> removeid = removelist.stream().map(RiskWarnTable::getRwtId).collect(Collectors.toList());
                baseMapper.deleteBatchIds(removeid);
            }
            //计算要添加的数据
            List<RiskWarnTableRequest> addlist = TableList.stream().filter(n -> !oldidlist.contains(n.getRwtId())).collect(Collectors.toList());
            if (addlist.size() > 0) {
                List<RiskWarnTable> pojos = addTable(addlist, RwiId);
                if (pojos.size() > 0)
                    saveBatch(pojos);
            }
            //获取要修改的数据
            List<RiskWarnTableRequest> updatalist = TableList.stream().filter(n -> oldidlist.contains(n.getRwtId())).collect(Collectors.toList());
            if (updatalist.size() > 0) {
                List<RiskWarnTable> pojos = addTable(updatalist, RwiId);
                saveOrUpdateBatch(pojos);
            }
        } else {
            List<RiskWarnTable> pojos = addTable(TableList, RwiId);
            if (pojos.size() > 0)
                saveBatch(pojos);
        }
        TableList.forEach(item -> {
            if (item.getRwtConlist() != null && item.getRwtConlist().size() > 0) {
                riskWarnTableFilterService.createOrUpdataRiskWarnTableFilter(RwiId, item.getRwtId(), item.getRwtConlist());
            }
        });
    }

    public List<RiskWarnTable> addTable(List<RiskWarnTableRequest> TableList, String RwiId) {
        List<RiskWarnTable> newlist = new ArrayList<>();
        TableList.forEach(data -> {
            RiskWarnTable newobj = new RiskWarnTable();
            newobj.setRwiId(RwiId);
            newobj.setRwtAssociationField(data.getRwtAssociationField());
            newobj.setRwtAssociationTable(data.getRwtAssociationTable());
            newobj.setRwtAssociationType(data.getRwtAssociationType());
            newobj.setRwtConditionalRelation(data.getRwtConditionalRelation());
            newobj.setRwtCreateTime(data.getRwtCreateTime());
            newobj.setRwtCreateUname(data.getRwtCreateUname());
            newobj.setRwtOtherSet(data.getRwtOtherSet());
            if (ValidateUtil.isEmpty(data.getRwtId())) {
                newobj.setRwtId(GenerateUUID.getUUID());
            } else {
                newobj.setRwtId(data.getRwtId());
            }
            newobj.setTableCode(data.getTableCode());
            newobj.setTableId(data.getTableId());
            newobj.setTableField(data.getTableField());
            newlist.add(newobj);
        });
        return newlist;
    }

    @Override
    public void deleteRiskWarnTable(String id) {

    }

    @Override
    public BaseResponse getRiskWarnTableList(String id) {
        QueryWrapper<RiskWarnTable> wrapper = new QueryWrapper<>();
        wrapper.eq("Rwi_Id", id);
        List<RiskWarnTable> list = baseMapper.selectList(wrapper);
        return BaseResponse.ok(list, list.size());
    }





}
