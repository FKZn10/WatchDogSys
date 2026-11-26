package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConMeasureRecordCheckMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordCheckVo;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordCheckService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Service
public class ConMeasureRecordCheckImpl extends ServiceImpl<ConMeasureRecordCheckMapper, ConMeasureRecordCheck> implements IConMeasureRecordCheckService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IConMeasureRecordService conMeasureRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createConMeasureRecordCheck(ConMeasureRecordCheckDto request) {
        ConMeasureRecordCheck entity = new ConMeasureRecordCheck();
        BeanUtils.copyProperties(request, entity);
        entity.setMearcId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(entity.getMearcFile()), entity.getMearcId());
        conMeasureRecordService.updateFinalAmount(entity);
        return BaseResponse.ok(entity.getMearId());
    }

    @Override
    public BaseResponse updateConMeasureRecordCheck(ConMeasureRecordCheckDto request) {
        if (ValidateUtil.isEmpty(request.getMearcId())) return BaseResponse.fail(ResMsg.notReqParam);
        ConMeasureRecordCheck entity = new ConMeasureRecordCheck();
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileAnnexService.refreshRelationFile(request.getMearcFile(), request.getMearcFileIds(), entity.getMearcId());
        conMeasureRecordService.updateFinalAmount(entity);
        return BaseResponse.ok(entity.getMearId());
    }

    @Override
    public BaseResponse getConMeasureRecordCheckById(String id) {
        ConMeasureRecordCheckVo data = baseMapper.getConMeasureRecordCheckById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getConMeasureRecordCheckList(ConMeasureRecordCheckDto request) {
        QueryMap<ConMeasureRecordCheckDto> params = new QueryMap<>(request);
        params.put("mearcUname").put("mearcNodeName").put("mearId");
        IPage<ConMeasureRecordCheckVo> page = new Page<>(request.getPage(), request.getSize());
        List<ConMeasureRecordCheckVo> datas = baseMapper.getConMeasureRecordCheckList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public void deleteConMeasureRecordCheckByMearIds(List<String> mearIds) {
        if (ValidateUtil.isEmpty(mearIds)) return;
        QueryWrapper<ConMeasureRecordCheck> wrapper = new QueryWrapper<>();
        wrapper.in("Mear_Id", mearIds);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteConMeasureRecordCheckByMeaId(String meaId) {
        baseMapper.deleteConMeasureRecordCheckByMeaId(meaId);
    }
}




