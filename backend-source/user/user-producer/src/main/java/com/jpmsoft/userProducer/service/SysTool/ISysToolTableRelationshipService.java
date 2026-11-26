package com.jpmsoft.userProducer.service.SysTool;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysToolTableRelationshipDto;
import com.jpmsoft.userProducer.pojo.entity.SysToolTableRelationship;
import com.jpmsoft.userProducer.pojo.vo.SysToolTableRelationshipVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ISysToolTableRelationshipService extends IService<SysToolTableRelationship> {
    BaseResponse GetSysToolTableRelationshi(String TableCode, String owner);

    BaseResponse CreateOrUpdateOrDeleteSysToolTableRelationship(SysToolTableRelationshipDto request);

    BaseResponse SynchronizeForeignKeys(String owner);

    BaseResponse GetSysToolTableRelationshiAndData(String TableCode, String pk, String id, String owner);

    List<SysToolTableRelationshipVo> getSysToolTableRelationshipData(String TableCode);

    BaseResponse GetSysToolTableRelationshiMap(String TableCode, String pk, String id, String owner);

    BaseResponse GetSysToolTableRelationshiDataByPage(String TableCode, String pk, Integer page, String id, String owner);

    List<SysToolTableRelationshipVo> OtherAssociations(List<SysToolTableRelationship> alllList, String TableCode, Integer num);

    List<SysToolTableRelationshipVo> getOtherData(String tableCode);

    Integer insertBatch(List<String> sqlList);

    BaseResponse GetSysToolTableRelationshiData(String code, String owner);

    BaseResponse importSysToolTableRelationshi(List<SysToolTableRelationship> data, String owner);

    Map<String, Map<String, String>> getTableSqlMap();

    Map<String, Map<String, String>> getTableSqlMapNewSet(String TableCode, String pk, String id, String owner);

    Integer insertSql(String sql);

}
