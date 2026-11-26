package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeModel;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsType;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsTypeVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface QsTypeMapper extends BaseMapper<QsType> {

    @Select("select * from  Qs_Type where Qst_Parent_Id=#{parentId} and Qst_Sort>#{qstSort}")
    List<QsType> selectSameLevelBySort(@Param("parentId") String parentId, @Param("qstSort") Integer qstSort);

    List<QsTypeVO> getQsType(@Param("params")Map<String, Object> map);

    List<QsTypeVO> getQsTypeByParentId(@Param("qstParentId")String qstId);

    List<QsTypeModel> getQsTypeList(@Param("params") Map<String, Object> params,@Param("page") IPage<QsTypeModel> page);


    QsType getQsTypeById(@Param("qstId") String qstId);


    void batchUpdateQsType(@Param("list") List<QsType> list);

    List<QsType> getAllChildByQstId(@Param("qstId") String qstId);

    List<QsType> getQsTypeByQstIdList(@Param("qstIdList") List<String> qstIdList);

    List<QsRecordResponse> getQsRecordListByParams(@Param("params") Map<String, Object> params);
}
