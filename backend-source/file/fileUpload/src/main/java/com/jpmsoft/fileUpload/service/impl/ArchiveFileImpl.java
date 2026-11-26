package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.ArchiveFileMapper;
import com.jpmsoft.fileUpload.mapper.ArchiveFilingApplyMapper;
import com.jpmsoft.fileUpload.pojo.dto.FormDataImpArchiveDTO;
import com.jpmsoft.fileUpload.pojo.dto.FullTextDTO;
import com.jpmsoft.fileUpload.pojo.dto.LuceneFileDTO;
import com.jpmsoft.fileUpload.pojo.entity.*;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MathUtil;
import com.jpmsoft.fileUpload.utils.lucene.Indexer;
import com.jpmsoft.fileUpload.utils.lucene.Searcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 13:05
 */
@Service
public class ArchiveFileImpl extends ServiceImpl<ArchiveFileMapper, ArchiveFile> implements IArchiveFileService {

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private Searcher searcher;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ArchiveFilingApplyMapper archiveFilingApplyMapper;
    @Resource
    private IArchiveDirectoryService archiveDirectoryService;
    @Resource
    private IArchiveService archiveService;
    @Resource
    private IArchiveFileService archiveFileService;
    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Override
    public String getAdLinkByAdId(String adId) {
        if (ValidateUtil.isEmpty(adId)) return null;
        return baseMapper.getAdLinkByAdId(adId);
    }

    /**
     * 支持的文件格式
     */
    private final static List<String> sfx = Arrays.asList("xlsx", "xls", "doc", "docx", "ppt", "pptx", "txt", "pdf");

    @Override
    public int createArchiveFileLuceneIndex() {
        List<LuceneFileDTO> data = baseMapper.getUnIndexArchiveFile(sfx);
        if (!CollectionUtils.isEmpty(data)) {
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            String indexDir = sysConfigService.getSysConfByCode("luceneIndexDir");
            if (ValidateUtil.isAnObjEmpty(root, archive, indexDir)) throw new JPMException(FileMsg.missFileDirectory);
            List<LuceneFileDTO> indexFiles = new ArrayList<>();
            for (LuceneFileDTO d : data) {
                String suffix = d.getFilePath().substring(d.getFilePath().lastIndexOf('.') + 1);
                if (ValidateUtil.isNotEmpty(suffix) && sfx.contains(suffix.toLowerCase(Locale.ENGLISH))) {
                    d.setFilePath(d.getFileLocation() == 1 ? archive + "/" + d.getFilePath() : root + "/" + d.getFilePath());
                    File file = new File(d.getFilePath());
                    if (file.exists() && file.isFile()) indexFiles.add(d);
                }
            }
            if (!CollectionUtils.isEmpty(indexFiles)) {
                int count = Indexer.makeIndex(indexFiles, indexDir);
                System.out.println("本次创建索引文件" + count + "个");
                //更新已创建索引的标记
                baseMapper.updateArchiveFileIndex(indexFiles.stream().map(LuceneFileDTO::getAfId).collect(Collectors.toList()), Boolean.TRUE);
                return count;
            }
        }
        return 0;
    }

