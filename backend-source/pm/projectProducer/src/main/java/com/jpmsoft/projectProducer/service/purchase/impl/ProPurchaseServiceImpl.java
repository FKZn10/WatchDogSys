package com.jpmsoft.projectProducer.service.purchase.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.purchase.ProPurchaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.purchase.ProPurchaseDto;
import com.jpmsoft.projectProducer.pojo.entity.purchase.ProPurchase;
import com.jpmsoft.projectProducer.pojo.vo.purchase.ProPurchaseVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.purchase.IProPurchaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class ProPurchaseServiceImpl extends ServiceImpl<ProPurchaseMapper, ProPurchase> implements IProPurchaseService {


    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse createOrUpdatePropurchase(ProPurchaseDto request) {
        if (ValidateUtil.isNotEmpty(request.getId()))
            return updatePropurchase(request);
        else
            return createPropurchase(request);
    }

    @Override
    public BaseResponse createPropurchase(ProPurchaseDto request) {
        ProPurchase entity = new ProPurchase();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        entity.setCreateTime(new Date());
        entity.setState(0);
        entity.setStateTime(new Date());
        baseMapper.insert(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
//        sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(request.getFileRelationId()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse updatePropurchase(ProPurchaseDto request) {
        if (ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail("id为空");
        }
        ProPurchase entity = baseMapper.selectById(request.getId());
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(ResMsg.infoNotExist);
        request.setCreateUid(entity.getCreateUid());
        request.setCreateUname(entity.getCreateUname());
        request.setCreateTime(entity.getCreateTime());
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        BeanUtils.copyProperties(request, entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
        List<String> id = new ArrayList<String>() {{
            add(request.getFileRelationId());
        }};

        List<String> ids = new ArrayList<String>() {{
            add(request.getFileIds());
        }};

        sysFileAnnexService.refreshRelationFile(id,ids, entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getPropurchaseById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail("id为空");
        }
        ProPurchaseVo data = baseMapper.getPropurchaseById(id);
        return BaseResponse.ok(data);
    }


    @Override
    public BaseResponse getPropurchaseList(ProPurchaseDto request) {

        IPage<ProPurchaseVo> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<ProPurchaseDto> params = new QueryMap<>(request);
        // 确保将需要的查询字段加入参数 Map
        params.put("pName").put("pId").put("projectType");

        if (request.getFilerTime() != null) {
            ZoneId zone = ZoneId.systemDefault();

            // 1. 将 java.util.Date 转换为 LocalDate，以便进行计算
            LocalDate localDate = request.getFilerTime().toInstant().atZone(zone).toLocalDate();

            // 2. 获取当月第一天
            LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
            // 3. 获取下个月第一天（作为范围的上限，不包含此天）
            LocalDate firstDayOfNextMonth = firstDayOfMonth.plusMonths(1);

            // 4. 转换为 java.util.Date 类型，时间为 00:00:00
            Date startDate = Date.from(firstDayOfMonth.atStartOfDay(zone).toInstant());
            Date endDate = Date.from(firstDayOfNextMonth.atStartOfDay(zone).toInstant());

            // 5. 【修正点】将 Date 对象直接放入 params 中，键名需与 XML 匹配
            params.put("filerTimeStart", startDate);
            params.put("filerTimeEnd", endDate);
        }

        // 调用 XML Mapper 方法
        List<ProPurchaseVo> datas = baseMapper.getPropurchaseList(page, params.getQuery());
        return BaseResponse.ok(datas,  (int) page.getTotal());
    }


}
