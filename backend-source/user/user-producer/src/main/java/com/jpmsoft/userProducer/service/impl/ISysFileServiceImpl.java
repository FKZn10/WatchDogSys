package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.config.RequestDataHelper;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysFileMapper;
import com.jpmsoft.userProducer.pojo.entity.SysFile;
import com.jpmsoft.userProducer.service.ISysFileService;
import com.jpmsoft.userProducer.service.ISysSplitTableDtlService;
import com.jpmsoft.userProducer.utils.ClazzUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:40
 */
@Service
public class ISysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Resource
    private ISysSplitTableDtlService sysSplitTableDtlService;

    @Override
    public <T, D extends BaseFileDto> boolean FileHelper(T entity, D request, String action, String fileRelationId) {
        Integer state = ValidateUtil.isNotNull(request.getState()) ? request.getState() : ApprovalStatusEnum.Not_Started.getIndex();
        return saveFile((String) getFieldValue(entity, "id"), request.getTId(), state + ""
                , request.getCreateUid(), request.getCreateUname(), (Date) getFieldValue(entity, "createTime"),
                request.getPId(), request.getFileTitle(), request.getFileCode(), action, request.getFileRelationId()
                , request.getSecId(), request.getTCode(), request.getConId());
    }

    public Boolean saveFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId) {
        SysFile fileentity = assembleEntity(id, tId, flowStatus, createUserId, createUserName, createTime, pId, title, code, sectionId, tCode, conId);
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;
    }

    private SysFile assembleEntity(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId,
                                   String title, String code, String sectionId, String tCode, String conId) {
        SysFile fileentity = baseMapper.selectById(id);
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(id);
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        if (flowStatus != null && flowStatus.equals("null"))
            flowStatus = null;
        fileentity.settCode(tCode);
        fileentity.settId(tId);
        fileentity.setFileflowstatus(flowStatus);
        fileentity.setFilecreateuser(createUserId);
        fileentity.setFilecreateuserName(createUserName);
        fileentity.setFilecreatedate(createTime);
        if (!ValidateUtil.isEmpty(pId))
            fileentity.setFileproject(pId);
        fileentity.setFilesectionid(sectionId);
        fileentity.setFilecontract(conId);
        return fileentity;
    }

    private void toCreateOrUpdateFile(SysFile fileentity, String status, String fileRelationId) {
        //if (ValidateUtil.isAnObjEmpty(fileentity.gettCode(), fileentity.gettId(), fileentity.getFiletitle()))
        //    throw new JPMException(ResMsg.fileCUEroor);
        RequestDataHelper.setRequestData(sysSplitTableDtlService.getSplitTableCode(fileentity.gettCode()));
        if (status.equals("create")) {
            fileentity.setFileRelationId(fileRelationId);
            baseMapper.insert(fileentity);
        } else if (status.equals("update")) {
            baseMapper.updateById(fileentity);
        } else {
            baseMapper.deleteById(fileentity.getFileid());
        }
    }

    /**
     * 反射获取对象值
     *
     * @param entity
     * @param field
     * @return
     */
    protected <T> Object getFieldValue(T entity, String field) {
        try {
            List<Field> fieldList = ClazzUtil.getAllFields(entity.getClass());
            Field f = ClazzUtil.getField(fieldList, field);
            f.setAccessible(true);
            Object value = f.get(entity);
            return value;
        } catch (Exception ex) {
            throw new JPMException(String.format(ResMsg.notTheReq, field));
        }
    }
}
