package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.JimuReportMapper;
import com.jpmsoft.userProducer.mapper.SysDictionaryMapper;
import com.jpmsoft.userProducer.pojo.dto.JimuReportDto;
import com.jpmsoft.userProducer.pojo.dto.SelectDto;
import com.jpmsoft.userProducer.pojo.dto.SysFormDTO;
import com.jpmsoft.userProducer.pojo.entity.*;
import com.jpmsoft.userProducer.pojo.vo.VueTreeVo;
import com.jpmsoft.userProducer.service.*;
import com.jpmsoft.userProducer.utils.IdWorker;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JimuReportImpl extends ServiceImpl<JimuReportMapper, JimuReport> implements IJimuReportService {

    @Resource
    IJimuDictItemService jimuDictItemService;
    @Resource
    IJimuDictService jimuDictService;
    @Resource
    IJimuReportDataSourceService jimuReportDataSourceService;
    @Resource
    IJimuReportDbFieldService jimuReportDbFieldService;
    @Resource
    IJimuReportDbParamService jimuReportDbParamService;
    @Resource
    IJimuReportDbService jimuReportDbService;
    @Resource
    IJimuReportShareService jimuReportShareService;
    @Resource
    IJimuReportLinkService jimuReportLinkService;
    @Resource
    IJimuReportMapService jimuReportMapService;
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Override
    public BaseResponse getJimuReportList(JimuReport request) {
        QueryWrapper<JimuReport> wrapper = new QueryWrapper<>();

        if (ValidateUtil.isNotEmpty(request.getType())) {
            wrapper.eq("TYPE", request.getType());
        }
        if (ValidateUtil.isNotEmpty(request.getTenantId())) {
            wrapper.eq("tenant_id", request.getTenantId());
        }
        wrapper.eq("del_flag", 0);
        if (ValidateUtil.isNotEmpty(request.getName())) {
            wrapper.like("name", request.getName());
        }
        if (ValidateUtil.isNotEmpty(request.getCreateBy())) {
            wrapper.like("create_by", request.getCreateBy());
        }
        wrapper.orderByDesc("create_time");
        return BaseResponse.ok(baseMapper.selectList(wrapper));

    }

    @Override
    public BaseResponse deleteJimuReportById(String id) {
        JimuReport obj = baseMapper.selectById(id);
        obj.setDelFlag("1");
        baseMapper.updateById(obj);
        return BaseResponse.ok("删除成功");
    }

    @Override
    public BaseResponse getJimuReportListByIds(List<String> ids, String tenantId) {
        List<JimuReport> reportList = baseMapper.selectBatchIds(ids);
        List<JimuReportDbField> dbFlieList = new ArrayList<>();
        List<JimuReportDbParam> dbParamList = new ArrayList<>();
        List<JimuDictItem> dictItemList = new ArrayList<>();
        List<JimuDict> dictList = new ArrayList<>();
        //获取数据库连接信息
        QueryWrapper<JimuReportDataSource> dataSourceWrapper = new QueryWrapper<>();
        dataSourceWrapper.in("report_id", ids);
        List<JimuReportDataSource> dataSourceList = jimuReportDataSourceService.list(dataSourceWrapper);
        //获取数据源表查询信息
        QueryWrapper<JimuReportDb> dbWrapper = new QueryWrapper<>();
        dbWrapper.in("jimu_report_id", ids);
        List<JimuReportDb> dbList = jimuReportDbService.list(dbWrapper);

        //获取数据源返回字段信息
        if (!CollectionUtils.isEmpty(dbList)) {
            QueryWrapper<JimuReportDbField> dbFileWrapper = new QueryWrapper<>();
            dbFileWrapper.in("jimu_report_db_id", dbList.stream().map(n -> n.getId()).collect(Collectors.toList()));
            dbFlieList = jimuReportDbFieldService.list(dbFileWrapper);
        }

        //获取数据源参数信息
        if (!CollectionUtils.isEmpty(dbList)) {
            QueryWrapper<JimuReportDbParam> dbParamWrapper = new QueryWrapper<>();
            dbParamWrapper.in("jimu_report_head_id", dbList.stream().map(n -> n.getId()).collect(Collectors.toList()));
            dbParamList = jimuReportDbParamService.list(dbParamWrapper);
        }
        //获取本次打印报表涉及字典信息
        if (!CollectionUtils.isEmpty(dbFlieList)) {
            QueryWrapper<JimuDict> dictWrapper = new QueryWrapper<>();
            dictWrapper.in("dict_code", dbFlieList.stream().map(n -> n.getDictCode()).collect(Collectors.toList()));
            if (ValidateUtil.isNotEmpty(tenantId)) {
                dictWrapper.eq("tenant_id", tenantId);
            }
            dictList = jimuDictService.list(dictWrapper);
        }
        if (!CollectionUtils.isEmpty(dictList)) {
            //获取本次打印报表涉及字典值信息
            QueryWrapper<JimuDictItem> dictItemWrapper = new QueryWrapper<>();
            dictItemWrapper.in("dict_id", dictList.stream().map(n -> n.getId()).collect(Collectors.toList()));
            dictItemList = jimuDictItemService.list(dictItemWrapper);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("JimuReport", reportList);
        data.put("JimuReportDataSource", dataSourceList);
        data.put("JimuReportDb", dbList);
        data.put("JimuReportDbField", dbFlieList);
        data.put("JimuReportDbParam", dbParamList);
        data.put("JimuDict", dictList);
        data.put("JimuDictItem", dictItemList);
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importJimuReportList(JimuReportDto data, String tenantId) {
        try {
            log.info("JimuReportDto:" + JacksonUtils.toJSONString(data));
            List<JimuReport> reportList = data.getJimuReport();
            log.info("JimuReport:" + JacksonUtils.toJSONString(reportList));
            List<JimuReportDataSource> dataSourceList = data.getJimuReportDataSource();
            log.info("JimuReportDataSource:" + JacksonUtils.toJSONString(dataSourceList));
            List<JimuReportDb> dbList = data.getJimuReportDb();
            log.info("JimuReportDb:" + JacksonUtils.toJSONString(dbList));
            List<JimuReportDbField> dbFlieList = data.getJimuReportDbField();
            log.info("JimuReportDbField:" + JacksonUtils.toJSONString(dbFlieList));
            List<JimuReportDbParam> dbParamList = data.getJimuReportDbParam();
            log.info("JimuReportDbParam:" + JacksonUtils.toJSONString(dbParamList));
            List<JimuDict> dictList = data.getJimuDict();
            log.info("JimuDict:" + JacksonUtils.toJSONString(dictList));
            if (!CollectionUtils.isEmpty(reportList)) {
                List<JimuReport> reportLists = baseMapper.selectBatchIds(reportList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(reportLists)) {
                    baseMapper.deleteBatchIds(reportList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                }
                for (JimuReport obj : reportList
                ) {
                    baseMapper.insert(obj);
                }
            }
            if (!CollectionUtils.isEmpty(dataSourceList)) {
                //获取数据库连接信息
                QueryWrapper<JimuReportDataSource> dataSourceWrapper = new QueryWrapper<>();
                dataSourceWrapper.in("report_id", reportList.stream().map(n -> n.getId()));
                List<JimuReportDataSource> dataSourceLists = jimuReportDataSourceService.list(dataSourceWrapper);

                if (!CollectionUtils.isEmpty(dataSourceLists)) {
                    jimuReportDataSourceService.removeBatchByIds(dataSourceLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
                }
                jimuReportDataSourceService.saveBatch(dataSourceList);
            }
            if (!CollectionUtils.isEmpty(dbList)) {
                //获取数据源表查询信息
                QueryWrapper<JimuReportDb> dbWrapper = new QueryWrapper<>();
                dbWrapper.in("jimu_report_id", reportList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                List<JimuReportDb> dbLists = jimuReportDbService.list(dbWrapper);
                if (!CollectionUtils.isEmpty(dbLists)) {
                    //查询到了历史数据
                    jimuReportDbService.removeBatchByIds(dbLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
                }
                jimuReportDbService.saveBatch(dbList);


                if (!CollectionUtils.isEmpty(dbFlieList)) {
                    //获取数据源返回字段信息
                    QueryWrapper<JimuReportDbField> dbFileWrapper = new QueryWrapper<>();
                    dbFileWrapper.in("jimu_report_db_id", dbList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                    List<JimuReportDbField> dbFlieLists = jimuReportDbFieldService.list(dbFileWrapper);

                    if (!CollectionUtils.isEmpty(dbFlieLists)) {
                        jimuReportDbFieldService.removeBatchByIds(dbFlieLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
                    }
                    jimuReportDbFieldService.saveBatch(dbFlieList);
                }
                if (!CollectionUtils.isEmpty(dbParamList)) {
                    //获取数据源参数信息
                    QueryWrapper<JimuReportDbParam> dbParamWrapper = new QueryWrapper<>();
                    dbParamWrapper.in("jimu_report_head_id", dbList.stream().map(n -> n.getId()).collect(Collectors.toList()));
                    List<JimuReportDbParam> dbParamLists = jimuReportDbParamService.list(dbParamWrapper);

                    if (!CollectionUtils.isEmpty(dbParamLists)) {
                        //查询到了历史数据
                        jimuReportDbParamService.removeBatchByIds(dbParamLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
                    }
                    jimuReportDbParamService.saveBatch(dbParamList);
                }


            }
            if (!CollectionUtils.isEmpty(dictList)) {
                //获取本次打印报表涉及字典信息
                QueryWrapper<JimuDict> dictWrapper = new QueryWrapper<>();
                dictWrapper.in("dict_code", dictList.stream().map(n -> n.getDictCode()).collect(Collectors.toList()));
                if (ValidateUtil.isNotEmpty(tenantId)) {
                    dictWrapper.eq("tenant_id", tenantId);
                }
                List<JimuDict> dictLists = jimuDictService.list(dictWrapper);

                if (!CollectionUtils.isEmpty(dictLists)) {
                    //查询到了历史数据
                    jimuDictService.removeBatchByIds(dictLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
                }
                for (JimuDict obj : dictList) {
                    QueryWrapper<JimuDictItem> dictItemWrapper = new QueryWrapper<>();
                    dictItemWrapper.in("dict_id", obj.getId());
                    if (ValidateUtil.isNotEmpty(tenantId)) {
                        dictItemWrapper.eq("tenant_id", tenantId);
                    }
                    List<JimuDictItem> dictItemLists = jimuDictItemService.list(dictItemWrapper);
                    if (!CollectionUtils.isEmpty(dictItemLists)) {
                        //查询到了历史数据
                        jimuDictItemService.removeBatchByIds(dictItemLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
                    }
                    List<String> codes = new ArrayList<>();
                    codes.add(obj.getDictCode());
                    SelectDto selectDto = new SelectDto();
                    selectDto.setTeId(tenantId);
                    selectDto.setCodes(codes);
                    Map<String, List<VueTreeVo>> map = getDictionaryList(selectDto);
                    List<JimuDictItem> items = new ArrayList<>();
                    if (map != null) {
                        for (String key : map.keySet()) {
                            log.info("DictionaryCode:" + key);
                            List<VueTreeVo> vueTreeVos = map.get(key);
                            getJimuDictItems(obj, items, vueTreeVos);
                        }
                    }
                    log.info("jimuDictItems:" + JacksonUtils.toJSONString(items));
                    jimuDictItemService.saveBatch(items);
                    log.info("jimuDict:" + JacksonUtils.toJSONString(obj));
                    jimuDictService.save(obj);

                }

            }
            return BaseResponse.ok("导入成功！");
        } catch (Exception ex) {
            return BaseResponse.fail(ex.getMessage());
        }

    }

    private void getJimuDictItems(JimuDict obj, List<JimuDictItem> items, List<VueTreeVo> vueTreeVos) {
        for (VueTreeVo vueTreeVo : vueTreeVos) {
            log.info("DictionaryName:" + vueTreeVo.getTitle() + ";DictionaryCode:" + vueTreeVo.getValue());
            if (vueTreeVo != null) {
                if (!vueTreeVo.getChildren().isEmpty()) {
                    setJimuDictItem(obj, items, vueTreeVo);
                    getJimuDictItems(obj, items, vueTreeVo.getChildren());
                } else {
                    setJimuDictItem(obj, items, vueTreeVo);
                }
            }
        }

    }

    private void setJimuDictItem(JimuDict obj, List<JimuDictItem> items, VueTreeVo vueTreeVo) {
        JimuDictItem item = new JimuDictItem();
        item.setId(IdWorker.getGenerateID());
        item.setDictId(obj.getId());
        item.setItemText(vueTreeVo.getTitle());
        item.setItemValue(vueTreeVo.getValue());
        item.setSortOrder(vueTreeVo.getdSort());
        item.setStatus("1");
        items.add(item);
    }

    @Override
    public BaseResponse importJimuReport(MultipartFile file, String tenantId) {
        try {

            return importJimuReportList(readMultipartFileJson(file), tenantId);
        } catch (Exception ex) {
            return BaseResponse.fail(ex.getMessage());
        }


    }

    @Override
    public BaseResponse refreshJimuDictItem(String customerId) {
        QueryWrapper<JimuDict> dictWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(customerId)) {
            dictWrapper.eq("tenant_id", customerId);
        }
        List<JimuDict> dictLists = jimuDictService.list(dictWrapper);
        for (JimuDict obj : dictLists) {
            log.info("DictCode:" + obj.getDictCode());
            QueryWrapper<JimuDictItem> dictItemWrapper = new QueryWrapper<>();
            dictItemWrapper.in("dict_id", obj.getId());
            if (ValidateUtil.isNotEmpty(customerId)) {
                dictItemWrapper.eq("tenant_id", customerId);
            }
            List<JimuDictItem> dictItemLists = jimuDictItemService.list(dictItemWrapper);
            if (!CollectionUtils.isEmpty(dictItemLists)) {
                //查询到了历史数据
                jimuDictItemService.removeBatchByIds(dictItemLists.stream().map(n -> n.getId()).collect(Collectors.toList()));
            }
            List<String> codes = new ArrayList<>();
            codes.add(obj.getDictCode());
            SelectDto selectDto = new SelectDto();
            selectDto.setTeId(customerId);
            selectDto.setCodes(codes);
            Map<String, List<VueTreeVo>> map = getDictionaryList(selectDto);
            List<JimuDictItem> items = new ArrayList<>();
            if (map != null) {
                for (String key : map.keySet()) {
                    log.info("DictionaryCode:" + key);
                    List<VueTreeVo> vueTreeVos = map.get(key);
                    getJimuDictItems(obj, items, vueTreeVos);
                }
            }
            log.info("jimuDictItems:" + JacksonUtils.toJSONString(items));
            jimuDictItemService.saveBatch(items);
        }
        return BaseResponse.ok();
    }

    public JimuReportDto readMultipartFileJson(MultipartFile file) throws IOException {
        // 读取文件内容

        byte[] fileBytes = file.getBytes();
        //获取文件类型
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if (".txt".equals(fileType)) {
            String  content=new String(fileBytes);
            String data = content.replace("jpm64=", "");
            byte[] decodedBytes = Base64.getDecoder().decode(data);
            String originalString = new String(decodedBytes, StandardCharsets.UTF_8);
            JimuReportDto requ = JacksonUtils.parseObject(originalString, JimuReportDto.class);
            return requ;
        } else {
            // 解析JSON数据
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(fileBytes);
            JimuReportDto requ = JacksonUtils.parseObject(jsonNode.toString(), JimuReportDto.class);
            return requ;
        }

        // 处理JSON数据
        // ...
    }


    private Map<String, List<VueTreeVo>> getDictionaryList(SelectDto request) {
        List<String> codes = request.getCodes();
        if (codes == null || codes.size() < 1) return null;
        String allKey = redisUtils.tenantKey("allDictionary", request.getTeId());
        Object dictionaryTreeList = redisUtils.get(allKey);
        List<SysDictionary> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<SysDictionary>>() {
            });
        } else {
            list = getAll(request.getTeId());
            redisUtils.set(allKey, JacksonUtils.toJSONString(list));
        }
        Map<String, List<VueTreeVo>> data = new HashMap<>();
        for (String code : codes) {
            List<VueTreeVo> treeList = new ArrayList<>();
            list.forEach(model -> {
                if (code.equals(model.getdCode())) {
                    list.forEach(child -> {
                        if (ValidateUtil.isNotEmpty(child.getdStatus()) && child.getdStatus().equals("1") && ValidateUtil.isNotEmpty(child.getdParentid()) && child.getdParentid().equals(model.getdId())) {
                            VueTreeVo vueTree = new VueTreeVo();
                            vueTree.setKey(child.getdId());
                            vueTree.setTitle(child.getdName());
                            vueTree.setValue(child.getdCode());
                            vueTree.setdStatus(child.getdStatus());
                            List<VueTreeVo> vueTrees = new ArrayList<>();
                            getDictionaryTreeChildren(vueTrees, child.getdId(), list);
                            vueTree.setChildren(vueTrees);
                            treeList.add(vueTree);
                        }
                    });
                }
            });
            data.put(code, treeList);
        }
        return data;
    }

    private List<SysDictionary> getAll(String teId) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) queryWrapper.eq("Te_Id", teId).or().eq("D_Mold", 1);
        queryWrapper.orderByAsc("D_Sort");
        return sysDictionaryMapper.selectList(queryWrapper);
    }

    private void getDictionaryTreeChildren(List<VueTreeVo> treeList, String parentId, List<SysDictionary> list) {
        list.forEach(model -> {
            if (ValidateUtil.isNotEmpty(model.getdStatus()) && model.getdStatus().equals("1") && ValidateUtil.isNotEmpty(model.getdParentid()) && model.getdParentid().equals(parentId)) {
                VueTreeVo vueTree = new VueTreeVo();
                vueTree.setKey(model.getdId());
                vueTree.setTitle(model.getdName());
                vueTree.setValue(model.getdCode());
                vueTree.setdStatus(model.getdStatus());
                List<VueTreeVo> vueTrees = new ArrayList<>();
                getDictionaryTreeChildren(vueTrees, model.getdId(), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            }
        });
    }
}
