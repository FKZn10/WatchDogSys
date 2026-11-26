package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.SysFileMapper;
import com.jpmsoft.fileUpload.pojo.dto.FormDataExpDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysFile;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import com.jpmsoft.fileUpload.service.ISysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysFileImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Override
    public String executeSql(String sql) {
        return baseMapper.executeSql(sql);
    }

    @Override
    public List<SysFile> getUnJmArchive(int size) {
        if (size == 0) size = 100;
        if (size > 500) size = 500;
        return baseMapper.getUnJmArchive(size);
    }

    @Override
    public SysFile file(Map<String, String> data, SysFileTemplate temp, LogUserInfoVo user, String projectKey) {
        String pk = data.get(temp.gettPrimaryKey().toUpperCase(Locale.ENGLISH));
        SysFile fileentity = baseMapper.selectById(pk);
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(pk);
            fileentity.setFiletitle(temp.gettName());
            fileentity.setFilecode(temp.gettCode());
        }
        fileentity.settId(temp.gettId());
        fileentity.settCode(temp.gettCode());
        fileentity.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        fileentity.setFilecreateuser(user.getId());
        fileentity.setFilecreateusername(user.getName());
        fileentity.setFilecreatedate(new Date());
        fileentity.setFilecontract(null);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (ValidateUtil.isNotEmpty(projectKey)) {
                if (entry.getKey().equalsIgnoreCase(projectKey))
                    fileentity.setFileproject(entry.getValue());
            } else {
                if (entry.getKey().equalsIgnoreCase("P_ID"))
                    fileentity.setFileproject(entry.getValue());
            }
            if (entry.getKey().equalsIgnoreCase("SEC_ID"))
                fileentity.setFilesectionid(entry.getValue());
            if (entry.getKey().equalsIgnoreCase("CON_ID"))
                fileentity.setFilecontract(entry.getValue());
            if (entry.getKey().equalsIgnoreCase("FILERELATIONID"))
                fileentity.setFileRelationId(entry.getValue());

        }
        return fileentity;
    }

    @Override
    public void inertSql(List<String> sql) {
        if (!CollectionUtils.isEmpty(sql)){
            for (String s : sql) {
                baseMapper.insertFormDataOne(s);
            }
        }
        //baseMapper.insertFormData(sql);
    }

    @Override
    public Map<String, String> selectByImportSQL(String sql, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) return new HashMap<>();
        List<Map> data = baseMapper.selectByImportSQL(sql, codes);
        if (!CollectionUtils.isEmpty(data)) {
            Map<String, String> res = new HashMap<>();
            for (Map d : data) {
                res.put(d.get("acCode").toString(), d.get("id").toString());
            }
            return res;
        }
        return null;
    }

    @Override
    public List<Map> selectByTableColumn(List<SysTableColumn> columns, String tableCode, FormDataExpDTO req) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        StringBuilder sTable = new StringBuilder();
        sTable.append(" from ").append(tableCode).append(" mn");
        String pk = "";
        for (int i = 0; i < columns.size(); i++) {
            SysTableColumn c = columns.get(i);
            if (ValidateUtil.isNotEmpty(c.getColumnPk()) && c.getColumnPk().equals("1")) {
                pk = c.getColumnCode();
                continue;
            }
            //链表查询字段
            if (ValidateUtil.isNotNull(c.getFilterType()) && c.getFilterType().equals(2)) {
                String bm = "alj" + i;
                sTable.append(" left join ").append(c.getAssocTableCode()).append(" ").append(bm).append(" on ").append("mn.")
                        .append(c.getColumnCode()).append("=").append(bm).append(".").append(c.getAssocColumnPk());
                sb.append(bm).append(".").append(c.getAssocColumnCode()).append(" as ").append(c.getAssocColumnCode()).append(",");
                if (ValidateUtil.isNotEmpty(c.getAssocShowCode()))
                    sb.append(bm).append(".").append(c.getAssocShowCode()).append(" as ").append(c.getAssocShowCode()).append(",");
            } else
                sb.append("mn.").append(c.getColumnCode()).append(" as ").append(c.getColumnCode()).append(",");
        }
        if (ValidateUtil.isEmpty(pk)) throw new JPMException(FileMsg.noPk);
        sb.append("f.File_Relation_Id as fileRelationId ");
        sTable.append(" left join Sys_File f on f.FileId=mn.").append(pk);
        sb.append(sTable);
        if (!CollectionUtils.isEmpty(req.getPks())) {
            sb.append(" where mn.").append(pk).append(" in (");
            boolean start = true;
            for (String reqPk : req.getPks()) {
                if (start) {
                    sb.append("'");
                    start = false;
                } else
                    sb.append(",'");
                sb.append(reqPk).append("'");
            }
            sb.append(")");
        }
        return baseMapper.selectBySql(sb.toString());
    }

    @Override
    public List<Map> selectOnlyColumn(List<SysTableColumn> columns, SysFileTemplate temp) {
        if (CollectionUtils.isEmpty(columns)) return null;
        StringBuilder oldSql = new StringBuilder();
        oldSql.append(" select ");
        columns.forEach(o -> {
            oldSql.append(o.getColumnCode()).append(" as ").append(o.getColumnCode()).append(",");
        });
        oldSql.append("1 from ").append(temp.gettTable())
                .append(" where ").append(temp.gettStateKey()).append("!=3");
        return baseMapper.selectBySql(oldSql.toString());
    }

    @Override
    public void batchSaveFile(List<String> ids, List<String> tIds,
                              String flowStatus, List<String> createUids,
                              List<String> createUnames, List<Date> createTimes,
                              List<String> pIds, String title, Object code,
                              String status, List<String> relationIds,
                              Object sectionId, String tCode, List<String> conIds) {
        if (CollectionUtils.isEmpty(ids) || ids.size() == 0) {
            throw new JPMException("file id 为空");
        }
        List<SysFile> fileList = new ArrayList<>();
        int size = ids.size();
        for (int i = 0; i < size; i++) {
            String id = safeGet(ids, i);
            String tId = safeGet(tIds, i);
            String createUid = safeGet(createUids, i);
            String createUname = safeGet(createUnames, i);
            Date createTime = new Date();
            String pId = safeGet(pIds, i);
            String relId = safeGet(relationIds, i);
            String conId = safeGet(conIds, i);


            String secId = (sectionId instanceof List ? ((List<String>) sectionId).get(i) : (String) sectionId);

            SysFile fileEntity = assembleEntity(id, tId, flowStatus, createUid, createUname, createTime,
                    pId, title, tCode, secId, tCode, conId);

            fileList.add(fileEntity);
        }
        // 统一批量操作
        batchCreateOrUpdateFiles(fileList, status, relationIds);
    }
    private <T> T safeGet(List<T> list, int index) {
        if (list != null && index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public void batchCreateOrUpdateFiles(List<SysFile> fileList, String status, List<String> relationIds) {
        if (CollectionUtils.isEmpty(fileList)) {
            return;
        }

        if ("create".equals(status)) {
            for (int i = 0; i < fileList.size(); i++) {
                SysFile file = fileList.get(i);
                String relationId = safeGet(relationIds, i);
                file.setFileRelationId(relationId);
            }
            this.saveBatch(fileList);
        } else if ("update".equals(status)) {
            this.updateBatchById(fileList);
        } else {
            List<String> ids = fileList.stream()
                    .map(SysFile::getFileid)
                    .filter(id -> id != null && !id.isEmpty())
                    .collect(Collectors.toList());
            if (!ids.isEmpty()) {
                baseMapper.deleteBatchIds(ids);
            }
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
        fileentity.setFilecreateusername(createUserName);
        fileentity.setFilecreatedate(createTime);
        if (!ValidateUtil.isEmpty(pId))
            fileentity.setFileproject(pId);
        fileentity.setFilesectionid(sectionId);
        fileentity.setFilecontract(conId);
        return fileentity;
    }




}
