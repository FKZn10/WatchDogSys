package com.jpmsoft.projectProducer.mapper.secInfo;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChangeDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【storage_change_detail(入库变更详情表)】的数据库操作Mapper
 * @createDate 2025-04-24 18:08:54
 * @Entity com.whjpsoft.pojo.StorageChangeDetail
 */
public interface StorageChangeDetailMapper extends BaseMapper<StorageChangeDetail> {

    @Select(" select mn.*,ex.* from STORAGE_CHANGE_DETAIL mn left join Ins_Expert ex on ex.ex_id=mn.ex_id where mn.sc_id=#{scId}")
    List<StorageChangeDetail> selectByScId(@Param("scId") String scId);

}




