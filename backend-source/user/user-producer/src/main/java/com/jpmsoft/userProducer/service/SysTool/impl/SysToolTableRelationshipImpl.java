package com.jpmsoft.userProducer.service.SysTool.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysToolTableRelationshipMapper;
import com.jpmsoft.userProducer.pojo.dto.SysToolTableRelationshipDto;
import com.jpmsoft.userProducer.pojo.entity.SysTable;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;
import com.jpmsoft.userProducer.pojo.entity.SysToolTableRelationship;
import com.jpmsoft.userProducer.pojo.vo.SysToolTableRelationshipVo;
import com.jpmsoft.userProducer.pojo.vo.TableForeignKeyVo;
import com.jpmsoft.userProducer.pojo.vo.TableInfoVo;
import com.jpmsoft.userProducer.service.ISysTableColumnService;
import com.jpmsoft.userProducer.service.ISysTableService;
import com.jpmsoft.userProducer.service.ITableManagementService;
import com.jpmsoft.userProducer.service.SysTool.ISysToolTableRelationshipService;
import io.micrometer.core.instrument.Statistic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysToolTableRelationshipImpl extends ServiceImpl<SysToolTableRelationshipMapper, SysToolTableRelationship> implements ISysToolTableRelationshipService {

    @Resource
    private ITableManagementService tableManagementService;
    @Resource
    ISysTableService sysTableService;
    @Resource
    ISysTableColumnService sysTableColumnService;
    private  Integer  pageSize=200;
    private  Map<String,Map<String,String>>   sqlAllList=new HashMap<>();
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateOrDeleteSysToolTableRelationship(SysToolTableRelationshipDto request) {
        List<SysToolTableRelationship> list = request.gettList();
        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.fail("未传入设置信息");
        }

        list.forEach(n -> {
            if (ValidateUtil.isEmpty(n.getId())) {
                //新增
                n.setId(GenerateUUID.getUUID());
                baseMapper.insert(n);
            } else {
                //修改
                baseMapper.updateById(n);
            }
        });
        //获取需要删除的信息
        List<String> deleteIds = request.getDeleteIds();
        if (!CollectionUtils.isEmpty(deleteIds)) {
            baseMapper.deleteBatchIds(deleteIds);
        }
        return BaseResponse.ok("设置成功");
    }

    public static List<String> deduplicateList(List<String> list) {
        Set<String> setWithoutDuplicates = new HashSet<>(list);
        List<String> listWithoutDuplicates = new ArrayList<>(setWithoutDuplicates);
        return listWithoutDuplicates;
    }


    @Override
    public BaseResponse GetSysToolTableRelationshiData(String code, String owner) {
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        if (!"All".equals(code)) {
            queryWrapper.eq("Sttr_Main_Table", code);
        }
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        return BaseResponse.ok(allTableRelationsh);

    }

    @Override
    public BaseResponse importSysToolTableRelationshi(List<SysToolTableRelationship> data, String owner) {

        try {
            if (CollectionUtil.isEmpty(data)) {
                return BaseResponse.fail("文件没有导入的数据");
            }
            //获取当前数据库的所有表信息
            List<TableInfoVo> allTable = tableManagementService.getTableInfoAll(owner);
            QueryWrapper<SysTableColumn> SysTableColumnwrp = new QueryWrapper<>();
            List<SysTableColumn> TableColumnlist = sysTableColumnService.list(SysTableColumnwrp);
            //获取所有的表关系
            QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
            List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
            for (SysToolTableRelationship n : data
            ) {
                Boolean typeStr = true;
                if (!CollectionUtil.isEmpty(allTableRelationsh)) {
                    List<SysToolTableRelationship> lis = allTableRelationsh.stream().filter(k -> k.getSttrMainTable().toLowerCase(Locale.ENGLISH).equals(k.getSttrMainTable().toLowerCase(Locale.ENGLISH)) && k.getSttrMainKey().toLowerCase(Locale.ENGLISH).equals(n.getSttrMainKey().toLowerCase(Locale.ENGLISH)) && k.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).equals(n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH)) && k.getSttrAssociationKey().toLowerCase(Locale.ENGLISH).equals(n.getSttrAssociationKey().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                    if (!CollectionUtil.isEmpty(lis)) {
                        typeStr = false;
                    }
                }
                if (typeStr) {
                    List<TableInfoVo> minTables = allTable.stream().filter(k -> k.getTableCode().toLowerCase(Locale.ENGLISH).equals(n.getSttrMainTable().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                    if (minTables.size() > 0) {
                        n.setSttrMainTable(minTables.get(0).getTableCode());
                        n.setSttrOwner(owner);

                        List<SysTableColumn> cols = TableColumnlist.stream().filter(k -> k.getTableId().equals(minTables.get(0).getTableId()) && k.getColumnCode().toLowerCase(Locale.ENGLISH).equals(n.getSttrMainKey().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                        if (cols.size() > 0) {
                            n.setSttrMainKey(cols.get(0).getColumnCode());
                        }

                    }
                    List<TableInfoVo> aTables = allTable.stream().filter(k -> k.getTableCode().toLowerCase(Locale.ENGLISH).equals(n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                    if (aTables.size() > 0) {
                        n.setSttrAssociationTable(aTables.get(0).getTableCode());
                        n.setSttrOwner(owner);
                        List<SysTableColumn> cols = TableColumnlist.stream().filter(k -> k.getTableId().equals(aTables.get(0).getTableId()) && k.getColumnCode().toLowerCase(Locale.ENGLISH).equals(n.getSttrAssociationKey().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                        if (cols.size() > 0) {
                            n.setSttrAssociationKey(cols.get(0).getColumnCode());
                        }
                    }

                    baseMapper.insert(n);
                }
            }
            return BaseResponse.ok("导入成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.fail(ex.getMessage());
        }


    }


    public List<SysToolTableRelationship> readMultipartFileJsonOne(MultipartFile file) throws IOException {
        // 读取文件内容
        byte[] fileBytes = file.getBytes();
        // 解析JSON数据
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fileBytes);
        List<SysToolTableRelationship> requ = JacksonUtils.parseArray(jsonNode.toString(), SysToolTableRelationship.class);
        return requ;
    }

    @Override
    public BaseResponse GetSysToolTableRelationshi(String TableCode, String owner) {
        //根据传入的表编码获取所有关联的表信息

        //获取所有的关系表
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(allTableRelationsh)) {
            //获取直接关联的表信息
            List<SysToolTableRelationship> direct = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH).equals(TableCode.toLowerCase(Locale.ENGLISH)) || n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).equals(TableCode.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            //获取间接管理的表信息
            //间接关联表信息


            if (!CollectionUtils.isEmpty(direct)) {
                List<String> tables = new ArrayList<>();
                List<SysToolTableRelationshipVo> returnList = new ArrayList<>();
                if (TableCode.toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase(Locale.ENGLISH))) {
                    //获取当前表关联里面是否有Sec_Project
                    List<SysToolTableRelationship> secPS = direct.stream().filter(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(secPS)) {
                        List<SysToolTableRelationshipVo> returnList1 = OtherAssociations(allTableRelationsh, "Sec_Info", 2);
                        tables.add("Sec_Info".toLowerCase(Locale.ENGLISH));
                        tables.addAll(returnList1.stream().map(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH)).collect(Collectors.toList()));
                        returnList.addAll(returnList1);
                    }
                }
                List<SysToolTableRelationshipVo> trList = OtherAssociations(allTableRelationsh, TableCode, 0);
                tables.addAll(trList.stream().map(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH)).collect(Collectors.toList()));
                returnList.addAll(trList);
                //获取数据表信息;
                List<TableInfoVo> tableAllList = tableManagementService.getTableInfoAll(owner);
                List<TableInfoVo> allTablel = tableAllList.stream().filter(n -> tables.contains(n.getTableCode().toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                Map<String, Object> returnData = new HashMap<>();
                returnData.put("directData", direct);
                returnData.put("otherAssociations", returnList);
                returnData.put("allTablelData", allTablel);
                return BaseResponse.ok(returnData);
            } else {
                return BaseResponse.ok(null);
            }
        } else {
            return BaseResponse.ok(null);
        }

    }

    @Override
    public List<SysToolTableRelationshipVo> OtherAssociations(List<SysToolTableRelationship> alllList, String TableCode, Integer num) {
        List<SysToolTableRelationshipVo> returnList = new ArrayList<>();
        List<SysToolTableRelationship> direct = alllList.stream().filter(n -> (n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).equals(TableCode.toLowerCase(Locale.ENGLISH)))).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(direct)) {
            direct.forEach(n -> {
                SysToolTableRelationshipVo entity = new SysToolTableRelationshipVo();
                BeanUtils.copyProperties(n, entity);
                entity.setOrderNum(num);
                returnList.add(entity);
                returnList.addAll(OtherAssociations(alllList, n.getSttrMainTable(), num + 1));
            });
        }
        return returnList;
    }

    @Override
    public BaseResponse SynchronizeForeignKeys(String owner) {
        List<TableForeignKeyVo> list = tableManagementService.getTableForeignKey(owner);
        //获取所有的关系表
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);

        list.forEach((res) -> {
            //获取
            Boolean type = false;
            if (!CollectionUtils.isEmpty(allTableRelationsh)) {
                List<SysToolTableRelationship> trList = allTableRelationsh.stream().filter(n -> (n.getSttrMainTable().toLowerCase(Locale.ENGLISH).equals(res.getFkTable().toLowerCase(Locale.ENGLISH)) && n.getSttrMainKey().toLowerCase(Locale.ENGLISH).equals(res.getFkCol().toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).equals(res.getPkTable().toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationKey().toLowerCase(Locale.ENGLISH).equals(res.getPkCol().toLowerCase(Locale.ENGLISH))) || (n.getSttrMainTable().toLowerCase(Locale.ENGLISH).equals(res.getPkTable().toLowerCase(Locale.ENGLISH)) && n.getSttrMainKey().toLowerCase(Locale.ENGLISH).equals(res.getPkCol().toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).equals(res.getFkTable().toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationKey().toLowerCase(Locale.ENGLISH).equals(res.getFkCol().toLowerCase(Locale.ENGLISH)))).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(trList)) {
                    type = true;
                }
            } else {
                type = true;
            }
            if (type) {
                SysToolTableRelationship obj = new SysToolTableRelationship();
                obj.setId(GenerateUUID.getUUID());
                obj.setSttrMainTable(res.getFkTable());
                obj.setSttrMainKey(res.getFkCol());
                obj.setSttrOwner(res.getFkOwner());
                obj.setSttrAssociationTable(res.getPkTable());
                obj.setSttrAssociationKey(res.getPkCol());
                baseMapper.insert(obj);
            }
        });

        return BaseResponse.ok("同步成功");
    }

    @Override
    public List<SysToolTableRelationshipVo> getOtherData(String tableCode) {
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(allTableRelationsh)) {
            Integer num = 0;
            List<SysToolTableRelationshipVo> direct = OtherAssociations(allTableRelationsh, tableCode, num);
            Map<String, Object> dataSize = new HashMap<>();
            if (tableCode.toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase(Locale.ENGLISH))) {
                //获取当前表关联里面是否有Sec_Project
                List<SysToolTableRelationship> secPS = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().trim().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).trim().equals(tableCode.toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secPS)) {
                    List<SysToolTableRelationship> secIs = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().toLowerCase(Locale.ENGLISH).trim().equals("Sec_Project".toLowerCase(Locale.ENGLISH)) && n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).trim().equals("Sec_Info".toLowerCase())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(secIs)) {
                        List<SysToolTableRelationshipVo> returnList1 = OtherAssociations(allTableRelationsh, "Sec_Info", 2);
                        direct.addAll(returnList1);
                    }
                }
            }
            return direct;
        } else {
            return null;
        }
    }
    @Override
    public  Map<String,Map<String,String>>  getTableSqlMap(){
        return  sqlAllList;
    }


    @Override
    public  Map<String,Map<String,String>>  getTableSqlMapNewSet(String TableCode, String pk, String id, String owner){
        sqlAllList=new HashMap<>();
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        String sqlStr = "from  " + TableCode + " where " + pk + "='" + id + "'";
        getSelectCountSql(allTableRelationsh, TableCode, sqlStr);
        if (TableCode.toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase())) {
            //获取当前表关联里面是否有Sec_Project
            List<SysToolTableRelationship> secPS = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().trim().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase()) && n.getSttrAssociationTable().toLowerCase().trim().equals(TableCode.toLowerCase())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(secPS)) {
                List<SysToolTableRelationship> secIs = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().toLowerCase().trim().equals("Sec_Project".toLowerCase()) && n.getSttrAssociationTable().toLowerCase().trim().equals("Sec_Info".toLowerCase())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secIs)) {
                    String secIql = "from  Sec_Info  where  " + secIs.get(0).getSttrAssociationKey() + " in ( select " + secIs.get(0).getSttrMainKey() + "  from  " + secIs.get(0).getSttrMainTable() + "  where  " + secPS.get(0).getSttrMainKey() + "  in (  select  " + secPS.get(0).getSttrAssociationKey() + " " + sqlStr + "))";
                    getSelectCountSql(allTableRelationsh, "Sec_Info", secIql);
                    Map<String, String> sqlObj = new HashMap<>();
                    if (!ValidateUtil.isEmpty(sqlAllList.get("Sec_Info".toLowerCase()))) {
                        sqlObj = sqlAllList.get("Sec_Info".toLowerCase());
                    }
                    sqlObj.put(GenerateUUID.getUUID(), secIql);
                    sqlAllList.put("Sec_Info".toLowerCase(), sqlObj);
                }
            }
        }
        Map<String, String> pSql = new HashMap<>();
        if (!ValidateUtil.isEmpty(sqlAllList.get(TableCode.toLowerCase()))) {
            pSql = sqlAllList.get(TableCode.toLowerCase());
        }
        pSql.put(GenerateUUID.getUUID(),sqlStr);
        sqlAllList.put(TableCode.toLowerCase(), pSql);
        List<TableInfoVo> tableAllList = tableManagementService.getTableInfoAll(owner);
        List<TableInfoVo> allTablel = new ArrayList<>();
        for (String key : sqlAllList.keySet()) {
            List<TableInfoVo> tableObjs = tableAllList.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tableObjs)) {
                allTablel.add(tableObjs.get(0));
            }
        }

        return  sqlAllList;
    }


    @Override
    public BaseResponse GetSysToolTableRelationshiMap(String TableCode, String pk, String id, String owner) {
        sqlAllList=new HashMap<>();
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(allTableRelationsh)) {
            Integer num = 0;
            List<SysToolTableRelationshipVo> direct = OtherAssociations(allTableRelationsh, TableCode, num);
            Map<String, Object> dataSize = new HashMap<>();
            String sqlStr = "from  " + TableCode + " where " + pk + "='" + id + "'";
            getSelectCountSql(allTableRelationsh, TableCode, sqlStr);
            if (TableCode.toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase())) {
                //获取当前表关联里面是否有Sec_Project
                List<SysToolTableRelationship> secPS = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().trim().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase()) && n.getSttrAssociationTable().toLowerCase().trim().equals(TableCode.toLowerCase())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secPS)) {
                    List<SysToolTableRelationship> secIs = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().toLowerCase().trim().equals("Sec_Project".toLowerCase()) && n.getSttrAssociationTable().toLowerCase().trim().equals("Sec_Info".toLowerCase())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(secIs)) {
                        List<SysToolTableRelationshipVo> returnList1 = OtherAssociations(allTableRelationsh, "Sec_Info", 2);
                        direct.addAll(returnList1);
                        String secIql = "from  Sec_Info  where  " + secIs.get(0).getSttrAssociationKey() + " in ( select " + secIs.get(0).getSttrMainKey() + "  from  " + secIs.get(0).getSttrMainTable() + "  where  " + secPS.get(0).getSttrMainKey() + "  in (  select  " + secPS.get(0).getSttrAssociationKey() + " " + sqlStr + "))";
                       getSelectCountSql(allTableRelationsh, "Sec_Info", secIql);
                        Map<String, String> sqlObj = new HashMap<>();
                        if (!ValidateUtil.isEmpty(sqlAllList.get("Sec_Info".toLowerCase()))) {
                            sqlObj = sqlAllList.get("Sec_Info".toLowerCase());
                        }
                        sqlObj.put(GenerateUUID.getUUID(), secIql);
                        sqlAllList.put("Sec_Info".toLowerCase(), sqlObj);
                    }
                }
            }
            Map<String, String> pSql = new HashMap<>();
            if (!ValidateUtil.isEmpty(sqlAllList.get(TableCode.toLowerCase()))) {
                pSql = sqlAllList.get(TableCode.toLowerCase());
            }
            pSql.put(GenerateUUID.getUUID(),sqlStr);
            sqlAllList.put(TableCode.toLowerCase(), pSql);
            List<TableInfoVo> tableAllList = tableManagementService.getTableInfoAll(owner);
            List<TableInfoVo> allTablel = new ArrayList<>();
            for (String key : sqlAllList.keySet()) {
                List<TableInfoVo> tableObjs = tableAllList.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(tableObjs)) {
                    allTablel.add(tableObjs.get(0));
                }
            }
            Map<String, Object> returnData = new HashMap<>();
            returnData.put("directData", direct);
            returnData.put("allTable", allTablel);
            Set<String> tableCodes = sqlAllList.keySet();
            List<List<String>> splitSets = splitList(new ArrayList<>(tableCodes), pageSize);
            returnData.put("number", splitSets.size());
            returnData.put("sqlMap", sqlAllList);
            return BaseResponse.ok(returnData);
        } else {
            return BaseResponse.ok(null);
        }
    }


    @Override
    public BaseResponse GetSysToolTableRelationshiDataByPage(String TableCode, String pk, Integer page, String id, String owner) {
            Map<String,Object> dataSize=new HashMap<>();
            QueryWrapper<SysTable> SysTablewrp = new QueryWrapper<>();
            List<SysTable> Tablelist = sysTableService.list(SysTablewrp);
            Set<String> tableCodes = sqlAllList.keySet();
            List<List<String>> splitSets = splitList(new ArrayList<>(tableCodes), pageSize);
            List<String> newSetData = splitSets.get(page);
            List<List<String>> splitSets2 = splitList(new ArrayList<>(newSetData), 10);
            ExecutorService executor = Executors.newFixedThreadPool(splitSets2.size());

            // 保存Future对象的列表，以便稍后获取结果
            List<Future<Object>> results = new ArrayList<>();
            // 提交查询任务
            for (List<String> key : splitSets2) {
                Future<Object> future = executor.submit(() -> getDataCount(sqlAllList, key, Tablelist));
                results.add(future);
            }

            // 关闭线程池，不再接受新任务
            executor.shutdown();
            // 获取查询结果
            for (Future<Object> result : results) {
                try {
                    // 获取查询结果，如果尚未完成则等待
                    //System.out.println(result.get());
                    Object dat = result.get();
                    Map<String, Object> data = (Map<String, Object>) dat;
                    for (String key : data.keySet()) {
                        dataSize.put(key, data.get(key));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return BaseResponse.ok(dataSize);

    }

    private List<List<String>> splitList(List<String> list, int listSize) {
        if (null == list || 0 == listSize) {
            return new ArrayList();
        }
        int length = list.size();
        int num = (length + listSize - 1) / listSize;
        List<List<String>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            int fromIndex = i * listSize;
            int toIndex = (i + 1) * listSize < length ? (i + 1) * listSize : length;
            newList.add(list.subList(fromIndex, toIndex));
        }
        return newList;
    }


    @Override
    public BaseResponse GetSysToolTableRelationshiAndData(String TableCode, String pk, String id, String owner) {
        sqlAllList=new HashMap<>();
        //获取所有的关系表
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(allTableRelationsh)) {
            Integer num = 0;
            List<SysToolTableRelationshipVo> direct = OtherAssociations(allTableRelationsh, TableCode, num);
            Map<String, Object> dataSize = new HashMap<>();
            String sqlStr = "from  " + TableCode + " where " + pk + "='" + id + "'";
            getSelectCountSql(allTableRelationsh, TableCode, sqlStr);
            if (TableCode.toLowerCase(Locale.ENGLISH).equals("Pre_Project".toLowerCase())) {
                //获取当前表关联里面是否有Sec_Project
                List<SysToolTableRelationship> secPS = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().trim().toLowerCase(Locale.ENGLISH).equals("Sec_Project".toLowerCase()) && n.getSttrAssociationTable().toLowerCase().trim().equals(TableCode.toLowerCase())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(secPS)) {
                    List<SysToolTableRelationship> secIs = allTableRelationsh.stream().filter(n -> n.getSttrMainTable().toLowerCase().trim().equals("Sec_Project".toLowerCase()) && n.getSttrAssociationTable().toLowerCase().trim().equals("Sec_Info".toLowerCase())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(secIs)) {
                        List<SysToolTableRelationshipVo> returnList1 = OtherAssociations(allTableRelationsh, "Sec_Info", 2);
                        direct.addAll(returnList1);
                        String secIql = "from  Sec_Info  where  " + secIs.get(0).getSttrAssociationKey() + " in ( select " + secIs.get(0).getSttrMainKey() + "  from  " + secIs.get(0).getSttrMainTable() + "  where  " + secPS.get(0).getSttrMainKey() + "  in (  select  " + secPS.get(0).getSttrAssociationKey() + " " + sqlStr + "))";
                       getSelectCountSql(allTableRelationsh, "Sec_Info", secIql);
                        Map<String, String> sqlObj = new HashMap<>();
                        if (!ValidateUtil.isEmpty(sqlAllList.get("Sec_Info".toLowerCase()))) {
                            sqlObj = sqlAllList.get("Sec_Info".toLowerCase());
                        }
                        sqlObj.put(GenerateUUID.getUUID(), secIql);
                        sqlAllList.put("Sec_Info".toLowerCase(), sqlObj);
                    }
                }
            }
            Map<String, String> pSql = new HashMap<>();
            if (!ValidateUtil.isEmpty(sqlAllList.get(TableCode.toLowerCase()))) {
                pSql = sqlAllList.get(TableCode.toLowerCase());
            }
            pSql.put(GenerateUUID.getUUID(),sqlStr);
            sqlAllList.put(TableCode.toLowerCase(), pSql);
            List<TableInfoVo> tableAllList = tableManagementService.getTableInfoAll(owner);
            QueryWrapper<SysTable> SysTablewrp = new QueryWrapper<>();
            List<SysTable> Tablelist = sysTableService.list(SysTablewrp);
            Set<String> tableCodes = sqlAllList.keySet();
            List<String> codelist = new ArrayList<>(tableCodes);
            List<TableInfoVo> allTablel = new ArrayList<>();
            //获取所有的表
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
            //List<String> tableCodes = new ArrayList<>();
            for (String key : sqlAllList.keySet()) {
                Map<String, String> sqlMap = sqlAllList.get(key);
                String idKey = "";
                List<TableInfoVo> tableObjs = tableAllList.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(tableObjs)) {
                    allTablel.add(tableObjs.get(0));
                    //idKey=tableObjs.get(0).get
                }

                List<SysTable> tabIfo = Tablelist.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(tabIfo)) {
                    idKey = tabIfo.get(0).getTablePk();
                }
                if (ValidateUtil.isEmpty(idKey)) {
                    dataSize.put(key, "未设置主键");
                } else {
                    try {
                        List<String> keyIds = new ArrayList<>();
                        for (String k : sqlMap.keySet()) {
                            String sql = "select  " + idKey + "  " + sqlMap.get(k);
                            List<Map<String, Object>> dataList = baseMapper.querySql(sql);
                            if (!CollectionUtil.isEmpty(dataList)) {
                                for (Map<String, Object> dataObj : dataList) {
                                    for (String ke : dataObj.keySet()) {
                                        if (ke.toLowerCase().trim().equals(idKey.toLowerCase().trim())) {
                                            keyIds.add("'" + dataObj.get(ke) + "'");
                                        }
                                    }
                                }
                            }
                        }
                        Set<String> set = new HashSet<>(keyIds);
                        List<String> distinctList = new ArrayList<>(set);
                        String idStr = String.join(",", distinctList);
                        Map<String, Integer> objSize = new HashMap<>();
                        objSize.put(key, distinctList.size());
                        objSize.put("SYS_File", baseMapper.queryPageSize("select  count(1) " + fileSql + " (  " + idStr + ");"));
                        objSize.put("sys_fileannex", baseMapper.queryPageSize("select  count(1) " + fileAnnex + " ( " + idStr + ");"));
                        objSize.put("Sys_Task_Processin_Time", baseMapper.queryPageSize("select  count(1) " + tptSql + " (  " + idStr + ");"));
                        objSize.put("Sys_Task_Processin", baseMapper.queryPageSize("select  count(1) " + tpSql + " ( " + idStr + ");"));
                        objSize.put("Sys_Task_Process_User", baseMapper.queryPageSize("select  count(1) " + (tpUsql.replace("[id]", idStr))));
                        objSize.put("SYS_File_Tp", baseMapper.queryPageSize("select  count(1) " + (file1Sql.replace("[id]", idStr))));
                        objSize.put("SYS_Task_Backups", baseMapper.queryPageSize("select  count(1) " + tbSql + " ( " + idStr + ");"));
                        objSize.put("Sys_Task_Auto_Complete_Log", baseMapper.queryPageSize("select  count(1) " + taclSql + " (  " + idStr + ");"));
                        objSize.put("SYS_Task", baseMapper.queryPageSize("select  count(1) " + taskSql + " ( " + idStr + ");"));
                        objSize.put("SYS_CoordinationTask_Backups", baseMapper.queryPageSize("select  count(1) " + ctbSql + " ( " + idStr + ");"));
                        objSize.put("SYS_CoordinationTask", baseMapper.queryPageSize("select  count(1) " + ctSql + " ( " + idStr + ");"));
                        objSize.put("SYS_CoordinationProcess", baseMapper.queryPageSize("select  count(1) " + cpSql + " (  " + idStr + ");"));
                        objSize.put("SYS_CarbonCopy", baseMapper.queryPageSize("select  count(1) " + ccSql + " ( " + idStr + ");"));
                        dataSize.put(key, objSize);
                    } catch (Exception ex) {
                        log.error("base服务, Failed to generate record! ", ex);
                        dataSize.put(key, "获取数据异常");
                    }
                }
            }

            Map<String, Object> returnData = new HashMap<>();
            returnData.put("directData", direct);
            returnData.put("dataSize", dataSize);
            returnData.put("allTable", allTablel);
            return BaseResponse.ok(returnData);
        } else {
            return BaseResponse.ok(null);
        }
    }

    @Override
    public List<SysToolTableRelationshipVo> getSysToolTableRelationshipData(String TableCode) {
        QueryWrapper<SysToolTableRelationship> queryWrapper = new QueryWrapper<>();
        List<SysToolTableRelationship> allTableRelationsh = baseMapper.selectList(queryWrapper);
        List<SysToolTableRelationshipVo> direct = OtherAssociations(allTableRelationsh, TableCode, 0);
        return direct;
    }


    public void getSelectCountSql(List<SysToolTableRelationship> alllList, String TableCode, String sqlStr) {
        Map<String, Map<String, String>> sqlEntity = new HashMap<>();
        List<SysToolTableRelationship> listData = alllList.stream().filter(n -> n.getSttrAssociationTable().toLowerCase(Locale.ENGLISH).equals(TableCode.toLowerCase())).collect(Collectors.toList());
        listData.forEach(n -> {
            String sql = "from " + n.getSttrMainTable() + " where " + n.getSttrMainKey() + " in ( select " + n.getSttrAssociationKey() + "  " + sqlStr + ")";
             getSelectCountSql(alllList, n.getSttrMainTable(), sql);
//            for (String key : obj.keySet()) {
//                Map<String, String> oldList = new HashMap<>();
//                if (!ValidateUtil.isEmpty(sqlEntity.get(key))) {
//                    oldList = sqlEntity.get(key);
//                }
//                Map<String, String> child = obj.get(key);
//                for (String key1 : child.keySet()) {
//                    oldList.put(key1.toLowerCase(), child.get(key1));
//                }
//                sqlEntity.put(key.toLowerCase(), oldList);
//
//            }
            Map<String, String> sqlObj = new HashMap<>();
            if (!ValidateUtil.isEmpty(sqlAllList.get(n.getSttrMainTable().trim().toLowerCase()))) {
                sqlObj = sqlAllList.get(n.getSttrMainTable().trim().toLowerCase());
            }
            sqlObj.put(GenerateUUID.getUUID(), sql);
            sqlAllList.put(n.getSttrMainTable().trim().toLowerCase(), sqlObj);
            if("OA_Attendance_Member".toLowerCase(Locale.ENGLISH).equals(n.getSttrMainTable().toLowerCase())){
                String dataList="";
            }
        });
        //return sqlEntity;

    }


    @Override
    public Integer insertBatch(List<String> sqlList) {
        return baseMapper.insertBatch(sqlList);
    }
    @Override
    public Integer insertSql(String sql) {
        return baseMapper.insertSql(sql);
    }

    public Map<String, Object> getDataCount(Map<String, Map<String, String>> sqlList, List<String> keys, List<SysTable> Tablelist) {
        Map<String, Object> dataSize = new HashMap<>();
        //获取所有的表
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


        for (String key : keys) {
            if("Fund_Scheme_Apply_Dtl".toLowerCase(Locale.ENGLISH).equals(key)||"Gantt_Tasks".toLowerCase(Locale.ENGLISH).equals(key)||"Me_Enter_Apply_Dtl".toLowerCase(Locale.ENGLISH).equals(key)||"Agent_Fund_Advance_List".toLowerCase(Locale.ENGLISH).equals(key)||"Pe_Info_Detail".toLowerCase(Locale.ENGLISH).equals(key)){
                String dataList="";
            }
            if("OA_Attendance_Member".toLowerCase(Locale.ENGLISH).equals(key)){
                String dataList="";
            }
            Map<String, String> sqlMap = sqlList.get(key);
            String idKey = "";
            List<SysTable> tabIfo = Tablelist.stream().filter(n -> n.getTableCode().toLowerCase(Locale.ENGLISH).equals(key.toLowerCase())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tabIfo)) {
                idKey = tabIfo.get(0).getTablePk();
            }
            if (ValidateUtil.isEmpty(idKey)) {
                dataSize.put(key, "未设置主键");
            } else {
                try {
                    List<String> keyIds = new ArrayList<>();
                    for (String k : sqlMap.keySet()) {
                        String sql = "select  " + idKey + "  " + sqlMap.get(k);
                        List<Map<String, Object>> dataList = baseMapper.querySql(sql);
                        if (!CollectionUtil.isEmpty(dataList)) {
                            for (Map<String, Object> dataObj : dataList) {
                                for (String ke : dataObj.keySet()) {
                                    if (ke.toLowerCase().trim().equals(idKey.toLowerCase().trim())) {
                                        keyIds.add("'" + dataObj.get(ke) + "'");
                                    }
                                }
                            }
                        }
                    }
                    Set<String> set = new HashSet<>(keyIds);
                    List<String> distinctList = new ArrayList<>(set);
                    String idStr = String.join(",", distinctList);

                    Map<String, Integer> objSize = new HashMap<>();
                    objSize.put(key, distinctList.size());
                    if (distinctList.size() == 0) {
                        objSize.put("SYS_File", 0);
                        objSize.put("sys_fileannex", 0);
                        objSize.put("Sys_Task_Processin_Time", 0);
                        objSize.put("Sys_Task_Processin", 0);
                        objSize.put("Sys_Task_Process_User", 0);
                        objSize.put("SYS_File_Tp", 0);
                        objSize.put("SYS_Task_Backups", 0);
                        objSize.put("Sys_Task_Auto_Complete_Log", 0);
                        objSize.put("SYS_Task", 0);
                        objSize.put("SYS_CoordinationTask_Backups", 0);
                        objSize.put("SYS_CoordinationTask", 0);
                        objSize.put("SYS_CoordinationProcess", 0);
                        objSize.put("SYS_CarbonCopy", 0);
                    } else {
                        objSize.put("SYS_File", baseMapper.queryPageSize("select  count(1) " + fileSql + " (  " + idStr + ");"));
                        objSize.put("sys_fileannex", baseMapper.queryPageSize("select  count(1) " + fileAnnex + " ( " + idStr + ");"));
                        objSize.put("Sys_Task_Processin_Time", baseMapper.queryPageSize("select  count(1) " + tptSql + " (  " + idStr + ");"));
                        objSize.put("Sys_Task_Processin", baseMapper.queryPageSize("select  count(1) " + tpSql + " ( " + idStr + ");"));
                        objSize.put("Sys_Task_Process_User", baseMapper.queryPageSize("select  count(1) " + (tpUsql.replace("[id]", idStr))));
                        objSize.put("SYS_File_Tp", baseMapper.queryPageSize("select  count(1) " + (file1Sql.replace("[id]", idStr))));
                        objSize.put("SYS_Task_Backups", baseMapper.queryPageSize("select  count(1) " + tbSql + " ( " + idStr + ");"));
                        objSize.put("Sys_Task_Auto_Complete_Log", baseMapper.queryPageSize("select  count(1) " + taclSql + " (  " + idStr + ");"));
                        objSize.put("SYS_Task", baseMapper.queryPageSize("select  count(1) " + taskSql + " ( " + idStr + ");"));
                        objSize.put("SYS_CoordinationTask_Backups", baseMapper.queryPageSize("select  count(1) " + ctbSql + " ( " + idStr + ");"));
                        objSize.put("SYS_CoordinationTask", baseMapper.queryPageSize("select  count(1) " + ctSql + " (  " + idStr + ");"));
                        objSize.put("SYS_CoordinationProcess", baseMapper.queryPageSize("select  count(1) " + cpSql + " ( " + idStr + ");"));
                        objSize.put("SYS_CarbonCopy", baseMapper.queryPageSize("select  count(1) " + ccSql + " ( " + idStr + ");"));
                    }
                    dataSize.put(key, objSize);
                } catch (Exception ex) {
                    log.error("base服务, Failed to generate record! ", ex);
                    dataSize.put(key, "获取数据异常");
                }
            }
        }
        return dataSize;
    }


}