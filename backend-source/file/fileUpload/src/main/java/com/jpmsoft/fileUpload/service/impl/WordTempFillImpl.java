package com.jpmsoft.fileUpload.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.pojo.dto.aspose.QsPlanTaskNodeDTO;
import com.jpmsoft.fileUpload.pojo.dto.aspose.QsPlanTaskWordDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysDictionaryService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.IWordTempFillService;
import com.jpmsoft.fileUpload.utils.AsposeUtil;
import com.jpmsoft.fileUpload.utils.DateUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/10 8:52
 */
@Service
public class WordTempFillImpl implements IWordTempFillService {

    @Resource
    private PmFeign pmFeign;
    @Resource
    private ISysDictionaryService sysDictionaryService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysConfigService sysConfigService;
    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Override
    public void exportQsPlanTaskWord(HttpServletResponse response, String id, String tmpCode) {
        BaseResponse res = pmFeign.getQsPlanTaskById(id);
        if (res != null && res.getSuccess()) {
            Map data = JacksonUtils.parseObject(JacksonUtils.toJSONString(res.getData()), Map.class);
            QsPlanTaskWordDTO qpt = new QsPlanTaskWordDTO();
            qpt.setPtCode(getStr(data, "ptCode"));
            qpt.setPName(getStr(data, "pName"));
            qpt.setLeaderName(getStr(data, "ptGroupMemberName"));
            qpt.setCheckDate(getTime(data, "ptEvaluationTime", "yyyy-MM-dd"));
            qpt.setPtMeasure(getDic(data, "ptMeasure", "JPMsoft_AQZGCL"));
            qpt.setUnitName(getStr(data, "ptContractorName") + "/" + getStr(data, "ptSupervisorName"));
            qpt.setPtType(getDic(data, "ptType", "JPMsoft_XJLX"));
            qpt.setPtRectifyRequireTime(getTime(data, "ptRectifyRequireTime", "yyyy-MM-dd"));
            qpt.setPtRectifyReply(getStr(data, "ptRectifyReply"));
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            SysFileAnnex temp = sysFileAnnexService.getOneFileAnnexByRelationId(sysConfigService.getSysConfByCode(tmpCode));
            String tempPath = root + "/" + temp.getFilePath();
            String[] tmp = tempPath.split("\\.");
            String suffix = "." + tmp[tmp.length - 1];
            String path = root + "/" + dirPath + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID() + suffix;
            if (data.containsKey("qsRecords") && data.get("qsRecords") != null) {
                List<Map> qsRecords = JacksonUtils.parseArray(JacksonUtils.toJSONString(data.get("qsRecords")), Map.class);
                List<QsPlanTaskNodeDTO> qsr = new ArrayList<>();
                List<SysDictionary> qptd = sysDictionaryService.getDictionaryByParentCode("JPMsoft_XJWTLX");
                List<SysDictionary> qrtd = sysDictionaryService.getDictionaryByParentCode("JPMsoft_SGLX");
                List<SysDictionary> qfd = sysDictionaryService.getDictionaryByParentCode("JPMsoft_AQYHYY");
                List<String> relationIds = qsRecords.stream().filter(f -> f.containsKey("qsrPic") && ValidateUtil.isNotEmpty(f.get("qsrPic").toString())).map(d -> d.get("qsrPic").toString()).collect(Collectors.toList());
                relationIds.addAll(qsRecords.stream().filter(f -> f.containsKey("qsrRectifyPic") && ValidateUtil.isNotEmpty(f.get("qsrRectifyPic").toString())).map(d -> d.get("qsrRectifyPic").toString()).collect(Collectors.toList()));
                List<SysFileAnnex> files = sysFileAnnexService.getFileByRelationIds(relationIds);
                for (Map qs : qsRecords) {
                    QsPlanTaskNodeDTO q = new QsPlanTaskNodeDTO();
                    q.setQsrFormation(getDic(qs, "qsrFormation", qfd));
                    q.setQsrDes(getStr(qs, "qsrDes"));
                    q.setQsrRectifyReply(getStr(qs, "qsrRectifyReply"));
                    q.setQsrLocation(getStr(qs, "qsrLocation"));
                    q.setQsrReasonType(getDic(qs, "qsrReasonType", qrtd));
                    q.setQsrProblemType(getDic(qs, "qsrProblemType", qptd));
                    String relationId = getStr(qs, "qsrPic");
                    String rectifyReId = getStr(qs, "qsrRectifyPic");
                    if (ValidateUtil.isNotEmpty(relationId)) {
                        List<SysFileAnnex> file = files.stream().filter(f -> f.getFileRelationId().equals(relationId)).collect(Collectors.toList());
                        q.setFiles(file);
                    }
                    if (ValidateUtil.isNotEmpty(rectifyReId)) {
                        List<SysFileAnnex> file = files.stream().filter(f -> f.getFileRelationId().equals(rectifyReId)).collect(Collectors.toList());
                        q.setRectifyFiles(file);
                    }
                    qsr.add(q);
                }
                qpt.setQsRecords(qsr);
                AsposeUtil.tempExportWord(tempPath, path, qpt, "s", getImgs(files, root, archive), 375D, 225D);
            } else
                AsposeUtil.tempExportWord(tempPath, path, qpt, "s", null, 375D, 225D);
            FileUtil.downFileAndDelFile(response, path, name(tmpCode) + "（" + DateUtil.Date2String(new Date(), "yyyyMMddHHMMss") + ")" + suffix);
        }
    }

