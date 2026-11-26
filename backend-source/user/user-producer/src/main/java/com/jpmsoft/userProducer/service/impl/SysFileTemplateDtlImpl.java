package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.SysFileTemplateDtlDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysFileTemplateDtlMapper;
import com.jpmsoft.userProducer.pojo.entity.SysFileTemplateDtl;
import com.jpmsoft.userProducer.service.ISysFileTemplateDtlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 文档模板扩展
 *
 * @author tianhelin
 * @date 2026/01/17
 */
@Service
public class SysFileTemplateDtlImpl extends ServiceImpl<SysFileTemplateDtlMapper, SysFileTemplateDtl> implements ISysFileTemplateDtlService {
    /**
     * 批量新增或修改文档模板扩展
     *
     * @param fileTemplateDtls
     * @param tId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSysFileTemplateDtlBatch(List<SysFileTemplateDtlDTO> fileTemplateDtls, String tId) {
        // 先删除
        remove(Wrappers.<SysFileTemplateDtl>lambdaQuery().eq(SysFileTemplateDtl::getTId, tId));
        // 再新增
        if (ValidateUtil.isNotEmpty(fileTemplateDtls)) {
            List<SysFileTemplateDtl> saveList = new ArrayList<>();
            BeanUtils.copyList(fileTemplateDtls, SysFileTemplateDtl.class).forEach(item -> {
                item.setId(GenerateUUID.getUUID());
                item.setTId(tId);
                if (ValidateUtil.isNull(item.getCreateTime())) {
                    item.setCreateTime(new Date());
                }
                saveList.add(item);
            });
            saveBatch(saveList);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据tIds获取文档模板扩展
     *
     * @param tIds
     * @return
     */
    @Override
    public List<SysFileTemplateDtlDTO> getSysFileTemplateDtlBytIds(Collection<String> tIds) {
        if (ValidateUtil.isEmpty(tIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getSysFileTemplateDtlBytIds(tIds);
    }

    /**
     * 新增或修改单个文档模板扩展
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSysFileTemplateDtl(SysFileTemplateDtlDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SysFileTemplateDtl entity = new SysFileTemplateDtl();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
        }
        this.saveOrUpdate(entity);
        return BaseResponse.ok();
    }
}
