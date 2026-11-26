package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.ArchiveFileRequest;
import com.jpmsoft.common.model.request.ImgCompressAndRefreshStateDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.enums.FileSourceEnum;
import com.jpmsoft.fileUpload.mapper.SysFileAnnexMapper;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.entity.SysFile;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.pojo.entity.TaskProcessin;
import com.jpmsoft.fileUpload.pojo.vo.*;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysFileAnnexImpl extends ServiceImpl<SysFileAnnexMapper, SysFileAnnex> implements ISysFileAnnexService {

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ISysFileTemplateService sysFileTemplateService;

    @Resource
    private ISecProjectService secProjectService;

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ITaskProcessinService taskProcessinService;
    @Resource
    private ISysFileAnnexService owner;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Override
    public void insert(SysFileAnnex sysFileAnnexPojo, int status) {
        //标记未上传
        if (status == 1) {
            OssClientParamsDto conf = sysConfigService.getOssParams();
            String root = sysConfigService.getRootDirectory();
            //桶内目录
            if (conf != null) {
                String dir = conf.getOssRootDir() + "/" + sysFileAnnexPojo.getFilePath().substring(0, sysFileAnnexPojo.getFilePath().lastIndexOf("/") + 1);
                String key = CrcOssUtil.upload(conf, root + sysFileAnnexPojo.getFilePath(), dir);
                if (ValidateUtil.isNotEmpty(key)) sysFileAnnexPojo.setFileUpOss(Boolean.TRUE);
            } else sysFileAnnexPojo.setFileUpOss(Boolean.FALSE);
        }
        sysFileAnnexPojo.setUploadStatus(status);
        sysFileAnnexPojo.setFileUpOss(Boolean.FALSE);
        sysFileAnnexPojo.setFileSourceType(FileSourceEnum.UserUpload.getIndex());
        baseMapper.insert(sysFileAnnexPojo);
    }

    @Override
    public BaseResponse getSystemFileList(SystemFileQueryDto query, String uId) {
        String teId = redisUtils.getTeIdByUid(uId);
        IPage<SysFileAnnex> page = new Page<>(query.getPage(), query.getSize());
        QueryMap<SystemFileQueryDto> params = new QueryMap<>(query);
        params.put("tableCode").put("pId").put("conId").put("secId").put("uploadUid").put("fileName")
                .put("teId", teId).put("pName").put("fileCreateUName").put("fileSuffixes");
        List<ProFileAnnex> files = baseMapper.getSystemFileList(page, params.getQuery());
        List<SystemFileVo> datas = new ArrayList<>();
        List<DownloadUrlDto> downs = fileUploadService.downloadAloneBath(files);
        files.forEach(model -> {
            SystemFileVo file = new SystemFileVo();
            BeanUtils.copyProperties(model, file);
            file.setUid(model.getFileId());
            file.setUrl(downs.stream().filter(n -> n.getFileIds().equals(model.getFileId())).findFirst().get().getUrl());
            file.setName(model.getFileName());
            file.setRelationid(model.getFileRelationId());
            file.setUploadTime(model.getFileUploadTime());
            datas.add(file);
        });
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse uploadSystemFile(SystemUploadDto request) {
        String root = sysConfigService.getRootDirectory();
        String downloadUrl = sysConfigService.getDownloadUrl();
        if (ValidateUtil.isEmpty(root) || ValidateUtil.isEmpty(downloadUrl))
            return BaseResponse.fail(FileMsg.missSysConfig);
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.in("file_id", request.getFileIds());
        List<SysFileAnnex> datas = baseMapper.selectList(wrapper);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<FileResVo> results = new ArrayList<>();
            List<SysFileAnnex> news = new ArrayList<>();
            Date now = new Date();
            news = datas.stream().map(data -> {
                if (!FileUtil.isFile(root + data.getFilePath()))
                    throw new JPMException(String.format(FileMsg.notFoundFile, data.getFileName()));
                String oldId = data.getFileId();

                //region 复制记录
                data.setFileId(GenerateUUID.getUUID());
                data.setFileCreateUId(request.getCreateUid());
                data.setFileCreateUName(request.getCreateUname());
                data.setFileRelationId(request.getFileRelationId());
                data.setTableCode(request.getTableCode());
                data.setTableName(request.getTableName());
                data.setColumnCode(request.getColumnCode());
                data.setColumnName(request.getColumnName());
                data.setFileNotRemove(request.isFileNotRemove());
                data.setFileSourceType(FileSourceEnum.SystemFile.getIndex());
                data.setFileSourceId(oldId);
                data.setFileUploadTime(now);
                data.setUploadStatus(0);
                data.setArchive(0);
                data.setFileReplace(0);
                data.setFileLocation(0);
                //endregion

                //region 响应文件信息
                FileResVo res = new FileResVo();
                res.setUid(data.getFileId());
                String url = fileUploadService.breakpointDownload(oldId);
                //先使用选择的历史文件来生成预览url
                res.setUrl(downloadUrl + url);
                res.setNoHostUrl("/fileServer" + url);
                res.setName(data.getFileName());
                res.setFileSuffix(data.getFileSuffix());
                res.setFileSize(data.getFileSize());
                res.setUploadTime(now);
                res.setRelationid(request.getFileRelationId());
                res.setFileInputCode(data.getFileInputCode());
                res.setFileInputName(data.getFileInputName());
                res.setFileInputTime(data.getFileInputTime());
                res.setFileInputVersion(data.getFileInputVersion());
                res.setFileNotRemove(data.isFileNotRemove());
                results.add(res);
                //endregion

                data.setFileHistoryId(null);
                data.setFileIsHistory(0);

                return data;
            }).collect(Collectors.toList());
            saveBatch(news);
            return BaseResponse.ok(FileMsg.uploadSuccess, results);
        }
        return BaseResponse.ok();
    }

    /**
     * 识别文件是删除还是新增
     *
     * @param fileRelationId
     * @param fileIds
     * @param delFileIds
     * @param delFiles
     * @param saveFiles
     */
    private void differentiateFiles(String fileRelationId, String fileIds, List<String> delFileIds, List<SysFileAnnex> delFiles, List<SysFileAnnex> saveFiles) {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("file_relation_id", fileRelationId);
        List<SysFileAnnex> list = baseMapper.selectList(wrapper);
        if (!ValidateUtil.isEmpty(fileIds)) {
            String[] files = fileIds.split(",");
            list.forEach(model -> {
                boolean flag = true;
                if (model.isFileNotRemove()) flag = false; //标记不允许删除
                else {
                    for (String fileId : files) {
                        if (fileId.equals(model.getFileId())) flag = false;
                    }
                }
                if (flag) {
                    delFileIds.add(model.getFileId());
                    delFiles.add(model);
                } else {
                    saveFiles.add(model);
                }
            });
        } else {//fileIds为空 视为全部删除
            list.forEach(model -> {
                if (!model.isFileNotRemove()) {//标记可删除的全部删除
                    delFileIds.add(model.getFileId());
                    delFiles.add(model);
                }
            });
        }
    }


    private void removeFiles(List<String> relationIds, String root) {
        if (CollectionUtils.isEmpty(relationIds)) return;
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.in("File_relation_id", relationIds);
        List<SysFileAnnex> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            delFileRecordAndEntityFile(root, data.stream().map(SysFileAnnex::getFileId).collect(Collectors.toList()), data);
    }


    /**
     * 删除文件记录和实体文件
     *
     * @param root
     * @param delFileIds
     * @param delFiles
     */
    private void delFileRecordAndEntityFile(String root, List<String> delFileIds, List<SysFileAnnex> delFiles) {
        if (delFileIds.size() > 0) baseMapper.deleteBatchIds(delFileIds);
        try {
            OssClientParamsDto conf = sysConfigService.getOssParams();
            //删除文件  删除本地文件异常不返回错误(不影响整体业务)
            for (SysFileAnnex entity : delFiles) {
                if (entity != null) {
                    String filePath = root + "/" + entity.getFilePath();
                    String ossKey = conf == null ? null : conf.getOssRootDir() + "/" + entity.getFilePath();
                    File file = new File(filePath);
                    if (file.exists() && file.isFile())
                        if (ValidateUtil.isNotEmpty(entity.getFileSourceType()) && entity.getFileSourceType().equals(FileSourceEnum.SystemFile.getIndex())) {
                            //系统关联类型 未确认上传之前的文件地址指向的是源文件
                            if (entity.getUploadStatus() == 1) {
                                file.delete();
                                if (entity.isFileUpOss()) CrcOssUtil.deleteFile(conf, ossKey);
                            }
                        } else {
                            file.delete();
                            if (entity.isFileUpOss()) CrcOssUtil.deleteFile(conf, ossKey);
                        }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse refreshRelationFile(List<Map<String, String>> data, String entityId) {
        refreshFileRelation(data, entityId, null);
        return BaseResponse.ok();
    }

    public List<SysFileAnnex> refreshFileRelation(List<Map<String, String>> data, String entityId, String root) {
        if (ValidateUtil.isEmpty(root)) {
            root = sysConfigService.getRootDirectory();
            if (root == null) throw new JPMException(FileMsg.missSysConfig);
        }
        //region 甄别需要删除的文件以及需要刷新的文件
        List<String> delFileIds = new ArrayList<>();
        List<SysFileAnnex> delFiles = new ArrayList<>();
        List<SysFileAnnex> saveFiles = new ArrayList<>();
        for (Map<String, String> map : data) {
            String fileRelationId = map.get("fileRelationId");
            String fileIds = map.get("fileIds");
            //两个值其中一个为空则不操作附件
            if (!ValidateUtil.isAnObjEmpty(fileRelationId, fileIds)) {
                //前端附件组件获取失败ids传参false
                if (!fileIds.equalsIgnoreCase("false")&&!fileIds.equalsIgnoreCase("undefined"))
                    //fileIds传入deleteAll标识删除所有附件
                    differentiateFiles(fileRelationId, fileIds.equalsIgnoreCase("deleteAll") ? null : fileIds, delFileIds, delFiles, saveFiles);
            }
        }
        //endregion
        //region 批量更新上传状态或拷贝文件
        List<SysFileAnnex> res = null;
        if (saveFiles.size() > 0) res = refreshStatusAndCopyFile(saveFiles, entityId, root);
        //endregion
        //region    删除多余记录以及实体文件
        delFileRecordAndEntityFile(root, delFileIds, delFiles);
        //endregion
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse refreshRelationFileBath(List<RefershFileDto> datas) {
        String root = sysConfigService.getRootDirectory();
        if (root == null) return BaseResponse.fail(FileMsg.missSysConfig);
        //region 甄别需要删除的文件以及需要刷新的文件
        List<String> delFileIds = new ArrayList<>();
        List<SysFileAnnex> delFiles = new ArrayList<>();
        List<SysFileAnnex> saveFiles = new ArrayList<>();
        for (RefershFileDto data : datas) {
            List<String> relationIds = data.getRelationIds();
            List<String> allFileIds = data.getFileIds();
            for (int i = 0; i < relationIds.size(); i++) {
                String fileRelationId = relationIds.get(i);
                String fileIds = allFileIds.get(i);
                //两个值其中一个为空则不操作附件
                if (!ValidateUtil.isAnObjEmpty(fileRelationId, fileIds)) {
                    //前端附件组件获取失败ids传参false
                    if (!fileIds.equalsIgnoreCase("false")&&!fileIds.equalsIgnoreCase("undefined"))
                        //fileIds传入deleteAll标识删除所有附件
                        differentiateFiles(fileRelationId, fileIds.equalsIgnoreCase("deleteAll") ? null : fileIds, delFileIds, delFiles, saveFiles);
                }
            }
            //region 批量更新上传状态或拷贝文件
            if (saveFiles.size() > 0) {
                refreshStatusAndCopyFile(saveFiles, data.getEntityId(), root);
            }
            //endregion
        }
        //endregion
        //region    删除多余记录以及实体文件
        delFileRecordAndEntityFile(root, delFileIds, delFiles);
        //endregion
        return BaseResponse.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addFormRefreshRelationFile(List<String> ids, String entityId) {
        if (ids.size() < 1) return BaseResponse.ok();
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.in("File_Relation_Id", ids);
        List<SysFileAnnex> files = baseMapper.selectList(wrapper);
        refreshStatusAndCopyFile(files, entityId, sysConfigService.getRootDirectory());
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addFormCopyAndRefreshRelationFile(List<Map<String, String>> data, String entityId) {
        String root = sysConfigService.getRootDirectory();
        if (root == null) return new BaseResponse("缺少系统参数！");
        //key: fileId value:relationId
        Map<String, String> idRelation = new HashMap<>();
        List<String> allFileIds = new ArrayList<>();
        List<String> allFileRelationIds = new ArrayList<>();
        log.info("data:{}", JacksonUtils.toJSONString(data));
        for (Map<String, String> map : data) {
            String fileRelationId = map.get("fileRelationId");
            String fileIds = map.get("fileIds");
            if (!ValidateUtil.isEmpty(fileIds)) {
                String[] files = fileIds.split(",");
                for (String fileId : files) {
                    idRelation.put(fileId, fileRelationId);
                    allFileIds.add(fileId);
                    allFileRelationIds.add(fileRelationId);
                }
            }
        }

        //找出所有与当前relationId不匹配的数据(说明这些文件数据来源非当前表单，需要复制过来)
        if(CollectionUtil.isNotEmpty(allFileRelationIds)){
            allFileRelationIds=allFileRelationIds.stream().distinct().collect(Collectors.toList());
            log.info("allFileRelationIds:{}", JacksonUtils.toJSONString(allFileRelationIds));
            for (String relationId:allFileRelationIds){
                List<SysFileAnnex> files = getFileByRelationId(relationId);
                log.info("files:{}", JacksonUtils.toJSONString(files));
                //要复制的文件数据
                List<SysFileAnnex> copyFiles = new ArrayList<>();
                //仅更新上传状态
                List<SysFileAnnex> onlyUpState = new ArrayList<>();
                OssClientParamsDto conf = sysConfigService.getOssParams();
                for (SysFileAnnex file : files) {
                    file.setFileEntityId(entityId);
                    String fileDir = file.getFilePath().substring(0, file.getFilePath().lastIndexOf("/") + 1);
                    if (!allFileIds.contains(file.getFileId())){
                        file.setUploadStatus(0);
                    }else {
                        file.setUploadStatus(1);
                    }
                    onlyUpState.add(file);
                    if (conf != null) {
                        String key = CrcOssUtil.upload(conf, root + file.getFilePath(), conf.getOssRootDir() + fileDir);
                        if (ValidateUtil.isNotEmpty(key)) file.setFileUpOss(Boolean.TRUE);
                    } else file.setFileUpOss(Boolean.FALSE);
                }
                log.info("onlyUpState:{}", JacksonUtils.toJSONString(onlyUpState));
                if (!onlyUpState.isEmpty()) updateBatchById(onlyUpState);
                log.info("copyFiles:{}", JacksonUtils.toJSONString(copyFiles));
                if (!copyFiles.isEmpty()) {
                    saveBatch(copyFiles);
                }
            }
        }

        return new BaseResponse(true, "操作成功！");
    }

    @Override
    public List<SysFileAnnex> getFileListByFileIds(String fileIds) {
        if (ValidateUtil.isEmpty(fileIds)) return null;
        String[] files = fileIds.split(",");
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("file_id", files[0]);
        for (int i = 1; i < files.length; i++) {
            wrapper.or().eq("file_id", files[i]);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysFileAnnex> getFileListByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.in("file_id", ids);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FileResVo> getFileByFileRelationId(String fileRelationId, Integer fileLoation, Boolean showAll) {
        List<ProFileAnnex> datas = baseMapper.getFilesByRelationIds(fileLoation,
                sysConfigService.showHistroyReplaceFile().equals("1"), Arrays.asList(fileRelationId), showAll);
        List<FileResVo> data = new ArrayList<>();
        ListToFileResponseList(datas, data);
        return data;
    }

    @Override
    public BaseResponse getFileCountByRelationIds(List<String> fileRelationIds, Integer fileLoation) {
        Integer fileReplace = 0;
        //判断是否显示历史被替换文件
        if (sysConfigService.showHistroyReplaceFile().equals("1")) fileReplace = 1;

        List<String> allRelation = baseMapper.getFileCountByRelationIds(fileRelationIds, fileReplace, fileLoation);
        Map<String, Integer> map = new HashMap<>();
        if (allRelation != null && allRelation.size() > 0) {
            for (String id : fileRelationIds) {
                List<String> theId = allRelation.stream().filter(f -> f.equals(id)).collect(Collectors.toList());
                map.put(id, theId == null ? 0 : theId.size());
            }
        }
        return new BaseResponse(true, "获取成功！", map);
    }

    @Override
    public BaseResponse getJimuFileCountByFileId(String entityId, Integer fileLocation) {
        List<ProFileAnnex> datas = baseMapper.getJimuFileCountByFileId(fileLocation
                , sysConfigService.showHistroyReplaceFile().equals("1"), entityId);
        //log.info("datas:" + JacksonUtils.toJSONString(datas));
        List<FileResVo> data = new ArrayList<>();
        ListToFileResponseList(datas, data);
        //log.info("data:" + JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data);
    }

    @Override
    public List<FileResVo> getFileByFileRelationIds(List<String> fileRelationIds, Integer fileLocation) {
        log.info("fileRelationIds:" + JacksonUtils.toJSONString(fileRelationIds));
        List<ProFileAnnex> datas = baseMapper.getFilesByRelationIds(fileLocation
                , sysConfigService.showHistroyReplaceFile().equals("1"), fileRelationIds, false);
        log.info("datas:" + JacksonUtils.toJSONString(datas));
        List<FileResVo> data = new ArrayList<>();
        ListToFileResponseList(datas, data);
        log.info("data:" + JacksonUtils.toJSONString(data));
        return data;
    }

    @Override
    public List<SysFileAnnex> getFilePojoByFileRelationIds(List<String> fileRelationIds) {
        if (fileRelationIds == null || fileRelationIds.size() < 1) return null;
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("upload_status", 1);
        //判断是否显示历史被替换文件
        if (sysConfigService.showHistroyReplaceFile().equals("1")) wrapper.eq("File_Replace", 1);
        else wrapper.ne("File_Replace", 1);
        wrapper.and(q -> {
            q.eq("file_relation_id", fileRelationIds.get(0));
            for (int i = 1; i < fileRelationIds.size(); i++) {
                q.or().eq("file_relation_id", fileRelationIds.get(i));
            }
        });
        List<SysFileAnnex> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public BaseResponse getFileByRelationIds(List<String> relationIds, int fileLocation) {
        List<FileResVo> data = getFileByFileRelationIds(relationIds, fileLocation);
        return BaseResponse.ok(data);
    }


    @Override
    public BaseResponse getAchievementsList(AchievementsRequest request) {
        String downloadUrl = sysConfigService.getDownloadUrl();
        if (downloadUrl == null) return BaseResponse.fail(FileMsg.missSysConfig);
        QueryMap<AchievementsRequest> params = new QueryMap<>(request);
        params.put("fileName").put("pName").put("cpdName").put("fileCreateUName").put("cplIds")
                .put("pId")
                //判断是否显示历史被替换文件
                .put("fileReplace", "1".equals(sysConfigService.showHistroyReplaceFile()) ? 1 : 0);
        Integer total = baseMapper.getAchievementsListCount(params.getQuery());
        if (total <= 0) {
            return BaseResponse.ok(new ArrayList<>(), 0);
        }

        List<ArchievementsVo> data = baseMapper.getAchievementsList(params.getQuery());
        data.forEach(model -> {
            model.setDownUrl(downloadUrl + "/jpm/downloadAloneFile/" + model.getFileId());
            model.setNoHostUrl("/fileServer/jpm/downloadAloneFile/" + model.getFileId());
            model.setAnnexId(model.getFileId());
            model.setFileId(GenerateUUID.getUUID());
        });
        return BaseResponse.ok(data, total);
    }

    @Override
    public SysFileAnnex getOneFileAnnexByRelationId(String relationId) {
        if (ValidateUtil.isEmpty(relationId)) return null;
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("File_Relation_Id", relationId);
        wrapper.eq("upload_status", 1);
        //判断是否显示历史被替换文件
        if (sysConfigService.showHistroyReplaceFile().equals("1")) wrapper.eq("File_Replace", 1);
        else wrapper.ne("File_Replace", 1);
        List<SysFileAnnex> entity = baseMapper.selectList(wrapper);
        if (entity != null && entity.size() > 0) return entity.get(0);
        else return null;
    }

    @Override
    public List<FileAnnexTableAndColumnDto> getFileAnnexTableAndColumnByTableCodes(List<String> tableCodes) {
        if (tableCodes == null || tableCodes.size() < 1) return null;
        return baseMapper.getFileAnnexTableAndColumnByTableCodes(tableCodes);
    }

    @Override
    public BaseResponse getUnArchiveFileRecordByPpId(ArchiveFileRecordDto request) {
        //过滤掉招标模块的数据
//        List<String> secCodes = request.gettCodes();
//        List<String> tCodes = sysFileTemplateService.filterSecTcode(secCodes);
//        secCodes.removeAll(tCodes);
        List<String> tCodes = request.gettCodes();
        List<String> secIds = secProjectService.getSecIdsByPid(request.getpId());
        List<ArchiveFileRecordVo> data = null;
        Integer total = 0;
        // if (tCodes != null && tCodes.size() > 0) {
        //非招标模块的逻辑
        Map<String, Object> filter = new HashMap<>();
        filter.put("start", ValidateUtil.isNotNull(request.getPage()) ? (request.getPage() - 1) * request.getSize() : 0);
        filter.put("size", ValidateUtil.isNotNull(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        filter.put("fileName", !ValidateUtil.isEmpty(request.getFileName()) ? request.getFileName() : null);
        Integer fileReplace = 0;
        //判断是否显示历史被替换文件
        if (sysConfigService.showHistroyReplaceFile().equals("1")) fileReplace = 1;
        //region old 归档附件查询
//
//        //获取表名和主键名称
//        List<FileTemplateTableAndPrimaryDto> tableAndPrimary = sysFileTemplateService.getFileTemplateTableAndPrimaryByPid(tCodes);
//        if (tableAndPrimary != null && tableAndPrimary.size() > 0) {
//            List<String> tableCodes = tableAndPrimary.stream().map(FileTemplateTableAndPrimaryDto::gettTable).collect(Collectors.toList());
//            //获取表明和文件存储的字段名
//            List<FileAnnexTableAndColumnDto> tableAndColumn = getFileAnnexTableAndColumnByTableCodes(tableCodes);
//
//            if (tableAndColumn != null && tableAndColumn.size() > 0) {
//                List<FileTableColumnAndPrimaryDto> params = new ArrayList<>();
//                for (FileAnnexTableAndColumnDto a : tableAndColumn) {
//                    if (!ValidateUtil.isEmpty(a.getColumnCode())) {
//                        for (FileTemplateTableAndPrimaryDto b : tableAndPrimary) {
//                            if (a.getTableCode().equalsIgnoreCase(b.gettTable())) {
//                                FileTableColumnAndPrimaryDto columnAndPrimary = new FileTableColumnAndPrimaryDto();
//                                columnAndPrimary.setFileColumnName(humpTurnDatabase(a.getColumnCode()));
//                                columnAndPrimary.setTableName(b.gettTable());
//                                columnAndPrimary.setPrimaryKey(b.gettPrimaryKey());
//                                if (params.indexOf(columnAndPrimary) < 0) params.add(columnAndPrimary);
//                            }
//                        }
//                    }
//                }
//                if (params.size() > 0) {
//                    data = baseMapper.getUnArchiveFileRecordBypId(params, request.getpId(), filter, tCodes, fileReplace, secIds);
//                    total = baseMapper.getUnArchiveFileRecordBypIdCount(params, request.getpId(), filter, tCodes, fileReplace, secIds);
//                } else {
//                    data = baseMapper.getUnArchiveFileRecordBypIdAndTcode(request.getpId(), filter, tCodes, fileReplace, secIds);
//                    total = baseMapper.getUnArchiveFileRecordBypIdAndTcodeCount(request.getpId(), filter, tCodes, fileReplace, secIds);
//                }
//            } else {
//                data = baseMapper.getUnArchiveFileRecordBypIdAndTcode(request.getpId(), filter, tCodes, fileReplace, secIds);
//                total = baseMapper.getUnArchiveFileRecordBypIdAndTcodeCount(request.getpId(), filter, tCodes, fileReplace, secIds);
//            }
//        }
        //endregion
        data = baseMapper.getUnArchiveFileRecordBypIdAndTcode(request.getpId(), filter, tCodes, fileReplace, secIds);
        total = baseMapper.getUnArchiveFileRecordBypIdAndTcodeCount(request.getpId(), filter, tCodes, fileReplace, secIds);
        return BaseResponse.ok(data, total);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse archiveFile(List<ArchiveFileRequest> datas, String apId, String fileCode, Integer apState) {
        String root = sysConfigService.getRootDirectory();
        String archiveDr = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isEmpty(root) || ValidateUtil.isEmpty(archiveDr)) return new BaseResponse("缺少基础配置！");
        List<SysFileAnnex> archiveFiles = baseMapper.getArchiveFileByApId(apId);
        //原来已生成的归档文件记录ID
        List<String> fileIds = archiveFiles.stream().map(SysFileAnnex::getFileId).collect(Collectors.toList());
        //现在需要归档的文件记录ID
        List<String> archiveFileIds = datas.stream().map(ArchiveFileRequest::getAfAnnexId).collect(Collectors.toList());
        //fileIds有而archiveFileIds内没有的 需要删除
        List<String> removeList = fileIds.stream().filter(item -> archiveFileIds.indexOf(item) < 0).collect(Collectors.toList());

        // archiveFileIds而fileIds内没有的 需要新增
        List<String> addList = archiveFileIds.stream().filter(item -> fileIds.indexOf(item) < 0).collect(Collectors.toList());
        //之前已勾选归档现在又取消勾选的
        if (removeList != null && removeList.size() > 0) {
            //把选择归档的文件标记为未归档
            baseMapper.updateFileAnnexArchiveByUnArchiveIds(removeList, 0);
            //删除之前生成的归档记录文件记录
            baseMapper.deleteBatchIds(removeList);
        }

        if (addList != null && addList.size() > 0) {
            //需要归档的文件信息
            List<SysFileAnnex> oldRecords = baseMapper.selectFileAnnexByIds(addList);
            //需要新增的归档文件信息
            List<ArchiveFileRequest> addData = datas.stream().filter(n -> addList.indexOf(n.getAfAnnexId()) >= 0).collect(Collectors.toList());
            //不需要修改的已归档信息
            List<ArchiveFileRequest> notChangeData = datas.stream().filter(n -> addList.indexOf(n.getAfAnnexId()) < 0).collect(Collectors.toList());
            //确认归档
            if (apState == 1) {
                //已经生成归档信息的fileId
                List<String> alreadyArchiveIds = notChangeData.stream().map(ArchiveFileRequest::getAfAnnexId).collect(Collectors.toList());
                //修改不删除也不新增的文件记录归档状态
                if (alreadyArchiveIds != null && alreadyArchiveIds.size() > 0)
                    baseMapper.updateFileAnnexArchiveByIds(alreadyArchiveIds, apState);
            }
            if (addData != null && addData.size() > 0) {
                //需要新增的文件信息
                List<SysFileAnnex> toAddEntity = new ArrayList<>();
                //需要备份的原始路径和新路径
                List<Map<String, String>> fromTo = new ArrayList<>();
                for (ArchiveFileRequest data : addData) {
                    List<SysFileAnnex> oldRecord = oldRecords.stream().filter(n -> n.getFileId().equals(data.getAfAnnexId())).collect(Collectors.toList());
                    if (oldRecord != null && oldRecord.size() > 0) {

                        String newId = GenerateUUID.getUUID();
                        SysFileAnnex newFile = oldRecord.get(0);
                        newFile.setFileUnarchiveId(newFile.getFileId());
                        newFile.setFileId(newId);
                        String oldPath = newFile.getFilePath();
                        newFile.setFilePath(fileCode + "/" + oldPath);
                        newFile.setFileLocation(1);
                        newFile.setArchive(apState);
                        data.setAfAnnexId(newId);
                        notChangeData.add(data);
                        toAddEntity.add(newFile);

                        Map<String, String> ft = new HashMap<>();
                        ft.put("from", oldPath);
                        ft.put("to", newFile.getFilePath());
                        ft.put("id", newId);
                        fromTo.add(ft);
                    }
                }
                //拷贝文件 返回失败的主键ID
                List<String> fildIds = FileUtil.listFileInDir(fromTo, sysConfigService.getOssParams(), root, archiveDr);
                //新增记录
                if (fildIds != null && fileIds.size() > 0) {
                    List<SysFileAnnex> finlyAddEntity = toAddEntity.stream().filter(n -> fileIds.indexOf(n.getFileId()) < 0).collect(Collectors.toList());
                    saveBatch(finlyAddEntity);
                    List<String> unarchiveIds = finlyAddEntity.stream().filter(n -> !ValidateUtil.isEmpty(n.getFileUnarchiveId())).map(SysFileAnnex::getFileUnarchiveId).collect(Collectors.toList());
                    if (unarchiveIds != null && unarchiveIds.size() > 0)
                        baseMapper.updateFileAnnexArchiveByIds(unarchiveIds, 1);
                    List<ArchiveFileRequest> finlyData = notChangeData.stream().filter(n -> fileIds.indexOf(n.getAfAnnexId()) < 0).collect(Collectors.toList());
                    return new BaseResponse(true, "归档完成", finlyData);
                } else {
                    saveBatch(toAddEntity);
                    List<String> unarchiveIds = toAddEntity.stream().filter(n -> !ValidateUtil.isEmpty(n.getFileUnarchiveId())).map(SysFileAnnex::getFileUnarchiveId).collect(Collectors.toList());
                    if (unarchiveIds != null && unarchiveIds.size() > 0)
                        baseMapper.updateFileAnnexArchiveByIds(unarchiveIds, 1);
                    return new BaseResponse(true, "归档完成", notChangeData);
                }
            }
        } else {
            //确认归档
            if (apState == 1) baseMapper.updateFileAnnexArchiveByApId(apId, apState);
        }
        return new BaseResponse(true, FileMsg.archiveSuccess);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId, Boolean skipArchive) {
        if (oldIds == null || newIds == null || oldIds.size() != newIds.size())
            return BaseResponse.fail(FileMsg.noReqParam);
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq(!skipArchive, "archive", 0);
        wrapper.and(wr -> {
            wr.eq("File_Relation_Id", oldIds.get(0));
            if (oldIds.size() > 1) {
                for (int i = 1; i < oldIds.size(); i++) {
                    wr.or().eq("File_Relation_Id", oldIds.get(i));
                }
            }
        });
        List<SysFileAnnex> entitys = baseMapper.selectList(wrapper);
        String root = sysConfigService.getRootDirectory();
        if (entitys != null && entitys.size() > 0) {
            List<SysFileAnnex> models = new ArrayList<>();
            OssClientParamsDto conf = sysConfigService.getOssParams();
            entitys.forEach(model -> {
                String oldFileId = model.getFileId();
                model.setFileId(GenerateUUID.getUUID());
                model.setFileRelationId(newIds.get(oldIds.indexOf(model.getFileRelationId())));
                model.setFileEntityId(entityId);
                try {
                    //复制文件
                    String dir = model.getFilePath().substring(0, model.getFilePath().lastIndexOf("/") + 1);
                    String newPath = dir + GenerateUUID.getUUID() + "." + model.getFileSuffix();
                    FileUtil.copy(root + model.getFilePath(), root + newPath);
                    model.setFilePath(newPath);
                    if (ValidateUtil.isNotNull(model.getUploadStatus()) && model.getUploadStatus() == 1 && conf != null) {
                        String key = CrcOssUtil.upload(conf, root + newPath, conf.getOssRootDir() + dir);
                        if (ValidateUtil.isNotEmpty(key)) model.setFileUpOss(Boolean.TRUE);
                    } else model.setFileUpOss(Boolean.FALSE);
                    if (skipArchive) {
                        model.setArchive(0);
                        model.setFileUnarchiveId(null);
                    }
                    models.add(model);
                } catch (Exception ex) {
                    log.error("文件复制失败！源头文件记录ID：" + oldFileId,ex);
                }
            });
            if (models.size() > 0) saveBatch(models);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds) {
        if (oldIds == null || newIds == null || oldIds.size() != newIds.size()) {
            return BaseResponse.fail(FileMsg.noReqParam);
        }
        // 查询需要复制的文件记录
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.in("File_Relation_Id", oldIds);
        List<SysFileAnnex> entitys = baseMapper.selectList(wrapper);
        String root = sysConfigService.getRootDirectory();
        // 处理文件
        if (entitys != null && !entitys.isEmpty()) {
            List<SysFileAnnex> models = new ArrayList<>();
            OssClientParamsDto conf = sysConfigService.getOssParams();
            entitys.forEach(model -> {
                String oldFileId = model.getFileId();
                model.setFileId(GenerateUUID.getUUID());
                model.setFileRelationId(newIds.get(oldIds.indexOf(model.getFileRelationId())));
                try {
                    //复制文件
                    String dir = model.getFilePath().substring(0, model.getFilePath().lastIndexOf("/") + 1);
                    String newPath = dir + GenerateUUID.getUUID() + "." + model.getFileSuffix();
                    FileUtil.copy(root + model.getFilePath(), root + newPath);
                    model.setFilePath(newPath);
                    if (ValidateUtil.isNotNull(model.getUploadStatus()) && model.getUploadStatus() == 1 && conf != null) {
                        String key = CrcOssUtil.upload(conf, root + newPath, conf.getOssRootDir() + dir);
                        if (ValidateUtil.isNotEmpty(key)) {
                            model.setFileUpOss(Boolean.TRUE);
                        }
                    } else {
                        model.setFileUpOss(Boolean.FALSE);
                    }
                    model.setArchive(0);
                    model.setFileUnarchiveId(null);
                    model.setUploadStatus(0);
                    models.add(model);
                } catch (Exception ex) {
                    log.error("文件复制失败！源头文件记录ID：{}", oldFileId, ex);
                }
            });
            if (!models.isEmpty()) {
                saveBatch(models);
            }

            // 转换为响应对象
            List<FileResVo> resVos = new ArrayList<>();
            ListToFileResponseList(BeanUtils.copyList(models, ProFileAnnex.class), resVos);
            return BaseResponse.ok(resVos);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId) {
        return copyFileAnnex2NewRelation(oldIds, newIds, entityId, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse replaceFormFile(List<String> oldIds, List<String> newIds, String entityId) {
        //表单被替换前文件
        List<SysFileAnnex> oldFiles = getFileByEntityId(entityId);
        //复制文件
        copyFileAnnex2NewRelation(oldIds, newIds, entityId);
        //删除被替换前文件
        if (ValidateUtil.isNotEmpty(oldFiles)) {
            //删除记录
            baseMapper.deleteBatchIds(oldFiles.stream().map(SysFileAnnex::getFileId).collect(Collectors.toList()));
            //删除文件
            String root = sysConfigService.getRootDirectory();
            OssClientParamsDto conf = sysConfigService.getOssParams();
            oldFiles.forEach(f -> {
                File file = new File(root + f.getFilePath());
                if (file.exists() && file.isFile()) {
                    file.delete();
                    if (f.isFileUpOss() && conf != null)
                        CrcOssUtil.deleteFile(conf, conf.getOssRootDir() + f.getFilePath());
                }
            });
        }
        return BaseResponse.ok();
    }

    @Override
    public List<AutoArchiveFileVo> getUnArchiveFiles() {
        //直接可获取到项目信息的未归档文件
        List<AutoArchiveFileVo> datas = baseMapper.getUnArchiveFiles();
        //项目建议书未归档文件
        List<AutoArchiveFileVo> proposals = baseMapper.getProProposalUnArchiveFiles();
        if (!CollectionUtils.isEmpty(proposals)) datas.addAll(proposals);
        return datas;
    }

    @Override
    public List<AutoArchiveFileVo> getUnXzArchiveFiles(List<String> adIds) {
        return baseMapper.getUnXzArchiveFiles(adIds);
    }

    @Override
    public List<SysFileAnnex> getFileByEntityIds(List<String> entityIds) {
        if (ValidateUtil.isEmpty(entityIds)) return null;
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("upload_status", 1);
        wrapper.eq("archive", 0);
        wrapper.eq("File_Location", 0);
        wrapper.and(wr -> {
            wr.eq("File_Entity_Id", entityIds.get(0));
            for (int i = 1; i < entityIds.size(); i++)
                wr.or().eq("File_Entity_Id", entityIds.get(i));
        });
        return baseMapper.selectList(wrapper);
    }

    @Override
    public SysFileAnnex checkFileMd5(String md5Str, Integer fileLocation) {
        if (fileLocation != null && fileLocation == 1) return baseMapper.md5GetArchiveFile(md5Str);
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("File_MD5_Code", md5Str);
        wrapper.eq("upload_status", 1);
        wrapper.eq("File_Location", fileLocation);
        wrapper.orderByDesc("file_upload_time");
        wrapper.last(" limit 1");
        SysFileAnnex file = baseMapper.selectOne(wrapper);
        if (file != null) return file;
        else return null;
    }

    @Override
    public void refershFileMd5Code(int num) {
        if (num < 1) return;
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAnObjEmpty(root, archive)) throw new JPMException(FileMsg.missSysConfig);
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.isNull("File_MD5_Code");
        wrapper.last("limit " + num);
        List<SysFileAnnex> files = baseMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(files)) throw new JPMException("所有文件MD5标识已处理完成");
        else {
            try {
                List<SysFileAnnex> updates = new ArrayList<>();
                String path;
                for (SysFileAnnex f : files) {
                    if (f.getFileLocation() == 1) path = archive + "/" + f.getFilePath();
                    else path = root + "/" + f.getFilePath();
                    File file = new File(path);
                    if (file.isFile() && file.exists()) {
                        f.setFileMd5Code(MD5File.getFileMD5Str(file));
                    } else f.setFileMd5Code("");
                    updates.add(f);
                }
                if (updates.size() > 0) updateBatchById(updates);
            } catch (Exception e) {
                throw new JPMException(e.getMessage());
            }
        }
    }

    @Override
    public List<SysFileAnnex> getAllArchiveFile() {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("archive", 1);
        wrapper.eq("File_Location", 1);
        wrapper.isNotNull("File_Unarchive_Id");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<FileAnnexInfoVo> getFileAnnexByFileId(String fileId) {
        SysFile fileObj = sysFileService.getById(fileId);
        if (fileObj != null) {
            List<String> relationIds = new ArrayList<>();
            relationIds.add(fileObj.getFileRelationId());
            SysFileTemplate temObj = sysFileTemplateService.getById(fileObj.gettId());
            if (temObj != null) {
                Map<String, Object> data = baseMapper.getData(temObj.gettTable(), temObj.gettPrimaryKey(), fileId);
                if (data != null) {
                    for (String key : data.keySet()) {
                        Object val = data.get(key);
                        if (!ValidateUtil.isEmpty(val)) {
                            if (val.toString().length() == 36 || val.toString().length() == 32) {
                                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                                Matcher m = p.matcher(val.toString());
                                if (!m.find()) {
                                    relationIds.add(val.toString());
                                }
                            } else if (val.toString().length() == 19) {
                                if (val.toString().matches("-?\\d+(\\.\\d+)?")) {
                                    relationIds.add(val.toString());
                                }
                            }
                        }
                    }
                }
            }
            QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
            wrapper.eq("tp_entityid", fileId);
            List<TaskProcessin> tpList = taskProcessinService.list(wrapper);
            List<SysFile> list = new ArrayList<>();
            list.add(fileObj);
            if (tpList != null && tpList.size() > 0) {
                List<SysFile> fileList = sysFileService.listByIds(tpList.stream().map(n -> n.getTpId()).collect(Collectors.toList()));
                if (fileList != null && fileList.size() > 0) {
                    relationIds.addAll(fileList.stream().map(n -> n.getFileRelationId()).collect(Collectors.toList()));
                    list.addAll(fileList);
                }
            }
            if (relationIds.size() == 0) {
                return new ArrayList<>();
            }
            //获取关联的附件信息
            List<FileAnnexInfoVo> fileList = baseMapper.getFileAnnerxByFileId(relationIds);

            for (FileAnnexInfoVo obj : fileList) {
                List<SysFile> newList = list.stream().filter(n -> n.getFileRelationId() != null && n.getFileRelationId().equals(obj.getFileRelationId())).collect(Collectors.toList());
                if (newList.size() > 0) {
                    obj.setFileId(newList.get(0).getFileid());
                }
            }
            return fileList;

        } else {
            return null;
        }

    }

    @Override
    public void deleteUnSaveFile() {
        QueryWrapper<SysFileAnnex> wr = new QueryWrapper<>();
        wr.eq("upload_status", 0);
        Long unUps = baseMapper.selectCount(wr);
        if (unUps != null && unUps > 0L) {
            Integer count = Integer.parseInt(String.valueOf(unUps));
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            int pageSiz = 5000;
            for (int i = 0; i < (int) Math.ceil((double) count / pageSiz); i++) {
                QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
                wrapper.eq("upload_status", 0);
                wrapper.last(" limit " + pageSiz);
                List<SysFileAnnex> files = baseMapper.selectList(wrapper);
                if (!CollectionUtils.isEmpty(files)) {
                    if (!ValidateUtil.isAnObjEmpty(root, archive)) {
                        List<String> ids = new ArrayList<>();
                        files.forEach(f -> {
                            String path = f.getFileLocation() == 1 ? archive + "/" + f.getFilePath() : root + "/" + f.getFilePath();
                            File file = new File(path);
                            if (file.exists() && file.isFile()) {
                                file.delete();
                                log.info("附件删除【" + f.getFileName() + "】 -》" + file.getPath());
                            }
                            ids.add(f.getFileId());
                        });
                        if (!CollectionUtils.isEmpty(ids)) this.removeBatchByIds(ids);
                    }
                }
            }
        }
    }

    @Override
    public void deleteOssLocalFile() {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("File_Up_Oss", Boolean.TRUE);
        Long count = baseMapper.selectCount(wrapper);
        if (count != null && count > 0L) {
            Integer size = Integer.parseInt(String.valueOf(count));
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            int pageSiz = 5000;
            for (int i = 0; i < (int) Math.ceil((double) size / pageSiz); i++) {
                QueryWrapper<SysFileAnnex> wr = new QueryWrapper<>();
                wr.eq("File_Up_Oss", Boolean.TRUE);
                wr.orderByDesc("file_upload_time");
                wr.last("limit " + i * pageSiz + "," + pageSiz);
                List<SysFileAnnex> files = baseMapper.selectList(wr);
                if (!CollectionUtils.isEmpty(files)) {
                    files.forEach(f -> {
                        String path = f.getFileLocation() == 1 ? archive + "/" + f.getFilePath() : root + "/" + f.getFilePath();
                        File file = new File(path);
                        if (file.exists() && file.isFile()) {
                            file.delete();
                            log.info("附件删除【" + f.getFileName() + "】 -》" + file.getPath());
                        }
                    });
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileArchiveStatus(List<String> fileIds) {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("archive", 1);
        wrapper.eq("file_location", 1);
        wrapper.in("File_Id", fileIds);
        List<SysFileAnnex> files = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(files)) {
            OssClientParamsDto conf = sysConfigService.getOssParams();
            String archive = sysConfigService.getArchiveDirectory();
            List<SysFileAnnex> data = new ArrayList<>();
            for (SysFileAnnex file : files) {
                if (conf != null) {
                    if (!file.isFileUpOss()) {
                        String dir = conf.getOssRootDir() + "/" + file.getFilePath().substring(0, file.getFilePath().lastIndexOf("/") + 1);
                        String key = CrcOssUtil.upload(conf, archive + file.getFilePath(), dir);
                        if (ValidateUtil.isNotEmpty(key)) file.setFileUpOss(Boolean.TRUE);
                    }
                } else file.setFileUpOss(Boolean.FALSE);
                file.setUploadStatus(1);
                data.add(file);
                updateBatchById(data);
            }
        }
    }

    @Override
    public String getFileIdsByRelationIds(List<String> relationIds) {
        if (!CollectionUtils.isEmpty(relationIds)) {
            QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
            wrapper.in("File_Relation_Id", relationIds);
            List<SysFileAnnex> data = baseMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(data)) {
                return String.join(",", data.stream().map(SysFileAnnex::getFileId).collect(Collectors.toList()));
            }
        }
        return null;
    }

    @Override
    public List<SysFileAnnex> getFileByRelationIds(List<String> relationIds) {
        if (CollectionUtils.isEmpty(relationIds)) return null;
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("Upload_Status", 1);
        wrapper.in("File_Relation_Id", relationIds);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public SysFileAnnex entity(String root, String path, int location, String fileName, String newName, String fileSuffix, String columnCode, String entityId, String relationId, LogUserInfoVo user, SysFileTemplate temp) {
        SysFileAnnex sysFileAnnexPojo = new SysFileAnnex();
        String realAddress = root + "/" + path + "/" + newName;
        sysFileAnnexPojo.setFileMd5Code(MD5File.getFileMD5Str(new File(realAddress)));
        sysFileAnnexPojo.setFileCreateUId(user.getId());
        sysFileAnnexPojo.setFileCreateUName(user.getName());
        sysFileAnnexPojo.setFileUploadTime(new Date());
        sysFileAnnexPojo.setFileSuffix(fileSuffix);
        sysFileAnnexPojo.setFileSize(FileUtil.getRemoteFileSize(realAddress));
        sysFileAnnexPojo.setFileRelationId(relationId);
        sysFileAnnexPojo.setFilePath(path + "/" + newName);
        sysFileAnnexPojo.setFileName(fileName);
        sysFileAnnexPojo.setFileCode(temp.gettCode());
        sysFileAnnexPojo.setTableName(temp.gettName());
        sysFileAnnexPojo.setTableCode(temp.gettCode());
        sysFileAnnexPojo.setColumnCode(columnCode);
        sysFileAnnexPojo.setFileId(GenerateUUID.getUUID());
        sysFileAnnexPojo.setFileNotRemove(Boolean.FALSE);
        sysFileAnnexPojo.setArchive(location);
        sysFileAnnexPojo.setFileLocation(location);
        sysFileAnnexPojo.setFileReplace(0);
        sysFileAnnexPojo.setFileEntityId(entityId);
        sysFileAnnexPojo.setTeId(user.getTeId());
        sysFileAnnexPojo.setUploadStatus(1);
        sysFileAnnexPojo.setFileUploadTime(new Date());
        sysFileAnnexPojo.setFileSourceType(FileSourceEnum.UserUpload.getIndex());
        return sysFileAnnexPojo;
    }

    @Override
    public List<SysFileAnnex> getArchiveFile(String adId) {
        if (ValidateUtil.isEmpty(adId)) return null;
        return baseMapper.getArchiveFile(adId);
    }

    @Override
    public BaseResponse refreshImgAndCompress(List<ImgCompressAndRefreshStateDTO> files, String entityId) {
        String root = sysConfigService.getRootDirectory();
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        List<String> removeImgs = new ArrayList<>();
        List<Map<String, String>> relationIds = new ArrayList<>();
        List<ImgCompressAndRefreshStateDTO> data = new ArrayList<>();
        files.forEach(f -> {
            if (ValidateUtil.isEmpty(f.getImgIds())) removeImgs.add(f.getImgRelationId());
            if (ValidateUtil.isEmpty(f.getCompressIds())) removeImgs.add(f.getCompressRelationId());
            if (ValidateUtil.isNotEmpty(f.getImgRelationId()) && ValidateUtil.isNotEmpty(f.getImgIds())) {
                Map<String, String> mp = new HashMap<>();
                mp.put("fileRelationId", f.getImgRelationId());
                mp.put("fileIds", f.getImgIds());
                relationIds.add(mp);
                f.setCompressRelationId(GenerateUUID.getUUID());
                //原图下载地址
                f.setImgUrl("/fileServer/jpm/downloadAloneFile/" + f.getImgIds());
                data.add(f);
            }
        });
        //删除确定要删除的附件
        removeFiles(removeImgs, root);
        //刷新原始图片附件状态
        if (!CollectionUtils.isEmpty(relationIds)) {
            //原图
            List<SysFileAnnex> yts = refreshFileRelation(relationIds, entityId, root);
            List<SysFileAnnex> compress = new ArrayList<>();
            CompressImgDTO ci = sysConfigService.getCompressImg();
            data.forEach(iar -> {
                SysFileAnnex yt = yts.stream().filter(f -> f.getFileRelationId().equals(iar.getImgRelationId())).findFirst().get();
                String source = root + "/" + yt.getFilePath();
                String newPath = yt.getFilePath().substring(0, yt.getFilePath().lastIndexOf("/") + 1);
                String fName = GenerateUUID.getUUID() + ".png";
                //压缩生成新的文件
                FileUtil.resizeImage(source, root + "/" + newPath, ci.getWidth(), ci.getHeight(), yt.getFileSuffix(), fName);
                yt.setFileId(GenerateUUID.getUUID());
                yt.setFileRelationId(iar.getCompressRelationId());
                yt.setFilePath(newPath + fName);
                yt.setFileSize(new File(root+"/"+yt.getFilePath()).length());
                yt.setFileSuffix("png");
                compress.add(yt);
                iar.setCompressIds(yt.getFileId());
                iar.setCompressUrl("/fileServer/jpm/downloadAloneFile/" + yt.getFileId());
            });
            saveBatch(compress);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSysfileAnnexList(FileAnnexInfoDto vo) {
        LambdaQueryWrapper<SysFileAnnex> queryWrapper = new LambdaQueryWrapper<>();
        if(ValidateUtil.isNotNull(vo)){
            if(ValidateUtil.isNotEmpty(vo.getFileCreateUName()))
                queryWrapper.like(SysFileAnnex::getFileCreateUName,vo.getFileCreateUName());
            if(ValidateUtil.isNotEmpty(vo.getFileName()))
                queryWrapper.like(SysFileAnnex::getFileName,vo.getFileName());
            if (!CollectionUtils.isEmpty(vo.getFileUploadTimes())) {
                if (vo.getFileUploadTimes().size() == 1)
                    queryWrapper.gt(SysFileAnnex::getFileUploadTime, vo.getFileUploadTimes().get(0));
                else {
                    if (ValidateUtil.isNotEmpty(vo.getFileUploadTimes().get(0)))
                        queryWrapper.gt(SysFileAnnex::getFileUploadTime, vo.getFileUploadTimes().get(0));
                    if (ValidateUtil.isNotEmpty(vo.getFileUploadTimes().get(1)))
                        queryWrapper.lt(SysFileAnnex::getFileUploadTime, vo.getFileUploadTimes().get(1));
                }
            }
        }
        queryWrapper.orderByDesc(SysFileAnnex::getFileUploadTime);
         Page<SysFileAnnex> sysFileAnnexPage = baseMapper.selectPage(new Page<>(vo.getPage(), vo.getSize()), queryWrapper);

        return BaseResponse.ok(sysFileAnnexPage.getRecords(),(int)sysFileAnnexPage.getTotal());
    }

    @Override
    public ResponseEntity<byte[]> ExportSysfileAnnex(FileAnnexInfoDto dto, HttpServletResponse response) {
        try {
        LambdaQueryWrapper<SysFileAnnex> queryWrapper = new LambdaQueryWrapper<>();
        if(ValidateUtil.isNotNull(dto)){
            if(ValidateUtil.isNotEmpty(dto.getFileCreateUName()))
                queryWrapper.like(SysFileAnnex::getFileCreateUName,dto.getFileCreateUName());
            if(ValidateUtil.isNotEmpty(dto.getFileName()))
                queryWrapper.like(SysFileAnnex::getFileName,dto.getFileName());
            if (!CollectionUtils.isEmpty(dto.getFileUploadTimes())) {
                if (dto.getFileUploadTimes().size() == 1)
                    queryWrapper.gt(SysFileAnnex::getFileUploadTime, dto.getFileUploadTimes().get(0));
                else {
                    if (ValidateUtil.isNotEmpty(dto.getFileUploadTimes().get(0)))
                        queryWrapper.gt(SysFileAnnex::getFileUploadTime, dto.getFileUploadTimes().get(0));
                    if (ValidateUtil.isNotEmpty(dto.getFileUploadTimes().get(1)))
                        queryWrapper.lt(SysFileAnnex::getFileUploadTime, dto.getFileUploadTimes().get(1));
                }
            }
        }
            queryWrapper.orderByDesc(SysFileAnnex::getFileUploadTime);
        Page<SysFileAnnex> sysFileAnnexPage = baseMapper.selectPage(new Page<>(1, 5000), queryWrapper);

            List<FileAnnexInfoDto> truncatedRecords = sysFileAnnexPage.getRecords().stream().map(info -> {
                FileAnnexInfoDto dto1 = new FileAnnexInfoDto();
                BeanUtils.copyProperties(info, dto1);
                return dto1;
            }).collect(Collectors.toList());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WriteCellStyle headStyle = new WriteCellStyle();
        WriteFont headFont = new WriteFont();
        headFont.setFontHeightInPoints((short) 12);
        headFont.setFontName("Arial");
        headStyle.setWriteFont(headFont);

        WriteCellStyle contentStyle = new WriteCellStyle();
        WriteFont contentFont = new WriteFont();
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setFontName("Arial");
        contentStyle.setWriteFont(contentFont);

        HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);
        EasyExcel.write(out, FileAnnexInfoDto.class)
                .registerWriteHandler(styleStrategy)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                .sheet("FileAnnexInfoDto")
                .doWrite(truncatedRecords);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=logs.xlsx");
        response.getOutputStream().write(out.toByteArray());
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
    } catch (
    IOException e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    /**
     * 新增修改表单时刷新附件上传状态
     * 如果附件来源为系统关联（即不上传实体文件选择系统类历史文件）且上传状态为未上传时拷贝源文件
     *
     * @param files
     * @param entityId
     */
    private List<SysFileAnnex> refreshStatusAndCopyFile(List<SysFileAnnex> files, String entityId, String root) {
        if (!CollectionUtils.isEmpty(files)) {
            String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
            List<SysFileAnnex> updates = new ArrayList<>();
            OssClientParamsDto conf = sysConfigService.getOssParams();
            for (SysFileAnnex file : files) {
                if (ValidateUtil.isNotEmpty(file.getFileSourceType()) && file.getFileSourceType().equals(FileSourceEnum.SystemFile.getIndex()) && file.getUploadStatus() == 0) { //来源为关联文件且上传状态为未上传 保存表单时拷贝实体文件
                    String filePath = dirPath + file.getFileCode() + "/" + file.getFileCreateUId() + "/" + dateDir;
                    String fileDirPath = root + "/" + filePath;
                    String uuid = GenerateUUID.getUUID();
                    String realUrlPath = fileDirPath + "/" + uuid + "." + file.getFileSuffix();
                    //拷贝文件
                    FileUtil.copy(root + file.getFilePath(), realUrlPath);
                    filePath = filePath + "/" + uuid + "." + file.getFileSuffix();
                    file.setFilePath(filePath);
                }
                file.setUploadStatus(1);
                file.setFileEntityId(entityId);
                //桶内目录
                if (conf != null) {
                    String dir = conf.getOssRootDir() + "/" + file.getFilePath().substring(0, file.getFilePath().lastIndexOf("/") + 1);
                    String key = CrcOssUtil.upload(conf, root + file.getFilePath(), dir);
                    if (ValidateUtil.isNotEmpty(key)) file.setFileUpOss(Boolean.TRUE);
                } else file.setFileUpOss(Boolean.FALSE);
                updates.add(file);
            }
            updateBatchById(updates);
            return updates;
        }
        return null;
    }


    private void ListToFileResponseList(List<ProFileAnnex> list, List<FileResVo> data) {
        String root = sysConfigService.getRootDirectory();
        List<DownloadUrlDto> downs = fileUploadService.downloadAloneBath(list);
        list.forEach(model -> {
            FileResVo file = new FileResVo();
            BeanUtils.copyProperties(model, file);
            file.setUid(model.getFileId());
            DownloadUrlDto down = downs.stream().filter(n -> n.getFileIds().equals(model.getFileId())).findFirst().get();
            file.setUrl(down.getUrl());
            file.setNoHostUrl(down.getNoHostUrl());
            file.setOpenUrl(down.getOpenUrl());
            file.setName(model.getFileName());
            file.setRelationid(model.getFileRelationId());
            file.setUploadTime(model.getFileUploadTime());
            file.setFileNotRemove(model.isFileNotRemove());
            file.setUploadUser(model.getFileCreateUId());
            String filePath = model.getFilePath();
            // 路径安全检查
            File realfile = new File(root, filePath);
            if (!realfile.exists()) {
                file.setIsExist(false);
            }else {
                data.add(file);
                file.setIsExist(true);
            }
        });
    }


    /**
     * entityID查询表单文件(未归档的表单附件（不包含流程附件Sys_File）)
     *
     * @param entityId
     * @return
     */
    private List<SysFileAnnex> getFileByEntityId(String entityId) {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("File_Entity_Id", entityId);
        wrapper.eq("archive", 0);
        wrapper.apply(" file_relation_id != (select file_relation_id from SYS_File where FileID='" + entityId + "' ) ");
        return baseMapper.selectList(wrapper);
    }


    private List<SysFileAnnex> getFileByIds(List<String> allFileIds) {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("File_Id", allFileIds.get(0));
        if (allFileIds.size() > 1) {
            for (int i = 1; i < allFileIds.size(); i++)
                wrapper.or().eq("File_Id", allFileIds.get(i));
        }
        return baseMapper.selectList(wrapper);
    }
    private List<SysFileAnnex> getFileByRelationId(String fileRelationId) {
        QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
        wrapper.eq("file_relation_id",fileRelationId);
        return baseMapper.selectList(wrapper);
    }


    /**
     * 驼峰转数据库字段
     *
     * @param str
     * @return
     */
    private String humpTurnDatabase(String str) {
        if (ValidateUtil.isEmpty(str)) return null;
        //包含下划线则为数据库字段 否则当小驼峰处理
        if (str.contains("_")) return str;
        //cusomer调用producer会调用get set
//        str = str.replaceAll("_", "");
        char[] letters = str.toCharArray();
        String filed = "";
        for (char letter : letters) {
            if (letter >= 'A' && letter <= 'Z') {
                filed += "_" + letter;
            } else filed += letter;
        }
        return filed;
    }

    /**
     * 给没有MD5的文件补全MD5
     *
     * @return
     */
    @Override
    public BaseResponse completeFileMd5() {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root)) {
            return BaseResponse.fail(FileMsg.missSysConfig);
        }

        int pageSize = 500;
        int pageNo = 1;

        // 只创建一次 SqlSession
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE, false)) {
            // 关闭一级缓存
            session.getConfiguration().setLocalCacheScope(LocalCacheScope.STATEMENT);
            SysFileAnnexMapper mapper = session.getMapper(SysFileAnnexMapper.class);
            while (true) {
                List<SysFileAnnex> records = mapper.selectPageNoMd5((pageNo - 1) * pageSize, pageSize);
                if (ValidateUtil.isEmpty(records)) {
                    break;
                }
                log.info("文件MD5批量处理 pageNo={}, size={}", pageNo, records.size());
                owner.doBatch(records, root);
                session.clearCache();
                pageNo++;
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 批量处理
     *
     * @param list
     * @param root
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void doBatch(List<SysFileAnnex> list, String root) {
        List<SysFileAnnex> validList = new ArrayList<>();
        for (SysFileAnnex file : list) {
            String path = root + "/" + file.getFilePath();
            File f = new File(path);
            if (f.exists() && f.isFile()) {
                file.setFileMd5Code(SecureUtil.md5(f));
                validList.add(file);
            }
        }
        if (ValidateUtil.isNotEmpty(list)) {
            this.updateBatchById(validList, 100);
        }
    }

    /**
     * 校验文件是否存在以及是否符合，将符合的文件返回
     *
     * @param files
     * @return
     */
    @Override
    public BaseResponse validateFileExist(List<SysFileAnnex> files, String fileType) {
        if (ValidateUtil.isEmpty(files)) {
            return BaseResponse.fail("文件列表不能为空");
        }

        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        OssClientParamsDto param = sysConfigService.getOssParams();
        if (ValidateUtil.isAnObjEmpty(root, archive)) {
            return BaseResponse.fail(FileMsg.missSysConfig);
        }
        List<SysFileAnnex> fileList = new ArrayList<>();
        files.forEach(file -> {
            // 文件路径
            String filePath = file.getFileLocation() == 0 ? root : archive;
            CrcOssUtil.downLocalNotExistFile(param, file.getFilePath(), filePath, file.getFileLocation());
            // 判断文件，传入的fileType为all则不判断类型
            String fileRealPath = filePath + "/" + file.getFilePath();
            if (FileUtil.isFile(fileRealPath) && ("all".equals(fileType) || ("picture".equals(fileType) && FileUtil.isImage(new File(fileRealPath))))) {
                fileList.add(file);
            }
        });
        return BaseResponse.ok(fileList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse copyFileWithFileAnnex(List<SysFileAnnex> entitys, Boolean skipArchive) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root)) {
            return BaseResponse.fail(FileMsg.missSysConfig);
        }
        if (ValidateUtil.isEmpty(entitys)) {
            return BaseResponse.ok();
        }

        List<SysFileAnnex> models = new ArrayList<>();
        OssClientParamsDto conf = sysConfigService.getOssParams();

        entitys.forEach(model -> {
            String oldFileId = model.getFileId();
            try {
                // 复制文件
                String dir = model.getFilePath().substring(0, model.getFilePath().lastIndexOf("/") + 1);
                String newPath = dir + GenerateUUID.getUUID() + "." + model.getFileSuffix();

                FileUtil.copy(root + "/" + model.getFilePath(), root + "/" + newPath);

                model.setFileId(GenerateUUID.getUUID());
                model.setFilePath(newPath);

                if (ValidateUtil.isNotNull(model.getUploadStatus()) && model.getUploadStatus() == 1 && conf != null) {
                    String key = CrcOssUtil.upload(conf, root + "/" + newPath, conf.getOssRootDir() + "/" + dir);
                    if (ValidateUtil.isNotEmpty(key)) {
                        model.setFileUpOss(Boolean.TRUE);
                    }
                } else {
                    model.setFileUpOss(Boolean.FALSE);
                }

                if (skipArchive) {
                    model.setArchive(0);
                    model.setFileLocation(0);
                    model.setFileUnarchiveId(null);
                }

                models.add(model);
            } catch (Exception ex) {
                log.error("文件复制失败！源头文件记录ID：" + oldFileId, ex);
            }
        });

        if (!models.isEmpty()) {
            this.saveBatch(models);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据文件id刷新文件上传状态
     *
     * @param fileId
     * @return
     */
    @Override
    public BaseResponse refreshFileAnnexUploadStatusByFileId(String fileId) {
        if (ValidateUtil.isEmpty(fileId)) {
            return BaseResponse.fail("缺少请求参数");
        }
        SysFileAnnex file = this.getById(fileId);
        if (ValidateUtil.isEmpty(file)) {
            return BaseResponse.fail("文件记录不存在");
        }
        file.setUploadStatus(1);
        this.updateById(file);
        return BaseResponse.ok();
    }
}
