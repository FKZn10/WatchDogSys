package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeModel;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsType;
import com.jpmsoft.projectProducer.pojo.request.qs.QsTypeRecordRequest;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface IQsTypeService extends IService<QsType> {

    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateQsType(QsTypeModel request);

    /**
     * 获取组内和当前修改类别相同的其他类别（只比对文字部分）
     * @param request
     * @return
     */
    BaseResponse getGroupSameType(QsTypeModel request);

    /**
     * ID删除
     *
     * @param qstId
     * @return
     */
    BaseResponse deleteQsTypeById(String qstId);

    /**
     * 获取List集合
     *
     * @return
     */
    BaseResponse getQsTypeTreeList(QsTypeModel request, String uId);


    BaseResponse getQsTypeList(QsTypeModel request, String uid);


    /**
     * 上移或者下移
     *
     * @param qstId
     * @param action
     * @return
     */
    BaseResponse upOrDownQsType(String qstId, String action,String teId);

    /**
     * 父ID查询
     *
     * @param parentId
     * @return
     */
    BaseResponse getQsTypeByParentId(String parentId);

    /**
     * 获取所有子孙节点
     *
     * @param qstIdList
     */
    List<QsType> getChildQsType(List<String> qstIdList);


    BaseResponse getQsTypeById(String qstId);

    /**
     * 导出
     * @param request
     * @return
     */
    Workbook exportQsType(QsTypeModel request, String uid);

    /**
     * 生成path
     * @return
     */
    BaseResponse generateQsTypePath();


    Workbook exportQsTypeAll(QsTypeModel request, String uid);

    BaseResponse getQsTypeInfo(QsTypeRecordRequest request);

    Workbook exportQsTypeInfo(QsTypeRecordRequest request, String uid);

    Workbook exportQsTypeInfoAll(QsTypeRecordRequest request, String uid);

    BaseResponse getQsTypeTree(QsTypeModel request);
}
