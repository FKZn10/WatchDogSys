package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpExperience;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfo;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollect;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChangeName;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.ClazzUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysFileIpml extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Resource
    private IProjectCpService projectCpService;

    @Resource
    private IConContractService conContractService;

    @Override
    public SysFile getfile(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Boolean havingSysFile(String id) {
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.eq("FileId", id);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        return false;
    }

    @Override
    public void saveFileOrUpdate(String uuid, String title, String status ,String userid,String userName, String fileCode,boolean flag, String tId, String tCode) {
        SysFile sysFilePojo = new SysFile();
        sysFilePojo.setFiletitle(title);
        sysFilePojo.setFilecode(fileCode);
        sysFilePojo.setFileflowstatus(status);
        sysFilePojo.setFilecreateuser(userid);
        sysFilePojo.setFilecreateuserName(userName);
        sysFilePojo.setFilecreatedate(new Date());
        sysFilePojo.setFileid(uuid);
        sysFilePojo.settId(tId);
        sysFilePojo.settCode(tCode);
        if (flag){
            this.save(sysFilePojo);
        }else {
            this.updateById(sysFilePojo);
        }

    }

    @Override
    public void deleteFileById(String id) {
        if (!ValidateUtil.isEmpty(id)) {
            baseMapper.deleteById(id);
        }
    }

    @Override
    public Boolean FileHelper(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId,
                              String title, String code, String status, String relationId, String sectionId, String tCode) {
        SysFile fileentity = assembleEntity(id, tId, flowStatus, createUserId, createUserName, createTime, pId, title, code, sectionId, tCode, null);
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;
    }

    @Override
    public Boolean saveFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId) {
        SysFile fileentity = assembleEntity(id, tId, flowStatus, createUserId, createUserName, createTime, pId, title, code, sectionId, tCode, conId);
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;
    }
    @Override
    public Boolean saveFileTeId(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId,String teId) {
        SysFile fileentity = assembleEntityTeId(id, tId, flowStatus, createUserId, createUserName, createTime, pId, title, code, sectionId, tCode, conId,teId);
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;
    }

    @Override
    public SysFile structureFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String relationId, String sectionId, String tCode) {
        SysFile fileentity = new SysFile();
        fileentity.setFileid(id);
        fileentity.setFiletitle(title);
        fileentity.setFilecode(code);
        if (flowStatus != null && flowStatus.equals("null"))
            flowStatus = null;
        fileentity.settCode(tCode);
        fileentity.settId(tId);
        fileentity.setFileflowstatus(flowStatus);
        fileentity.setFilecreateuser(createUserId);
        fileentity.setFilecreateuserName(createUserName);
        fileentity.setFilecreatedate(createTime);
        fileentity.setFilesectionid(sectionId);
        if (!ValidateUtil.isEmpty(pId))
            fileentity.setFileproject(pId);
        fileentity.setFileRelationId(relationId);
        return fileentity;
    }

    @Override
    public Boolean FileHelper(PreProject entity, String tId, String title, String code, String status, String relationId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getpId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getpId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(entity.getState() + "");
        fileentity.setFilecreateuser(entity.getpCreateUserId());
        fileentity.setFilecreateuserName(entity.getpCreateUserName());
        fileentity.setFilecreatedate(entity.getpCreateTime());
        fileentity.setFileproject(entity.getpId());
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;

    }

    @Override
    public Boolean FileHelper(PreProjectRegister entity, String tId, String title, String code, String status, String relationId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getPrId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getPrId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(entity.getPrStatus());
        fileentity.setFilecreateuser(entity.getPrApplicantId());
        fileentity.setFilecreateuserName(entity.getPrCreateUserName());
        fileentity.setFilecreatedate(entity.getPrCreateTime());
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;

    }

    @Override
    public Boolean FileHelper(PreProjectChangeName entity, String tId, String title, String code, String status, String relationId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getPcnId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getPcnId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(entity.getPcnFlowstate() == null ? "0" : entity.getPcnFlowstate().toString());
        fileentity.setFilecreateuser(entity.getPcnCreateUid());
        fileentity.setFilecreateuserName(entity.getPcnCreateUname());
        fileentity.setFilecreatedate(entity.getPcnCreateTime());
        fileentity.setFileproject(entity.getpId());
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;

    }

    @Override
    public Boolean FileHelper(PeInfo entity, String tId, String title, String code, String status, String relationId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getPeId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getPeId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(entity.getPeFlowState().toString());
        fileentity.setFilecreateuser(entity.getPeCreateUid());
        fileentity.setFilecreateuserName(entity.getPeCreateUname());
        fileentity.setFilecreatedate(entity.getPeCreateTime());
        fileentity.setFilecontract(entity.getPeConId());
        fileentity.setFileproject(entity.getPeProjectId());
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;

    }

    @Override
    public Boolean FileHelper(ProjectCpExperience entity, String tId, String title, String code, String status, String relationId, String cpId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getCpeId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getCpeId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        fileentity.setFilecreateuser(entity.getCpeCreateUid());
        fileentity.setFilecreateuserName(entity.getCpeCreateUname());
        fileentity.setFilecreatedate(entity.getCpeCreateTime());
        fileentity.setFilecontract(null);
        if (!ValidateUtil.isEmpty(cpId)) {
            ProjectCp cp = projectCpService.getById(cpId);
            fileentity.setFileproject(cp.getpId());
        }
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;

    }

    @Override
    public Boolean FileHelper(ProjectCpWorkRecord entity, String tId, String title, String code, String status, String relationId, String cpId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getCpwrId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getCpwrId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        fileentity.setFilecreateuser(entity.getCpwrCreateUid());
        fileentity.setFilecreateuserName(entity.getCpwrCreateUname());
        fileentity.setFilecreatedate(entity.getCpwrCreateTime());
        fileentity.setFilecontract(null);
        if (!ValidateUtil.isEmpty(cpId)) {
            ProjectCp cp = projectCpService.getById(cpId);
            fileentity.setFileproject(cp.getpId());
        }
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;
    }

    @Override
    public void FileHelper(PeInfoCollect entity, String tId, String title, String code, String status, String fileRelationId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getPecId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getPecId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(entity.getPecFlowState().toString());
        fileentity.setFilecreateuser(entity.getPecUid());
        fileentity.setFilecreateuserName(entity.getPecUname());
        fileentity.setFilecreatedate(entity.getPecDate());
        toCreateOrUpdateFile(fileentity, status, fileRelationId);
    }

    @Override
    public void FileHelper(ConContract entity, String tId, String title, String code, String status, String fileRelationId, String tCode) {
        SysFile fileentity = baseMapper.selectById(entity.getConId());
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(entity.getConId());
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        fileentity.settId(tId);
        fileentity.settCode(tCode);
        fileentity.setFileflowstatus(entity.getConApprovalState().toString());
        fileentity.setFilecreateuser(entity.getConCreateUserId());
        fileentity.setFilecreateuserName(entity.getConCreateUserName());
        fileentity.setFilecreatedate(entity.getConCreateTime());
        fileentity.setFilecontract(entity.getConId());
        fileentity.setFileproject(entity.getConProjectId());
        fileentity.setFilesectionid(entity.getConSectionId());
        toCreateOrUpdateFile(fileentity, status, fileRelationId);
    }

    @Override
    public void deleteByFileIds(List<String> ids) {
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public void onlyUpdateState(Integer state, String fileId) {
        baseMapper.onlyUpdateState(state, fileId);
    }

    @Override
    public void onlyUpdateState(Integer state, List<String> fileId) {
        if (!CollectionUtils.isEmpty(fileId)) return;
        baseMapper.onlyUpdateStateBath(state, fileId);
    }

    @Override
    public void createSecInfoFileByBath(List<SecInfoChangeRes> secInfos, String pId, String tId, String title, String code, String tCode, String relationId) {
        if (ValidateUtil.isNotEmpty(secInfos)) {
            List<SysFile> data = new ArrayList<>();
            secInfos.forEach(sec -> {
                SysFile fileentity = new SysFile();
                fileentity.setFileid(sec.getSecId());
                fileentity.setFiletitle(title);
                fileentity.setFilecode(code);
                fileentity.settCode(tCode);
                fileentity.settId(tId);
                //招标计划变更新增的所有标段信息默认流程已结束
                fileentity.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex() + "");
                fileentity.setFilecreateuser(sec.getSecCreateUid());
                fileentity.setFilecreateuserName(sec.getSecCreateUname());
                fileentity.setFilecreatedate(sec.getSecCreateTime());
                fileentity.setFilesectionid(sec.getSecId());
                if (!ValidateUtil.isEmpty(pId))
                    fileentity.setFileproject(pId);
                fileentity.setFileRelationId(ValidateUtil.isEmpty(relationId) ? GenerateUUID.getUUID() : relationId);
                data.add(fileentity);
            });
            saveBatch(data);
        }

    }

    @Override
    public void saveFileOrUpdate(String uuid, String title, String status, String userid, String userName, String fileCode, boolean flag) {
        SysFile sysFilePojo = new SysFile();
        sysFilePojo.setFiletitle(title);
        sysFilePojo.setFilecode(fileCode);
        sysFilePojo.setFileflowstatus(status);
        sysFilePojo.setFilecreateuser(userid);
        sysFilePojo.setFilecreateuserName(userName);
        sysFilePojo.setFilecreatedate(new Date());
        sysFilePojo.setFileid(uuid);
        if (flag) {
            this.save(sysFilePojo);
        } else {
            this.updateById(sysFilePojo);
        }

    }

    @Override
    public void updateFileStateBatchIds(List<String> fileIds, Integer state) {
        if (CollectionUtils.isEmpty(fileIds)) return;
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.in("fileid", fileIds);
        List<SysFile> files = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(files)) {
            List<SysFile> ups = files.stream().map(f -> {
                f.setFileflowstatus(state + "");
                return f;
            }).collect(Collectors.toList());
            updateBatchById(ups);
        }
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
        if (ValidateUtil.isNotEmpty(conId) && ValidateUtil.isEmpty(sectionId)) {
            ConContract con = conContractService.getById(conId);
            if (con != null)
                sectionId = con.getConSectionId();
        }
        fileentity.setFilesectionid(sectionId);
        fileentity.setFilecontract(conId);
        return fileentity;
    }

    private SysFile assembleEntityTeId(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId,
                                   String title, String code, String sectionId, String tCode, String conId,String teId) {
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
        fileentity.setTeId(teId);
        if (!ValidateUtil.isEmpty(pId))
            fileentity.setFileproject(pId);
        if (ValidateUtil.isNotEmpty(conId) && ValidateUtil.isEmpty(sectionId)) {
            ConContract con = conContractService.getById(conId);
            if (con != null)
                sectionId = con.getConSectionId();
        }
        fileentity.setFilesectionid(sectionId);
        fileentity.setFilecontract(conId);
        return fileentity;
    }

    private void toCreateOrUpdateFile(SysFile fileentity, String status, String fileRelationId) {
        //if (ValidateUtil.isAnObjEmpty(fileentity.gettCode(), fileentity.gettId(), fileentity.getFiletitle()))
        //    throw new JPMException(ResMsg.fileCUEroor);
        if (status.equals("create")) {
            fileentity.setFileRelationId(fileRelationId);
            baseMapper.insert(fileentity);
        } else if (status.equals("update")) {
            baseMapper.updateById(fileentity);
        } else {
            baseMapper.deleteById(fileentity.getFileid());
        }
    }


    @Override
    public <T, D extends BaseFileDto> boolean FileHelper(T entity,D request,String action,String fileRelationId){
       Integer state= ValidateUtil.isNotNull(request.getState()) ? request.getState() : ApprovalStatusEnum.Not_Started.getIndex();
       return saveFile((String) getFieldValue(entity, "id"), request.getTId(), state + ""
                , request.getCreateUid(), request.getCreateUname(), (Date) getFieldValue(entity, "createTime"),
                request.getPId(), request.getFileTitle(), request.getFileCode(), action, request.getFileRelationId()
                , request.getSecId(), request.getTCode(), request.getConId());
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

    @Override
    public void updatePId(SysFile sysFile) {
       if(ValidateUtil.isNotEmpty(sysFile.getFileid())){
           if(ValidateUtil.isNotEmpty(sysFile.getFileproject())){
               baseMapper.updateById(sysFile);
           }
       }
    }

    /**
     * 如果流程里的FileRelationId为null，给它赋值并返回
     *
     * @param id
     */
    @Override
    public String fixFileRelationId(String id) {
        SysFile sysFile = this.getById(id);
        if (ValidateUtil.isNull(sysFile)) {
            return "";
        }
        if (ValidateUtil.isNotEmpty(sysFile.getFileRelationId())) {
            return sysFile.getFileRelationId();
        }
        String newRelationId = GenerateUUID.getUUID();
        sysFile.setFileRelationId(newRelationId);
        this.updateById(sysFile);
        return newRelationId;
    }

    @Override
    public FileTemplateTree getFileTemplateByTCode(String tCode) {
        if (ValidateUtil.isEmpty(tCode)) {
            return null;
        }
        return baseMapper.getFileTemplateByTCode(tCode);
    }
}

