package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/21 11:15
 */
public interface ISysDictionaryService extends IService<SysDictionary> {

    List<SysDictionary> getDictionaryByParentCode(String code);

    List<String> getCodeList(String code);

    List<SysDictionary> getDictionaryByParentCodes(List<String> codes);
}
