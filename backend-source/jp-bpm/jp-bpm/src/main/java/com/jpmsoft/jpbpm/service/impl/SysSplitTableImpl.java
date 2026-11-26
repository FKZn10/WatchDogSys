package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysSplitTableMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysSplitTable;
import com.jpmsoft.jpbpm.service.SysSplitTableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysSplitTableImpl extends ServiceImpl<SysSplitTableMapper, SysSplitTable> implements SysSplitTableService {
    @Override
   public SysSplitTable selectSysSplitTableBySplitId(String splitId) {
       return  baseMapper.selectById(splitId);
   }
}
