package com.jpmsoft.userProducer.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.dto.SelectTableDto;
import com.jpmsoft.userProducer.pojo.entity.TableInfo;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import com.jpmsoft.userProducer.pojo.vo.TableForeignKeyVo;
import com.jpmsoft.userProducer.pojo.vo.TableInfoVo;

import java.util.List;

public interface ITableManagementService extends IService<TableInfo> {
    BaseResponse getTableInfo(String owner, String token);

    BaseResponse gettabledata(SelectTableDto request, String userid);

    BaseResponse synchronizationpProperties(String owner, String userid);

    BaseResponse datacomparison(List<ExlTableColumnDto> request, String owner, String exlstate, String userid);

    BaseResponse getPatternName();

    List<TableInfoVo> getTableByTableCode(String owner, String columnCode);

    List<TabcolumsVo> getTableColumnbByTableCodeAndColumnCode(String tableCode, String columnCode);

    Integer insertTableAndColumn(List<String> sqlList);

    BaseResponse getDbName();
    List<TabcolumsVo> getTableColumnByTableCode(String owner,String tableCode);
    List<TableInfoVo> getTableInfoAll(String owner);

    List<TableForeignKeyVo>  getTableForeignKey(String owner);
    BaseResponse  getTableInfoAndColumnByTableColde(List<String> codeList);
    List<TabcolumsVo>  getTableColumnList(String owner,String  tablecode);

    void synchronizationpBind(String owner);
}
