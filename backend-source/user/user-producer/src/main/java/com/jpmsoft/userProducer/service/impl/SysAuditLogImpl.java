package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.userProducer.mapper.SysAuditLogMapper;
import com.jpmsoft.userProducer.pojo.dto.SysAuditLogQueryDto;
import com.jpmsoft.userProducer.pojo.entity.SysAuditLog;
import com.jpmsoft.userProducer.pojo.vo.SysAuditLogVo;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysAuditLogService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:54
 */
@Service
public class SysAuditLogImpl extends ServiceImpl<SysAuditLogMapper, SysAuditLog> implements ISysAuditLogService {

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;


    @Override
    public BaseResponse getSysAuditLogList(SysAuditLogQueryDto query) {
        QueryMap<SysAuditLogQueryDto> queryMap = new QueryMap<>(query);
        queryMap.put("salFormId").put("createUname").put("salType").put("fileTitle");
        IPage<SysAuditLogVo> page = new Page<>(query.getPage(), query.getSize());
        List<SysAuditLogVo> data = baseMapper.getSysAuditLogList(page, queryMap.getQuery());
        if (!CollectionUtils.isEmpty(data)) {
            InterfaceEncDecDTO encDec = sysConfigEntityService.getInterfaceEncDec();
            if (encDec.getAuditListVerify()) {
                List<SysAuditLog> list = baseMapper.selectBatchIds(data.stream().map(SysAuditLogVo::getId).collect(Collectors.toList()));
                list.forEach(d -> {
                    apiUtilsService.signVerify(encDec, encDec.getAuditSign(), d, d.getSign());
                });
            }
        }
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }
}
