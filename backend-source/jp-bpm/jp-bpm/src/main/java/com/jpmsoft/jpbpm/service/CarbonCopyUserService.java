package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.CarbonCopyUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarbonCopyUserService extends IService<CarbonCopyUser> {

    List<CarbonCopyUser> getCarbonCopyUserListByActId(String ccActid,String customerId);
}
