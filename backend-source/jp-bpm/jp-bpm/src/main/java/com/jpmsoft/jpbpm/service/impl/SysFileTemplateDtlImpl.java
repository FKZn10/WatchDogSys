package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysFileTemplateDtlMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplateDtl;
import com.jpmsoft.jpbpm.service.SysFileTemplateDtlService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysFileTemplateDtlImpl extends ServiceImpl<SysFileTemplateDtlMapper, SysFileTemplateDtl> implements SysFileTemplateDtlService {
    @Override
    public SysFileTemplateDtl getInterfaceVersion(String tId, String pageCode, String pId, String dId) {
        QueryWrapper<SysFileTemplateDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_id", tId);
        List<SysFileTemplateDtl> sysFileTemplateDtls = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(sysFileTemplateDtls)) {
            if (ValidateUtil.isNotEmpty(pId)) {
                List<SysFileTemplateDtl> pList = sysFileTemplateDtls.stream().filter(n -> pId.equals(n.getPId())).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(pList)) {
                    if (ValidateUtil.isNotEmpty(pageCode)) {
                        List<SysFileTemplateDtl> pCodeList = pList.stream().filter(n -> pageCode.equals(n.getTdCode())).collect(Collectors.toList());
                        if (ValidateUtil.isNotEmpty(pCodeList)) {
                            return pCodeList.get(0);
                        } else {
                            return pList.get(0);
                        }
                    }
                }
            }

            if (ValidateUtil.isNotEmpty(pageCode)) {
                List<SysFileTemplateDtl> pList = sysFileTemplateDtls.stream().filter(n -> pageCode.equals(n.getTdCode())).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(pList)) {
                    return pList.get(0);
                }
            }
            return null;
        } else {
            return null;
        }
    }

    @Override
    public SysFileTemplateDtl getInterfaceVersionByCode(String tId, String pageCode) {
        QueryWrapper<SysFileTemplateDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_id", tId);
        queryWrapper.eq("td_Code", pageCode);
        queryWrapper.eq("status", "1");
        List<SysFileTemplateDtl> sysFileTemplateDtls = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(sysFileTemplateDtls)) {
            return sysFileTemplateDtls.get(0);
        } else {
            return null;
        }

    }


}
