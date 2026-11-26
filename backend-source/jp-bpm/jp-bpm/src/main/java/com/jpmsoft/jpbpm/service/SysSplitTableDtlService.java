package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysSplitTableDtl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SysSplitTableDtlService extends IService<SysSplitTableDtl> {

    String getTableCodeByTcode(String tcode,String newYear,String tableCode);
}
