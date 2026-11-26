package com.jpmsoft.projectProducer.service.other;

import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;

import java.util.List;

public interface ISysDictionaryService {
    List<SysDictionary> getSysDictionaryByCode(String  code);


    String  getTitle(String titleStr,String id,List<SysDictionary> allList);

    List<String> getCodeList(String code);

    /**
     * 根据父字典编码获取下级字典列表
     *
     * @param code 父字典编码
     */
    List<SysDictionary> getDictionaryByParentCode(String code);
}
