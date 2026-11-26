package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Project.PreProjectChangeNameResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChangeName;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PreProjectChangeNameMapper extends BaseMapper<PreProjectChangeName> {

    /**
     * 分页
     *
     * @param pcnName
     * @param sortType
     * @param sortFiled
     * @param start
     * @param size
     * @return
     */
    List<PreProjectChangeNameResponse> getPreProjectChangeNameByPage(@Param("pcnName") String pcnName,@Param("pId") String pId,@Param("teId") String teId, @Param("sortType") String sortType,
                                                                     @Param("sortFiled") String sortFiled, @Param("start") Integer start,
                                                                     @Param("size") Integer size);

    Integer getPreProjectChangeNameCount(@Param("pcnName") String pcnName,@Param("pId") String pId,@Param("teId") String teId);

    PreProjectChangeNameResponse getPreProjectChangeNameById(@Param("pcnId") String pcnId);
}
