package com.jpmsoft.userProducer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.constants.ResMsg;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.DeleteLogDto;
import com.jpmsoft.common.model.requestAndResponse.RemarkInfoDto;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.userProducer.mapper.RemarkInfoMapper;
import com.jpmsoft.userProducer.pojo.entity.DeleteLog;
import com.jpmsoft.userProducer.pojo.entity.RemarkInfo;
import com.jpmsoft.userProducer.service.ISysFileAnnexService;
import com.jpmsoft.userProducer.service.ISysFileService;
import com.jpmsoft.userProducer.service.RemarkInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/25 09:19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RemarkInfoServiceImpl extends ServiceImpl<RemarkInfoMapper, RemarkInfo> implements RemarkInfoService {
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse insertRemarkInfo(RemarkInfoDto model) {
        String primaryId = model.getId();
        RemarkInfo entity ;
        if (primaryId == null || primaryId.isEmpty()) {
            entity = new RemarkInfo();
            BeanUtil.copyProperties(model,entity);
            primaryId= GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(DateTime.now());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), entity.getId());
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(model,entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getRemarkInfoList(RemarkInfoDto request) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        QueryMap<RemarkInfoDto> params = new QueryMap<>(request);
        params.put("remarkFunctionCode").put("remarkSourceId");
        List<RemarkInfoDto> remarkInfoList = baseMapper.getRemarkInfoList(params.getQuery());
        log.info("remarkInfoList:{}", JacksonUtils.toJSONString(remarkInfoList));
        return BaseResponse.ok(remarkInfoList);
    }

}
