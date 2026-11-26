package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineDatasourceTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineDatasourceTempResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.OutlineDatasourceTempMapper;
import com.jpmsoft.userProducer.pojo.entity.OutlineDatasourceTemp;
import com.jpmsoft.userProducer.service.IOutlineDatasourceTempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务功能数据源关联
 *
 * @author tianhelin
 * @date 2025/6/26
 */
@Service
public class OutlineDatasourceTempImpl extends ServiceImpl<OutlineDatasourceTempMapper, OutlineDatasourceTemp> implements IOutlineDatasourceTempService {
    /**
     * 新增或修改业务功能数据源关联
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDatasourceTemp(OutlineDatasourceTempRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineDatasourceTemp temp = new OutlineDatasourceTemp();
        BeanUtils.copyProperties(request, temp);
        if (ValidateUtil.isEmpty(temp.getId())) {
            temp.setId(GenerateUUID.getUUID());
        }
        this.saveOrUpdate(temp);
        return BaseResponse.ok(temp.getId());
    }

    /**
     * 删除业务功能数据源关联
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse deleteDatasourceTempById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        this.removeById(id);
        return BaseResponse.ok();
    }

    /**
     * 分页查询业务功能数据源关联
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getDatasourceTempPage(OutlineDatasourceTempRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineDatasourceTemp> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineDatasourceTempResponse> datas = baseMapper.getDatasourceTempPage(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据odIds查询业务功能数据源关联
     *
     * @param odIds
     * @return
     */
    @Override
    public List<OutlineDatasourceTempResponse> getDatasourceTempByOdIds(List<String> odIds) {
        if (ValidateUtil.isNotEmpty(odIds)) {
            return baseMapper.getDatasourceTempByOdIds(odIds);
        }
        return Collections.emptyList();
    }

    /**
     * 批量新增或修改业务功能数据源关联
     *
     * @param datasourceTempList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchCreateOrUpdateDatasourceTemp(List<OutlineDatasourceTempRequest> datasourceTempList) {
        if (ValidateUtil.isEmpty(datasourceTempList)) {
            return BaseResponse.ok();
        }
        // 先删除旧数据
        lambdaUpdate().eq(OutlineDatasourceTemp::getOdId, datasourceTempList.get(0).getOdId()).remove();
        // 再新增
        this.saveBatch(datasourceTempList.stream().map(item -> {
            OutlineDatasourceTemp temp = new OutlineDatasourceTemp();
            BeanUtils.copyProperties(item, temp);
            temp.setId(GenerateUUID.getUUID());
            return temp;
        }).collect(Collectors.toList()));
        return BaseResponse.ok();
    }
}
