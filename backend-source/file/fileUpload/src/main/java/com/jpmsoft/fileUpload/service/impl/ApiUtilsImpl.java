package com.jpmsoft.fileUpload.service.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.SignAndVerifyDTO;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.SecretStringGenerator;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.ConBoqDtlDto;
import com.jpmsoft.fileUpload.pojo.dto.ConMearDeclareDto;
import com.jpmsoft.fileUpload.pojo.dto.InvBudgetDeclareDtlDto;
import com.jpmsoft.fileUpload.pojo.dto.VueTreeDto;
import com.jpmsoft.fileUpload.service.IApiUtilsService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class ApiUtilsImpl implements IApiUtilsService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BaseResponse getBaseResponseByUrl(String url) {
        BaseResponse data = restTemplate.getForObject(url, BaseResponse.class);
        return data;
    }

    @Override
    public BaseResponse getApproveList(String fileId) {
        FilerecordRequest request = new FilerecordRequest();
        request.setEntityid(fileId);
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://bpm-server/v1/flow/getApproveList";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public BaseResponse getGanttTasksProcessExport(GanttPlanExportRequest request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://project-consumer/plan/v1/ganttTasksProcess/getGanttTasksProcessExport";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<InvBudgetDeclareDtlDto> getInvBudgetDeclareDtlListByPid(String pId) {
        try {
            String url = "http://project-consumer/v1/invBudgetDeclareDtl/getInvBudgetDeclareDtlListByPid?pId=" + pId;
            BaseResponse baseResponse = restTemplate.getForObject(url, BaseResponse.class);
            if (baseResponse.getSuccess() == Boolean.TRUE && baseResponse.getStatusCode() == HttpStatus.SC_OK
                    && ValidateUtil.isNotNull(baseResponse.getData())) {
                List<InvBudgetDeclareDtlDto> data = JacksonUtils.parseArray(JacksonUtils.toJSONString(baseResponse.getData()), InvBudgetDeclareDtlDto.class);
                return data;
            } else
                return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<PreProjectResponse> getAllProjectList(PreProjectRequest request, String uId) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://project-producer/v1/project/getAllProjectList/" + uId;
            List<PreProjectResponse> baseResponse = restTemplate.postForObject(url, formEntity, List.class);
            return baseResponse;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<ConBoqDtlDto> getConBoqDtlByConId(String conId) {
        try {
            String url = "http://project-producer/v1/conbopDtl/getConBoqDtlByConId?conId=" + conId;
            List<ConBoqDtlDto> result = restTemplate.getForObject(url, List.class);
            if (ValidateUtil.isNotEmpty(result)) {
                List<ConBoqDtlDto> datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(result), ConBoqDtlDto.class);
                return datas;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<ConMearDeclareDto> getConMearDeclareByConId(String conId) {
        try {
            String url = "http://project-producer/v1/conMeasureRecord/getConMearDeclareByConId?conId=" + conId;
            List<ConMearDeclareDto> result = restTemplate.getForObject(url, List.class);
            if (ValidateUtil.isNotEmpty(result)) {
                List<ConMearDeclareDto> datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(result), ConMearDeclareDto.class);
                return datas;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<VueTreeDto> getDictionaryQDLBTree() {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(new HashMap<String, Object>().put("code", "JPMsoft_QDLB"));
            String url = "http://user-producer/v1/SystemManage/getDictionaryTree";
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            List<VueTreeDto> datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(baseResponse.getData()), VueTreeDto.class);
            return datas;
        } catch (Exception ex) {
            throw new JPMException(String.format(FileMsg.notFoundDictionary, "JPMsoft_QDLB"));
        }
    }

    @Override
    public String SelfSm2Decrypt(String encDataHex) {
        if (ValidateUtil.isEmpty(encDataHex) || encDataHex.equals("null")) return null;
        String url = "http://api-utils/v1/decrypt/SelfSm2Decrypt?encDataHex=" + encDataHex;
        String data = restTemplate.getForObject(url, String.class);
        return data;
    }


    @Override
    public String sign(InterfaceEncDecDTO ied, Boolean sign, Object object) {
        if (sign) {
            if (ValidateUtil.isNotEmpty(ied.getSignUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(SecretStringGenerator.toString(object));
                BaseResponse res = sendPost(ied.getSignUrl(), sg);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("签名结果:" + res.getData().toString());
                    return res.getData().toString();
                }
                throw new JPMException(FileMsg.dataWzSignError);
            }
            throw new JPMException(String.format(FileMsg.missSysConfig, "interfaceEncDecConfig"));
        } else return null;
    }

    @Override
    public Boolean signVerify(InterfaceEncDecDTO ied, Boolean verify, Object object, String sign) {
        if (verify) {
            if (ValidateUtil.isNotEmpty(ied.getVerifyUrl())) {
                SignAndVerifyDTO sg = new SignAndVerifyDTO();
                sg.setContent(SecretStringGenerator.toString(object));
                sg.setSign(sign);
                BaseResponse res = sendPost(ied.getVerifyUrl(), sg);
                if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                    System.out.println("验签结果:" + res.getData());
                    if (ValidateUtil.isNotNull(res.getData()) && !Boolean.parseBoolean(res.getData() + ""))
                        throw new JPMException(FileMsg.dataVerifyError);
                    else return true;
                }
            }
        } else return true;
        return false;
    }


    public BaseResponse sendPost(String url, Object data) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(data);
            BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
            return baseResponse;
        } catch (Exception ex) {
            return BaseResponse.fail("服务内部调用失败！");
        }
    }

    /**
     * 组装POST请求参数
     *
     * @param object
     * @return
     */
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}
