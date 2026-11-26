package com.jpmsoft.projectProducer.service.construction.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConStartApplyModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.construction.ConStartApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConStartApply;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.service.construction.IConStartApplyService;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConStartApplyImpl extends FileImpl<ConStartApplyMapper, ConStartApply, ConStartApplyModel> implements IConStartApplyService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IConContractService conContractService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConStartApply(ConStartApplyModel request) {
        ConStartApply entity = new ConStartApply();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getSaId());
    }

    @Override
    public BaseResponse getConStartApplyList(ConStartApplyModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Start_Apply", uId, "Sa_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Sa_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConStartApplyModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pNameHistory").put("saSupervisorName")
                .put("saSupervisorNameHistory").put("saContractorName").put("saContractorNameHistory")
                .put("saRemark").put("saTitle").put("saState").put("saCreateUname");
        IPage<ConStartApplyModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConStartApplyModel> datas = baseMapper.getConStartApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConStartApplyById(String saId) {
        return BaseResponse.ok(baseMapper.getConStartApplyById(saId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncDataToContract(String saId) {
        if (ValidateUtil.isEmpty(saId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConStartApply conStartApply = this.getById(saId);
        if (ValidateUtil.isNull(conStartApply)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(conStartApply.getSaState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 查询文件
        SysFile file = sysFileService.getById(saId);
        if (ValidateUtil.isNull(file)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<ConContract> contracts = conContractService.list(Wrappers.<ConContract>lambdaQuery()
                .eq(ConContract::getConProjectId, conStartApply.getPId())
                .eq(ConContract::getConType, "01")
                .eq(ConContract::getConApprovalState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        if (ValidateUtil.isNotEmpty(contracts)) {
            String sourceRelationId = file.getFileRelationId();
            if (ValidateUtil.isEmpty(sourceRelationId)) {
                return BaseResponse.ok();
            }
            List<String> conStartsTos = contracts.stream()
                    .map(ConContract::getConStartsTo)
                    .filter(ValidateUtil::isNotEmpty)
                    .collect(Collectors.toList());
            conStartsTos.add(sourceRelationId);
            List<SysFileannex> fileannexes = sysFileAnnexService.list(Wrappers.<SysFileannex>lambdaQuery()
                    .in(SysFileannex::getFileRelationId, conStartsTos)
            );
            if (ValidateUtil.isEmpty(fileannexes)) {
                return BaseResponse.ok();
            }
            Map<String, List<SysFileannex>> fileannexMap = fileannexes.stream()
                    .filter(item -> ValidateUtil.isNotEmpty(item.getFileRelationId()))
                    .collect(Collectors.groupingBy(SysFileannex::getFileRelationId));
            List<SysFileannex> sourceFileannexes = fileannexMap.get(sourceRelationId);
            if (ValidateUtil.isEmpty(sourceFileannexes)) {
                return BaseResponse.ok();
            }

            // 同步数据
            for (ConContract contract : contracts) {
                contract.setConStartDate(conStartApply.getSaDate());
                String conStartsTo = contract.getConStartsTo();
                if (ValidateUtil.isEmpty(conStartsTo)) {
                    conStartsTo = GenerateUUID.getUUID();
                    contract.setConStartsTo(conStartsTo);
                }
                // 复制文件
                List<SysFileannex> copyFileannexes = getNeedCopyFileannexes(sourceFileannexes, fileannexMap.get(conStartsTo), conStartsTo, contract.getConId());
                if (ValidateUtil.isNotEmpty(copyFileannexes)) {
                    BaseResponse response = sysFileAnnexService.copyFileWithFileAnnex(copyFileannexes, true);
                    if (ValidateUtil.isNull(response) || !response.getSuccess().equals(Boolean.TRUE) || response.getStatusCode() != HttpURLConnection.HTTP_OK) {
                        throw new JPMException(ResMsg.fileSaveRefershError);
                    }
                }
            }
            conContractService.updateBatchById(contracts);
        }
        return BaseResponse.ok();
    }

    private List<SysFileannex> getNeedCopyFileannexes(List<SysFileannex> sourceFileannexes,
                                                      List<SysFileannex> contractFileannexes,
                                                      String conStartsTo,
                                                      String conId) {
        Set<String> existsMd5Codes = ValidateUtil.isEmpty(contractFileannexes) ? new HashSet<>() : contractFileannexes.stream()
                                                                                                   .map(this::getMd5Code)
                                                                                                   .filter(ValidateUtil::isNotEmpty)
                                                                                                   .collect(Collectors.toSet());
        List<SysFileannex> copyFileannexes = new ArrayList<>();
        for (SysFileannex sourceFileannex : sourceFileannexes) {
            String md5Code = getMd5Code(sourceFileannex);
            if (ValidateUtil.isNotEmpty(md5Code) && existsMd5Codes.contains(md5Code)) {
                continue;
            }
            copyFileannexes.add(copyFileannex(sourceFileannex, conStartsTo, conId));
            if (ValidateUtil.isNotEmpty(md5Code)) {
                existsMd5Codes.add(md5Code);
            }
        }
        return copyFileannexes;
    }

    private String getMd5Code(SysFileannex fileannex) {
        if (ValidateUtil.isNull(fileannex) || ValidateUtil.isEmpty(fileannex.getFileMd5Code())) {
            return null;
        }
        return fileannex.getFileMd5Code().toLowerCase(Locale.ROOT);
    }

    private SysFileannex copyFileannex(SysFileannex sourceFileannex, String fileRelationId, String entityId) {
        SysFileannex targetFileannex = new SysFileannex();
        targetFileannex.setFileId(sourceFileannex.getFileId());
        targetFileannex.setFileRelationId(fileRelationId);
        targetFileannex.setFilePath(sourceFileannex.getFilePath());
        targetFileannex.setFileSize(sourceFileannex.getFileSize());
        targetFileannex.setFileSuffix(sourceFileannex.getFileSuffix());
        targetFileannex.setFileName(sourceFileannex.getFileName());
        targetFileannex.setFileUploadTime(sourceFileannex.getFileUploadTime());
        targetFileannex.setFileCreateUId(sourceFileannex.getFileCreateUId());
        targetFileannex.setFileCreateUName(sourceFileannex.getFileCreateUName());
        targetFileannex.setFileCode(sourceFileannex.getFileCode());
        targetFileannex.setTableName(sourceFileannex.getTableName());
        targetFileannex.setTableCode(sourceFileannex.getTableCode());
        targetFileannex.setColumnName(sourceFileannex.getColumnName());
        targetFileannex.setColumnCode(sourceFileannex.getColumnCode());
        targetFileannex.setUploadStatus(sourceFileannex.getUploadStatus());
        targetFileannex.setArchive(sourceFileannex.getArchive());
        targetFileannex.setFileLocation(sourceFileannex.getFileLocation());
        targetFileannex.setFileUnarchiveId(sourceFileannex.getFileUnarchiveId());
        targetFileannex.setFileReplace(sourceFileannex.getFileReplace());
        targetFileannex.setFileEntityId(entityId);
        targetFileannex.setFileMd5Code(sourceFileannex.getFileMd5Code());
        return targetFileannex;
    }
}
