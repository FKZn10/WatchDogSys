package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysTOrganizationMapper;
import com.jpmsoft.userProducer.pojo.dto.SysTOrganizationDto;
import com.jpmsoft.userProducer.pojo.entity.SysTOrganization;
import com.jpmsoft.userProducer.pojo.vo.SysTOrganizationVo;
import com.jpmsoft.userProducer.service.ISysTDepartmentService;
import com.jpmsoft.userProducer.service.ISysTOrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 9:53
 */
@Service
public class SysTOrganizationImpl extends ServiceImpl<SysTOrganizationMapper, SysTOrganization> implements ISysTOrganizationService {

    @Resource
    private ISysTDepartmentService sysTDepartmentService;

    @Override
    public BaseResponse createOrUpdateSysTOrganization(SysTOrganizationDto request) {
        SysTOrganization entity = new SysTOrganization();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getToId())) {
            entity.setToId(GenerateUUID.getUUID());
            entity.setToCreateTime(new Date());
            baseMapper.insert(entity);
        } else
            baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getToId());
    }

    @Override
    public BaseResponse getSysTOrganizationList(SysTOrganizationDto request) {
        QueryMap<SysTOrganizationDto> queryMap = new QueryMap<>(request);
        queryMap.put("toName").put("toCid").put("toStatus").put("toCreateUname").put("toCName");
        IPage<SysTOrganizationVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysTOrganizationVo> datas = baseMapper.getSysTOrganizationList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getSysTOrganizationById(String id) {
        return BaseResponse.ok(baseMapper.getSysTOrganizationById(id));
    }

    @Override
    public BaseResponse delTOrganizationById(String id) {
        SysTOrganization entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        if (sysTDepartmentService.haveInfoWithTOrgan(id)) return BaseResponse.fail(BaseMsg.delTDepFirst);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }
}
