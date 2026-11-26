package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysPositionUserP;
import com.jpmsoft.projectProducer.pojo.entity.project.PpOwnerUnit;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/18 13:30
 */
public interface ISysPositionUserPService extends IService<SysPositionUserP> {



    void syncProjectPositionPUser(PreProject project, List<PpOwnerUnit> ownerUnits, DepartmentPCjConfDTO conf);
}
