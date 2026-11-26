package com.jpmsoft.userProducer.service.SysTool;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysToolDataBackupDto;
import com.jpmsoft.userProducer.pojo.entity.SysToolDataBackup;
import com.jpmsoft.userProducer.pojo.vo.SysToolTableRelationshipVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISysToolDataBackupService extends IService<SysToolDataBackup> {
    BaseResponse GetSysToolDataBackupList(SysToolDataBackupDto request);
    BaseResponse DataBackup(SysToolDataBackupDto request);
    BaseResponse DataDeletBatch(SysToolDataBackupDto request);
}