    @Override
    public void exportQsPlanTaskExcel(HttpServletResponse response, String id) {
        BaseResponse res = pmFeign.getQsPlanTaskById(id);
        if (res != null && res.getSuccess()) {

        } else throw new JPMException("未获取到数据源，无法导出!");
    }


    private String name(String tmpCode) {
        String name = "";
        switch (tmpCode) {
            case "inspectionNoticeWordTemp":
                name = "安全检查结果通知单";
                break;
            case "rectificationNoticeWordTmp":
                name = "安全检查整改回复单";
                break;
            case "qualityNoticeWordTemp":
                name = "质量检查结果通知单";
                break;
            case "qualityNoticeWordTmp":
                name = "质量检查整改回复单";
                break;
        }
        return name;
    }


    private Map<String, String> getImgs(List<SysFileAnnex> files) {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        Map<String, String> imgs = new HashMap<>();
        files.forEach(f -> {
            imgs.put(f.getFileId(), f.getFileLocation() == 1 ? archive + "/" + f.getFilePath() : root + "/" + f.getFilePath());
        });
        return imgs;
    }

    private Map<String, String> getImgs(List<SysFileAnnex> files, String root, String archive) {
        Map<String, String> imgs = new HashMap<>();
        files.forEach(f -> {
            imgs.put(f.getFileId(), f.getFileLocation() == 1 ? archive + "/" + f.getFilePath() : root + "/" + f.getFilePath());
        });
        return imgs;
    }

    /**
     * 时间字段
     */
    private String getTime(Map data, String key, String format) {
        if (data.containsKey(key)) {
            return convertDate(data.get(key).toString(), format);
        }
        return null;
    }

    //将带有T的时间字符串转换成yyyy-MM-dd HH:mm:ss
    public static String convertDate(String strDate, String fmt) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = formatter.parse(strDate);
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            String sDate = sdf.format(date);
            return sDate;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串
     */
    private String getStr(Map data, String key) {
        if (data.containsKey(key))
            if (data.get(key) != null)
                return data.get(key).toString();
        return null;
    }

    /**
     * 字典编码转字典名称
     */
    private String getDic(Map data, String key, String dic) {
        List<SysDictionary> dirs = sysDictionaryService.getDictionaryByParentCode(dic);
        if (CollectionUtils.isEmpty(dirs)) throw new JPMException(String.format(FileMsg.notFoundDictionary, dic));
        String k = getStr(data, key);
        if (ValidateUtil.isEmpty(k)) return null;
        else {
            List<SysDictionary> ds = dirs.stream().filter(n -> n.getDCode().equals(k)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(ds)) return ds.stream().findFirst().get().getDName();
        }
        return null;
    }

    /**
     * 字典编码转字典名称
     */
    private String getDic(Map data, String key, List<SysDictionary> dirs) {
        if (CollectionUtils.isEmpty(dirs)) return null;
        String k = getStr(data, key);
        if (ValidateUtil.isEmpty(k)) return null;
        else {
            if (k.contains(",")) {
                List<SysDictionary> ds = dirs.stream().filter(n -> k.contains(n.getDCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ds))
                    return ds.stream().map(n -> n.getDName()).collect(Collectors.joining(","));
            } else {
                List<SysDictionary> ds = dirs.stream().filter(n -> n.getDCode().equals(k)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ds)) return ds.stream().findFirst().get().getDName();
            }
        }
        return null;
    }
}
