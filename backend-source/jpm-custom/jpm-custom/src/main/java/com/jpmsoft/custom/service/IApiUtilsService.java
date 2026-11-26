package com.jpmsoft.custom.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

public interface IApiUtilsService {

    /**
     * web传输数据 解密字段
     *
     * @param encDataHex
     * @return
     */
    String SelfSm2Decrypt(String encDataHex);

    /**
     * 根据编码规则生成编码
     *
     * @param tCode
     * @param request
     * @return
     */
    String generateCodeByTCode(String tCode, Object request);

    /**
     * 根据编码规则生成编码
     *
     * @param tCode
     * @param pId
     * @param conId
     * @param secId
     * @param request
     * @return
     */
    String generateCodeByCustomized(String tCode, String pId, String conId, String secId, Object request);

    /**
     * 刷新自增序号
     *
     * @param tCode
     * @param request
     * @return
     */
    BaseResponse refershAutoNo(String tCode, Object request);

    /**
     * 刷新自增序号
     *
     * @param tCode
     * @param pId
     * @param conId
     * @param secId
     * @param request
     * @return
     */
    BaseResponse refershAutoNoByCustomized(String tCode, String pId, String conId, String secId, Object request);

    /**
     * 校验编码规则是否启用
     *
     * @param tCode
     * @return
     */
    Boolean sequenceRestEnable(String tCode);

    BaseResponse createOrDeletePUPByBath(List<ReportedPersonnelDtlModel> datas);

    /**
     * 获取用户所有组织机构及其下机组织机构ID
     *
     * @param uId
     * @return
     */
    List<String> getAllChildDepartment(String uId);

    /**
     * 获取自动编码
     *
     * @param rule    编码规则
     * @param request 请求入参实体
     * @return
     */
    String getAutoCode(String rule, Object request);

    /**
     * 获取用户密码加密后结果
     *
     * @param passWord
     * @param salt
     * @return
     */
    String PasswrodEncryptionResult(String passWord, String salt);

    /**
     * post请求
     * @param url 请求地址
     * @param key   通用表单创建主表单ID
     * @param data  body入参
     * @return
     */
    BaseResponse sendPost(String url,String key, Object data);
}
