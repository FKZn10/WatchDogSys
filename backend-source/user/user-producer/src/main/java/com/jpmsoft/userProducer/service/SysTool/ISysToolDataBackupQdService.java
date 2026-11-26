package com.jpmsoft.userProducer.service.SysTool;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackupQd;
import org.springframework.stereotype.Service;

@Service
public interface ISysToolDataBackupQdService extends IService<SysToolDataBackupQd> {
    BaseResponse GetSysToolDataBackupQdList(String sysId);

    BaseResponse GetSysToolDataBackupData(String id);

    BaseResponse restorationById(String id, String token);
    BaseResponse fullRestoration(String id, String token);
}
