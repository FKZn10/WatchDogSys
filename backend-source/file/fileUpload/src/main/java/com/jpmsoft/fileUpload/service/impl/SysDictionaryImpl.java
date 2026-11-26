package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.SysDictionaryMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.service.ISysDictionaryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/21 11:15
 */
@Service
public class SysDictionaryImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {

    @Override
    public List<SysDictionary> getDictionaryByParentCode(String code) {
        return baseMapper.getDictionaryByParentCode(code);
    }

    @Override
    public List<String> getCodeList(String code) {
        List<SysDictionary> dis = getDictionaryByParentCode(code);
        if (!CollectionUtils.isEmpty(dis))
            return dis.stream().map(SysDictionary::getDCode).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<SysDictionary> getDictionaryByParentCodes(List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) return null;
        return baseMapper.getDictionaryByParentCodes(codes);
    }
}
