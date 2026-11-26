package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.SysDictionaryMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDictionaryImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {
    @Override
    public List<SysDictionary> getSysDictionaryByCode(String code) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("D_Sort");
        List<SysDictionary> list = baseMapper.selectList(queryWrapper);

        List<SysDictionary> codeDics = list.stream().filter(n -> n.getdCode() != null && n.getdCode().equals(code)).collect(Collectors.toList());
        List<SysDictionary> returnList = new ArrayList<>();
        if (codeDics.size() > 0) {
            returnList.add(codeDics.get(0));
            List<SysDictionary> divList = getDictionaryList(codeDics.get(0).getdId(), list);
            if (divList.size() > 0) {
                returnList.addAll(divList);
            }
        }
        return returnList;
    }

    public List<SysDictionary> getDictionaryList(String id, List<SysDictionary> allList) {
        List<SysDictionary> returnList = new ArrayList<>();
        List<SysDictionary> newList = allList.stream().filter(n -> n.getdParentid() != null && n.getdParentid().equals(id)).collect(Collectors.toList());
        for (SysDictionary obj : newList) {
            returnList.add(obj);
            List<SysDictionary> divList = getDictionaryList(obj.getdId(), allList);
            if (divList.size() > 0) {
                returnList.addAll(divList);
            }
        }
        return returnList;
    }


    @Override
    public String getTitle(String titleStr, String id, List<SysDictionary> allList) {
        List<SysDictionary> list = allList.stream().filter(n -> n.getdId().equals(id)).collect(Collectors.toList());
        if (list.size() > 0) {
            String parentId = list.get(0).getdParentid();
            String titlenName = list.get(0).getdName();
            if (parentId.equals("0")) {
                return titlenName + "_" + titleStr;
            } else {
                return getTitle(titlenName + "_" + titleStr, parentId, allList);
            }
        } else {
            return titleStr;
        }
    }

    @Override
    public List<String> getCodeList(String code) {
        List<SysDictionary> dis = getDictionaryByParentCode(code);
        if (!CollectionUtils.isEmpty(dis))
            return dis.stream().map(SysDictionary::getdCode).collect(Collectors.toList());
        return null;
    }
    public List<SysDictionary> getDictionaryByParentCode(String code) {
        return baseMapper.getDictionaryByParentCode(code);
    }
}
