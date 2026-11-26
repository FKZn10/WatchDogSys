package com.jpmsoft.projectProducer.service.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelDtlModel;
import com.jpmsoft.projectProducer.pojo.dto.base.VueTreeDto;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;

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
     * 实现和前端一样的加密
     *
     * @param json
     * @return
     */
    String Sm2EncryptionFront(String json, String publicKey);

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

    /**
     * 字段变更记录
     *
     * @param olds
     * @param news
     * @param tableName
     * @param uId
     * @return
     */
    BaseResponse insertFiledChangeRecord(Object olds, Object news, String tableName, String uId);

    /**
     * 批量
     *
     * @param olds
     * @param news
     * @param tableName
     * @param uId
     * @return
     */
    BaseResponse insertFiledChangeRecordBath(List<Object> olds, List<Object> news, String tableName, String uId);

    BaseResponse createOrDeletePUPByBath(List<ReportedPersonnelDtlModel> datas);

    /**
     * 获取用户所有组织机构及其下机组织机构ID
     *
     * @param uId
     * @return
     */
    List<String> getAllChildDepartment(String uId);

    /**
     * 获取字典
     *
     * @param code
     * @return
     */
    List<VueTreeDto> getDictionaryTree(String code);

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
    String PasswrodEncryptionResult(String passWord, String salt, String account);

    String PasswrodEncryptionResultNoToken(String passWord, String salt, String account);

    BaseResponse sendPost(String url, Object data);

    /**
     * 退回流程到发起人
     *
     * @param returnInitiatorDto
     * @return
     */
    BaseResponse rejectFlowToInitiator(ReturnInitiatorDto returnInitiatorDto);

    String getFileTemplateTableById(String id);


    /**
     * 签名
     *
     * @param ied    基础配置
     * @param sign   是否生成签名
     * @param object 实体类对象@SecretField注解自动生成明文
     * @return
     */
    String sign(InterfaceEncDecDTO ied, Boolean sign, Object object);

    /**
     * 签名验证
     *
     * @param ied    基础配置
     * @param verify 是否验证签名
     * @param object 实体类对象@SecretField注解自动生成明文
     * @param sign   签名密文
     * @return
     */
    Boolean signVerify(InterfaceEncDecDTO ied, Boolean verify, Object object, String sign);
}
