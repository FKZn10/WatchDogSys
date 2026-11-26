package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.AuditTypeEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysTableMapper;
import com.jpmsoft.userProducer.pojo.dto.TableDto;
import com.jpmsoft.userProducer.pojo.dto.TableSqlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import com.jpmsoft.userProducer.pojo.vo.TableInfoVo;
import com.jpmsoft.userProducer.rabbitMQ.Sender;
import com.jpmsoft.userProducer.service.ISysTableColumnService;
import com.jpmsoft.userProducer.service.ISysTableService;
import com.jpmsoft.userProducer.service.ISysUserService;
import com.jpmsoft.userProducer.service.ITableManagementService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.HumpConversion;
import com.jpmsoft.userProducer.utils.SqlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysTableImpl extends ServiceImpl<SysTableMapper, SysTable> implements ISysTableService {

    @Resource
    private ISysTableColumnService sysTableColumnService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private Sender sender;
    @Resource
    private ITableManagementService tableManagementService;

    @Override
    public BaseResponse GetTableList(TableDto request) {
        QueryWrapper<SysTable> queryWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getTableCode()))
            queryWrapper.like("lower(Table_Code)", request.getTableCode().toLowerCase(Locale.ENGLISH));
        if (!ValidateUtil.isEmpty(request.getTableName()))
            queryWrapper.like("Table_Name", request.getTableName());
        if (!ValidateUtil.isEmpty(request.getTableDb()))
            queryWrapper.like("Table_Db", request.getTableDb());

        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                queryWrapper.orderByAsc(request.getSortFiled());
            else
                queryWrapper.orderByDesc(request.getSortFiled());
        } else
            queryWrapper.orderByDesc("Table_CreateTime");

        queryWrapper.orderByDesc("Table_CreateTime");
        IPage<SysTable> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateTable(TableDto request, String uId) {
        SysUser userObj = sysUserService.getById(uId);
        SysTable sysTable;
        String oldCode = "";
        if (!ValidateUtil.isEmpty(request.getTableId())) {
            sysTable = baseMapper.selectById(request.getTableId());
            oldCode = sysTable.getTableCode();
        } else {
            sysTable = new SysTable();
        }

        QueryWrapper<SysTable> wrapper = new QueryWrapper<>();
        wrapper.eq("lower(Table_Code)", request.getTableCode().toLowerCase(Locale.ENGLISH));
        if (!ValidateUtil.isEmpty(request.getTableId()))
            wrapper.ne("Table_ID", request.getTableId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);

        sysTable.setTableClassname(request.getTableClassname());
        sysTable.setTableCode(request.getTableCode());
        sysTable.setTableDb(request.tableDb);
        sysTable.setTableType(request.getTableType());
        sysTable.setTableStats(request.getTableStats());
        sysTable.setTableRemark(request.getTableRemark());
        sysTable.setTablePk(request.getTablePk());
        sysTable.setTableName(request.getTableName());
        Map<String, String> fieldMaps = setFieldMaps(sysTable.getTableDb());
        if (!ValidateUtil.isEmpty(request.getTableId())) {
            baseMapper.updateById(sysTable);
            //获取数据库表
            List<TableInfoVo> sTableList = tableManagementService.getTableByTableCode(sysTable.getTableDb(), oldCode);
            if (CollectionUtils.isEmpty(sTableList)) {
                //新增表字段管理信息（默认值）
                List<String> sqlList = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datastr = sdf.format(new Date());

                List<SysTableColumn> cList = new ArrayList<>();
                SysTableColumn columnobj = new SysTableColumn();
                columnobj.setColumnId(GenerateUUID.getUUID());
                columnobj.setTableId(sysTable.getTableId());
                columnobj.setColumnCode(sysTable.getTablePk());
                columnobj.setColumnName("主键");
                columnobj.setColumnType("VARCHAR");
                columnobj.setColumnLength(36);
                columnobj.setColumnNumber(1);
                columnobj.setColumnTime(new Date());
                columnobj.setColumnPk("1");
                columnobj.setColumnJavaSmallHump(HumpConversion.baseTurnHump(sysTable.getTablePk()));
                cList.add(columnobj);
                if (request.isGeneralField()) {
                    baseMapper.createTableMin(sysTable.getTableDb(), sysTable.getTableCode(), sysTable.getTablePk());

                    SysTableColumn columnobj1 = new SysTableColumn();
                    columnobj1.setColumnId(GenerateUUID.getUUID());
                    columnobj1.setTableId(sysTable.getTableId());
                    columnobj1.setColumnCode("Create_Uid");
                    columnobj1.setColumnName("创建人id");
                    columnobj1.setColumnType("VARCHAR");
                    columnobj1.setColumnLength(36);
                    columnobj1.setColumnNumber(2);
                    columnobj1.setColumnTime(new Date());
                    columnobj1.setColumnPk("2");
                    columnobj1.setColumnJavaSmallHump(HumpConversion.baseTurnHump("Create_Uid"));
                    cList.add(columnobj1);
                    SysTableColumn columnobj2 = new SysTableColumn();
                    columnobj2.setColumnId(GenerateUUID.getUUID());
                    columnobj2.setTableId(sysTable.getTableId());
                    columnobj2.setColumnCode("Create_Uname");
                    columnobj2.setColumnName("创建人名称");
                    columnobj2.setColumnType("VARCHAR");
                    columnobj2.setColumnLength(100);
                    columnobj2.setColumnNumber(3);
                    columnobj2.setColumnTime(new Date());
                    columnobj2.setColumnJavaSmallHump(HumpConversion.baseTurnHump("Create_Uname"));
                    cList.add(columnobj2);
                    SysTableColumn columnobj3 = new SysTableColumn();
                    columnobj3.setColumnId(GenerateUUID.getUUID());
                    columnobj3.setTableId(sysTable.getTableId());
                    columnobj3.setColumnCode("Create_Time");
                    columnobj3.setColumnName("创建时间");
                    columnobj3.setColumnType("TIMESTAMP");
                    columnobj3.setColumnNumber(4);
                    columnobj3.setColumnTime(new Date());
                    columnobj3.setColumnJavaSmallHump(HumpConversion.baseTurnHump("Create_Time"));
                    cList.add(columnobj3);
                    SysTableColumn columnobj4 = new SysTableColumn();
                    columnobj4.setColumnId(GenerateUUID.getUUID());
                    columnobj4.setTableId(sysTable.getTableId());
                    columnobj4.setColumnCode("State");
                    columnobj4.setColumnName("审批状态");
                    columnobj4.setColumnType("INTEGER");
                    columnobj4.setColumnNumber(5);
                    columnobj4.setColumnTime(new Date());
                    columnobj4.setColumnPk("1");
                    columnobj4.setColumnJavaSmallHump(HumpConversion.baseTurnHump("State"));
                    cList.add(columnobj4);
                    SysTableColumn columnobj5 = new SysTableColumn();
                    columnobj5.setColumnId(GenerateUUID.getUUID());
                    columnobj5.setTableId(sysTable.getTableId());
                    columnobj5.setColumnCode("State_Time");
                    columnobj5.setColumnName("审批时间");
                    columnobj5.setColumnType("TIMESTAMP");
                    columnobj5.setColumnNumber(6);
                    columnobj5.setColumnTime(new Date());
                    columnobj5.setColumnJavaSmallHump(HumpConversion.baseTurnHump("State_Time"));
                    cList.add(columnobj5);


                } else {
                    baseMapper.createTableSimple(sysTable.getTableDb(), sysTable.getTableCode(), sysTable.getTablePk());
                }
                sysTableColumnService.saveBatch(cList);
                String content = "新增表：" + sysTable.getTableCode() + "；";
            } else {
                if (!oldCode.equals(sysTable.getTableCode())) {
//                    baseMapper.updateTableName(request.getTableDb(), oldCode, sysTable.getTableCode());
//                    String content = "修改表名：" +oldCode+"修改为" + sysTable.getTableCode() +"；";
//                    sender.sendAudit(content, uId, userObj.getuName(), sysTable.getTableId(), AuditTypeEnum.add.getkey());
                }
            }
            return BaseResponse.ok();
        } else {
            sysTable.setTableCreateuser(request.getTableCreateuser());
            sysTable.setTableCreateusername(request.getTableCreateusername());
            sysTable.setTableCreatetime(new Date());
            sysTable.setTableId(GenerateUUID.getUUID());
            baseMapper.insert(sysTable);
            List<TableInfoVo> sTableList = tableManagementService.getTableByTableCode(sysTable.getTablePk(), sysTable.getTableCode());
            if (CollectionUtils.isEmpty(sTableList)) {

                List<SysTableColumn> cList = new ArrayList<>();
                //新增表字段管理信息（默认值）
                List<String> sqlList = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datastr = sdf.format(new Date());
                SysTableColumn columnobj = new SysTableColumn();
                columnobj.setColumnId(GenerateUUID.getUUID());
                columnobj.setTableId(sysTable.getTableId());
                columnobj.setColumnCode(sysTable.getTablePk());
                columnobj.setColumnName("主键");
                columnobj.setColumnType("VARCHAR");
                columnobj.setColumnLength(36);
                columnobj.setColumnNumber(1);
                columnobj.setColumnTime(new Date());
                columnobj.setColumnPk("1");
                columnobj.setColumnJavaSmallHump(HumpConversion.baseTurnHump(sysTable.getTablePk()));
                cList.add(columnobj);

                if (request.isGeneralField()) {
                    baseMapper.createTableMin(sysTable.getTableDb(), sysTable.getTableCode(), sysTable.getTablePk());
                    SysTableColumn columnobj1 = new SysTableColumn();
                    columnobj1.setColumnId(GenerateUUID.getUUID());
                    columnobj1.setTableId(sysTable.getTableId());
                    columnobj1.setColumnCode("Create_Uid");
                    columnobj1.setColumnName("创建人id");
                    columnobj1.setColumnType("VARCHAR");
                    columnobj1.setColumnLength(36);
                    columnobj1.setColumnNumber(2);
                    columnobj1.setColumnTime(new Date());
                    columnobj1.setColumnPk("2");
                    columnobj1.setColumnJavaSmallHump(HumpConversion.baseTurnHump("Create_Uid"));
                    cList.add(columnobj1);
                    SysTableColumn columnobj2 = new SysTableColumn();
                    columnobj2.setColumnId(GenerateUUID.getUUID());
                    columnobj2.setTableId(sysTable.getTableId());
                    columnobj2.setColumnCode("Create_Uname");
                    columnobj2.setColumnName("创建人名称");
                    columnobj2.setColumnType("VARCHAR");
                    columnobj2.setColumnLength(100);
                    columnobj2.setColumnNumber(3);
                    columnobj2.setColumnTime(new Date());
                    columnobj2.setColumnJavaSmallHump(HumpConversion.baseTurnHump("Create_Uname"));
                    cList.add(columnobj2);
                    SysTableColumn columnobj3 = new SysTableColumn();
                    columnobj3.setColumnId(GenerateUUID.getUUID());
                    columnobj3.setTableId(sysTable.getTableId());
                    columnobj3.setColumnCode("Create_Time");
                    columnobj3.setColumnName("创建时间");
                    columnobj3.setColumnType("TIMESTAMP");
                    columnobj3.setColumnNumber(4);
                    columnobj3.setColumnTime(new Date());
                    columnobj3.setColumnJavaSmallHump(HumpConversion.baseTurnHump("Create_Time"));
                    cList.add(columnobj3);
                    SysTableColumn columnobj4 = new SysTableColumn();
                    columnobj4.setColumnId(GenerateUUID.getUUID());
                    columnobj4.setTableId(sysTable.getTableId());
                    columnobj4.setColumnCode("State");
                    columnobj4.setColumnName("审批状态");
                    columnobj4.setColumnType("INTEGER");
                    columnobj4.setColumnNumber(5);
                    columnobj4.setColumnTime(new Date());
                    columnobj4.setColumnPk("1");
                    columnobj4.setColumnJavaSmallHump(HumpConversion.baseTurnHump("State"));
                    cList.add(columnobj4);
                    SysTableColumn columnobj5 = new SysTableColumn();
                    columnobj5.setColumnId(GenerateUUID.getUUID());
                    columnobj5.setTableId(sysTable.getTableId());
                    columnobj5.setColumnCode("State_Time");
                    columnobj5.setColumnName("审批时间");
                    columnobj5.setColumnType("TIMESTAMP");
                    columnobj5.setColumnNumber(6);
                    columnobj5.setColumnTime(new Date());
                    columnobj5.setColumnJavaSmallHump(HumpConversion.baseTurnHump("State_Time"));
                    cList.add(columnobj5);
                } else {
                    baseMapper.createTableSimple(sysTable.getTableDb(), sysTable.getTableCode(), sysTable.getTablePk());
                }
                sysTableColumnService.saveBatch(cList);
                String content = "新增表：" + sysTable.getTableCode() + "；";
                sender.sendAudit(content, uId, userObj.getuName(), sysTable.getTableId(), AuditTypeEnum.add.getkey());
            }
            baseMapper.createUniqueIndex(sysTable, sysTable.getTablePk());
            return BaseResponse.ok();
        }
    }


    private String toLowerCamelCase(String input) {
        String codeStr = HumpConversion.underlineToCamel(input.toLowerCase(Locale.ENGLISH));
        if (ValidateUtil.isNotEmpty(codeStr)) {
            codeStr = codeStr.substring(0, 1).toLowerCase(Locale.ENGLISH) + codeStr.substring(1, codeStr.length());

        }
        return codeStr;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeleteTable(TableDto request, String uId) {
        SysTable sysTable = baseMapper.selectById(request.getTableId());
        if (sysTable == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(request.getTableId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSysTableinfo(String tableId) {
        SysTable obj = baseMapper.selectById(tableId);
        return BaseResponse.ok(obj, 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncTableAndColumn(String id, int drop) {
        SysTable table = baseMapper.selectById(id);
        if (table == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        //drop表
        if (drop == 1) dropTable(table.getTableDb(), table.getTableCode());
        List<SysTableColumn> columns = sysTableColumnService.getColumnListByTableCode(table.getTableCode());
        List<SysTableColumn> pks = columns.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnPk()) && n.getColumnPk().equals("1")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pks)) return BaseResponse.fail(BaseMsg.columnNotPk);
        String pk = pks.stream().findFirst().get().getColumnCode();
        //创建表
        baseMapper.createTable(table, columns, pk);
        //创建主键索引
        baseMapper.createUniqueIndex(table, pk);
        return BaseResponse.ok();
    }

    @Override
    public List<String> getTableIdByCode(String code) {
        QueryWrapper<SysTable> wrapper = new QueryWrapper<>();
        wrapper.eq("Table_Code", code);
        List<SysTable> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(SysTable::getTableId).collect(Collectors.toList());
        return null;
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


    @Override
    public BaseResponse updateTableDataById(Map<String, Object> request, String uId) {
        //获取数据库表表字段
        List<SysTableColumn> columns = sysTableColumnService.getColumnListByTableCode(request.get("tableCode").toString());
        if (!CollectionUtils.isEmpty(columns)) {


            List<String> sql = new ArrayList<>();
            for (Map.Entry<String, Object> entry : request.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if ((!CollectionUtils.isEmpty(columns.stream().filter(n -> n.getColumnCode().equals(key)).collect(Collectors.toList())))) {
                    sql.add(key + "='" + val + "'");
                }
            }
            if (sql.size() > 0) {
                String sqlStr = String.join(",", sql);
                String tableDb = request.get("tableDb").toString();
                String tableCode = request.get("tableCode").toString();
                String tablePk = request.get("tablePk").toString();
                String id = request.get("id").toString();
                baseMapper.updateTableDataById(tableDb, tableCode, sqlStr, tablePk, id);
                SysUser userObj = sysUserService.getById(uId);
                String content = "修改" + "”" + tableCode + "“表中" + tablePk + "为“" + id + "”；修改内容为：" + sqlStr;
                sender.sendAudit(content, uId, userObj.getuName(), id, AuditTypeEnum.upd.getkey());
            }
        }
        return BaseResponse.ok();
    }

    private static final String[] SQL_KEYWORDS = new String[]{
            "SELECT", "UPDATE", "DELETE", "INSERT", "CREATE", "ALTER", "DROP", "TRUNCATE"
    };

    public static boolean containsSqlKeyword(String input) {
        if (input == null) {
            return false;
        }
        input = input.toUpperCase(Locale.ENGLISH); // 转换为大写，便于匹配
        for (String keyword : SQL_KEYWORDS) {
            if (input.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteTableDataById(String tableId, String id, String uId) {
        //检测id里有没有非法字符
        if (containsSqlKeyword(id)) {
            return BaseResponse.fail("参数错误");
        }
        SysTable obj = baseMapper.selectById(tableId);
        SysUser userObj = sysUserService.getById(uId);
        if (obj != null) {
            baseMapper.deleteTableData("delete  from " + obj.getTableCode() + " where " + obj.getTablePk() + "='" + id + "'");
            baseMapper.deleteTableData("delete from Sys_Extend_Value where FileID='" + id + "'");
            baseMapper.deleteTableData("delete from SYS_File where FileID='" + id + "'");
            baseMapper.deleteTableData("delete from sys_fileannex where  File_Entity_Id='" + id + "'");
            baseMapper.deleteTableData("delete from SYS_Task where TaskEntityID='" + id + "'");
            baseMapper.deleteTableData("delete from Sys_Task_Process_User  where  Tpu_Tp_Id in  (select tp_id from Sys_Task_Processin where tp_entityid='" + id + "')");
            baseMapper.deleteTableData("delete from Sys_Task_Processin where tp_entityid='" + id + "'");
            baseMapper.deleteTableData("delete from Sys_Task_Processin_Time where Tpt_File_Id='" + id + "'");
            baseMapper.deleteTableData("delete from SYS_Task_Backups where TaskEntityID='" + id + "'");
            baseMapper.deleteTableData("delete from Sys_Task_Auto_Complete_Log where  File_Id='" + id + "'");

            String content = "删除" + "”" + obj.getTableCode() + "“表中" + obj.getTablePk() + "为“" + id + "”；";
            sender.sendAudit(content, uId, userObj.getuName(), id, AuditTypeEnum.del.getkey());
            return BaseResponse.ok();
        } else {
            return BaseResponse.fail("未获取到");
        }
    }

    @Override
    public void createTableColumn(String tableDb, String tableCode, String columnCode, Integer columnLength, String columnType) {
        columnType = columnType.toUpperCase(Locale.ENGLISH);
        baseMapper.createTableColumn(tableDb, tableCode, columnCode, columnLength, columnType);

    }

    @Override
    public void updateTableColumnName(String tableDb, String tableCode, String oldColumnName, String newColumnName) {
        baseMapper.updateTableColumnName(tableDb, tableCode, oldColumnName, newColumnName);
    }

    @Override
    public void updateTableColumnType(String tableDb, String tableCode, String columnCode, String columnType, Integer columnLength) {
        columnType = columnType.toUpperCase(Locale.ENGLISH);
        baseMapper.updateTableColumnType(tableDb, tableCode, columnCode, columnType, columnLength);
    }


    public String tableColumnSql(Map<String, String> fieldMaps) {
        String stt = fieldMaps.get("SYS_TableColumn") + "(" + fieldMaps.get("Column_ID") + ", " + fieldMaps.get("Table_ID") + ",  " + fieldMaps.get("Column_Code") + ", " + fieldMaps.get("Column_Name") + ",  " + fieldMaps.get("Column_Type") + ",  " + fieldMaps.get("Column_Length") + ", " + fieldMaps.get("Column_Time") + ", " + fieldMaps.get("Column_Number") + ", " + fieldMaps.get("Column_Java_Small_Hump") + ") ";
        return stt;
    }


    public Map<String, String> setFieldMaps(String owner) {
        List<TableInfoVo> tableinfolist = tableManagementService.getTableInfoAll(owner);
        //判断当前数据库中实际表字段名称是大写还是小写
        Map<String, String> fieldMaps = new HashMap<>();
        List<TableInfoVo> minTables = tableinfolist.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals("sys_table") || n.getTableCode().toLowerCase(Locale.ENGLISH).equals("sys_tablecolumn")).collect(Collectors.toList());
        List<String> tableKeyList = new ArrayList<>();
        tableKeyList.add("Table_PK");
        tableKeyList.add("Table_ID");
        tableKeyList.add("Table_Code");
        tableKeyList.add("Table_DB");
        tableKeyList.add("Table_CreateTime");

        List<String> tableColumnKeyList = new ArrayList<>();
        tableColumnKeyList.add("Column_ID");
        tableColumnKeyList.add("Table_ID");
        tableColumnKeyList.add("Column_Code");
        tableColumnKeyList.add("Column_Type");
        tableColumnKeyList.add("Column_Length");
        tableColumnKeyList.add("Column_Time");
        tableColumnKeyList.add("Column_Number");
        tableColumnKeyList.add("Column_Java_Small_Hump");

        for (TableInfoVo item : minTables) {
            List<TabcolumsVo> columnlist = tableManagementService.getTableColumnByTableCode(owner, item.getTableCode());
            if (item.getTableCode().toLowerCase(Locale.ENGLISH).equals("sys_table")) {
                fieldMaps.put("Sys_Table", item.getTableCode());

                for (TabcolumsVo itam : columnlist) {
                    String key = itam.getColumnCode().toLowerCase(Locale.ENGLISH);
                    for (String keyStr : tableKeyList) {
                        if (keyStr.toLowerCase(Locale.ENGLISH).equals(key)) {
                            fieldMaps.put(keyStr, itam.getColumnCode());
                        }
                    }
                }
                //
            }

            if (item.getTableCode().toLowerCase(Locale.ENGLISH).equals("sys_tablecolumn")) {
                fieldMaps.put("SYS_TableColumn", item.getTableCode());
                for (TabcolumsVo itam : columnlist) {
                    String key = itam.getColumnCode().toLowerCase(Locale.ENGLISH);
                    for (String keyStr : tableColumnKeyList) {
                        if (keyStr.toLowerCase(Locale.ENGLISH).equals(key)) {
                            fieldMaps.put(keyStr, itam.getColumnCode());
                        }
                    }
                }
            }
        }

        return fieldMaps;
    }

    /**
     * 获取表的SQL
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getTableSql(TableSqlDTO request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getTableId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SysTable table = this.getById(request.getTableId());
        if (ValidateUtil.isNull(table)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<SysTableColumn> list = sysTableColumnService.list(Wrappers.<SysTableColumn>lambdaQuery().eq(SysTableColumn::getTableId, request.getTableId()));
        // 表名
        String tableCode = table.getTableCode();
        // 主键
        List<SysTableColumn> pks = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getColumnPk()) && "1".equals(n.getColumnPk())).collect(Collectors.toList());
        List<String> pksCodes = new ArrayList<>();
        if (ValidateUtil.isEmpty(pks)) {
            pksCodes.add(table.getTablePk());
        } else {
            pksCodes = pks.stream().map(SysTableColumn::getColumnCode).collect(Collectors.toList());
        }
        // 删除State
        pksCodes.remove("State");
        if (ValidateUtil.isEmpty(pksCodes)) {
            return BaseResponse.fail("表【" + tableCode + "】未设置主键，无法生成建表语句");
        }
        StringBuilder tableSql = new StringBuilder();
        if (ValidateUtil.isNotEmpty(request.getDelete()) && "1".equalsIgnoreCase(request.getDelete())) {
            // 添加删除表语句
            tableSql.append("drop table if exists ").append(tableCode).append(";\n");
        }
        // 构建建表语句
        if (ValidateUtil.isNotEmpty(request.getCreate()) && "1".equalsIgnoreCase(request.getCreate())) {
            if (ValidateUtil.isNotEmpty(request.getDelete()) && "1".equalsIgnoreCase(request.getDelete())) {
                tableSql.append("\n");
            }
            tableSql.append("create table ").append(tableCode).append("\n(\n");
            list.sort(Comparator.comparing(SysTableColumn::getColumnNumber, Comparator.nullsLast(Comparator.naturalOrder())));
            for (SysTableColumn column : list) {
                // 按照顺序添加字段，主键设置为not null，其他字段默认为null
                // 字段名
                String columnCode = column.getColumnCode();
                // 字段类型
                String columnType = column.getColumnType();
                // 字段长度
                Integer columnLength = column.getColumnLength();
                tableSql.append("    ").append(columnCode).append(" ");
                // 某些类型需要指定长度
                tableSql.append(SqlUtils.handleColumnLength(columnType, columnLength));

                // 是否允许为空
                tableSql.append(SqlUtils.handleNullConstraint(column.getColumnPk(), columnCode, column.getColumnNull()));

                tableSql.append(",");
                tableSql.append("\n");
            }

            // 添加主键约束
            tableSql.append("    primary key (")
                    .append(String.join(", ", pksCodes))
                    .append(")\n");

            tableSql.append(");");
        }

        // 备注
        if (ValidateUtil.isNotEmpty(request.getRemark()) && "1".equalsIgnoreCase(request.getRemark())) {
            if (ValidateUtil.isNotEmpty(request.getCreate()) && "1".equalsIgnoreCase(request.getCreate())) {
                tableSql.append("\n");
            }
            // 获取数据库类型和名称
            BaseResponse dbName = tableManagementService.getDbName();
            if (!dbName.getSuccess()) {
                return BaseResponse.ok(tableSql.toString());
            }
            Object data = dbName.getData();
            String dbType = ((Map<String, String>) data).get("dbName");
            if (ValidateUtil.isEmpty(dbType)) {
                return BaseResponse.ok(tableSql.toString());
            }
            String remarkSql = SqlUtils.generateTableAndColumnRemarks(dbType, table, list);
            if (ValidateUtil.isNotEmpty(remarkSql)) {
                tableSql.append("\n").append(remarkSql);
            }
        }

        // 全部的字段的新增
        if (ValidateUtil.isNotEmpty(request.getColumns()) && "1".equalsIgnoreCase(request.getColumns())) {
            if (ValidateUtil.isNotEmpty(request.getRemark()) && "1".equalsIgnoreCase(request.getRemark())) {
                tableSql.append("\n");
            }
            for (SysTableColumn column : list) {
                // 字段名
                String columnCode = column.getColumnCode();
                // 字段类型
                String columnType = column.getColumnType();
                // 字段长度
                Integer columnLength = column.getColumnLength();
                tableSql.append("alter table ").append(tableCode)
                        .append(" add ").append(columnCode).append(" ")
                        .append(SqlUtils.handleColumnLength(columnType, columnLength))
                        .append(SqlUtils.handleNullConstraint(column.getColumnPk(), columnCode, column.getColumnNull()))
                        .append(";\n");
            }
        }
        return BaseResponse.ok(tableSql.toString());
    }
}
