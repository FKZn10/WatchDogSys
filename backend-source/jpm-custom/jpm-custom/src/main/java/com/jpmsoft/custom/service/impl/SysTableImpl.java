package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.custom.constants.CustomMsg;
import com.jpmsoft.custom.mapper.SysTableMapper;
import com.jpmsoft.custom.pojo.dto.TableActionDTO;
import com.jpmsoft.custom.pojo.dto.TableColumnDTO;
import com.jpmsoft.custom.pojo.entity.SysTable;
import com.jpmsoft.custom.pojo.entity.SysTableColumn;
import com.jpmsoft.custom.service.ISysTableColumnService;
import com.jpmsoft.custom.service.ISysTableService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysTableImpl extends ServiceImpl<SysTableMapper, SysTable> implements ISysTableService {

    @Resource
    private ISysTableColumnService sysTableColumnService;

    @Override
    public BaseResponse create(TableActionDTO request) {
        if (ValidateUtil.isAnObjEmpty(request.getTbCode()))
            return BaseResponse.fail(CustomMsg.reqIllegal);
        if (CollectionUtils.isEmpty(request.getColumns()))
            return BaseResponse.fail(CustomMsg.notColumn);
        if (tbExist(request.getTbCode()))
            return BaseResponse.fail(String.format(CustomMsg.tableExist, request.getTbCode()));
        String pk = null;
        SysTable tb = new SysTable();
        Date now = new Date();
        tb.setTableId(GenerateUUID.getUUID()).setTableName(request.getTbName()).setTableCode(request.getTbCode()).setTableCreatetime(now);
        int index = 1;
        List<SysTableColumn> columns = new ArrayList<>();
        for (TableColumnDTO column : request.getColumns()) {
            SysTableColumn cm = new SysTableColumn();
            BeanUtils.copyProperties(column, cm);
            cm.setColumnId(GenerateUUID.getUUID()).setTableId(tb.getTableId()).setTableCode(tb.getTableCode())
                    .setColumnJavaSmallHump(VoInfoUtil.toLowerCamelCase(column.getCmCode())).setColumnCode(column.getCmCode())
                    .setColumnName(column.getCmName()).setColumnType(column.getCmType()).setColumnLength(column.getCmLength())
                    .setColumnFile(column.isCmFile()).setColumnPk(column.isCmPk() ? "1" : null).setColumnNumber(index)
                    .setColumnRemark(column.getCmRemark());
            index += 1;
            columns.add(cm);
            if (column.isCmPk() && ValidateUtil.isEmpty(pk)) pk = column.getCmCode();
        }
        try {
            baseMapper.createTable(request.getTbCode(), pk, request.getColumns());
            baseMapper.insert(tb);
            sysTableColumnService.saveBatch(columns);
        } catch (Exception ex) {
            log.error("创建表SQL执行失败 MSG:" + ex.getMessage(), ex);
            ex.printStackTrace();
            return BaseResponse.fail(CustomMsg.createTbError);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse addColumn(TableActionDTO request) {
        verify(request);
        baseMapper.alterTable(request.getColumns(), request.getTbCode(), "add");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse modifyColumn(TableActionDTO request) {
        verify(request);
        baseMapper.alterTable(request.getColumns(), request.getTbCode(), "modify");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse dropColumn(TableActionDTO request) {
        verify(request);
        baseMapper.alterTable(request.getColumns(), request.getTbCode(), "DROP COLUMN");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse renameColumn(TableActionDTO request) {
        verify(request);
        baseMapper.renameColumn(request.getColumns(), request.getTbCode());
        return BaseResponse.ok();
    }

    @Override
    public SysTable getByCode(String tableCode) {
        return baseMapper.getByCode(tableCode);
    }


    private void verify(TableActionDTO request) {
        if (ValidateUtil.isEmpty(request.getTbCode()) && CollectionUtils.isEmpty(request.getColumns()))
            throw new JPMException(CustomMsg.reqIllegal);
        if (!tbExist(request.getTbCode()))
            throw new JPMException(String.format(CustomMsg.tableNotExist, request.getTbCode()));
    }

    /**
     * 删除指定模式（库）下的指定表
     *
     * @param schema
     * @param table
     */
    private void dropTable(String schema, String table) {
        baseMapper.dropTable(schema, table);
    }


    private boolean tbExist(String code) {
        QueryWrapper<SysTable> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_Code", code);
        return baseMapper.exists(wrapper);
    }
}
