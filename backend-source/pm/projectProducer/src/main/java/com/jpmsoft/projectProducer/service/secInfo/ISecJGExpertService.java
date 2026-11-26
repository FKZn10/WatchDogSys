package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertVacationDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpert;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISecJGExpertService extends IService<SecJGExpert> {


    BaseResponse createOrUpdateSecJGExpert(SecJGExpertModel request);

    /**
     * 身份证取当前创建人
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSecJGExpertCurrent(SecJGExpertModel request);

    /**
     * 身份证取当前创建人
     * 并验证数据唯一
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSecJGExpertVerify(SecJGExpertModel request);

    /**
     * 重置专家库明文身份证件
     * @return
     */
    BaseResponse resetJGPlaintextNum();

    /**
     * 分页查询专家库
     * @param request
     * @return
     */
    BaseResponse getSecJGExpertList(SecJGExpertModel request);

    /**
     * 分页查询专家库(城建定制专家鉴权逻辑)
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getSecJGExpertCjDzList(SecJGExpertModel request, String uid);

    Workbook exportExcelSecJGExpertCjDzList(SecJGExpertModel request, String uid);

    /**
     * 外部专家模板
     * @param request
     * @param uid
     * @return
     */
    Workbook excelSecJGExpertTemplate(SecJGExpertModel request, String uid);


    /**
     * 外部专家导入
     * @param file
     * @param user
     */
    BaseResponse importSecJGExpert(MultipartFile file, LogUserInfoVo user) throws IOException;

    /**
     * ID查询专家详情
     * @param exId
     * @return
     */
    BaseResponse getSecJGExpertById(String exId);

    /**
     * ID删除专家
     * @param exId
     * @return
     */
    BaseResponse deleteSecJGExpertById(String exId);

    /**
     * 证件号校验专家是否已存在
     * @param idNum
     * @param id
     * @param teId
     * @return
     */
    boolean expertJGExists(String idNum, String id, String teId);

    /**
     * 抽取专家信息
     * @param request
     * @param teId
     * @return
     */
    BaseResponse extractJGExpertInfo(ExtractJGExpertDto request, String teId);

    /**
     * 抽取专家信息(限定技术类每个专家的专业随机抽取，经济类按人数随机抽取)
     * @param request
     * @param teId
     * @return
     */
    BaseResponse extractJGExpert(ExtractJGExpertDto request, String teId);


    /**
     * 招标代理抽取专家信息(限定技术类每个专家的专业随机抽取，经济类按人数随机抽取)
     * @param request
     * @param teId
     * @return
     */
    BaseResponse extractJGAgencyExpert(ExtractJGAgencyExpertDto request, String teId);


    /**
     * 专家请假申请获取专家信息
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getSecJGExpertWhenVacation(SecJGExpertVacationDTO request, String uid);

    /**
     * 专家请假申请获取专家信息(不根据当前操作人身份证排除)
     * @param request
     * @param uid
     * @return
     */
    BaseResponse getSecJGExpertWhenVacationNoId(SecJGExpertVacationDTO request, String uid);

    /**
     * 获取当前登录用户对应的专家信息
     * @param uid
     * @return
     */
    BaseResponse getSecJGExpertByCurrent(String uid);


    List<SecJGExpertModel> getExpertListByIds(List<String> ids);

    /**
     * 批量更新专家状态
     * @param ids
     * @param state
     */
    void updateExpertStateBath(List<String> ids, Integer state);


    BaseResponse extractJGExpertCj(ExtractJGExpertDto request, String teId);

    BaseResponse extractJGExpertJudge(ExtractJGExpertDto request, String teId);
}
