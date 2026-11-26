package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysTableColumnMapper;
import com.jpmsoft.fileUpload.pojo.dto.jaxbxml.SysTableColumnXmlDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import com.jpmsoft.fileUpload.service.ISysTableColumnService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 8:36
 */
@Service
public class SysTableColumnImpl extends ServiceImpl<SysTableColumnMapper, SysTableColumn> implements ISysTableColumnService {

    @Override
    public List<SysTableColumn> getColumnListByTableCode(String tableCode) {
        if (ValidateUtil.isEmpty(tableCode)) return null;
        return baseMapper.getColumnListByTableCode(tableCode.toUpperCase(Locale.ENGLISH));
    }

    @Override
    public List<SysTableColumn> getColumnListByTableCode(String tableCode, String action) {
        List<SysTableColumn> data = getColumnListByTableCode(tableCode);
        if (!CollectionUtils.isEmpty(data))
            if (action.equalsIgnoreCase("exp"))
                return data.stream().filter(f -> ValidateUtil.isNotNull(f.getColumnExp()) && f.getColumnExp()).collect(Collectors.toList());
            else if (action.equalsIgnoreCase("imp"))
                return data.stream().filter(f -> ValidateUtil.isNotNull(f.getColumnImp()) && f.getColumnImp()).collect(Collectors.toList());
        return data;
    }

    @Override
    public List<String> getColumnCodeByTableCode(String tableCode) {
        List<SysTableColumn> data = getColumnListByTableCode(tableCode);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(SysTableColumn::getColumnCode).map(String::toUpperCase).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<SysTableColumnXmlDTO> getColumns(List<String> tableIds) {
        QueryWrapper<SysTableColumn> wrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(tableIds))
            wrapper.in("Table_Id", tableIds);
        List<SysTableColumn> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(d -> {
                SysTableColumnXmlDTO xmlDTO = new SysTableColumnXmlDTO();
                BeanUtils.copyProperties(d, xmlDTO);
                return xmlDTO;
            }).collect(Collectors.toList());
        else return null;
    }

    @Override
    public void deleteByTbCodes(List<String> tbCodes) {
        QueryWrapper<SysTableColumn> wrapper = new QueryWrapper<>();
        wrapper.in("Table_Code", tbCodes);
        baseMapper.delete(wrapper);
    }

    @Override
    public void insertBatchSomeColumn(List<SysTableColumn> columns) {
        if (columns.size() < 1000) {
            baseMapper.insertBatchSomeColumn(columns);
        } else {
            int insertTimes = (int) Math.ceil(columns.size() * 1.0 / 1000);
            for (int i = 0; i < insertTimes; i++) {
                baseMapper.insertBatchSomeColumn(columns.subList(1000 * i, Math.min(1000 * (i + 1), columns.size())));
            }
        }

    }

    @Override
    public String selectPk(String tableCode) {
        QueryWrapper<SysTableColumn> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_Code", tableCode);
        wrapper.eq("column_Pk", 1);
        List<SysTableColumn> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().findFirst().get().getColumnCode();
        return null;
    }
}
