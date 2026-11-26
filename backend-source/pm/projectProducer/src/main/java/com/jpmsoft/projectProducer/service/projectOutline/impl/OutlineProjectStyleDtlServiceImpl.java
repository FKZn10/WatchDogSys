package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleDtlRequest;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectStyleDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyleDtl;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectStyleDtlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理规划子系统 样式详情
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Service
public class OutlineProjectStyleDtlServiceImpl extends ServiceImpl<OutlineProjectStyleDtlMapper, OutlineProjectStyleDtl> implements IOutlineProjectStyleDtlService {
    /**
     * 批量创建体系模板样式详情
     *
     * @param styleList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCreateOutlineStyleDtl(String opsId, List<OutlineProjectStyleDtlRequest> styleList) {
        if (ValidateUtil.isNotEmpty(styleList)) {
            // 每个分类只能有一个样式
            List<String> categories = styleList.stream()
                    .map(OutlineProjectStyleDtlRequest::getCategory)
                    .distinct()
                    .collect(Collectors.toList());
            if (categories.size() != styleList.size()) {
                throw new JPMException("每个分类只能有一个样式");
            }
        }

        this.remove(Wrappers.<OutlineProjectStyleDtl>lambdaQuery().eq(OutlineProjectStyleDtl::getOpsId, opsId));
        if (ValidateUtil.isNotEmpty(styleList)) {
            List<OutlineProjectStyleDtl> list = styleList.stream().map(style -> {
                OutlineProjectStyleDtl outlineProjectStyleDtl = new OutlineProjectStyleDtl();
                BeanUtils.copyProperties(style, outlineProjectStyleDtl);
                outlineProjectStyleDtl.setId(GenerateUUID.getUUID());
                outlineProjectStyleDtl.setOpsId(opsId);
                return outlineProjectStyleDtl;
            }).collect(Collectors.toList());
            this.saveBatch(list);
        }
    }
}
