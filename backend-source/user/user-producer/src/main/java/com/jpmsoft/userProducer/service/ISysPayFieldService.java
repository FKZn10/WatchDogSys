package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.SysPayFieldDto;
import com.jpmsoft.userProducer.pojo.entity.SysPayField;
import com.jpmsoft.userProducer.pojo.entity.SysPayTemplate;
import com.jpmsoft.userProducer.pojo.vo.SysPayFieldVo;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:10
 */
@Deprecated
public interface ISysPayFieldService extends IService<SysPayField> {

    void createOrUpdateSysPayField(List<SysPayFieldDto> datas, SysPayTemplate payTemplate);

    void deleteSysPayFieldByPtId(String ptId);

    List<SysPayFieldVo> getSysPayFieldByTmpId(String ptId);
}
