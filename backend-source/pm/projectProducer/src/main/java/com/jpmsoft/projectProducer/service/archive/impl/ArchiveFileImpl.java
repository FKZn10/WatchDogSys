package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Archive.ArchiveFileRequest;
import com.jpmsoft.common.model.request.Archive.FileServerToArchiveRequest;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveFileMapper;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFile;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.archive.IArchiveBorrowService;
import com.jpmsoft.projectProducer.service.archive.IArchiveFileService;
import com.jpmsoft.projectProducer.service.archive.IArchiveFileViewService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArchiveFileImpl extends ServiceImpl<ArchiveFileMapper, ArchiveFile> implements IArchiveFileService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IArchiveBorrowService archiveBorrowService;
    @Resource
    private IArchiveFileViewService archiveFileViewService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateArchiveFile(List<ArchiveFileRequest> datas, String apId, Integer apState, String fileCodes, String uId, String uName, String teId) {
        if (CollectionUtils.isEmpty(datas)) {
            archiveBorrowService.delArchiveBorrowByApId(apId);
            archiveFileViewService.delArchiveFileViewByApId(apId);
            deleteAllArchiveFileByApId(apId);
            return;
        }
        ArchiveFileRequest one = datas.get(0);
        BaseResponse baseResponse = new BaseResponse();
        if (one.getAfFrom() == 1) {
            //拷贝文件归档
            FileServerToArchiveRequest files = new FileServerToArchiveRequest();
            files.setDatas(datas);
            files.setApId(apId);
            files.setFileCode(fileCodes);
            files.setApState(apState);
            baseResponse = sysFileAnnexService.archiveFile(files);
        }
        if (baseResponse == null)
            throw new JPMException(ResMsg.archiveError);
        List<ArchiveFileRequest> toChangeData = new ArrayList<>();
        //能操作此步骤时 说明文件未归档。所以不存在借阅记录（外键表）所以可以直接移除
        deleteAllArchiveFileByApId(apId);
        if (baseResponse.getData() == null)
            toChangeData = datas;
        else {
            List<ArchiveFileRequest> fileReturn = JacksonUtils.parseObject(JacksonUtils.toJSONString(baseResponse.getData()), new TypeReference<List<ArchiveFileRequest>>() {
            });
            //筛选重叠部分
            for (ArchiveFileRequest data : datas) {
                List<ArchiveFileRequest> overlap = fileReturn.stream().filter(n -> n.getAfId().equals(data.getAfId())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(overlap)) {
                    toChangeData.add(data);
                }
            }
            toChangeData.addAll(fileReturn);
        }
        if (!CollectionUtils.isEmpty(toChangeData)) {
            List<ArchiveFile> entitys = new ArrayList<>();
            List<String> fileIds=new ArrayList<>();
            toChangeData.forEach(data -> {
                ArchiveFile entity = new ArchiveFile();
                BeanUtils.copyProperties(data, entity);
                entity.setAfCreateTime(data.getAfCreateTime() == null ? new Date() : data.getAfCreateTime());
                entity.setApId(apId);
                entity.setAfState(apState);
                entity.setAfCreateUid(uId);
                entity.setAfCreateUname(uName);
                entity.setTeId(teId);
                fileIds.add(data.getAfAnnexId());
                entitys.add(entity);
            });

            if (entitys.size() > 0) {
                saveBatch(entitys);
            }
        }
    }


    @Override
    public void deleteAllArchiveFileByApId(String apId) {
        QueryWrapper<ArchiveFile> wrapper = new QueryWrapper<>();
        wrapper.eq("Ap_Id", apId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ArchiveFileResponse> getArchiveFileByApIds(List<String> apIds) {
        if (apIds != null && apIds.size() > 0)
            return baseMapper.getArchiveFileByApIds(apIds);
        else
            return new ArrayList<>();
    }

    /**
     * 按权限及项目内 MD5 去重规则查询文件统计，分别执行精简计数及分页查询。
     * 空结果或超出末页时跳过明细查询，避免执行无效的展示关联。
     *
     * @param request 文件筛选及分页条件
     * @param uId     当前用户 ID
     * @return 当前页文件及总数
     */
    @Override
    public BaseResponse getArchiveFileStatistics(ArchiveStatisticsModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Archive_File", uId, "Af_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        return queryArchiveFileStatistics(request, authSql, null, null);
    }

    /**
     * 按目录授权和附件创建人的同部门关系逐条统计归档附件。
     *
     * @param request 文件筛选及分页条件
     * @param user    当前登录用户
     * @return 当前页文件及总数
     */
    @Override
    public BaseResponse getArchiveFileStatisticsWZ(ArchiveStatisticsModel request, LogUserInfoVo user) {
        if (user == null || ValidateUtil.isEmpty(user.getId())
                || (user.isEnableTenant() && ValidateUtil.isEmpty(user.getTeId()))) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        List<String> directoryIds = getWzVisibleDirectoryIds(user);
        if (directoryIds.isEmpty()) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        return queryArchiveFileStatistics(request, null, user, directoryIds);
    }

    /**
     * 从本级授权通过的目录向下传播权限，遇到有独立配置或租户不匹配的目录时停止。
     *
     * @param user 当前登录用户
     * @return 可查看的目录 ID
     */
    private List<String> getWzVisibleDirectoryIds(LogUserInfoVo user) {
        Map<String, List<String>> inheritingChildren = new HashMap<>();
        Set<String> visibleIds = new LinkedHashSet<>();
        for (ArchiveFileMapper.DirectoryAccess directory : baseMapper.getWzDirectoryAccess(user)) {
            if (directory.getAccessState() == ArchiveFileMapper.DirectoryAccess.GRANTED) {
                visibleIds.add(directory.getAdId());
            } else if (directory.getAccessState() == ArchiveFileMapper.DirectoryAccess.INHERIT) {
                inheritingChildren.computeIfAbsent(directory.getParentId(), parent -> new ArrayList<>())
                        .add(directory.getAdId());
            }
        }
        Deque<String> pending = new ArrayDeque<>(visibleIds);
        while (!pending.isEmpty()) {
            for (String child : inheritingChildren.getOrDefault(pending.removeFirst(), Collections.emptyList())) {
                if (visibleIds.add(child)) {
                    pending.addLast(child);
                }
            }
        }
        return new ArrayList<>(visibleIds);
    }

    /**
     * 复用统计筛选、计数和分页查询，根据调用入口应用对应的权限。
     *
     * @param request 文件筛选及分页条件
     * @param authSql 原统计接口的权限条件
     * @param wzUser  WZ 接口当前登录用户，原接口传空
     * @param directoryIds WZ 可查看的目录，原接口传空
     * @return 当前页文件及总数
     */
    private BaseResponse queryArchiveFileStatistics(ArchiveStatisticsModel request, String authSql,
                                                    LogUserInfoVo wzUser, List<String> directoryIds) {
        QueryMap<ArchiveStatisticsModel> params = new QueryMap<>(request);
        if (wzUser != null) {
            params.put("wzUser", wzUser);
            params.put("wzDirectoryIds", directoryIds);
            params.put("teId", wzUser.isEnableTenant() ? wzUser.getTeId() : null);
            // 排序字段会作为 SQL 标识符拼接，仅接受字段名及可选的表别名。
            if (request.getSortFiled() != null && !request.getSortFiled().matches("[A-Za-z_][A-Za-z0-9_]*")) {
                params.put("sortFiled", null);
            }
            if (request.getSortSideFiled() != null
                    && !request.getSortSideFiled().matches("(?:[A-Za-z_][A-Za-z0-9_]*[.])?[A-Za-z_][A-Za-z0-9_]*")) {
                params.put("sortSideFiled", null);
            }
        }
        params.put("authSql", authSql).put("afFrom").put("adId").put("afName").put("afCode").put("afState").put("adLink")
                .put("afType").put("pName").put("pId").put("pCode").put("adCode").put("adName").put("apTitle")
                .put("apAuthor").put("pBuildModel").put("apKeyword").put("apPosition").put("apDocumentNo").put("apIssueCompany")
                .put("pBuildModel").put("pType").put("pUseUnit").put("pImpCompany").put("pCompetentDepartment").put("apState")
                .put(request.getAfFileSizes(), "afFileSizeStart", "afFileSizeEnd").put("adType").put("retrieval")
                .put(request.getpConstructionStarts(), "pConstructionStartStart", "pConstructionStartEnd")
                .put(request.getpConstructionEnds(), "pConstructionEndStart", "pConstructionEndEnd")
                .put(request.getApApprovalDates(), "apApprovalDateStart", "apApprovalDateEnd")
                .put(request.getApReceiptTimes(), "apReceiptTimeStart", "apReceiptTimeEnd")
                .put(request.getAfCreateTimes(), "afCreateTimeStart", "afCreateTimeEnd");
        IPage<ArchiveStatisticsModel> page = new Page<>(request.getPage(), request.getSize(), false);
        long total = baseMapper.getArchiveFileStatisticsCount(params.getQuery());
        page.setTotal(total);
        if (total == 0 || (page.getSize() > 0 && (page.getCurrent() - 1) * page.getSize() >= total)) {
            return BaseResponse.ok(new ArrayList<ArchiveStatisticsModel>(), (int) total);
        }
        List<ArchiveStatisticsModel> datas = baseMapper.getArchiveFileStatistics(page, params.getQuery());
        return BaseResponse.ok(datas, (int) total);
    }

    @Override
    public BaseResponse updateArchiveFileState(String id, Integer state) {
        ArchiveFile file = baseMapper.selectById(id);
        if (file == null) return BaseResponse.fail(ResMsg.infoNotExist);
        file.setAfState(state);
        baseMapper.updateById(file);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteArchiveFileById(ArchiveStatisticsModel request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getAfId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ArchiveFile file = this.getById(request.getAfId());
        if (ValidateUtil.isNull(file)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (file.getAfState() != null && file.getAfState() == 1) {
            return BaseResponse.fail("当前文件状态不允许删除");
        }
        this.removeById(request.getAfId());
        return BaseResponse.ok();
    }
}
