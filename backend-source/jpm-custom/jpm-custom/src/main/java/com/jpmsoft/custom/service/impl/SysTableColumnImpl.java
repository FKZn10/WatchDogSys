package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.mapper.SysTableColumnMapper;
import com.jpmsoft.custom.pojo.entity.SysTableColumn;
import com.jpmsoft.custom.service.ISysTableColumnService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysTableColumnImpl extends ServiceImpl<SysTableColumnMapper, SysTableColumn> implements ISysTableColumnService {


    @Override
    public BaseResponse GetTableColumnListByTableCode(List<String> TableCode) {
        String sqlstr = "";
        Map<String, Object> newlist = new HashMap<>();
        for (String item : TableCode) {
            QueryWrapper<SysTableColumn> queryWrapper = new QueryWrapper<>();
            queryWrapper.inSql("Table_ID", "select Table_ID  from Sys_Table where  Table_Code='" + item + "' ");
            queryWrapper.orderByAsc("Column_Number");
            List<SysTableColumn> list = baseMapper.selectList(queryWrapper);
            newlist.put(item, list);
        }
        ;
        return BaseResponse.ok(newlist, newlist.size());
    }

    @Override
    public List<SysTableColumn> getColumnListByTableCode(String tableCode) {
        return baseMapper.getColumnListByTableCode(tableCode);
    }

    @Override
    public List<SysTableColumn> getColumnListByTableId(String tableId) {
        QueryWrapper<SysTableColumn> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_ID", tableId);
        return baseMapper.selectList(wrapper);
    }

}
