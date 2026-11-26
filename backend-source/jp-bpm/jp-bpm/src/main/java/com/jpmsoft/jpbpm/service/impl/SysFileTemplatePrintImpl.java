package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysFileTemplatePrintMapper;
import com.jpmsoft.jpbpm.pojo.dto.SysFileTemplatePrintDto;
import com.jpmsoft.jpbpm.pojo.entity.FileTemplate;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplatePrint;
import com.jpmsoft.jpbpm.pojo.vo.SysFileTemplatePrintVo;
import com.jpmsoft.jpbpm.service.FileTemplateService;
import com.jpmsoft.jpbpm.service.SysFileTemplateApiFieldService;
import com.jpmsoft.jpbpm.service.SysFileTemplatePrintService;
import com.jpmsoft.jpbpm.util.CheckNullUtil;
import com.jpmsoft.jpbpm.util.PrintRuleMatchUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class SysFileTemplatePrintImpl extends ServiceImpl<SysFileTemplatePrintMapper, SysFileTemplatePrint> implements SysFileTemplatePrintService {

    @Autowired
    private SysFileTemplateApiFieldService sysFileTemplateApiFieldService;
    @Resource
    private FileTemplateService fileTemplateService;

    @Override
    public BaseResponse createOrUpdateFileTemplatePrint(SysFileTemplatePrintDto request,String customerId) {
        SysFileTemplatePrint entity = new SysFileTemplatePrint();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getTpId())) {
            entity.setTpCreateTime(new Date());
            entity.setTpId(GenerateUUID.getUUID());
            entity.setTpState(1);
            entity.setTeId(customerId);
            baseMapper.insert(entity);
        } else {
            SysFileTemplatePrint model = baseMapper.selectById(request.getTpId());
            if (model == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            entity.setTpCreateTime(model.getTpCreateTime());
            entity.setTpCreateUid(model.getTpCreateUid());
            entity.setTpCreateUname(model.getTpCreateUname());
            baseMapper.updateById(entity);
        }
        sysFileTemplateApiFieldService.createOrUpdateFileTemplateApiField(request.getFields(), entity.getTpId());
        return new BaseResponse(true, "操作成功！", entity.getTpId());
    }

    @Override
    public BaseResponse deleteFileTemplatePrintBytpId(String tpId,String customerId) {
        SysFileTemplatePrint model = baseMapper.selectById(tpId);
        if (model == null)
            return new BaseResponse("信息不存在，请刷新重试！");
        if (!sysFileTemplateApiFieldService.can2DelFileTemplatePrint(tpId))
            return new BaseResponse("请先删除关联数据！");
        baseMapper.deleteById(tpId);
        return new BaseResponse(true, "操作成功！");
    }

    @Override
    public BaseResponse getFileTemplatePrintList(SysFileTemplatePrintDto request,String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !CheckNullUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !CheckNullUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        boolean sortFiledEmpty = ValidateUtil.isEmpty(request.getSortFiled());
        boolean sortTypeEmpty = ValidateUtil.isEmpty(request.getSortType());
        boolean useDefaultSort = sortFiledEmpty && sortTypeEmpty;
        params.put("sortFiled", useDefaultSort ? null : sortFiledEmpty ? "Tp_Create_Time" : request.getSortFiled());
        params.put("sortType", useDefaultSort ? null : sortTypeEmpty ? "DESC" : request.getSortType());

        params.put("tType", !ValidateUtil.isEmpty(request.gettType()) ? request.gettType() : null);
        params.put("tpName", !ValidateUtil.isEmpty(request.getTpName()) ? request.getTpName() : null);
        params.put("tName", !ValidateUtil.isEmpty(request.gettName()) ? request.gettName() : null);
        params.put("tCode", !ValidateUtil.isEmpty(request.gettCode()) ? request.gettCode() : null);
        params.put("tId", !ValidateUtil.isEmpty(request.gettId()) ? request.gettId() : null);
        params.put("teId", "All".equals(customerId)?null:customerId);
        params.put("tpState", request.getTpState() != null ? request.getTpState() : null);
        List<SysFileTemplatePrintVo> datas = baseMapper.getFileTemplatePrintList(params);

        // 根据过滤条件过滤结果集和总数
        List<String> removedTpIds = new ArrayList<>();

        boolean needRuleFilter = ValidateUtil.isNotEmpty(request.gettCode()) && ValidateUtil.isNotEmpty(request.getEntityId());
        Map<String, Map<String, Object>> ruleMap = new HashMap<>();
        if (needRuleFilter && ValidateUtil.isNotEmpty(datas)) {
            for (SysFileTemplatePrintVo data : datas) {
                Map<String, Object> remarkMap = PrintRuleMatchUtil.parseRuleJson(data.getTpRemark());
                if (ValidateUtil.isNotEmpty(remarkMap)) {
                    ruleMap.put(data.getTpId(), remarkMap);
                }
            }
        }

        if (needRuleFilter && ValidateUtil.isNotEmpty(ruleMap)) {

            boolean isAllCustomer = "All".equals(customerId);

            FileTemplate template = fileTemplateService.getOne(Wrappers.<FileTemplate>lambdaQuery()
                    .eq(FileTemplate::gettCode, request.gettCode())
                    .eq(!isAllCustomer && ValidateUtil.isNotEmpty(customerId),
                            FileTemplate::getTeId, customerId)
                    .orderByDesc(FileTemplate::gettCreatetime)
                    .last(" limit 1 ")
            );

            if (ValidateUtil.isNotNull(template)) {
                String tableName = template.gettTable();
                String pkName = baseMapper.getTablePkName(tableName);
                List<Map<String, Object>> records = baseMapper.getTableDataByIds(tableName, pkName, request.getEntityId());

                if (ValidateUtil.isNotEmpty(records) && ValidateUtil.isNotEmpty(datas)) {
                    Iterator<SysFileTemplatePrintVo> iterator = datas.iterator();
                    Map<String, Map<String, Object>> relationRecordCache = new HashMap<>();
                    PrintRuleMatchUtil.RelationRecordLoader relationRecordLoader = (relationTable, relationPkName, relationValue) -> {
                        List<Map<String, Object>> relationRecords = baseMapper.getTableDataByField(relationTable, relationPkName, relationValue);
                        return ValidateUtil.isNotEmpty(relationRecords) ? relationRecords.get(0) : null;
                    };

                    while (iterator.hasNext()) {
                        SysFileTemplatePrintVo data = iterator.next();
                        Map<String, Object> remarkMap = ruleMap.get(data.getTpId());
                        if (ValidateUtil.isEmpty(remarkMap)) {
                            continue;
                        }

                        // 是否有任意一条 record 满足规则
                        boolean match = PrintRuleMatchUtil.matchAny(records, remarkMap, relationRecordLoader, relationRecordCache);

                        // 不匹配删除并记录ID
                        if (!match) {
                            removedTpIds.add(data.getTpId());
                            iterator.remove();
                        }
                    }
                }
            }
        }

        if (ValidateUtil.isNotEmpty(removedTpIds)) {
            params.put("removedTpIds", removedTpIds);
        }
        Integer total = baseMapper.getFileTemplatePrintListCount(params);
        return new BaseResponse("获取成功", datas, total);
    }

    @Override
    public  List<SysFileTemplatePrint> getFileTemplatePrintList(String tId,String customerId) {
        QueryWrapper<SysFileTemplatePrint>  wrapper = new QueryWrapper<>();
        wrapper.eq("t_id", tId);
        return  baseMapper.selectList(wrapper);
    }
}
