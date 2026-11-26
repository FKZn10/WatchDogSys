package com.jpmsoft.projectProducer.service.conSupNotice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.conSupNotice.ConSupNoticeDwMapper;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeDw;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeDwService;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/6 14:44
 */
@Service
public class ConSupNoticeDwImpl extends ServiceImpl<ConSupNoticeDwMapper, ConSupNoticeDw> implements IConSupNoticeDwService {

    @Resource
    private ISysCompanyService sysCompanyService;

    @Override
    public void createOrUpdate(List<ConSupNoticeDw> data, String snId) {
        deleteBySnId(snId);
        data = JacksonUtils.parseArray(JacksonUtils.toJSONString(data), ConSupNoticeDw.class);
        if (!CollectionUtils.isEmpty(data)) {
            data.forEach(d -> {
                d.setId(GenerateUUID.getUUID());
                d.setSnId(snId);
            });
            saveBatch(data);
        }
    }

    @Override
    public void deleteBySnId(String snId) {
        QueryWrapper<ConSupNoticeDw> wrapper = new QueryWrapper<>();
        wrapper.eq("Sn_Id", snId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConSupNoticeDw> getBySnId(String snId) {
        if (ValidateUtil.isEmpty(snId)) return null;
        return baseMapper.getBySnId(snId);
    }

    @Override
    public List<ConSupNoticeDw> getBySnId(List<String> snId) {
        if (!CollectionUtils.isEmpty(snId)) {
            if (snId.size() == 1) return getBySnId(snId.get(0));
            QueryWrapper<ConSupNoticeDw> wrapper = new QueryWrapper<>();
            wrapper.in("Sn_Id", snId);
            List<ConSupNoticeDw> data = baseMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(data)) {
                List<SysCompany> scs = sysCompanyService.getCompanyByIds(data.stream().map(ConSupNoticeDw::getSndCompanyId).collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(scs)) {
                    data.forEach(d -> {
                        if (ValidateUtil.isEmpty(d.getSndCompanyId())) {
                            List<SysCompany> sc = scs.stream().filter(f -> f.getcId().equals(d.getSndCompanyId())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(sc))
                                d.setSndCompanyName(sc.stream().findFirst().get().getcName());
                        }
                    });
                }
            }
            return data;
        }
        return null;
    }
}
