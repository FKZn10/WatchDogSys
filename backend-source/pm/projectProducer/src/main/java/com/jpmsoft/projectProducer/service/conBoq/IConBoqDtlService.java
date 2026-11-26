package com.jpmsoft.projectProducer.service.conBoq;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqDtl;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConBoqDtlVo;

import java.util.List;

public interface IConBoqDtlService extends IService<ConBoqDtl> {

    BaseResponse createOne(ConBoqDtlDto request);
    BaseResponse updateOne(ConBoqDtlDto request);
    BaseResponse deleteOne(String boqdId);
    BaseResponse deleteBath(List<String> ids);
    /**
     * 保存
     */
    BaseResponse saveBoqDtl(RequestBean requestBean);

    /**
     *
     */
    BaseResponse createOrUpdateOrdeleteConBoqDtl(RequestBean requestBean);

    /**
     * 清除临时表
     */
    BaseResponse deleteConBoqDtlTemp(RequestBean requestBean);

    /**
     * 导入清单数据
     *
     * @return
     */
//    BaseResponse importExcel(MultipartFile file, HttpServletRequest request, String bopId, String parentId);
    BaseResponse importExcel(RequestBean requestBean);

    /**
     * temp临时表数据
     */
    BaseResponse getConBoqDtlTempList(RequestBean requestBean);


    void createOrUpdateConBoqDtl(List<ConBoqDtlDto> datas, String boqId);

    void delConBoqDtlByBoqId(List<ConBoqDtlDto> datas,String boqId);

    List<ConBoqDtlVo> getConBoqDtlByBoqId(String boqId);

    List<ConBoqDtlVo> getConBoqDtlByBoqIdAndParent(String boqId,String parentId);

    BaseResponse getConBoqDtlAndMeasureTree(ConBoqDtlMeasureDTO request);

    Integer getConBoqDtlAndMeasureOutNum(ConBoqDtlMeasureDTO request);

    List<ConBoqDtlDto> getConBoqDtlByConId(String conId);
    BaseResponse getConBoqDtlTreeByConId(String conId,String boqId,String parentId);
    /**
     * 合同+清单ID查询
     *
     * @param conId
     * @param boqdCode
     * @return
     */
    BaseResponse getConBoqDtlByConIdAndCode(String conId, String boqdCode);

    /**
     * 批量boqdId查询上级信息
     *
     * @param boqdIds
     * @return
     */
    BaseResponse getConBoqDtlParentByIds(List<String> boqdIds);

    /**
     * 获取概算工程量清单列表
     *
     * @param request
     * @return
     */
    BaseResponse getConBoqDtlBudgetList(ConBoqBudgetDto request);

    List<ConBoqDtl> getBoQdName(List<String> boqIds, List<String> boqdParIds);
}
