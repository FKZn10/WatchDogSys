package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysPersonalBpmAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysPersonalBpmAuthorizeService extends IService<SysPersonalBpmAuthorize> {

    List<String>  getCreateUidByAuthorizerUid(String uId,String customerId);
}
