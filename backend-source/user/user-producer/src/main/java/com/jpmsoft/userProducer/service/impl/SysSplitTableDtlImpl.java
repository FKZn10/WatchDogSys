package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysSplitTableDtlMapper;
import com.jpmsoft.common.model.request.SysSplitTableDtlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSplitTableDtl;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysSplitTableDtlService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/7 10:04
 */
@Service
public class SysSplitTableDtlImpl extends ServiceImpl<SysSplitTableDtlMapper, SysSplitTableDtl> implements ISysSplitTableDtlService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    @Override
    public BaseResponse createSysSplitTableDtl(SysSplitTableDtlDTO request, String token) {
        if (ValidateUtil.isAllEmpty(request.getTableCode(), request.getTCode()))
            return BaseResponse.fail(BaseMsg.notReqParam);

        QueryWrapper<SysSplitTableDtl> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTableCode()) && ValidateUtil.isNotEmpty(request.getTCode())) {
            wrapper.eq("table_code", request.getTableCode()).or().eq("t_code", request.getTCode());
        } else {
            if (ValidateUtil.isNotEmpty(request.getTCode()))
                wrapper.eq("t_code", request.getTCode());
            if (ValidateUtil.isNotEmpty(request.getTableCode()))
                wrapper.eq("table_code", request.getTableCode());
        }
        if (baseMapper.exists(wrapper)) return BaseResponse.fail("表编码或文档模板编码已存在数据配置！");
        SysSplitTableDtl entity = new SysSplitTableDtl();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        entity.setId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        redisUtils.delete("bpmSplitTableInfo");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteSysSplitTableDtlById(String id) {
        SysSplitTableDtl entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(id);
        redisUtils.delete("bpmSplitTableInfo");
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSysSplitTableDtlList(SysSplitTableDtlDTO request) {
        QueryWrapper<SysSplitTableDtl> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getStId()))
            wrapper.eq("st_id", request.getStId());
        if (ValidateUtil.isNotEmpty(request.getTCode()))
            wrapper.like("t_code", request.getTCode());
        if (ValidateUtil.isNotEmpty(request.getTName()))
            wrapper.like("t_name", request.getTName());
        if (ValidateUtil.isNotEmpty(request.getCreateUname()))
            wrapper.like("create_uname", request.getCreateUname());
        if (ValidateUtil.isNotEmpty(request.getTableName()))
            wrapper.like("table_name", request.getTableName());
        if (ValidateUtil.isNotEmpty(request.getTableCode()))
            wrapper.like("table_code", request.getTableCode());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("create_time");
        IPage<SysSplitTableDtl> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        List<SysSplitTableDtl> data = iPage.getRecords();
        return BaseResponse.ok(data, total.intValue());
    }

    @Override
    public String getSplitTableCode(String code) {
        String enable = sysConfigEntityService.getSysConfigByCode("bpmSplitTableEnable");
        if (ValidateUtil.isEmpty(enable) || !enable.equals("1")) return null;
        Object obj = redisUtils.get("bpmSplitTableInfo");
        if (ValidateUtil.isNull(obj)) {
            List<SysSplitTableDtlDTO> data = baseMapper.getAllSysSplitTableDtlDTO();
            if (!CollectionUtils.isEmpty(data)) {
                Map<String, String> map = new HashMap<>();
                data.forEach(d -> {
                    if (ValidateUtil.isNotEmpty(d.getTCode())) map.put(d.getTCode(), d.getStCode());
                    if (ValidateUtil.isNotEmpty(d.getTableCode())) map.put(d.getTableCode(), d.getStCode());
                });
                redisUtils.set("bpmSplitTableInfo", JacksonUtils.toJSONString(map));
                return map.getOrDefault(code, null);
            } else
                return null;
        } else {
            Map<String, String> map = JacksonUtils.parseObject(obj.toString(), Map.class);
            if (map != null)
                return map.getOrDefault(code, null);
            else return null;
        }
    }
}
