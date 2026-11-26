package com.jpmsoft.userProducer.service.SysTool.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysToolDataBackupQdMapper;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackup;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackupQd;
import com.jpmsoft.userProducer.pojo.entity.SysToolTableRelationship;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.pojo.vo.SysToolTableRelationshipVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolDataBackupQdService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolDataBackupService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolTableRelationshipService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.sql.DatabaseMetaData;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysToolDataBackupQdImpl extends ServiceImpl<SysToolDataBackupQdMapper, SysToolDataBackupQd> implements ISysToolDataBackupQdService {

    @Resource
    private ISysConfigEntityService sysConfigService;

    @Resource
    private ISysToolDataBackupService sysToolDataBackupService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysToolTableRelationshipService sysToolTableRelationshipService;

    @Override
    public BaseResponse GetSysToolDataBackupQdList(String sysId) {
        QueryWrapper<SysToolDataBackupQd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Sys_id", sysId);
        return BaseResponse.ok(baseMapper.selectList(queryWrapper));
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public BaseResponse GetSysToolDataBackupData(String id) {
        SysToolDataBackupQd obj = baseMapper.selectById(id);
        String logFilePath = sysConfigService.getSysConfigByCode("rootDirectory");
        logFilePath = logFilePath + "/delDataBackup/";
        Map<String, Object> returnData = new HashMap<>();
        String sp = "//";
        String url = logFilePath + "/" + obj.getBackupFileAddress();
        String nameStr = url.replaceAll("\\\\", sp).replaceAll("/", sp);
        File businessData = new File(nameStr);
        returnData.put(obj.getTableCode(), getDataToFile(businessData));
        String[] addStr = obj.getBackupFileAddress().split("/");
        //获取其他数
        String urlStt = obj.getBackupFileAddress().replace(addStr[addStr.length - 1], "");
        String directoryPath = logFilePath + "/" + urlStt + "/" + obj.getTableCode();
        File directory = new File(directoryPath);
        // 确保目录存在且确实是一个目录
        if (directory.exists() && directory.isDirectory()) {
            // 获取目录下所有文件
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile()) { // 确保是文件而非目录
                    System.out.println("Reading file: " + file.getName());
                    returnData.put(file.getName().replace(".txt", ""), getDataToFile(file));
                }
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
        return BaseResponse.ok(returnData);
    }


    public List<Map<String, String>> getDataToFile(File file) {
        List<Map<String, String>> DataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String stt = "";
            while ((line = br.readLine()) != null) {
                stt += line;
            }

            String[] datas = stt.split("【分行20240914】");
            for (Integer i = 0; i < datas.length; i++) {
                String st = datas[i];
                if (ValidateUtil.isNotEmpty(st)) {
                    String[] das = st.split("【分割00100】");
                    Map<String, String> entity = new HashMap<>();
                    for (String v : das
                    ) {
                        String[] values = v.split("=");
                        if (values.length == 2) {
                            entity.put(values[0].trim(), values[1]);
                        } else {
                            entity.put(v.replace("=", ""), "");
                        }

                    }
                    DataList.add(entity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataList;
    }


    @Override
    public BaseResponse restorationById(String id, String token) {
        SysToolDataBackupQd obj = baseMapper.selectById(id);
        if ("1".equals(obj.getRestoreState())) {
            return BaseResponse.fail("当期数据已经还原");
        }
        LogUserInfoVo userInfo = redisUtils.getUserInfoByToken(token);
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> trList = sysToolTableRelationshipService.list(queryWrapper);
        if (CollectionUtil.isEmpty(trList)) {
            return BaseResponse.fail("未获取到关系图，备份还原失败");
        }
        //判断当前数据的外联表是否已经还原
        trList = trList.stream().filter(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH).equals(obj.getTableCode().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
        if (!CollectionUtil.isEmpty(trList)) {
            List<String> codes = trList.stream().map(n -> n.getSttrAssociationTable()).collect(Collectors.toList());
            QueryWrapper<SysToolDataBackupQd> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("Sys_id", obj.getSysId());
//            queryWrapper1.ne("Restore_State", "1");
            queryWrapper1.in("Table_Code", codes);
            List<SysToolDataBackupQd> qdList = baseMapper.selectList(queryWrapper1);
            qdList = qdList.stream().filter(n -> n.getRestoreState() != 1).collect(Collectors.toList());
            if (qdList.size() > 0) {
                return BaseResponse.fail("请先还原" + String.join("，", qdList.stream().map(n -> n.getTableCode()).collect(Collectors.toList())) + "表的数据");
            }
        }

        String logFilePath = sysConfigService.getSysConfigByCode("rootDirectory");
        logFilePath = logFilePath + "/delDataBackup/";
        List<String> sqlLit = new ArrayList<>();
        sqlLit.addAll(getInsertSql(logFilePath, obj));
        obj.setRestoreState(1);
        obj.setRestoreTime(new Date());
        if (sqlLit.size() > 0) {
            String dbName = getDbName();
            if ("dm".equals(dbName.toLowerCase(Locale.ENGLISH))) {
                sysToolTableRelationshipService.insertBatch(sqlLit);
            } else {
                for (String stt : sqlLit) {
                    try {
                        sysToolTableRelationshipService.insertSql(stt);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        baseMapper.updateById(obj);
        QueryWrapper<SysToolDataBackupQd> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("Restore_State", "0");
        queryWrapper2.eq("Sys_id", obj.getSysId());
        queryWrapper2.ne("id", id);
        long num = baseMapper.selectCount(queryWrapper2);
        if (num == 0) {
            SysToolDataBackup ent = sysToolDataBackupService.getById(obj.getSysId());
            ent.setStdReductionStatus(true);
            ent.setStdReductionUid(userInfo.getId());
            ent.setStdReductionUname(userInfo.getName());
            sysToolDataBackupService.updateById(ent);
        }
        return BaseResponse.ok("数据还原成功");
    }

    @Override
    public BaseResponse fullRestoration(String id, String token) {
        SysToolDataBackup obj = sysToolDataBackupService.getById(id);
        LogUserInfoVo userInfo = redisUtils.getUserInfoByToken(token);
        if ("1".equals(obj.getStdReductionStatus())) {

            return BaseResponse.fail("当期数据已经还原");
        }
        //获取当前数据下所有未还原的数据
        Map<String, Object> map = new HashMap<>();
        map.put("Restore_State", "0");
        map.put("Sys_id", id);
        List<SysToolDataBackupQd> list = baseMapper.selectByMap(map);
        if (CollectionUtil.isEmpty(list)) {
            return BaseResponse.fail("当期数据已经还原");
        }
        // List<SysToolTableRelationshipVo> trList = sysToolTableRelationshipService.getOtherData(obj.getStdbTableCode());
        List<SysToolTableRelationshipVo> trList = sysToolTableRelationshipService.getSysToolTableRelationshipData(obj.getStdbTableCode());
        if (CollectionUtil.isEmpty(trList) && list.size() > 1) {
            return BaseResponse.fail("未获取到关系图，备份还原失败");
        }

        List<String> codeList = list.stream().map(n -> n.getTableCode().toLowerCase(Locale.ENGLISH)).collect(Collectors.toList());
        Map<String, Integer> codeMap = new HashMap<>();
        for (SysToolTableRelationshipVo ob : trList
        ) {
            if (codeList.contains(ob.getSttrMainTable().toLowerCase(Locale.ENGLISH))) {
                codeMap.put(ob.getSttrMainTable(), ob.getOrderNum());
            }
        }
        codeMap.put(obj.getStdbTableCode(), -1);
        List<Map.Entry<String, Integer>> list1 = new ArrayList<>(codeMap.entrySet());

        // 根据值排序
        list1.sort(Map.Entry.comparingByValue());

        // 创建一个LinkedHashMap，保持排序
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list1) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        String logFilePath = sysConfigService.getSysConfigByCode("rootDirectory");

        List<SysToolDataBackupQd> updataList = new ArrayList<>();


        List<String> massage = new ArrayList<>();
        logFilePath = logFilePath + "/delDataBackup/";

        //  List<String> distinctList = new ArrayList<>(new HashSet<>(tCode));
        String dbName = getDbName();
        for (String n : sortedMap.keySet()
        ) {
            List<SysToolDataBackupQd> lis = list.stream().filter(k -> k.getTableCode().toLowerCase(Locale.ENGLISH).equals(n.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            if (!CollectionUtil.isEmpty(lis)) {
                try {
                    List<String> sqls = getInsertSql(logFilePath, lis.get(0));
                    if ("dm".equals(dbName.toLowerCase(Locale.ENGLISH))) {
                        sysToolTableRelationshipService.insertBatch(sqls);
                    } else {
                        for (String stt : sqls) {
                            try {
                                sysToolTableRelationshipService.insertSql(stt);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    SysToolDataBackupQd entity = lis.get(0);
                    entity.setRestoreState(1);
                    entity.setRestoreTime(new Date());
                    updataList.add(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (updataList.size() == list.size()) {
            obj.setStdReductionStatus(true);
            obj.setStdReductionUid(userInfo.getId());
            obj.setStdReductionUname(userInfo.getName());
        }
        sysToolDataBackupService.updateById(obj);

        for (SysToolDataBackupQd n : updataList
        ) {
            baseMapper.updateById(n);
        }
        return BaseResponse.ok(massage);
    }

    public List<String> getInsertSql(String logFilePath, SysToolDataBackupQd obj) {
        List<String> returnDat = new ArrayList<>();
        String sp = "//";
        String url = logFilePath + "/" + obj.getBackupFileAddress();
        String nameStr = url.replaceAll("\\\\", sp).replaceAll("/", sp);
        File businessData = new File(nameStr);
        returnDat.addAll(InsertSqlBind(getDataToFile(businessData), obj.getTableCode()));
        String[] addStr = obj.getBackupFileAddress().split("/");
        //获取其他数
        String urlStt = obj.getBackupFileAddress().replace(addStr[addStr.length - 1], "");
        String directoryPath = logFilePath + "/" + urlStt + "/" + obj.getTableCode();
        File directory = new File(directoryPath);
        // 确保目录存在且确实是一个目录
        if (directory.exists() && directory.isDirectory()) {
            // 获取目录下所有文件
            File[] files = directory.listFiles();
            Map<String, List<String>> neeSql = new HashMap<>();
            for (File file : files) {
                if (file.isFile()) { // 确保是文件而非目录
                    System.out.println("Reading file: " + file.getName());
                    String key = file.getName().replace(".txt", "");
                    neeSql.put(key, InsertSqlBind(getDataToFile(file), key));

                }
            }

            returnDat.addAll(neeSql.get("SYS_File"));
            returnDat.addAll(neeSql.get("SYS_Task"));
            returnDat.addAll(neeSql.get("Sys_Task_Auto_Complete_Log"));
            returnDat.addAll(neeSql.get("SYS_Task_Backups"));
            returnDat.addAll(neeSql.get("Sys_Task_Processin"));
            returnDat.addAll(neeSql.get("Sys_Task_Process_User"));
            returnDat.addAll(neeSql.get("Sys_Task_Processin_Time"));
            returnDat.addAll(neeSql.get("SYS_CarbonCopy"));
            returnDat.addAll(neeSql.get("SYS_CoordinationTask"));
            returnDat.addAll(neeSql.get("SYS_CoordinationTask_Backups"));
            returnDat.addAll(neeSql.get("SYS_CoordinationProcess"));
            returnDat.addAll(neeSql.get("SYS_File1"));
            returnDat.addAll(neeSql.get("sys_fileannex"));
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
        return returnDat;
    }


    public List<String> InsertSqlBind(List<Map<String, String>> data, String tableCode) {
        if (tableCode.equals("SYS_File1")) {
            tableCode = "SYS_File";
        }
        String codes = tableCode;
        List<String> returnDat = new ArrayList<>();
        if (!CollectionUtil.isEmpty(data)) {
            data.forEach(n -> {
                List<String> sql1 = new ArrayList<>();
                List<String> sql2 = new ArrayList<>();
                for (String key : n.keySet()) {
                    sql1.add(key);
                    sql2.add("'" + n.get(key) + "'");
                }
                returnDat.add("insert into  " + codes + "(" + String.join(",", sql1) + ") VALUES (" + String.join(",", sql2) + ");");
            });
        }
        return returnDat;
    }


    public String getDbName() {
        String dbName = "";
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DatabaseMetaData databaseMetaData = sqlSession.getConnection().getMetaData();
            dbName = databaseMetaData.getDatabaseProductName();
        } catch (Exception ex) {
            sqlSession.close();
        } finally {
            sqlSession.close();
        }
        return dbName;
    }

}
