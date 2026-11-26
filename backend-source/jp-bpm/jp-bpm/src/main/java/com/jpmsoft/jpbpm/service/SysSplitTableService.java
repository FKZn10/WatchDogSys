package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysSplitTable;
import org.springframework.stereotype.Service;

@Service
public interface SysSplitTableService extends IService<SysSplitTable> {
    SysSplitTable selectSysSplitTableBySplitId(String splitId);
}
