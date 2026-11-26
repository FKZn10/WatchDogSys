package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChange;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.StorageChangeXctVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【storage_change(入库变更表)】的数据库操作Mapper
 * @createDate 2025-04-24 18:08:54
 * @Entity com.whjpsoft.pojo.StorageChange
 */
public interface StorageChangeMapper extends BaseMapper<StorageChange> {


    List<StorageChangeXctVO> getStorageChangeList(@Param("page") IPage<StorageChangeXctVO> page, @Param("params") StorageChangeRequest params);

}




