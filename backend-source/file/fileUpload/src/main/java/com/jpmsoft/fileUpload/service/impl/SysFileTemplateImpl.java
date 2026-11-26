package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.SysFileTemplateMapper;
import com.jpmsoft.fileUpload.pojo.dto.FileTemplateTableAndPrimaryDto;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysFileTemplateDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.service.ISysFileTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysFileTemplateImpl extends ServiceImpl<SysFileTemplateMapper, SysFileTemplate> implements ISysFileTemplateService {

    @Override
    public List<SysFileTemplate> getByCodes(List<String> codes) {
        QueryWrapper<SysFileTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", codes);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FileTemplateTableAndPrimaryDto> getFileTemplateTableAndPrimaryByPid(List<String> tCodes) {
        if (tCodes == null || tCodes.size() < 1)
            return null;
        return baseMapper.getFileTemplateTableAndPrimaryByPid(tCodes);
    }

    @Override
    public List<String> filterSecTcode(List<String> codes) {
        if (codes == null || codes.size() < 1) return null;
        QueryWrapper<SysFileTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", "SectionManage");
        SysFileTemplate pojo = baseMapper.selectOne(wrapper);
        QueryWrapper<SysFileTemplate> wr = new QueryWrapper<>();
        wr.eq("T_ParentID", pojo.gettId());
        List<SysFileTemplate> childs = baseMapper.selectList(wr);
        List<String> chilCode = childs.stream().map(SysFileTemplate::gettCode).collect(Collectors.toList());
        codes.removeAll(chilCode);
        return codes;
    }

    @Override
    public List<String> getTablesByCodes(List<String> codes) {
        QueryWrapper<SysFileTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", codes.get(0));
        for (int i = 1; i < codes.size(); i++)
            wrapper.or().eq("T_Code", codes.get(i));
        List<SysFileTemplate> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0)
            return datas.stream().map(SysFileTemplate::gettTable).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<SysFileTemplateDto> getAllFileTemp() {
        return baseMapper.getAllFileTemp();
    }

    @Override
    public SysFileTemplate getByCode(String tCode) {
        QueryWrapper<SysFileTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", tCode);
        List<SysFileTemplate> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().findFirst().get();
        return null;
    }
}
