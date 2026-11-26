package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.ApplyRole;

public interface IApplyRoleService extends IService<ApplyRole> {



    BaseResponse approve2SyncDepartmentP(String id,String token);


    BaseResponse syncAll(String token);
}
