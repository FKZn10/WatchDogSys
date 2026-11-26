package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableMapRequest;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableRequest;
import com.jpmsoft.common.model.requestAndResponse.RiskWarn.RiskWarnIndexModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.SqlSecurityValidatorUtil;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.RiskWarnIndexMapper;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnIndex;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTable;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTableMap;
import com.jpmsoft.userProducer.service.IRiskWarnIndexService;
import com.jpmsoft.userProducer.service.IRiskWarnTableMapService;
import com.jpmsoft.userProducer.service.IRiskWarnTableService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskWarnIndexImpl extends ServiceImpl<RiskWarnIndexMapper, RiskWarnIndex> implements IRiskWarnIndexService {

    @Resource
    IRiskWarnTableService riskWarnTableService;
    @Resource
    IRiskWarnTableMapService riskWarnTableMapService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateRiskWarnIndex(RiskWarnIndexRequest request) {
        RiskWarnIndex entity = new RiskWarnIndex();
        // 新建规则先分配 ID，SQL 占位符替换不能依赖尚未生成的 request.rwiId。
        String ruleId = ValidateUtil.isEmpty(request.getRwiId()) ? GenerateUUID.getUUID() : request.getRwiId();

        if (!ValidateUtil.isEmpty(request.getRwiQuerySql())) {
            String allsql = normalizeRuleSql(request.getRwiQuerySql(), ruleId, request.getdId());
            String validated = SqlSecurityValidatorUtil.validateSelectOnly(allsql);
            if (!"OK".equalsIgnoreCase(validated)) {
                return new BaseResponse(false, validated);
            }
            request.setRwiQuerySQL(allsql);
        }
        // 数据快照 SQL：与应用生成 SQL 同样做安全校验 + 占位符替换
        if (!ValidateUtil.isEmpty(request.getRwiSnapshotSql())) {
            String snapshotSql = normalizeRuleSql(request.getRwiSnapshotSql(), ruleId, request.getdId());
            String validated = SqlSecurityValidatorUtil.validateSelectOnly(snapshotSql);
            if (!"OK".equalsIgnoreCase(validated)) {
                return new BaseResponse(false, validated);
            }
            request.setRwiSnapshotSql(snapshotSql);
        }
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getRwiId())) {
            entity.setRwiId(ruleId);
            entity.setRwiCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            RiskWarnIndex model = baseMapper.selectById(request.getRwiId());
            if (model == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setRwiCreateTime(model.getRwiCreateTime());
            entity.setRwiCreateUid(model.getRwiCreateUid());
            entity.setRwiCreateUname(model.getRwiCreateUname());
            baseMapper.updateById(entity);
        }
        if (request.getRiskWarnTableList() != null && request.getRiskWarnTableList().size() > 0) {
            riskWarnTableService.createOrUpdataRiskWarnTable(entity.getRwiId(), request.getRiskWarnTableList());
        }
        if (request.getRiskWarnTableMapList() != null && request.getRiskWarnTableMapList().size() > 0) {
            riskWarnTableMapService.createOrUpdataRiskWarnTableMap(entity.getRwiId(), request.getRiskWarnTableMapList());
        }
        return BaseResponse.ok(entity.getRwiId());
    }

    /** 使用字面量替换，避免 replaceAll 将中文占位符解释为字符类。 */
    static String normalizeRuleSql(String sql, String ruleId, String deptId) {
        if (sql == null) return null;
        return sql.replace("[预警规则Id]", ruleId == null ? "" : ruleId)
                .replace("[部门id]", deptId == null ? "" : deptId)
                .replace("%%27%", "'")
                .replace("%%22%", "\"")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }

    @Override
    public BaseResponse getRiskWarnIndexList(RiskWarnIndexModel request) {
        QueryMap<RiskWarnIndexModel> queryMap = new QueryMap<>(request);
        queryMap.put("rwiType").put("rwiBasis").put("rwiLevel")
                .put("rwiState").put("dName").put("rwiPersonUname").put("rwiName");
        List<RiskWarnIndexModel> datas = baseMapper.getRiskWarnIndexList(queryMap.getQuery());
        Integer total = baseMapper.getRiskWarnIndexListCount(queryMap.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse deleteRiskWarnIndexById(String rwiId) {
        RiskWarnIndex model = baseMapper.selectById(rwiId);
        if (model == null)
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(rwiId);
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse getRiskWarnIndexById(String rwiId) {
        RiskWarnIndex model = baseMapper.selectById(rwiId);
        return BaseResponse.ok(model);
    }

    //    导入
    @Override
    public BaseResponse importRiskWarnIndex(MultipartFile file) {
        try {
            List<RiskWarnIndexRequest> data = readMultipartFileJson(file);
            List<String> massage = new ArrayList<>();
            data.forEach((n) -> {
                BaseResponse ret = createOrUpdateRiskWarnIndex(n);
                if (ret.getSuccess() == false) {
                    massage.add(n.getRwiName());
                }
            });
            return BaseResponse.ok(massage);
        } catch (Exception ex) {
            return BaseResponse.fail(ex.getMessage());
        }
    }

    public List<RiskWarnIndexRequest> readMultipartFileJson(MultipartFile file) throws IOException {
        // 读取文件内容
        byte[] fileBytes = file.getBytes();
        // 解析JSON数据
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fileBytes);
        List<RiskWarnIndexRequest> requ = JacksonUtils.parseArray(jsonNode.toString(), RiskWarnIndexRequest.class);
        return requ;
        // 处理JSON数据
        // ...
    }

//    导出
    @Override
    public BaseResponse exportRiskWarnIndex(List<String> ids) {
        List<RiskWarnIndex> data = baseMapper.selectBatchIds(ids);
        QueryWrapper<RiskWarnTable> riskWarnTableWrapper = new QueryWrapper<>();
        riskWarnTableWrapper.in("Rwi_Id", ids);
        List<RiskWarnTable> riskWarnTableList = riskWarnTableService.list(riskWarnTableWrapper);
        QueryWrapper<RiskWarnTableMap> riskWarnTableMapWrapper = new QueryWrapper<>();
        riskWarnTableMapWrapper.in("Rwi_Id", ids);
        List<RiskWarnTableMap> riskWarnTableMapList = riskWarnTableMapService.list(riskWarnTableMapWrapper);
        List<RiskWarnIndexRequest> returnList = new ArrayList<>();

        data.forEach((n) -> {
            RiskWarnIndexRequest entity = new RiskWarnIndexRequest();
            BeanUtils.copyProperties(n, entity);
            List<RiskWarnTable> riskWarnTables = riskWarnTableList.stream().filter(k -> k.getRwiId().equals(n.getRwiId())).collect(Collectors.toList());
            List<RiskWarnTableMap> riskWarnTableMaps = riskWarnTableMapList.stream().filter(k -> k.getRwiId().equals(n.getRwiId())).collect(Collectors.toList());
            List<RiskWarnTableRequest> riskWarnTablees = new ArrayList<>();
            List<RiskWarnTableMapRequest> riskWarnTableMapes = new ArrayList<>();
            riskWarnTables.forEach(i -> {
                RiskWarnTableRequest obj = new RiskWarnTableRequest();
                BeanUtils.copyProperties(i, obj);
                riskWarnTablees.add(obj);
            });
            List<RiskWarnTableMapRequest> riskWarnTableM= new ArrayList<>();
            riskWarnTableMaps.forEach(i -> {
                RiskWarnTableMapRequest obj = new RiskWarnTableMapRequest();
                BeanUtils.copyProperties(i, obj);
                riskWarnTableM.add(obj);
            });
            entity.setRiskWarnTableList(riskWarnTablees);
            entity.setRiskWarnTableMapList(riskWarnTableMapes);
            returnList.add(entity);
        });
        return BaseResponse.ok(returnList);

    }

}
