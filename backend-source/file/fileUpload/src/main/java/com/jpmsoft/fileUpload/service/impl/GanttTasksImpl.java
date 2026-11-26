package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.EasyExcelData;
import com.jpmsoft.fileUpload.enums.FileSourceEnum;
import com.jpmsoft.fileUpload.mapper.GanttTasksMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.GanttTaskFeedbackDTO;
import com.jpmsoft.fileUpload.pojo.entity.GanttTasks;
import com.jpmsoft.fileUpload.pojo.entity.GanttTasksProcess;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import com.jpmsoft.fileUpload.utils.MathUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GanttTasksImpl extends ServiceImpl<GanttTasksMapper, GanttTasks> implements IGanttTasksService {

    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IFileUploadService fileUploadService;
    @Resource
    private IGanttTasksProcessService ganttTasksProcessService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    private String getRoot() {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        return root;
    }

    @Override
    public void exportGanttTaskFeedback(String planId, HttpServletResponse response) {
        String root = getRoot();
        List<GanttTasksResponse> data = baseMapper.exportGanttTaskFeedback(planId);
        GanttTasksResponse gt = data.stream().findFirst().get();
        String pName = gt.getPName().replaceAll(".", "").replaceAll("/", "").replaceAll("\\\\", "");
        String fileName = gt.getPlanName() + "(" + pName + ")进度计划执行反馈.xlsx";
        File rootDir = new File(root + "/" + dirPath + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID() + "/");
        rootDir.mkdirs();
        String filePath = excelMake(data, rootDir.getPath(), fileName);
        FileUtil.downFileAndDelFile(response, filePath, fileName);
    }

    @Override
    public void exportGanttTaskFeedBackZip(String planId, HttpServletResponse response) {
        String root = getRoot();
        List<GanttTasksResponse> data = baseMapper.exportGanttTaskFeedback(planId);
        GanttTasksResponse gt = data.stream().findFirst().get();
        String pName = gt.getPName().replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", "");
        String fileName = gt.getPlanName() + "(" + pName + ")进度计划执行反馈.xlsx";
        String tempDir = root + "/" + dirPath + "/JPBPMTemporaryFolder/";
        String rootPath = tempDir + GenerateUUID.getUUID() + "/";
        File rootDir = new File(rootPath);
        rootDir.mkdirs();
        String excelPath = excelMake(data, rootPath, fileName);
        File fileDir = new File(rootPath + "files");
        fileDir.mkdirs();
        String downZip = tempDir + GenerateUUID.getUUID() + ".zip";
        FileUtil.zip(rootPath, downZip);
        FileUtil.downFileAndDelFile(response, downZip, "进度计划执行反馈.zip");
        FileUtil.deleteFiles(new File(rootPath), true);
    }

    @Override
    public BaseResponse importGanttTaskFeedback(MultipartFile file, LogUserInfoVo user) {
        List<GanttTaskFeedbackDTO> feedbacks = new EasyExcelData<GanttTaskFeedbackDTO>().getExcelData(file, GanttTaskFeedbackDTO.class, 1);
        if (!CollectionUtils.isEmpty(feedbacks)) {
            List<String> taskIds = feedbacks.stream().map(GanttTaskFeedbackDTO::getTaskId).collect(Collectors.toList());
            List<GanttTasks> entities = idsSelect(taskIds);
            List<GanttTasks> updates = new ArrayList<>();
            for (GanttTasks entity : entities) {
                List<GanttTaskFeedbackDTO> fd = feedbacks.stream().filter(f -> f.getTaskId().equals(entity.getTaskId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(fd)) {
                    GanttTaskFeedbackDTO one = fd.stream().findFirst().get();
                    entity.setTaskProgress(one.getTaskProgress());
                    updates.add(entity);
                }
            }
            if (!CollectionUtils.isEmpty(updates)) updateBatchById(updates);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse importGanttTaskFeedbackZip(MultipartFile file, LogUserInfoVo user) throws Exception {
        String root = getRoot();
        File tfFile = fileUploadService.temporaryStorageFile(root, file, ".*\\.(zip)$", FileMsg.onlyZip, "universalImport");
        String decPath = root + "/JPBPMTemporaryFolder/decompression/" + GenerateUUID.getUUID();
        FileUtil.decompression(tfFile.getPath(), decPath);
        File[] excels = FileUtil.findExcel(decPath);
        if (excels == null || excels.length < 1) {
            FileUtil.deleteFile(tfFile);
            return BaseResponse.fail(FileMsg.notFoundExcel);
        }
        try {
            for (File excel : excels) {
                List<GanttTaskFeedbackDTO> data = ExcelUtil.importGtZip(excel);
                if (!CollectionUtils.isEmpty(data)) {
                    List<String> taskIds = data.stream().map(GanttTaskFeedbackDTO::getTaskId).collect(Collectors.toList());
                    List<GanttTasks> entities = idsSelect(taskIds);
//                    List<GanttTasksProcess> gtps = ganttTasksProcessService.getByTaskIds(taskIds);
                    List<GanttTasksProcess> gtpAdds = new ArrayList<>();
                    List<GanttTasks> updates = new ArrayList<>();
                    List<SysFileAnnex> files = new ArrayList<>();
                    Date now = new Date();
                    for (GanttTasks entity : entities) {
                        List<GanttTaskFeedbackDTO> fd = data.stream().filter(f -> f.getTaskId().equals(entity.getTaskId())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(fd)) {
                            GanttTaskFeedbackDTO one = fd.stream().findFirst().get();
                            if (one.getGtpRate() == 0F) continue;//本次未填报
                            entity.setTaskBeginTime(one.getTaskBeginTime());
                            entity.setTaskFinishTime(one.getTaskFinishTime());
//                            List<GanttTasksProcess> gtpc = gtps.stream().filter(f -> f.getTaskId().equals(entity.getTaskId())).collect(Collectors.toList());
//                            Float rate = 0f;
//                            if (!CollectionUtils.isEmpty(gtpc)) {
//                                for (GanttTasksProcess g : gtpc) {
//                                    rate = MathUtil.floatAddFloat(rate, g.getGtpRate());
//                                }
//                                rate = MathUtil.floatAddFloat(rate, one.getGtpRate());
//                            } else {
//                                rate = one.getGtpRate();
//                            }
                            if (MathUtil.floatAddFloat(entity.getTaskProgress(), one.getGtpRate()) > 100F)
                                throw new JPMException(String.format(FileMsg.ganttRateOut, one.getTaskText()));
                            entity.setTaskProgress(MathUtil.floatAddFloat(entity.getTaskProgress(), one.getGtpRate()));
                            updates.add(entity);
                            GanttTasksProcess gtp = new GanttTasksProcess();
                            gtp.setTaskId(one.getTaskId());
                            gtp.setGtpId(GenerateUUID.getUUID());
                            gtp.setGtpDate(one.getGtpDate());
                            gtp.setGtpRate(one.getGtpRate());
                            gtp.setGtpImageProgress(one.getGtpImageProgress());
                            gtp.setGtpCreateTime(now);
                            gtp.setGtpCreateUname(user.getName());
                            gtp.setGtpCreateUid(user.getId());
                            gtp.setGtpState(ApprovalStatusEnum.Already_Over.getIndex());
                            gtp.setGtpStateTime(now);
                            gtp.setGtpScenePhotos(GenerateUUID.getUUID());
                            gtpAdds.add(gtp);
                            List<String> filePaths = JacksonUtils.parseArray(one.getGtpScenePhotos(), String.class);
                            if (!CollectionUtils.isEmpty(filePaths)) {
                                String regex = sysConfigService.getSysConfByCode("fileUploadXzRegex");
                                for (String name : filePaths) {
                                    String path = name.replace("files\\\\", "files/");
                                    path = decPath + "/" + path;
                                    File f = new File(path);
                                    if (f.exists() && f.isFile()) {
                                        if (fileUploadService.checkFileType(f.getName(), regex)) {//排除非法文件
                                            String[] fName = f.getName().split("\\.");
                                            String fileSuffix = fName[fName.length - 1];
                                            SysFileAnnex annex = new SysFileAnnex();
                                            annex.setFileSize(f.length());
                                            annex.setFileId(GenerateUUID.getUUID());
                                            annex.setFileSourceType(FileSourceEnum.UserUpload.getIndex());
                                            annex.setArchive(0);
                                            annex.setFileLocation(0);
                                            annex.setUploadStatus(1);
                                            annex.setFileReplace(0);
                                            annex.setFileUploadTime(now);
                                            annex.setFileRelationId(gtp.getGtpScenePhotos());
                                            annex.setFileName(f.getName());
                                            annex.setFileSuffix(fileSuffix);
                                            annex.setTeId(user.getTeId());
                                            annex.setFileUpOss(Boolean.FALSE);
                                            annex.setFileCreateUId(user.getId());
                                            annex.setFileCreateUName(user.getName());
                                            annex.setFilePath(rootPath(root, user.getId(), fileSuffix, "PlanFeedback"));
                                            annex.setFileMd5Code(MD5File.getFileMD5Str(f));
                                            //拷贝迁移文件
                                            FileUtil.copy(f.getPath(), root + annex.getFilePath());
                                            files.add(annex);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ganttTasksProcessService.create(gtpAdds);
                    if (!CollectionUtils.isEmpty(updates)) updateBatchById(updates);
                    if (!CollectionUtils.isEmpty(files)) sysFileAnnexService.saveBatch(files);
                }
            }
        } finally {
            FileUtil.deleteFiles(new File(decPath), true);
            FileUtil.deleteFile(tfFile);
        }
        return BaseResponse.ok();
    }


    private String rootPath(String root, String uid, String fileSuffix, String code) {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + code + "/" + uid + "/" + dateDir;
        String fileDirPath = root + "/" + filePath;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = GenerateUUID.getUUID();
        return filePath + "/" + fileName + "." + fileSuffix;
    }

    private String excelMake(List<GanttTasksResponse> data, String excelPath, String excelName) {
        if (!CollectionUtils.isEmpty(data)) {
            List<GanttTaskFeedbackDTO> exps = new ArrayList<>();
            List<GanttTasksResponse> tops = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getTaskParent()) && f.getTaskParent().equals("0")).collect(Collectors.toList());
            List<List<Integer>> rowFontIndex = new ArrayList<>();
            if (!CollectionUtils.isEmpty(tops)) {
                //按树形顺序
                for (GanttTasksResponse top : tops) {
                    GanttTaskFeedbackDTO gt = new GanttTaskFeedbackDTO();
                    BeanUtils.copyProperties(top, gt);
                    exps.add(gt);
                    rowFontIndex.add(Arrays.asList(4));
                    addChild(rowFontIndex, data, exps, top.getTaskId(), gt.getTaskText());
                }
            }
            if (!CollectionUtils.isEmpty(exps)) {
                String filePath = excelPath + excelName;
                ExcelUtil.export2File(exps, GanttTaskFeedbackDTO.class, filePath, "进度计划执行反馈");
                //endregion
                return filePath;
            } else throw new JPMException(FileMsg.notData2Export);
        } else throw new JPMException(FileMsg.notData2Export);
    }

    private List<GanttTasks> idsSelect(List<String> taskIds) {
        QueryWrapper<GanttTasks> wrapper = new QueryWrapper<>();
        wrapper.in("Task_Id", taskIds);
        return baseMapper.selectList(wrapper);
    }

    private void addChild(List<List<Integer>> rowFontIndex, List<GanttTasksResponse> data, List<GanttTaskFeedbackDTO> exps, String parent, String parentName) {
        List<GanttTasksResponse> tops = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getTaskParent()) && f.getTaskParent().equals(parent)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(tops)) {
            for (GanttTasksResponse top : tops) {
                GanttTaskFeedbackDTO gt = new GanttTaskFeedbackDTO();
                BeanUtils.copyProperties(top, gt);
                gt.setTaskParentText(parentName);
                exps.add(gt);
                rowFontIndex.add(new ArrayList<>());
                addChild(rowFontIndex, data, exps, top.getTaskId(), parentName + "-" + gt.getTaskText());
            }
        }
    }
}
