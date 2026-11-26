package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysTableColumnMapper;
import com.jpmsoft.userProducer.pojo.dto.ColumnSqlDTO;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.dto.TableColumnDto;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import com.jpmsoft.userProducer.rabbitMQ.Sender;
import com.jpmsoft.userProducer.service.ISysTableColumnService;
import com.jpmsoft.userProducer.service.ISysTableService;
import com.jpmsoft.userProducer.service.ISysUserService;
import com.jpmsoft.userProducer.service.ITableManagementService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.HumpConversion;
import com.jpmsoft.userProducer.utils.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysTableColumnImpl extends ServiceImpl<SysTableColumnMapper, SysTableColumn> implements ISysTableColumnService {

    @Resource
    private ISysTableService sysTableService;
    @Resource
    private ITableManagementService tableManagementService;
    @Resource
    private Sender sender;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse GetTableColumnList(TableColumnDto request) {
        QueryWrapper<SysTableColumn> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTableIdList())) {
            queryWrapper.in("Table_ID", request.getTableIdList());
        } else {
            queryWrapper.eq(ValidateUtil.isNotEmpty(request.getTableId()), "Table_ID", request.getTableId());
        }
        if (ValidateUtil.isNotNull(request.getColumnExp()))
            queryWrapper.eq("Column_Exp", request.getColumnExp());
        if (ValidateUtil.isNotNull(request.getColumnImp()))
            queryWrapper.eq("Column_Imp", request.getColumnImp());
        if (ValidateUtil.isNotNull(request.getColumnFile()))
            queryWrapper.eq("Column_File", request.getColumnFile());
        if (ValidateUtil.isNotEmpty(request.getTableCode()) && !request.getTcSearchType())
            queryWrapper.eq("upper(Table_Code)", request.getTableCode().toUpperCase(Locale.ENGLISH));
        if (ValidateUtil.isNotEmpty(request.getTableCode()) && request.getTcSearchType())
            queryWrapper.like("Table_Code", request.getTableCode());
        if (ValidateUtil.isNotEmpty(request.getColumnCode()))
            queryWrapper.like("Column_Code", request.getColumnCode());
        if (ValidateUtil.isNotEmpty(request.getColumnJavaSmallHump()))
            queryWrapper.like("Column_Java_Small_Hump", request.getColumnJavaSmallHump());
        if (ValidateUtil.isNotEmpty(request.getColumnName()))
            queryWrapper.like("Column_Name", request.getColumnName());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                queryWrapper.orderByAsc(request.getSortFiled());
            else
                queryWrapper.orderByDesc(request.getSortFiled());
        } else
            queryWrapper.orderByAsc("Column_Number");
        IPage<SysTableColumn> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


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
        List<SysTableColumn> datas = baseMapper.getColumnListByTableCode(tableCode);
        return datas;
    }

    @Override
    public BaseResponse setColumnExpOrImp(String tableCode, String action, Boolean enable) {
        List<String> tableId = sysTableService.getTableIdByCode(tableCode);
        if (!CollectionUtils.isEmpty(tableId)) {
            tableId.forEach(id -> {
                if (action.equalsIgnoreCase("exp"))
                    baseMapper.setColumnExpOrImp(id, "Column_Exp", enable);
                else if (action.equalsIgnoreCase("imp"))
                    baseMapper.setColumnExpOrImp(id, "Column_Imp", enable);
                else
                    throw new JPMException(BaseMsg.unknownAction);
            });
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse formatHump() {
        List<SysTableColumn> columns = list();
        columns.forEach(c -> {
            if (ValidateUtil.isNotEmpty(c.getColumnCode()))
                c.setColumnJavaSmallHump(HumpConversion.baseTurnHump(c.getColumnCode()));
        });
        updateBatchById(columns);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse CreateOrUpdateTableColumn(TableColumnDto request, String uId) {
        SysTableColumn pojo;
        SysTable table = sysTableService.getById(request.getTableId());
        SysUser userObj = sysUserService.getById(uId);
        String oldColumn = "";
        if (!ValidateUtil.isEmpty(request.getColumnId())) {
            pojo = baseMapper.selectById(request.getColumnId());
            oldColumn = pojo.getColumnCode();
        } else {
            pojo = new SysTableColumn();
        }

        QueryWrapper<SysTableColumn> wrapper = new QueryWrapper<>();
        wrapper.eq("Column_Code", request.getColumnCode());
        wrapper.eq("Table_ID", request.getTableId());
        if (!ValidateUtil.isEmpty(request.getColumnId()))
            wrapper.ne("Column_ID", request.getColumnId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        BeanUtils.copyProperties(request, pojo);
        if (ValidateUtil.isEmpty(pojo.getColumnCode())) pojo.setColumnCode(pojo.getColumnCode().trim());
        pojo.setColumnJavaSmallHump(HumpConversion.baseTurnHump(pojo.getColumnCode()));
        if (!ValidateUtil.isEmpty(request.getColumnId())) {
            baseMapper.updateById(pojo);
            List<TabcolumsVo> oldList = tableManagementService.getTableColumnbByTableCodeAndColumnCode(table.getTableCode(), oldColumn);
            if (CollectionUtils.isEmpty(oldList)) {
                sysTableService.createTableColumn(table.getTableDb(), table.getTableCode(), pojo.getColumnCode(), pojo.getColumnLength(), pojo.getColumnType());
                String content = "新增表字段：" + "”" + table.getTableCode() + "“表中新增" + pojo.getColumnCode() + "；";
                sender.sendAudit(content, uId, userObj.getuName(), table.getTableId(), AuditTypeEnum.add.getkey());
            } else {
                if (!oldColumn.equals(pojo.getColumnCode())) {
//                    sysTableService.updateTableColumnName(table.getTableDb(),table.getTableCode(),oldColumn,pojo.getColumnCode());
//                    String content = "新增表字段修改：" + "”" + table.getTableCode() + "“表中"+oldColumn+"修改" + pojo.getColumnCode() + "；";
//                    sender.sendAudit(content, uId, userObj.getuName(), table.getTableId(), AuditTypeEnum.add.getkey());
                }
                sysTableService.updateTableColumnType(table.getTableDb(), table.getTableCode(), oldColumn, pojo.getColumnType(), pojo.getColumnLength());
            }
        } else {
            pojo.setColumnId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
            List<TabcolumsVo> oldList = tableManagementService.getTableColumnbByTableCodeAndColumnCode(table.getTableCode(), pojo.getColumnCode());
            if (CollectionUtils.isEmpty(oldList)) {
                sysTableService.createTableColumn(table.getTableDb(), table.getTableCode(), pojo.getColumnCode(), pojo.getColumnLength(), pojo.getColumnType());
                String content = "新增表字段：" + "”" + table.getTableCode() + "“表中新增" + pojo.getColumnCode() + "；";
                sender.sendAudit(content, uId, userObj.getuName(), table.getTableId(), AuditTypeEnum.add.getkey());
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse DeleteTableColumn(TableColumnDto request, String uId) {
        if (ValidateUtil.isEmpty(request.getColumnId()))
            return new BaseResponse(BaseMsg.notReqParam);
        SysTableColumn sysTableColumn = baseMapper.selectById(request.getColumnId());
        if (sysTableColumn == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(request.getColumnId());
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse importProperties(List<ExlTableColumnDto> request) {
        QueryWrapper<SysTableColumn> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_ID", request.get(0).getTableId());
        List<SysTableColumn> list = baseMapper.selectList(wrapper);
        for (SysTableColumn item : list) {
            if (!ValidateUtil.isEmpty(item.getColumnCode())) {
                List<ExlTableColumnDto> obj = request.stream().filter(n -> n.getColumnCode().equals(item.getColumnCode())).collect(Collectors.toList());
                if (obj.size() > 0) {
                    item.setColumnName(obj.get(0).getColumnName());
                    if (!ValidateUtil.isEmpty(obj.get(0).getColumnPk())) {
                        if (ValidateUtil.isEmpty(item.getColumnPk())) {
                            item.setColumnPk(obj.get(0).getColumnPk());
                        }
                    }
                    if (!ValidateUtil.isEmpty(obj.get(0).getColumnNull())) {
                        if (ValidateUtil.isEmpty(item.getColumnNull())) {
                            item.setColumnNull(obj.get(0).getColumnNull());
                        }
                    }
                    if (ValidateUtil.isNotEmpty(obj.get(0).getColumnCode())) {
                        item.setColumnJavaSmallHump(HumpConversion.baseTurnHump(obj.get(0).getColumnCode()));
                    }

                    baseMapper.updateById(item);
                }
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 生成字段的sql
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getColumnSql(ColumnSqlDTO request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getColumnIdList())) {
            return new BaseResponse(BaseMsg.notReqParam);
        }
        List<SysTableColumn> columns = this.listByIds(request.getColumnIdList());
        if (ValidateUtil.isEmpty(columns)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        // 表名
        SysTable table = sysTableService.getById(columns.get(0).getTableId());
        if (ValidateUtil.isNull(table)) {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        String tableCode = table.getTableCode();
        StringBuilder columnSql = new StringBuilder();
        // 删除字段
        if (ValidateUtil.isNotEmpty(request.getDelete()) && "1".equalsIgnoreCase(request.getDelete())) {
            for (SysTableColumn column : columns) {
                // 字段名
                String columnCode = column.getColumnCode();
                columnSql.append("alter table ").append(tableCode).append(" drop column ").append(columnCode).append(";\n");
            }
        }

        // 新增字段
        if (ValidateUtil.isNotEmpty(request.getCreate()) && "1".equalsIgnoreCase(request.getCreate())) {
            if (ValidateUtil.isNotEmpty(request.getDelete()) && "1".equalsIgnoreCase(request.getDelete())) {
                columnSql.append("\n");
            }
            for (SysTableColumn column : columns) {
                // 字段名
                String columnCode = column.getColumnCode();
                // 字段类型
                String columnType = column.getColumnType();
                // 字段长度
                Integer columnLength = column.getColumnLength();
                // 基础sql
                columnSql.append("alter table ").append(tableCode).append(" add ").append(columnCode).append(" ");
                // 某些类型需要指定长度
                columnSql.append(SqlUtils.handleColumnLength(columnType, columnLength));

                // 是否允许为空
                columnSql.append(SqlUtils.handleNullConstraint(column.getColumnPk(), columnCode, column.getColumnNull()));

                columnSql.append(";\n");
            }
        }

        if (ValidateUtil.isNotEmpty(request.getRemark()) && "1".equalsIgnoreCase(request.getRemark())) {
            if (ValidateUtil.isNotEmpty(request.getCreate()) && "1".equalsIgnoreCase(request.getCreate())) {
                columnSql.append("\n");
            }
            // 获取数据库类型和名称
            BaseResponse dbName = tableManagementService.getDbName();
            if (!dbName.getSuccess()) {
                return dbName;
            }
            Object data = dbName.getData();
            String dbType = ((Map<String, String>) data).get("dbName");
            if (ValidateUtil.isEmpty(dbType)) {
                return BaseResponse.fail("未获取到数据库类型，无法生成建表语句");
            }
            // 字段备注
            String remarksSql = SqlUtils.generateTableAndColumnRemarks(dbType, table, columns);
            if (ValidateUtil.isNotEmpty(remarksSql)) {
                columnSql.append(remarksSql);
            }
        }
        return BaseResponse.ok(columnSql.toString());
    }
}
