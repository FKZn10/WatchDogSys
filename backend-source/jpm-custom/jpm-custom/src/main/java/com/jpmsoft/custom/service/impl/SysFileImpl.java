package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SysSplitTableDtlDTO;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.mapper.SysFileMapper;
import com.jpmsoft.custom.pojo.entity.ConContract;
import com.jpmsoft.custom.pojo.entity.SysFile;
import com.jpmsoft.custom.service.IConContractService;
import com.jpmsoft.custom.service.ISysConfigService;
import com.jpmsoft.custom.service.ISysFileService;
import com.jpmsoft.custom.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysFileImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

    @Resource
    private IConContractService conContractService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public SysFile getfile(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Integer sql2Insert(String sql) {
        return baseMapper.sql2Insert(sql);
    }

    @Override
    public Integer insertBath(List<String> inserts) {
        if (CollectionUtils.isEmpty(inserts)) return null;
        for (String insert : inserts) {
            //mysql不支持批量insert into
            baseMapper.sql2Insert(insert);
        }
        return 1;
    }

    @Override
    public Integer sql2Upate(String sql) {
        return baseMapper.sql2Upate(sql);
    }

    @Override
    public Integer deleteBath(List<String> deletes) {
        if (CollectionUtils.isEmpty(deletes)) return null;
        for (String delete : deletes) {
            //mysql不支持批量insert into
            baseMapper.sql2Delete(delete);
        }
        return 1;
    }

    @Override
    public Map<String, Object> querySql(String sql) {
        if (ValidateUtil.isEmpty(sql)) return null;
        return baseMapper.querySql(sql);
    }

    @Override
    public List<Map<String, Object>> queryList(String sql) {
        if (ValidateUtil.isEmpty(sql)) return null;
        return baseMapper.queryList(sql);
    }

    @Override
    public BaseResponse queryPage(String sql, String sumSql, Map<String, Object> request) {
        int start = 0;
        int pageSize = 10;
        if (ValidateUtil.isNotNull(request)) {
            if (request.containsKey("size")) pageSize = Integer.parseInt(request.get("size").toString());
            if (request.containsKey("page")) start = (Integer.parseInt(request.get("page").toString()) - 1) * pageSize;
//            if (request.containsKey("sortFiled"))
//                request.put("sortFiled", humpTurnDatabase(request.get("sortFiled") + ""));
        }
        List<Map<String, Object>> data = baseMapper.queryPage(sql, request, start, pageSize);
        int size = 0;
        //如果结果集为空则无需查询数量
        if (!CollectionUtils.isEmpty(data))
            size = baseMapper.queryPageSize(sql);
        if (ValidateUtil.isNotEmpty(sumSql)) {//统计查询
            Map<String, Object> returnData = new HashMap<>();
            Map<String, Object> res = baseMapper.sumQuery(sumSql);
            returnData.put("stats", res);//统计对象
            returnData.put("data", data);//list对象
            return BaseResponse.ok(returnData, size);
        } else
            return BaseResponse.ok(data, size);
    }

    @Override
    public Boolean havingSysFile(String id) {
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.eq("FileId", id);
        if (baseMapper.selectCount(wrapper) > 0) return true;
        return false;
    }

    @Override
    public void deleteFileById(String id) {
        if (!ValidateUtil.isEmpty(id)) {
            baseMapper.deleteById(id);
        }
    }


    @Override
    public Boolean saveFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String status, String relationId, String sectionId, String tCode, String conId) {
        SysFile fileentity = assembleEntity(id, tId, flowStatus, createUserId, createUserName, createTime, pId, title, code, sectionId, tCode, conId, status);
        toCreateOrUpdateFile(fileentity, status, relationId);
        return true;
    }

    @Override
    public SysFile structureFile(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String relationId, String sectionId, String tCode) {
        SysFile fileentity = new SysFile();
        fileentity.setFileid(id);
        fileentity.setFiletitle(title);
        fileentity.setFilecode(code);
        if (flowStatus != null && flowStatus.equals("null")) flowStatus = null;
        fileentity.settCode(tCode);
        fileentity.settId(tId);
        fileentity.setFileflowstatus(flowStatus);
        fileentity.setFilecreateuser(createUserId);
        fileentity.setFilecreateuserName(createUserName);
        fileentity.setFilecreatedate(createTime);
        fileentity.setFilesectionid(sectionId);
        if (!ValidateUtil.isEmpty(pId)) fileentity.setFileproject(pId);
        fileentity.setFileRelationId(relationId);
        return fileentity;
    }

    @Override
    public void deleteByFileIds(List<String> ids) {
        baseMapper.deleteBatchIds(ids);
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
                if (!ValidateUtil.isEmpty(pId)) fileentity.setFileproject(pId);
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

    private SysFile assembleEntity(String id, String tId, String flowStatus, String createUserId, String createUserName, Date createTime, String pId, String title, String code, String sectionId, String tCode, String conId, String status) {
        SysFile fileentity = baseMapper.selectById(id);
        if (fileentity == null) {
            fileentity = new SysFile();
            fileentity.setFileid(id);
            fileentity.setFiletitle(title);
            fileentity.setFilecode(code);
        }
        if (flowStatus != null && flowStatus.equals("null")) flowStatus = null;
        fileentity.settCode(tCode);
        fileentity.settId(tId);
        fileentity.setFileflowstatus(flowStatus);
        if (status.equalsIgnoreCase("create")) {
            //仅创建时赋值创建人信息
            fileentity.setFilecreateuser(createUserId);
            fileentity.setFilecreateuserName(createUserName);
            fileentity.setFilecreatedate(createTime);
        }
        if (!ValidateUtil.isEmpty(pId)) fileentity.setFileproject(pId);
        if (ValidateUtil.isNotEmpty(conId) && ValidateUtil.isEmpty(sectionId)) {
            ConContract con = conContractService.getById(conId);
            if (con != null && ValidateUtil.isEmpty(con.getConSectionId())) sectionId = con.getConSectionId();
        }
        fileentity.setFilesectionid(sectionId);
        fileentity.setFilecontract(conId);
        return fileentity;
    }

    private void toCreateOrUpdateFile(SysFile fileentity, String status, String fileRelationId) {
        //if (ValidateUtil.isAnObjEmpty(fileentity.gettCode(), fileentity.gettId(), fileentity.getFiletitle()))
        //    throw new JPMException(ResMsg.fileCUEroor);
//      todo  RequestDataHelper.setRequestData(getSplitTableCode(fileentity.gettCode()));
        if (status.equalsIgnoreCase("create")) {
            fileentity.setFileRelationId(ValidateUtil.isEmpty(fileRelationId) ? GenerateUUID.getUUID() : fileRelationId);
            baseMapper.insert(fileentity);
        } else if (status.equalsIgnoreCase("update")) {
            baseMapper.updateById(fileentity);
        } else {
            baseMapper.deleteById(fileentity.getFileid());
        }
    }

    public String getSplitTableCode(String code) {
        String enable = sysConfigService.getConfigByCode("bpmSplitTableEnable");
        if (ValidateUtil.isEmpty(enable) || !enable.equals("1")) return null;
        Object obj = redisUtils.get("bpmSplitTableInfo");
        if (ValidateUtil.isNull(obj)) {
            List<SysSplitTableDtlDTO> data = baseMapper.getAllSysSplitTableDtlDTO();
            if (!CollectionUtils.isEmpty(data)) {
                Map<String, String> map = new HashMap<>();
                data.forEach(d -> {
                    if (ValidateUtil.isNotEmpty(d.getTCode())) map.put(d.getTCode(), d.getStCode());
                    if (ValidateUtil.isNotEmpty(d.getTableCode())) map.put(d.getTableCode(), d.getStCode());
                });
                redisUtils.set("bpmSplitTableInfo", JacksonUtils.toJSONString(map));
                return map.getOrDefault(code, null);
            } else
                return null;
        } else {
            Map<String, String> map = JacksonUtils.parseObject(obj.toString(), Map.class);
            if (map != null)
                return map.getOrDefault(code, null);
            else return null;
        }
    }

    /**
     * 驼峰转数据库字段
     *
     * @param str
     * @return
     */
    private String humpTurnDatabase(String str) {
        if (ValidateUtil.isEmpty(str))
            return null;
        //cusomer调用producer会调用get set
        str = str.replaceAll("_", "");
        char[] letters = str.toCharArray();
        String filed = "";
        for (char letter : letters) {
            if (letter >= 'A' && letter <= 'Z') {
                filed += "_" + letter;
            } else
                filed += letter;
        }
        return filed;
    }
}

