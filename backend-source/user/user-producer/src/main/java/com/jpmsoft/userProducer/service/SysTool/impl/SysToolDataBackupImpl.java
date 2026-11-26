package com.jpmsoft.userProducer.service.SysTool.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysToolDataBackupMapper;
import com.jpmsoft.userProducer.pojo.dto.SysToolDataBackupDto;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackup;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackupQd;
import com.jpmsoft.userProducer.pojo.vo.SysToolTableRelationshipVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysTableService;
import com.jpmsoft.userProducer.service.ITableManagementService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolDataBackupQdService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolDataBackupService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolTableRelationshipService;
import com.jpmsoft.userProducer.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysToolDataBackupImpl extends ServiceImpl<SysToolDataBackupMapper, SysToolDataBackup> implements ISysToolDataBackupService {

    @Resource
    ISysToolTableRelationshipService sysToolTableRelationshipService;
    @Resource
    ISysToolDataBackupQdService sysToolDataBackupQdService;
    @Resource
    ISysTableService sysTableService;
    @Resource
    private ITableManagementService tableManagementService;
    @Resource
    private ISysConfigEntityService sysConfigService;


    String fileSql = "from  SYS_File where  FileID in";
    String fileAnnex = "from sys_fileannex where  File_Entity_Id in ";
    String tptSql = "from Sys_Task_Processin_Time  where  Tpt_File_Id in";
    String tpSql = "from Sys_Task_Processin where  tp_entityid in";
    String tpUsql = "from Sys_Task_Process_User where  Tpu_Tp_Id in (select  tp_id  from Sys_Task_Processin where   tp_entityid in ([id]) );";
    String file1Sql = "from  SYS_File where  FileID in (select  tp_id  from Sys_Task_Processin where   tp_entityid in ([id]));";
    String tbSql = "from SYS_Task_Backups where  TaskEntityID in ";
    String taclSql = "from Sys_Task_Auto_Complete_Log where  File_Id in ";
    String taskSql = "from SYS_Task where  TaskEntityID in ";
    String ctbSql = "from SYS_CoordinationTask_Backups where  CT_FileID in ";
    String ctSql = "from SYS_CoordinationTask where  CT_FileID in ";
    String cpSql = "from SYS_CoordinationProcess where  CP_FileID in ";
    String ccSql = "from SYS_CarbonCopy where  CC_FileID in ";


    @Override
    public BaseResponse GetSysToolDataBackupList(SysToolDataBackupDto request) {
        QueryMap<SysToolDataBackupDto> queryMap = new QueryMap<>(request);
        queryMap.put("stdbTableCode").put("stdbTitle")
                .put("timeStart", ValidateUtil.isEmpty(request.getStart()) ? null : DateUtil.String2Date(request.getStart(), "yyyy-MM-dd"));
        if (ValidateUtil.isEmpty(request.getEnd())) {
            queryMap.put("timeEnd", null);
        } else {
            Date timeEnd = DateUtil.DateChangeDay(DateUtil.String2Date(request.getEnd(), "yyyy-MM-dd"), 1);
            queryMap.put("timeEnd", timeEnd);
        }
        List<SysToolDataBackup> sysUserPojoIPage = baseMapper.getSysToolDataBackup(queryMap.getQuery());
        Integer total = baseMapper.getSysToolDataBackupCount(queryMap.getQuery());
        return BaseResponse.ok(sysUserPojoIPage, total);
    }


    @Override
    public BaseResponse DataBackup(SysToolDataBackupDto request) {
        long time = new Date().getTime();
        String logFilePath = sysConfigService.getSysConfigByCode("rootDirectory");
        //缺少配置参数
        if (ValidateUtil.isEmpty(logFilePath)) {
            return BaseResponse.fail("未配置数据备份存放地址");
        }
        logFilePath = logFilePath + "/delDataBackup/" + request.getStdbTableCode();
        List<SysToolTableRelationshipVo> alllList = sysToolTableRelationshipService.getSysToolTableRelationshipData(request.getStdbTableCode());

        QueryWrapper<SysTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Table_Code", request.getStdbTableCode());
        List<SysTable> tableList = sysTableService.list(queryWrapper);
        if (ValidateUtil.isEmpty(tableList)) {
            return BaseResponse.fail("数据表管理中未找到" + request.getStdbTableCode());
        }
        String sqlStr = "from  " + tableList.get(0).getTableCode() + " where " + tableList.get(0).getTablePk() + "='" + request.getDataId() + "'";
        Map<String, String> sqlList = getSelectCountSql(alllList, request.getStdbTableCode(), sqlStr);
        if (request.getStdbTableCode().toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase(Locale.ENGLISH))) {
            //获取当前表关联里面是否有Sec_Project
            List<SysToolTableRelationshipVo> secPS = alllList.stream().filter(n -> n.getSttrMainTable().trim().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).trim().equals(request.getStdbTableCode().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(secPS)) {
                List<SysToolTableRelationshipVo> secIs = alllList.stream().filter(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH).trim().equals("Sec_Project".toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).trim().equals("Sec_Info".toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secIs)) {
                    String secIql = "from  Sec_Info  where  " + secIs.get(0).getSttrAssociationKey() + " in ( select " + secIs.get(0).getSttrMainKey() + "  from  " + secIs.get(0).getSttrMainTable() + "  where  " + secPS.get(0).getSttrMainKey() + "  in (  select  " + secPS.get(0).getSttrAssociationKey() + " " + sqlStr + "))";
                    Map<String, String> sqlList1 = getSelectCountSql(alllList, "Sec_Info", secIql);
                    for (String key : sqlList1.keySet()) {
                        sqlList.put(key, sqlList1.get(key));
                    }
                    sqlList.put("Sec_Info".toLowerCase(Locale.ENGLISH), secIql);
                }
            }
        }
        sqlList.put(request.getStdbTableCode(), sqlStr);
        List<String> tableCodes = request.getIds();
        if (CollectionUtils.isEmpty(tableCodes)) {
            return BaseResponse.fail("参数错误");
        }
        QueryWrapper<SysTable> SysTablewrp = new QueryWrapper<>();
        List<SysTable> Tablelist = sysTableService.list(SysTablewrp);
        List<SysToolDataBackupQd> qdList = new ArrayList<>();
        int num = 0;
        for (String key : tableCodes
        ) {
            num++;
            String sql = sqlList.get(key);
            String fileName = key + "-" + DateUtil.Date2String(new Date(), "yyyyMMddHHmm") + ".txt";
            Boolean typeState =setText(logFilePath + "/" + request.getDataId(), fileName, sql);;
//            Map<String,Object> dataObj=new HashMap<>();
//            dataObj.put(key,baseMapper.querySql("select * " + sql));

            String idKey = "";
            List<SysTable> tabIfo = Tablelist.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tabIfo)) {
                idKey = tabIfo.get(0).getTablePk();
            }
            String fileSql1 = fileSql + " ( select  " + idKey + " " + sql + ");";
            String fileAnnexSql = fileAnnex + " ( select  " + idKey + " " + sql + ");";
            String tptSql1 = tptSql + " ( select  " + idKey + " " + sql + ");";
            String tpSql1 = tpSql + " ( select  " + idKey + " " + sql + ");";
            String tpUsql1 = (tpUsql.replace("[id]", "select  " + idKey + " " + sql + ""));
            String file1Sql1 = (file1Sql.replace("[id]", "select  " + idKey + " " + sql + ""));
            String tbSql1 = tbSql + " ( select  " + idKey + " " + sql + ");";
            String taclSql1 = taclSql + " ( select  " + idKey + " " + sql + ");";
            String taskSql1 = taskSql + " ( select  " + idKey + " " + sql + ");";
            String ctbSql1 = ctbSql + " ( select  " + idKey + " " + sql + ");";
            String ctSql1 = ctSql + " ( select  " + idKey + " " + sql + ");";
            String cpSql1 = cpSql + " ( select  " + idKey + " " + sql + ");";
            String ccSql1 = ccSql + " ( select  " + idKey + " " + sql + ");";

