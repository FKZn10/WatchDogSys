package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.response.FileTemplateinfoResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.FileTemplateMapper;
import com.jpmsoft.jpbpm.pojo.dto.TemplateinitializationDto;
import com.jpmsoft.jpbpm.pojo.entity.*;
import com.jpmsoft.jpbpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileTemplateImpl extends ServiceImpl<FileTemplateMapper, FileTemplate> implements FileTemplateService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private FileService fileService;
    @Autowired
    private SysMultiFormFlowService sysMultiFormFlowService;
    @Autowired
    private SysFileTemplateDtlService sysFileTemplateDtlService;

    @Override
    public BaseResponse getTemplate(TemplateinitializationDto request, String customerId) {
        String templatecode = request.getTemplatecode();
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", templatecode);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        FileTemplate FileTemplateobj = baseMapper.selectOne(wrapper);
        if (FileTemplateobj != null) {
            return new BaseResponse(true, "获取成功!", FileTemplateobj);
        } else
            return new BaseResponse("未设置编码为”" + templatecode + "“的流程模板!");

    }

    @Override
    public BaseResponse getTemplatebyfileid(FilerecordRequest requst, String customerId) {
        FileTemplate FileTemplateobj = null;
        FileTemplateinfoResponse obj = new FileTemplateinfoResponse();
        if (!ValidateUtil.isEmpty(requst.getTemplatecode())) {
            QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
            wrapper.eq("T_Code", requst.getTemplatecode());
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            FileTemplateobj = baseMapper.selectOne(wrapper);


        }
        File fileobj = ValidateUtil.isEmpty(requst.getEntityid()) ? null : fileService.getFileById(requst.getEntityid(), requst.gettCode(), requst.getYear());
        if (fileobj != null) {
            obj.setFileid(fileobj.getFileid());
            Flow flowobj = new Flow();
            if (!ValidateUtil.isEmpty(fileobj.getFlowId())) {
                flowobj = flowService.getById(fileobj.getFlowId());
                if (flowobj != null && FileTemplateobj == null) {
                    FileTemplateobj = baseMapper.selectById(flowobj.getTId());
                }
            } else {
                if (FileTemplateobj == null) {
                    FileTemplateobj = baseMapper.selectById(fileobj.gettId());
                }
            }
        }
        if (fileobj != null) {
            if (FileTemplateobj == null) {
                FileTemplateobj = new FileTemplate();
                FileTemplateobj.settUpdatepage("FileInfo/Details");
                FileTemplateobj.settInfopage("FileInfo/Details");
                FileTemplateobj.settUpdatepage("FileInfo/Details");
            } else if (ValidateUtil.isEmpty(FileTemplateobj.gettUpdatepage())) {
                FileTemplateobj.settUpdatepage("FileInfo/Details");
                FileTemplateobj.settInfopage("FileInfo/Details");
                FileTemplateobj.settFlowpage("FileInfo/Details");
            }

        }
        obj.setTaskboj(ValidateUtil.isEmpty(requst.getTaskid()) ? null : taskService.getById(requst.getTaskid()));
        obj.setFileobj(fileobj);
        obj.setFiletemplate(FileTemplateobj);
        if (FileTemplateobj != null) {
            if (fileobj == null) {
                String  pId = requst.getPid();
                String dId = requst.getdId();
                String pageCode = requst.getPageCode();
                SysFileTemplateDtl ftd = sysFileTemplateDtlService.getInterfaceVersion(FileTemplateobj.gettId(),pageCode,pId,dId);
                obj.setVersionUsed(ftd);
                if (ftd != null) {
                    if (ValidateUtil.isNotEmpty(ftd.getTdInfopage())) {
                        FileTemplateobj.settInfopage(ftd.getTdInfopage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdFlowpage())) {
                        FileTemplateobj.settFlowpage(ftd.getTdFlowpage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdUpdatepage())) {
                        FileTemplateobj.settUpdatepage(ftd.getTdUpdatepage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdAppFlowpage())) {
                        FileTemplateobj.settAppFlowpage(ftd.getTdAppFlowpage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdAppUpdatepage())) {
                        FileTemplateobj.settAppUpdatepage(ftd.getTdAppUpdatepage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdAppInfopage())) {
                        FileTemplateobj.settAppInfopage(ftd.getTdAppInfopage());
                    }
                }
            } else {
                SysFileTemplateDtl ftd = sysFileTemplateDtlService.getInterfaceVersionByCode(FileTemplateobj.gettId(), fileobj.getPageCode());
                obj.setVersionUsed(ftd);
                if (ftd != null) {
                    if (ValidateUtil.isNotEmpty(ftd.getTdInfopage())) {
                        FileTemplateobj.settInfopage(ftd.getTdInfopage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdFlowpage())) {
                        FileTemplateobj.settFlowpage(ftd.getTdFlowpage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdUpdatepage())) {
                        FileTemplateobj.settUpdatepage(ftd.getTdUpdatepage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdAppFlowpage())) {
                        FileTemplateobj.settAppFlowpage(ftd.getTdAppFlowpage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdAppUpdatepage())) {
                        FileTemplateobj.settAppUpdatepage(ftd.getTdAppUpdatepage());
                    }
                    if (ValidateUtil.isNotEmpty(ftd.getTdAppInfopage())) {
                        FileTemplateobj.settAppInfopage(ftd.getTdAppInfopage());
                    }
                }
            }
            return new BaseResponse("", obj, 1);

        } else {
            return new BaseResponse("未设置流程模板!");
        }
    }

    @Override
    public BaseResponse getTemplateList(FilerecordRequest requst, String customerId) {
        List<FileTemplate> FileTemplateobj = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        List<String> tCodeList = new ArrayList<>();
        if (!ValidateUtil.isEmpty(requst.getTemplatecode())) {
            if (requst.getTemplatecode().contains(",")) {
                tCodeList = Arrays.stream(requst.getTemplatecode().split(",")).collect(Collectors.toList());
            } else {
                tCodeList.add(requst.getTemplatecode());
            }

        }
        if (!ValidateUtil.isEmpty(requst.getEntityid())) {
            //获取关联表单信息
            SysMultiFormFlow obj = new SysMultiFormFlow();
            obj.setMffMainFileId(requst.getEntityid());
            List<SysMultiFormFlow> list = sysMultiFormFlowService.GetSysMultiFormFlow(obj, customerId);
            if (list.size() > 0) {
                if (tCodeList.size() <= 0) {
                    tCodeList = list.stream().map(n -> n.gettCode()).collect(Collectors.toList());
                }
                List<String> fileidlist = list.stream().map(n -> n.getMffFileId()).collect(Collectors.toList());
                fileList = fileService.getListByIds(fileidlist, requst.gettCode(), requst.getYear());
            }

        }
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        wrapper.in("T_Code", tCodeList);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        FileTemplateobj = baseMapper.selectList(wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("template", FileTemplateobj);
        data.put("file", fileList);
        data.put("task", taskService.getById(requst.getTaskid()));
        return new BaseResponse("", data, 1);
    }

    @Override
    public FileTemplate getTemplatebyTCode(String tCode, String customerId) {
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", tCode);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        FileTemplate pojo = baseMapper.selectOne(wrapper);
        return pojo;
    }
}
