package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SecExpertMapper;
import com.jpmsoft.fileUpload.pojo.entity.SecExpert;
import com.jpmsoft.fileUpload.pojo.entity.SysFile;
import com.jpmsoft.fileUpload.service.ISecExpertService;
import com.jpmsoft.fileUpload.service.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecExpertImpl extends ServiceImpl<SecExpertMapper, SecExpert> implements ISecExpertService {

    @Autowired
    private ISysFileService sysFileService;


    @Override
    public BaseResponse importSecExpert(List<SecExpertModel> datas, String uId, String uName) {
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecExpert> entitys = new ArrayList<>();
            List<SysFile> filePojos = new ArrayList<>();
            Date now = new Date();
            for (SecExpertModel data : datas) {
                SecExpert entity = new SecExpert();
                BeanUtils.copyProperties(data, entity);
                entity.setExId(GenerateUUID.getUUID());
                entity.setExIdType("身份证");
                entity.setExCreateTime(now);
                entity.setExCreateUid(uId);
                entity.setExCreateUname(uName);
                entity.setExState(ApprovalStatusEnum.Already_Over.getIndex());
                entity.setExStateTime(now);
                entitys.add(entity);
                SysFile filePojo = new SysFile();
                filePojo.setFileid(entity.getExId());
                filePojo.settCode("SecExpert01");
                filePojo.setFiletitle("专家信息备案");
                filePojo.setFileflowstatus("7");
                filePojo.setFilecreateusername(uName);
                filePojo.setFilecreateuser(uId);
                filePojo.setFilecreatedate(now);
                filePojo.setFileRelationId(GenerateUUID.getUUID());
                filePojos.add(filePojo);
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
            if (ValidateUtil.isNotEmpty(filePojos))
                sysFileService.saveBatch(filePojos);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<SecExpert> getSecExpertBySrId(String srId) {
        return baseMapper.getSecExpertBySrId(srId);
    }
}