    @Override
    public BaseResponse fullSearch(FullTextDTO request) {
        if (ValidateUtil.isEmpty(request.getText())) return BaseResponse.ok();
        try {
            List<String> afIds = searcher.search(request);
            if (!CollectionUtils.isEmpty(afIds)) return BaseResponse.ok(baseMapper.getArchiveFileStatistics(afIds));
        } catch (Exception ex) {
            return BaseResponse.fail();
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse archiveProjectFile(String faId) {
        ArchiveFilingApply entity = archiveFilingApplyMapper.selectById(faId);
        if (entity == null) return BaseResponse.fail(FileMsg.infoNotExist);
        if (!Objects.equals(entity.getFaState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(FileMsg.stateCannotAct);
        if (ValidateUtil.isEmpty(entity.getPId())) return BaseResponse.fail(FileMsg.lackProInfo);
        List<ArchiveFile> data = baseMapper.getProjectArchiveUpFile(entity.getPId());
        if (!CollectionUtils.isEmpty(data)) {
            List<String> annexIds = new ArrayList<>();
            Set<String> apIds = new HashSet<>();
            data = data.stream().peek(d -> {
                d.setAfState(1);
                annexIds.add(d.getAfAnnexId());
                apIds.add(d.getApId());
            }).collect(Collectors.toList());
            sysFileAnnexService.updateFileArchiveStatus(annexIds);
            updateBatchById(data);
            archiveService.refreshApStateBath(new ArrayList<>(apIds), 1);
        }
        return BaseResponse.ok(FileMsg.archiveSuccess);
    }

    @Override
    public List<ArchiveFileResponse> getByAdIds(List<String> ids, String pid) {
        return baseMapper.getByAdIds(ids, pid);
    }

    @Override
    public void formImpArchive(List<FormDataImpArchiveDTO> archives, LogUserInfoVo user, String root, String archive) {
        if (!CollectionUtils.isEmpty(archives)) {
            List<String> adCodes = new ArrayList<>();
            archives.forEach(e -> {
                if (ValidateUtil.isNotEmpty(e.getAdCode()) && !CollectionUtils.isEmpty(e.getFiles())) {
                    String[] codes = e.getAdCode().split(",");
                    adCodes.addAll(Arrays.asList(codes));
                }
            });
            List<ArchiveDirectory> dirs = archiveDirectoryService.getDirByCodes(adCodes);
            if (!CollectionUtils.isEmpty(dirs)) {
                List<Archive> ars = new ArrayList<>();
                List<ArchiveFile> ariFiles = new ArrayList<>();
                List<SysFileAnnex> fileAnnexes = new ArrayList<>();
                int order = 1;
                for (FormDataImpArchiveDTO a : archives) {
                    if (ValidateUtil.isNotEmpty(a.getAdCode()) && !CollectionUtils.isEmpty(a.getFiles())) {
                        Date now = new Date();
                        for (SysFileAnnex file : a.getFiles()) {
                            List<String> codes = Arrays.asList(a.getAdCode().split(","));
                            List<ArchiveDirectory> ads = dirs.stream().filter(f -> codes.contains(f.getAdCode())).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(ads))
                                for (ArchiveDirectory ad : ads) {
                                    if (ad.getAdType().equals("01") && ValidateUtil.isEmpty(a.getPId())) continue;
                                    SysFileAnnex nf=new SysFileAnnex();
                                    BeanUtils.copyProperties(file,nf);
                                    nf.setArchive(1);
                                    nf.setFileLocation(1);
                                    nf.setFileId(GenerateUUID.getUUID());
                                    String source = root + "/" + nf.getFilePath();
                                    nf.setFilePath(archivePath(user.getId(), nf.getFileSuffix(), ad.getAdId()));
                                    FileUtil.copy(source, archive + nf.getFilePath());
                                    fileAnnexes.add(nf);
                                    Archive ar = new Archive();
                                    ar.setAdId(ad.getAdId());
                                    ar.setApId(GenerateUUID.getUUID());
                                    if (ad.getAdType().equals("01"))
                                        ar.setPId(a.getPId());
                                    ar.setApDescCreateUid(user.getId());
                                    ar.setApDescCreateUname(user.getName());
                                    ar.setApDescCreateTime(now);
                                    ar.setApState(1);
                                    ar.setApTitle(ad.getAdName());
//                                    ar.setApTitle(nf.getFileName().substring(0, nf.getFileName().length() - nf.getFileSuffix().length() - 1));
                                    ars.add(ar);
                                    ArchiveFile af = new ArchiveFile();
                                    af.setAfId(GenerateUUID.getUUID());
                                    af.setAfType(nf.getFileSuffix());
                                    af.setAfOrder(order);
                                    af.setApId(ar.getApId());
                                    af.setAfAnnexId(nf.getFileId());
                                    af.setAfCreateUid(user.getId());
                                    af.setAfCreateUname(user.getName());
                                    af.setAfCreateTime(now);
                                    af.setAfFrom(1);
                                    af.setAfName(nf.getFileName());
                                    af.setAfState(1);
                                    af.setAfIndex(Boolean.FALSE);
                                    af.setAfFileSize(MathUtil.FloatDivisionInt(Float.valueOf(nf.getFileSize()), 1024 * 1024));
                                    ariFiles.add(af);
                                }
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(fileAnnexes)) sysFileAnnexService.saveBatch(fileAnnexes);
                if (!CollectionUtils.isEmpty(ars)) archiveService.saveBatch(ars);
                if (!CollectionUtils.isEmpty(ariFiles)) archiveFileService.saveBatch(ariFiles);
            }
        }
    }

    private String archivePath(String uid, String fileSuffix, String adId) {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + adId + "/" + uid + "/" + dateDir;
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = GenerateUUID.getUUID();
        return filePath + "/" + fileName + "." + fileSuffix;
    }
}
