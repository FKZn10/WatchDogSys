package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.TableInfoMapper;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.dto.SelectTableDto;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.entity.TableInfo;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import com.jpmsoft.userProducer.pojo.vo.TableForeignKeyVo;
import com.jpmsoft.userProducer.pojo.vo.TableInfoVo;
import com.jpmsoft.userProducer.rabbitMQ.Sender;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.HumpConversion;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableManagementIpml extends ServiceImpl<TableInfoMapper, TableInfo> implements ITableManagementService {
    @Value("${system.dbName}")
    public String dbname;
    @Resource
    ISysUserService ISysUserService;
    @Resource
    ISysTableService sysTableService;
    @Resource
    ISysTableColumnService sysTableColumnService;
    @Resource
    ITableColumnManagementService tableColumnManagementService;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private RedisUtils redisUtils;
    @Resource
    private Sender sender;
    @Override
    public BaseResponse getTableInfo(String owner, String userid) {
        if (!Getselectrolue(userid)) {
            return BaseResponse.fail(BaseMsg.notAuthUser);
        }
        List<TableInfoVo> models = baseMapper.getTableInfoList(owner);

        return BaseResponse.ok(models, models.size());
    }

    @Override
    public BaseResponse gettabledata(SelectTableDto request, String userid) {
        if (!Getselectrolue(userid)) return BaseResponse.fail(BaseMsg.notAuthUser);
        String tableName = "";
        String owner = request.getOwner();
        String keyStr = request.getTablePk();
        String valueStr = request.getValueStr();

        if (ValidateUtil.isEmpty(keyStr)) {
            keyStr = null;

        }
        if (ValidateUtil.isEmpty(valueStr)) {
            valueStr = null;

        }
        if (ValidateUtil.isEmpty(owner)) {
            owner = "Vue_Antd_Admin";
        }
        List<TableInfoVo> tableinfolist = baseMapper.getTableInfoList(owner);
        List<TableInfoVo> tableinfo = tableinfolist.stream().filter(n -> n.getTableCode().equalsIgnoreCase(request.getTableCode())).collect(Collectors.toList());
        if (tableinfo.size() > 0) {
            tableName = tableinfo.get(0).getTableCode();

            List<Map<String, Object>> models = baseMapper.selecttabledata(tableName, keyStr, valueStr, request.getStart(), request.getSize());
            Integer count = baseMapper.selecttabledatacount(tableName, keyStr, valueStr);
            return BaseResponse.ok(models, count);
        } else {
            return new BaseResponse("参数错误");
        }

    }

    @Override
    public synchronized BaseResponse synchronizationpProperties(String owner, String userid) {
        Object d = redisUtils.get("synchronizationpTableStates");
        if (!Getselectrolue(userid)) return BaseResponse.fail(BaseMsg.notAuthUser);
        if ("1".equals(d)) {
            return BaseResponse.fail("当前正在执行数据表同步程序，请勿重复点击");
        }
        //往消息队列中发送消息信息
        sender.sendTableSynchronization(owner+"//"+new Date().getTime());
        return BaseResponse.ok("数据表同步操作已提交，请等待30分钟后继续操作后续业务");
    }

    @Override
    public void synchronizationpBind(String owner) {
        try {
            if (ValidateUtil.isEmpty(owner)) {
                owner = "Vue_Antd_Admin";
            }
            redisUtils.set("synchronizationpTableStates", "1");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datastr = sdf.format(new Date());
            List<TableInfoVo> tableInfoList = baseMapper.selectSysTable(owner);
            // 获取当前系统中的所有表信息
            QueryWrapper<SysTable> SysTablewrp = new QueryWrapper<>();

            List<SysTable> tableList = sysTableService.list(SysTablewrp);
            for (TableInfoVo item : tableInfoList) {
                try {
                    List<SysTable> updateTables = new ArrayList<>();
                    List<SysTable> addTables = new ArrayList<>();
                    List<SysTableColumn> tableColumns = new ArrayList<>();

                    String tableid = "";
                    String tablePk = "";
                    // 获取当前表信息
                    List<TabcolumsVo> tableColumnList = baseMapper.getTableColumnByTableCode1(owner, item.getTableCode());
                    if (!CollectionUtils.isEmpty(tableColumnList)) {
                        List<TabcolumsVo> pkList = tableColumnList.stream().filter(n -> "P".equals(n.getIsPek()) || "PRI".equals(n.getIsPek())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(pkList)) {
                            tablePk = pkList.get(0).getColumnCode();
                        }
                    }
                    // 获取当前记录的表信息
                    List<SysTable> tabs = tableList.stream().filter(n -> n.getTableCode() != null && n.getTableCode().toLowerCase(Locale.ENGLISH).equals(item.getTableCode().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(tabs)) {
                        // 当前表没有新增表信息
                        tableid = tabs.get(0).getTableId();
                        String tableCode = tabs.get(0).getTableCode();
                        if (ValidateUtil.isEmpty(tabs.get(0).getTablePk())) {
                            if (!ValidateUtil.isEmpty(tablePk)) {
                                SysTable obj = new SysTable();
                                obj.setTablePk(tablePk);
                                obj.setTableId(tableid);
                                obj.setTableCode(item.getTableCode());
                                obj.setTableDb(item.getOwner());
                                updateTables.add(obj);
                            }
                        } else if (tabs.get(0).getTableCode().equals(item.getTableCode()) == false && tabs.get(0).getTableDb().equals(item.getOwner()) == false) {
                            SysTable obj = new SysTable();
                            obj.setTableId(tableid);
                            obj.setTableCode(item.getTableCode());
                            obj.setTableDb(item.getOwner());
                            updateTables.add(obj);
                        }

                    } else {
                        tableid = GenerateUUID.getUUID();
                        SysTable newtable = new SysTable();
                        newtable.setTableId(tableid);
                        newtable.setTableCode(item.getTableCode());
                        newtable.setTableDb(item.getOwner());
                        newtable.setTableCreatetime(new Date());
                        addTables.add(newtable);
                    }

                    QueryWrapper<SysTableColumn> SysTableColumnwrp = new QueryWrapper<>();
                    SysTableColumnwrp.eq("Table_ID", tableid);
                    List<SysTableColumn> TableColumnlist = sysTableColumnService.list(SysTableColumnwrp);
                    if (!CollectionUtils.isEmpty(tableColumnList)) {
                        for (TabcolumsVo itam : tableColumnList) {
                            String tableidstr = tableid;
                            List<SysTableColumn> TableColumnlistm = TableColumnlist.stream().filter(n -> n.getColumnCode().toLowerCase(Locale.ENGLISH).equals(itam.getColumnCode().toLowerCase(Locale.ENGLISH)) && n.getTableId().equals(tableidstr)).collect(Collectors.toList());
                            if (TableColumnlistm.size() == 0) {
                                SysTableColumn columnobj = new SysTableColumn();
                                columnobj.setTableId(tableid);
                                columnobj.setColumnId(GenerateUUID.getUUID());
                                columnobj.setColumnCode(itam.getColumnCode());
                                columnobj.setColumnType(itam.getDataType());
                                try {
                                    columnobj.setColumnLength(ValidateUtil.isEmpty(itam.getDataLength()) ? null : Integer.parseInt(itam.getDataLength()));
                                } catch (Exception e) {
                                    columnobj.setColumnLength(null);
                                }
                                columnobj.setColumnNumber(itam.getColumnId());
                                // columnobj.setColumnCsharpcode(codeStr);
                                columnobj.setColumnJavaSmallHump(HumpConversion.baseTurnHump(itam.getColumnCode()));
                                columnobj.setColumnTime(new Date());
                                tableColumns.add(columnobj);

                            }
                        }

                    }
                    log.warn("当前执行" + item.getTableCode() + "：" + addTables.size() + "____|" + updateTables.size() + "____|" + tableColumns.size());
                    if (addTables.size() > 0) {
                        boolean num = sysTableService.saveBatch(addTables);
                    }
                    if (updateTables.size() > 0) {
                        boolean num = sysTableService.saveOrUpdateBatch(updateTables);
                    }
                    if (tableColumns.size() > 0) {
                        boolean num = sysTableColumnService.saveBatch(tableColumns);
                    }
                } catch (Exception ex) {
                    log.error("up服务——同步数据表结构：" + ex);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("up服务———数据同步错误" + ex);
        } finally {
            redisUtils.set("synchronizationpTableStates", "0");
        }
    }

    @Override
    public Integer insertTableAndColumn(List<String> sqlList) {
        return baseMapper.insertTableAndColumn(sqlList);
    }


    @Override
    public BaseResponse datacomparison(List<ExlTableColumnDto> request, String owner, String exlstate, String userid) {
        if (!Getselectrolue(userid)) return BaseResponse.fail(BaseMsg.notAuthUser);
        if (ValidateUtil.isEmpty(owner)) {
            owner = "Vue_Antd_Admin";
        }

        List<TabcolumsVo> tablelist = baseMapper.getTableColumn(owner, null);
        Map<String, List<TabcolumsVo>> keyList = tablelist.stream().collect(Collectors.groupingBy(TabcolumsVo::getTableCode));
        String tablename = "";
        List<String> tablenamelist = new ArrayList<>();
        List<String> columnsqllist = new ArrayList<>();
        if (exlstate.equals("1")) {
            for (ExlTableColumnDto itmd : request) {
                try {
                    if (!itmd.getTableCode().toLowerCase(Locale.ENGLISH).equals(tablename.toLowerCase(Locale.ENGLISH))) {
                        List<TabcolumsVo> newlist = keyList.get(itmd.getTableCode());
                        if (newlist == null) {
                            newlist = keyList.get(itmd.getTableCode().toLowerCase(Locale.ENGLISH));
                        }
                        if (newlist == null) {
                            if (!tablenamelist.contains(itmd.getTableCode().toLowerCase(Locale.ENGLISH))) {
                                tablenamelist.add(itmd.getTableCode().toLowerCase(Locale.ENGLISH));
                            }
                        } else {
                            if (newlist.size() == 0) {
                                if (!tablenamelist.contains(itmd.getTableCode().toLowerCase(Locale.ENGLISH))) {
                                    tablenamelist.add(itmd.getTableCode().toLowerCase(Locale.ENGLISH));
                                }
                            }
                        }
                    }
                    if (!tablenamelist.contains(itmd.getTableCode().toLowerCase(Locale.ENGLISH))) {
                        List<TabcolumsVo> TableColumnlist = tablelist.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(itmd.getTableCode().toLowerCase(Locale.ENGLISH)) && n.getColumnCode().toLowerCase(Locale.ENGLISH).equals(itmd.getColumnCode().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                        if (TableColumnlist.size() == 0) {
                            String sql = "alter table  " + itmd.getTableCode() + " ADD " + itmd.getColumnCode() + " ";
                            if (ValidateUtil.isEmpty(itmd.getColumnType())) {
                                sql += itmd.getDataType();
                                if (!(itmd.getDataType().contains("INT") || itmd.getDataType().equals("TIMESTAMP") || itmd.getDataType().equals("SMALLINT"))) {
                                    if (itmd.getDataPrecision() == null) {
                                        sql = sql + "(" + itmd.getDataLength() + ")";
                                    } else {
                                        sql = sql + "(" + itmd.getDataPrecision() + "," + itmd.getDataScale() + ")";
                                    }
                                }
                            } else {
                                sql += itmd.getColumnType();
                            }

                            sql = sql + ";";
                            columnsqllist.add(sql);
                        } else {
                            TabcolumsVo Columnnobj = TableColumnlist.get(0);
                            if (ValidateUtil.isEmpty(itmd.getColumnType())) {
                                if (CheckFieldType(itmd.getDataType(), Columnnobj.getDataType(), itmd.getDataLength(), Columnnobj.getDataLength())) {
                                    String sql1 = "--表“" + Columnnobj.getTableCode() + "”字段:" + Columnnobj.getColumnCode() + ",原类型" + Columnnobj.getDataType() + "；现类型：" + itmd.getDataType() + "；原长度：" + Columnnobj.getDataLength() + ";现长度：" + itmd.getDataLength() + ";";
                                    columnsqllist.add(sql1);
                                    String sql = "alter table  " + itmd.getTableCode() + " modify " + itmd.getColumnCode() + " ";
                                    sql += itmd.getDataType();
                                    if (!(itmd.getDataType().contains("INT") || itmd.getDataType().equals("TIMESTAMP") || itmd.getDataType().equals("SMALLINT"))) {
                                        if (Columnnobj.getDataPrecision() == null) {
                                            if (!ValidateUtil.isEmpty(itmd.getDataLength())) {
                                                sql = sql + "(" + itmd.getDataLength() + ")";
                                            }
                                        } else {
                                            sql = sql + "(" + itmd.getDataPrecision() + "," + itmd.getDataScale() + ")";
                                        }
                                    }
                                    sql = sql + ";";
                                    columnsqllist.add(sql);
                                }
                            } else {
                                if (!itmd.getColumnType().toLowerCase(Locale.ENGLISH).equals(Columnnobj.getColumnType().toLowerCase(Locale.ENGLISH))) {
                                    String sql = "alter table  " + itmd.getTableCode() + " modify " + itmd.getColumnCode() + "  " + itmd.getColumnType();
                                    columnsqllist.add(sql);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    String ex = e.getMessage();
                }
            }
        } else {
            for (String key : keyList.keySet()) {
                List<TabcolumsVo> list = keyList.get(key);
                if (list == null || list.size() == 0) {
                    list = keyList.get(key.toLowerCase(Locale.ENGLISH));
                }
                if (list.size() > 0) {
                    List<ExlTableColumnDto> TableColumnnlist = new ArrayList<>();
                    try {
                        TableColumnnlist = request.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                    } catch (Exception ex) {
                        tablenamelist.add(key.toLowerCase(Locale.ENGLISH));
                    }
                    if (TableColumnnlist.size() == 0) {
                        tablenamelist.add(key.toLowerCase(Locale.ENGLISH));
                    } else {
                        for (TabcolumsVo Columnnobj : list) {
                            try {
                                String cname = Columnnobj.getColumnCode().toLowerCase(Locale.ENGLISH);
                                List<ExlTableColumnDto> Columnnlist = TableColumnnlist.stream().filter(n -> n.getColumnCode().toLowerCase(Locale.ENGLISH).equals(cname)).collect(Collectors.toList());
                                if (Columnnlist.size() == 0) {
                                    String sql = "alter table  " + Columnnobj.getTableCode() + " ADD " + Columnnobj.getColumnCode() + " ";

                                    if (ValidateUtil.isEmpty(Columnnobj.getColumnType())) {
                                        sql += Columnnobj.getDataType();
                                        if (!(Columnnobj.getDataType().contains("INT") || Columnnobj.getDataType().equals("TIMESTAMP") || Columnnobj.getDataType().equals("SMALLINT"))) {
                                            if (Columnnobj.getDataPrecision() == null) {
                                                if (!ValidateUtil.isEmpty(Columnnobj.getDataLength())) {
                                                    sql = sql + "(" + Columnnobj.getDataLength() + ")";
                                                }
                                            } else {
                                                sql = sql + "(" + Columnnobj.getDataPrecision() + "," + Columnnobj.getDataScale() + ")";
                                            }
                                        }
                                    } else {
                                        sql += Columnnobj.getColumnType();
                                    }
                                    sql = sql + ";";
                                    columnsqllist.add(sql);
                                } else {
                                    ExlTableColumnDto ExlTableColumn = Columnnlist.get(0);
                                    if (ValidateUtil.isEmpty(ExlTableColumn.getColumnType())) {
                                        if (CheckFieldType(Columnnobj.getDataType(), ExlTableColumn.getDataType(), Columnnobj.getDataLength(), ExlTableColumn.getDataLength())) {
                                            String sql1 = "--表“" + Columnnobj.getTableCode() + "”字段:" + Columnnobj.getColumnCode() + ",原类型" + ExlTableColumn.getDataType() + "；现类型：" + Columnnobj.getDataType() + "；原长度：" + ExlTableColumn.getDataLength() + ";现长度：" + Columnnobj.getDataLength() + ";";
                                            columnsqllist.add(sql1);
                                            String sql = "alter table  " + Columnnobj.getTableCode() + " modify " + Columnnobj.getColumnCode() + " ";
                                            sql += Columnnobj.getDataType();
                                            if (!(Columnnobj.getDataType().contains("INT") || Columnnobj.getDataType().equals("TIMESTAMP") || Columnnobj.getDataType().equals("SMALLINT"))) {
                                                if (Columnnobj.getDataPrecision() == null) {
                                                    sql = sql + "(" + Columnnobj.getDataLength() + ")";
                                                } else {
                                                    sql = sql + "(" + Columnnobj.getDataPrecision() + "," + Columnnobj.getDataScale() + ")";
                                                }
                                            }
                                            sql = sql + ";";
                                            columnsqllist.add(sql);
                                        }
                                    } else {
                                        if (!ExlTableColumn.getColumnType().equals(Columnnobj.getColumnType())) {
                                            String sql = "alter table  " + Columnnobj.getTableCode() + " modify " + Columnnobj.getColumnCode() + "  " + Columnnobj.getColumnType();
                                            columnsqllist.add(sql);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                String stri = e.getMessage();
                            }
                        }

                    }
                }
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("tablename", tablenamelist);
        data.put("columnsql", columnsqllist);
        return BaseResponse.ok(data, data.size());
    }

    private Boolean CheckFieldType(String newType, String oldType, String newLength, String oldLength) {

        Boolean str = false;
        if (newType.toLowerCase(Locale.ENGLISH).equals(oldType.toLowerCase(Locale.ENGLISH))) {
            //字段类型相同
            if (!ValidateUtil.isEmpty(newLength) && !ValidateUtil.isEmpty(oldLength) && newLength != null && oldLength != null && !oldLength.toLowerCase(Locale.ENGLISH).equals("null") && !newLength.toLowerCase(Locale.ENGLISH).equals("null")) {
                //长度都不为空
                if (!newLength.equals(oldLength)) {
                    //长度不一致
                    str = true;
                }
            }
        } else {
            //类型不一致时
            List<String> textList = new ArrayList<>();
            textList.add("longvarchar");
            textList.add("longtext");

            if (textList.contains(newType.toLowerCase(Locale.ENGLISH)) && textList.contains(oldType.toLowerCase(Locale.ENGLISH))) {
                return false;
            }

            if (newType.toLowerCase(Locale.ENGLISH).contains("int") && oldType.toLowerCase(Locale.ENGLISH).contains("int")) {
                return false;
            }
            str = true;
        }
        return str;
    }

    private Boolean Getselectrolue(String userid) {
        SysUser obj = ISysUserService.getById(userid);
        if (obj != null) {
            return true;
//            if (obj.getuAccount().equals("admin") || obj.getuAccount().equals("jpmadmin")) {
//                return true;
//            } else {
//                return false;
//            }
        } else {
            return false;
        }
    }


    @Override
    public BaseResponse getPatternName() {
        return BaseResponse.ok(baseMapper.getPatternName());
    }


    @Override
    public List<TableInfoVo> getTableByTableCode(String owner, String columnCode) {
        return baseMapper.getTableByTableCode(owner, columnCode);
    }

    @Override
    public List<TabcolumsVo> getTableColumnbByTableCodeAndColumnCode(String tableCode, String columnCode) {
        return baseMapper.getTableColumnbByTableCodeAndColumnCode(tableCode, columnCode);
    }


    @Override
    public List<TabcolumsVo> getTableColumnByTableCode(String owner, String tableCode) {
        return baseMapper.getTableColumnByTableCode(owner, tableCode);
    }

    @Override
    public List<TableInfoVo> getTableInfoAll(String owner) {
        return baseMapper.getTableInfoList(owner);
    }

    @Override
    public BaseResponse getDbName() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        String dbName = "";
        String schema = "";
        try {
            DatabaseMetaData databaseMetaData = sqlSession.getConnection().getMetaData();
            dbName = databaseMetaData.getDatabaseProductName();
            schema = databaseMetaData.getConnection().getSchema();
            Map<String, String> mag = new HashMap<>();
            mag.put("dbName", dbName);
            mag.put("schema", schema);
            return BaseResponse.ok(mag);
        } catch (Exception ex) {
            sqlSession.close();
            return BaseResponse.fail("获取数据信息失败");
        } finally {
            sqlSession.close();
            Map<String, String> mag = new HashMap<>();
            mag.put("dbName", dbName);
            mag.put("schema", schema);
            return BaseResponse.ok(mag);
        }
    }


    @Override
    public List<TableForeignKeyVo> getTableForeignKey(String owner) {
        return baseMapper.getTableForeignKey(owner);
    }

    @Override
    public List<TabcolumsVo> getTableColumnList(String owner, String tablecode) {
        List<TabcolumsVo> tableColumnList = baseMapper.getTableColumnByTableCode1(owner, tablecode);
        return tableColumnList;
    }

    @Override
    public BaseResponse getTableInfoAndColumnByTableColde(List<String> codeList) {
        QueryWrapper<SysTable> queryWrapper = new QueryWrapper<>();
        // queryWrapper.in("Table_Code", codeList);
        List<SysTable> tableList = sysTableService.list(queryWrapper);

        tableList = tableList.stream().filter(n -> codeList.contains(n.getTableCode().toUpperCase(Locale.ENGLISH))).collect(Collectors.toList());


        List<String> ids = tableList.stream().map(n -> n.getTableId()).collect(Collectors.toList());
        QueryWrapper<SysTableColumn> queryWrapperCol = new QueryWrapper<>();
        queryWrapperCol.in("Table_ID", ids);
        List<SysTableColumn> tableColList = sysTableColumnService.list(queryWrapperCol);


        Map<String, Object> returnData = new HashMap<>();
        returnData.put("tableList", tableList);
        returnData.put("tableColList", tableColList);

        return BaseResponse.ok(returnData);
    }


    public static class synchronizationpBind extends Thread {

        private ISysTableService sysTableService;
        private ISysTableColumnService sysTableColumnService;
        private String owner;
        private String userid;
        private RedisUtils redisUtils;
        private List<TableInfoVo> tableInfoList;
        private ITableColumnManagementService tableColumnManagementService;
        protected Log log;
        private TableInfoMapper baseMapper;
        // private BaseMapper baseMapper;

        public TableInfoMapper getBaseMapper() {
            return baseMapper;
        }

        public void setBaseMapper(TableInfoMapper baseMapper) {
            this.baseMapper = baseMapper;
        }

        public void setSysTableService(ISysTableService sysTableService) {
            this.sysTableService = sysTableService;
        }

        public void setSysTableColumnService(ISysTableColumnService sysTableColumnService) {
            this.sysTableColumnService = sysTableColumnService;
        }

        public ISysTableService getSysTableService() {
            return sysTableService;
        }

        public ISysTableColumnService getSysTableColumnService() {
            return sysTableColumnService;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public RedisUtils getRedisUtils() {
            return redisUtils;
        }

        public void setRedisUtils(RedisUtils redisUtils) {
            this.redisUtils = redisUtils;
        }

        public List<TableInfoVo> getTableInfoList() {
            return tableInfoList;
        }

        public void setTableInfoList(List<TableInfoVo> tableInfoList) {
            this.tableInfoList = tableInfoList;
        }

        public ITableColumnManagementService getTableColumnManagementService() {
            return tableColumnManagementService;
        }

        public void setTableColumnManagementService(ITableColumnManagementService tableColumnManagementService) {
            this.tableColumnManagementService = tableColumnManagementService;
        }

        public Log getLog() {
            return log;
        }

        public void setLog(Log log) {
            this.log = log;
        }

        @Override
        public void run() {
            try {
            } catch (
                    Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage());
            }
        }
    }
}
