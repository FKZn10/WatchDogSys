package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoSubjectExtraChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectExtraChange;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoSubjectExtraChangeService;
import org.springframework.stereotype.Service;

/**
 * 科目信息扩展变更服务实现
 */
@Service
public class SecInfoSubjectExtraChangeImpl extends ServiceImpl<SecInfoSubjectExtraChangeMapper, SecInfoSubjectExtraChange> implements ISecInfoSubjectExtraChangeService {
}
