package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertVacationDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpert;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISecExpertService extends IService<SecExpert> {

    BaseResponse resetPlaintextNum();

    BaseResponse createOrUpdateSecExpert(SecExpertModel request);

    /**
     * 身份证取当前创建人
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSecExpertCurrent(SecExpertModel request);

    /**
     * 身份证取当前创建人
     * 并验证数据唯一
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSecExpertVerify(SecExpertModel request);


    BaseResponse getSecExpertList(SecExpertModel request);

    /**
     * 城建定制鉴权
     *
     * @param request
     * @return
     */
    BaseResponse getSecExpertCjDzList(SecExpertModel request, String token);

    Workbook exportExcelSecExpertCjDzList(SecExpertModel request, String uId);

    BaseResponse getSecExpertById(String exId);

    BaseResponse deleteSecExpertById(String exId);

    /**
     * 随机抽取专家
     *
     * @param request
     * @return
     */
    BaseResponse extractExpertInfo(ExtractExpertDto request, String teId);

    BaseResponse extractExpert(ExtractExpertDto request, String teId);

    boolean expertExists(String idNum, String id, String teId);

    /**
     * 修改专家状态
     *
     * @param exId
     * @param status
     */
    void updateSecExpertStatus(String exId, Integer status);

    /**
     * 专家请假申请
     * 根据当前操作人查询选取记录以及专家信息
     * 同时根据配置项判断是否可以申请请假
     *
     * @param request
     * @return
     */
    BaseResponse getSecExpertWhenVacation(SecExpertVacationDTO request, String uId);

    /**
     * 专家请假申请
     * 根据当前操作人查询选取记录以及专家信息
     * 同时根据配置项判断是否可以申请请假
     * <p>
     * 不根据当前操作人的身份证排除
     *
     * @param request
     * @return
     */
    BaseResponse getSecExpertWhenVacationNoId(SecExpertVacationDTO request, String uId);

    /**
     * 获取当前登录人的专家库信息
     *
     * @param uId
     * @return
     */
    BaseResponse getSecExpertByCurrent(String uId);

    /**
     * 批量更新专家库状态
     *
     * @param ids
     */
    void updateExpertStateBath(List<String> ids, Integer state);

    List<SecExpertModel> getExpertListByIds(List<String> ids);

    List<SecExpertModel> getExtractExpertList(String teId);

    List<SecExpertModel> getExtractExpertList(List<String> avoidUser, List<String> avoidUnit);

    /**
     * 校验专家工作年限
     *
     * @param exIdList
     */
    void checkExpertWorkTime(List<String> exIdList);

    BaseResponse updatePersonnel(SecExpertModel request);

    BaseResponse importExcelSecExpertCjDzList(MultipartFile file, LogUserInfoVo userInfoByToken);

    /**
     * 根据用户ID获取专家信息
     *
     * @param uId
     * @param tokenUId
     * @return
     */
    BaseResponse getSecExpertByuId(String uId, String tokenUId);

    /**
     * 专家库多条件查询(城建定制专家鉴权逻辑)
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse getSecExpertCjDzListClass(SecExpertModel request, String token);

    /**
     * 评标专家申请驳回到发起人
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token);

    /**
     * 新的一年复制专家库
     *
     * @param year
     * @return
     */
    BaseResponse copyAllSecExpert(Integer year);

    BaseResponse invalidProcessData(String exCid);

    BaseResponse updateChangeStatus(String exId, String changeStatus);

    BaseResponse getSecExpertChangeStatus(String exId);

    BaseResponse handleDisableSecExpert();

    /**
     * 获取可以抽取的专家数量
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getExtractSecExpertCount(ExtractExpertDto request, String teId);

    BaseResponse syncCompanyToSecExpert(String uId);
}