//            dataObj.put("SYS_File",baseMapper.querySql("select * " + fileSql1));
//            dataObj.put("sys_fileannex",baseMapper.querySql("select * " + fileAnnexSql));
//            dataObj.put("Sys_Task_Processin_Time",baseMapper.querySql("select * " + tptSql1));
//            dataObj.put("Sys_Task_Processin",baseMapper.querySql("select * " + tpSql1));
//            dataObj.put("Sys_Task_Process_User",baseMapper.querySql("select * " + tpUsql1));
//            dataObj.put("SYS_File1",baseMapper.querySql("select * " + file1Sql1));
//            dataObj.put("SYS_Task_Backups",baseMapper.querySql("select * " + tbSql1));
//            dataObj.put("Sys_Task_Auto_Complete_Log",baseMapper.querySql("select * " + taclSql1));
//            dataObj.put("SYS_Task",baseMapper.querySql("select * " + taskSql1));
//            dataObj.put("SYS_CoordinationTask_Backups",baseMapper.querySql("select * " + ctbSql1));
//            dataObj.put("SYS_CoordinationTask",baseMapper.querySql("select * " + ctSql1));
//            dataObj.put("SYS_CoordinationProcess",baseMapper.querySql("select * " + cpSql1));
//            dataObj.put("SYS_CarbonCopy",baseMapper.querySql("select * " + ccSql1));

            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_File.txt", fileSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "sys_fileannex.txt", fileAnnexSql);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "Sys_Task_Processin_Time.txt", tptSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "Sys_Task_Processin.txt", tpSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "Sys_Task_Process_User.txt", tpUsql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_File1.txt", file1Sql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_Task_Backups.txt", tbSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "Sys_Task_Auto_Complete_Log.txt", taclSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_Task.txt", taskSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_CoordinationTask_Backups" + ".txt", ctbSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_CoordinationTask.txt", ctSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_CoordinationProcess.txt", cpSql1);
            setText(logFilePath + "/" + request.getDataId() + "/" + key, "SYS_CarbonCopy.txt", ccSql1);
            if (typeState) {
                SysToolDataBackupQd obj = new SysToolDataBackupQd();
                obj.setId(GenerateUUID.getUUID());
                obj.setBackupFileAddress(request.getStdbTableCode() + "/" + request.getDataId() + "/" + fileName);
                obj.setDataCount(baseMapper.queryPageSize("select count(*) " + sql) + "");
                obj.setTableCode(key);
                obj.setSysId(request.getId());
                obj.setSerialNumber(num);
                obj.setDeletedState(0);
                obj.setRestoreState(0);
                qdList.add(obj);
            }
        }
        if (qdList.size() != tableCodes.size()) {
            return BaseResponse.fail("数据备份失败");
        }
        SysToolDataBackup ent = baseMapper.selectById(request.getId());
        if (ent == null) {
            //新增主表信息
            SysToolDataBackup entity = new SysToolDataBackup();
            BeanUtils.copyProperties(request, entity);
            entity.setStdbBackupStatus(false);
            entity.setStdbDeleteStatus(false);
            baseMapper.insert(entity);
        } else {
            ent.setStdbBackupStatus(request.getStdbBackupStatus());
        }
        sysToolDataBackupQdService.saveBatch(qdList);
        return BaseResponse.ok(tableCodes);
    }


    private Boolean setText(String logFilePath, String fileName, String sql) {
        try {
            File dir = new File(logFilePath);
            if (!dir.exists() || !dir.isDirectory())
                dir.mkdirs();
            File file = new File(logFilePath, fileName);
            if (!file.exists() || !file.isFile())
                file.createNewFile();
        } catch (Exception ex) {

        }
        Boolean typeState = true;
        List<Map<String, Object>> dataList = baseMapper.querySql("select * " + sql);
        List<String> datas = new ArrayList<>();
        try {
            for (Map<String, Object> obj : dataList
            ) {
                String result = obj.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(Collectors.joining(", "));
                datas.add(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            typeState = false;
        }

        File file = new File(logFilePath + "/" + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String data : datas) {
                writer.write(data);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            typeState = false;
        }
        return typeState;
    }


    public Map<String, String> getSelectCountSql(List<SysToolTableRelationshipVo> alllList, String TableCode, String sqlStr) {
        Map<String, String> sqlEntity = new HashMap<>();
        List<SysToolTableRelationshipVo> listData = alllList.stream().filter(n -> n.getSttrAssociationTable().toLowerCase().equals(TableCode.toLowerCase())).collect(Collectors.toList());
        listData.forEach(n -> {
            String sql = "from " + n.getSttrMainTable() + " where " + n.getSttrMainKey() + " in ( select " + n.getSttrAssociationKey() + "  " + sqlStr + ")";
            sqlEntity.put(n.getSttrMainTable(), sql);
            Map<String, String> obj = getSelectCountSql(alllList, n.getSttrMainTable(), sql);
            for (String key : obj.keySet()) {
                sqlEntity.put(key, obj.get(key));
            }
        });
        return sqlEntity;

    }

    @Override
    public BaseResponse DataDeletBatch(SysToolDataBackupDto request) {

        List<SysToolTableRelationshipVo> alllList = sysToolTableRelationshipService.getSysToolTableRelationshipData(request.getStdbTableCode());

        QueryWrapper<SysTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Table_Code", request.getStdbTableCode());
        List<SysTable> tableList = sysTableService.list(queryWrapper);
        if (ValidateUtil.isEmpty(tableList)) {
            return BaseResponse.fail("数据表管理中未找到" + request.getStdbTableCode());
        }
        String sqlStr = "from  " + tableList.get(0).getTableCode() + " where " + tableList.get(0).getTablePk() + "='" + request.getDataId() + "'";
        Map<String, String> sqlList = getSelectCountSql(alllList, request.getStdbTableCode(), sqlStr);
        if (request.getStdbTableCode().toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase(Locale.ENGLISH))) {
            //获取当前表关联里面是否有Sec_Project
            List<SysToolTableRelationshipVo> secPS = alllList.stream().filter(n -> n.getSttrMainTable().trim().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).trim().equals(request.getStdbTableCode().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(secPS)) {
                List<SysToolTableRelationshipVo> secIs = alllList.stream().filter(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH).trim().equals("Sec_Project".toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).trim().equals("Sec_Info".toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secIs)) {
                    String secIql = "from  Sec_Info  where  " + secIs.get(0).getSttrAssociationKey() + " in ( select " + secIs.get(0).getSttrMainKey() + "  from  " + secIs.get(0).getSttrMainTable() + "  where  " + secPS.get(0).getSttrMainKey() + "  in (  select  " + secPS.get(0).getSttrAssociationKey() + " " + sqlStr + "))";
                    Map<String, String> sqlList1 = getSelectCountSql(alllList, "Sec_Info", secIql);
                    for (String key : sqlList1.keySet()) {
                        sqlList.put(key, sqlList1.get(key));
                    }
                    sqlList.put("Sec_Info".toLowerCase(Locale.ENGLISH), secIql);
                }
            }
        }

        sqlList.put(request.getStdbTableCode(), sqlStr);
        List<String> tableCodes = request.getIds();
        if (CollectionUtils.isEmpty(tableCodes)) {
            return BaseResponse.fail("参数错误");
        }
        QueryWrapper<SysTable> SysTablewrp = new QueryWrapper<>();
        List<SysTable> Tablelist = sysTableService.list(SysTablewrp);
        List<String> deleteSqls = new ArrayList<>();
        for (String key : tableCodes
        ) {
            String sql = sqlList.get(key);
            String idKey = "";
            List<SysTable> tabIfo = Tablelist.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tabIfo)) {
                idKey = tabIfo.get(0).getTablePk();
            }
            String fileSql1 = "   delete " + fileSql + " ( select  " + idKey + " " + sql + ");";
            String fileAnnexSql = "delete  " + fileAnnex + " ( select  " + idKey + " " + sql + ");";
            String tptSql1 = "delete " + tptSql + " ( select  " + idKey + " " + sql + ");";
            String tpSql1 = "delete " + tpSql + " ( select  " + idKey + " " + sql + ");";
            String tpUsql1 = "delete " + (tpUsql.replace("[id]", "select  " + idKey + " " + sql + ""));
            String file1Sql1 = "delete " + (file1Sql.replace("[id]", "select  " + idKey + " " + sql + ""));
            String tbSql1 = "delete " + tbSql + " ( select  " + idKey + " " + sql + ");";
            String taclSql1 = "delete  " + taclSql + " ( select  " + idKey + " " + sql + ");";
            String taskSql1 = "delete " + taskSql + " ( select  " + idKey + " " + sql + ");";
            String ctbSql1 = "delete " + ctbSql + " ( select  " + idKey + " " + sql + ");";
            String ctSql1 = "delete " + ctSql + " ( select  " + idKey + " " + sql + ");";
            String cpSql1 = "delete " + cpSql + " ( select  " + idKey + " " + sql + ");";
            String ccSql1 = "delete " + ccSql + " ( select  " + idKey + " " + sql + ");";
            deleteSqls.add(ccSql1);
            deleteSqls.add(cpSql1);
            deleteSqls.add(ctSql1);
            deleteSqls.add(ctbSql1);
            deleteSqls.add(taskSql1);
            deleteSqls.add(taclSql1);
            deleteSqls.add(tbSql1);
            deleteSqls.add(file1Sql1);
            deleteSqls.add(tpUsql1);
            deleteSqls.add(tptSql1);
            deleteSqls.add(tpSql1);
            deleteSqls.add(fileAnnexSql);
            deleteSqls.add(fileSql1);
            deleteSqls.add("delete " + sql + ";");
        }
        baseMapper.deleteBath(deleteSqls);
        SysToolDataBackup ent = baseMapper.selectById(request.getId());
        ent.setStdbDeleteStatus(true);
        baseMapper.updateById(ent);
        QueryWrapper<SysToolDataBackupQd> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("Sys_id", request.getId());
        SysToolDataBackupQd obj = new SysToolDataBackupQd();
        obj.setDeletedState(1);
        sysToolDataBackupQdService.saveOrUpdate(obj, queryWrapper1);
        return BaseResponse.ok(tableCodes);
    }
}
