package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.FiledChangeRecordDto;
import com.jpmsoft.userProducer.pojo.dto.SendFiledChangeDto;
import com.jpmsoft.userProducer.pojo.entity.FiledChangeRecord;

import java.util.List;

public interface IFiledChangeRecordService extends IService<FiledChangeRecord> {


    BaseResponse  saveFiledChangeRecord(FiledChangeRecord data);

    BaseResponse  saveFiledChangeRecordBath(List<FiledChangeRecord> data);

    /**
     * (单表单)字段修改记录
     *
     * @param model
     * @return
     */
    BaseResponse insertFiledChangeRecord(SendFiledChangeDto model);

    /**
     * (多表单)字段修改记录
     * 例：招标计划变更
     *
     * @param models
     * @return
     */
    BaseResponse insertFiledChangeRecordBath(List<SendFiledChangeDto> models);

    BaseResponse getFiledChangeRecordList(FiledChangeRecordDto request);

    /**
     * 跟组排序取每个字段最后一次更改的值
     *
     * @param fcrKey
     * @return
     */
    BaseResponse getFiledLastChangeByKey(String fcrKey);

    BaseResponse getAllFiledLastChangeByKey(String fcrKey);
}
