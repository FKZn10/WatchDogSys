package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineDatasourceTempRequest;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.SqlSecurityValidatorUtil;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.OutlineDatasourceMapper;
import com.jpmsoft.userProducer.pojo.dto.OutlineDatasourceDto;
import com.jpmsoft.userProducer.pojo.entity.OutlineDatasource;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.pojo.vo.OutlineDatasourceVo;
import com.jpmsoft.userProducer.service.IOutlineDatasourceTempService;
import com.jpmsoft.userProducer.service.OutlineDatasourceService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OutlineDatasourceServiceImpl extends ServiceImpl<OutlineDatasourceMapper, OutlineDatasource> implements OutlineDatasourceService {
    @Resource
    private IOutlineDatasourceTempService outlineDatasourceTempService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOutlineDatasource(OutlineDatasourceDto request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // SQL注入校验
        if ("01".equalsIgnoreCase(request.getOdType()) && ValidateUtil.isNotEmpty(request.getOdSource())) {
            String validated = SqlSecurityValidatorUtil.validateSelectOnly(request.getOdSource());
            if (!"OK".equalsIgnoreCase(validated)) {
                return BaseResponse.fail(validated);
            }
        }
        OutlineDatasource entity = new OutlineDatasource();
        BeanUtils.copyProperties(request, entity);
        // 别名唯一判断
        if (datasourceAliasExist(entity.getOdAlias(), entity.getId())) {
            return BaseResponse.fail("数据源别名已存在，请修改后重试");
        }
        if (ValidateUtil.isNotEmpty(entity.getId())) {
            entity.setUpdateTime(new Date());
            baseMapper.updateById(entity);
        } else {
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            baseMapper.insert(entity);
        }
        List<OutlineDatasourceTempRequest> datasourceTempList = new ArrayList<>();
        List<String> tCodeList = request.getTCodeList();
        if (ValidateUtil.isNotEmpty(tCodeList)) {
            for (String tCode : tCodeList) {
                OutlineDatasourceTempRequest datasourceTemp = new OutlineDatasourceTempRequest();
                datasourceTemp.setTCode(tCode);
                datasourceTemp.setOdId(entity.getId());
                datasourceTempList.add(datasourceTemp);
            }
            outlineDatasourceTempService.batchCreateOrUpdateDatasourceTemp(datasourceTempList);
        }
        return BaseResponse.ok(entity.getId());
    }

    private Boolean datasourceAliasExist(String alias, String id) {
        return lambdaQuery()
                .eq(OutlineDatasource::getOdAlias, alias)
                .ne(ValidateUtil.isNotEmpty(id), OutlineDatasource::getId, id)
                .exists();
    }

    @Override
    public BaseResponse getOutlineDatasourceList(OutlineDatasourceDto request) {
        Page<OutlineDatasource> page = new Page<>(request.getPage(), request.getSize());
        QueryWrapper<OutlineDatasourceDto> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(request.getOdName()), "od_name", request.getOdName())
                .eq(StringUtils.isNotBlank(request.getOdType()), "od_type", request.getOdType())
                .eq(StringUtils.isNotBlank(request.getOdStatus()), "od_status", request.getOdStatus())
                .eq(StringUtils.isNotBlank(request.getOdMethod()), "od_method", request.getOdMethod())
                .eq(StringUtils.isNotBlank(request.getCreateUid()), "create_uid", request.getCreateUid())
                .eq(StringUtils.isNotBlank(request.getCreateUname()), "create_uname", request.getCreateUname())
                .orderByDesc("create_time");
        if (ValidateUtil.isNotEmpty(request.getOplId())) {
            List<String> odIds = baseMapper.getOdIdsByOplId(request.getOplId());
            if (ValidateUtil.isEmpty(odIds)) {
                return BaseResponse.ok();
            }
            wrapper.in("id", odIds);
        }
        if (ValidateUtil.isNotEmpty(request.getOslId())) {
            List<String> odIds = baseMapper.getOdIdsByOslId(request.getOslId());
            if (ValidateUtil.isEmpty(odIds)) {
                return BaseResponse.ok();
            }
            wrapper.in("id", odIds);
        }
        IPage<OutlineDatasource> result = baseMapper.selectOutlineByWrapper(page, wrapper);
        return BaseResponse.ok(result.getRecords(), Math.toIntExact(result.getTotal()));
    }

    @Override
    public BaseResponse getOutlineDatasourceById(String id) {
        OutlineDatasource data = baseMapper.selectById(id);
        if (ValidateUtil.isNull(data)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineDatasourceVo res = new OutlineDatasourceVo();
        BeanUtils.copyProperties(data, res);
        res.setDatasourceTempList(outlineDatasourceTempService.getDatasourceTempByOdIds(Collections.singletonList(id)));
        return BaseResponse.ok(res);
    }

}
