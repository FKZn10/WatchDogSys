package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.custom.mapper.ConContractMapper;
import com.jpmsoft.custom.pojo.entity.ConContract;
import com.jpmsoft.custom.service.IConContractService;
import org.springframework.stereotype.Service;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/30 9:28
 */
@Service
public class ConContractImpl extends ServiceImpl<ConContractMapper, ConContract> implements IConContractService {
}
