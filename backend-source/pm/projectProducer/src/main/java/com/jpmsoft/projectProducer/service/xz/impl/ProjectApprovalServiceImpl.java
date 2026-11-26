package com.jpmsoft.projectProducer.service.xz.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.jpmsoft.common.model.request.xz.ProjectApprovalQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApprovalVO;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.mapper.xz.ProjectApprovalMapper;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApproval;
import com.jpmsoft.projectProducer.service.xz.ProjectApprovalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/9/12 10:48
 * @Description:
 * @Version: 1.0
 */
@Service
public class ProjectApprovalServiceImpl extends ServiceImpl<ProjectApprovalMapper, ProjectApproval> implements ProjectApprovalService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResult<ProjectApprovalVO> page(ProjectApprovalQueryRequest request) {
        PageResult<ProjectApprovalVO> result = new PageResult<>();
        IPage<ProjectApprovalVO> page = new Page<>(request.getPageNum(), request.getPageSize());

        if (StringUtils.isNotBlank(request.getSortFiled())) {
            request.setSortFiled(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getSortFiled()));
        }

        List<ProjectApprovalVO> vos = this.baseMapper.queryPage(page, request.getSortFiled(), request.getSortType(), request.getProjectName(), request.getApplicationCompanyId(), request.getApplicationCompanyName(), request.getPlanYear(), request.getProjectType(), request.getProjectCategory(), request.getProjectManager(), request.getApplicationNum(), request.getProjectAmountType());
        result.setTotal(page.getTotal());
        result.setRows(vos);
        return result;
    }
}
