package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.DictionaryDto;
import com.jpmsoft.userProducer.pojo.dto.SelectDto;
import com.jpmsoft.userProducer.pojo.entity.SysDictionary;
import com.jpmsoft.userProducer.pojo.vo.VueTreeVo;

import java.util.List;


public interface ISysDictionaryService extends IService<SysDictionary> {

    /**
     * 数据字典列表
     *
     * @return
     */
    BaseResponse getDictionaryTreeList(String dName, String dCode, String token);

    /**
     * 新增修改删除字典
     *
     * @param request
     * @return
     */
    BaseResponse CUDDictionary(DictionaryDto request);


    BaseResponse getSelctList(SelectDto request);

    /**
     * 获取字典树形
     *
     * @return
     */
    BaseResponse getDictionaryTree(SelectDto request);

    /**
     * 获取字典树形
     * 批量
     *
     * @param request
     * @return
     */
    BaseResponse getDictionaryTreeByCodes(SelectDto request);

    /**
     * 上移或者下移字典节点
     *
     * @return
     */
    BaseResponse upOrDownDictionarySort(String dId, String action,String token);


    List<VueTreeVo>  getDictionaryByParentCode(String code, String token);

    BaseResponse getApplicationDictionary(SelectDto request);

    BaseResponse addDictionaryByCodeCj(DictionaryDto request);
}
