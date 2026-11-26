package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.TableColumnInfoMapper;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.entity.TableColumnInfo;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import com.jpmsoft.userProducer.service.ISysTableColumnService;
import com.jpmsoft.userProducer.service.ISysUserService;
import com.jpmsoft.userProducer.service.ITableColumnManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TableColumnManagementImpl extends ServiceImpl<TableColumnInfoMapper, TableColumnInfo> implements ITableColumnManagementService {
    @Resource
    ISysUserService ISysUserService;
    @Resource
    ISysTableColumnService sysTableColumnService;
    @Resource
    ITableColumnManagementService tableColumnManagementService;

    @Override
    public BaseResponse getTableColumnInfo(String tableCode, String userid) {
//        if (!Getselectrolue(userid)) return BaseResponse.fail(BaseMsg.notAuthUser);
        List<TabcolumsVo> models = baseMapper.getTableColumn(tableCode);
        return BaseResponse.ok(models, models.size());
    }


    @Override
    public BaseResponse importProperties(List<ExlTableColumnDto> request, String userid) {
        if (!Getselectrolue(userid)) return BaseResponse.fail(BaseMsg.notAuthUser);
        return sysTableColumnService.importProperties(request);
    }
    @Override
    public  List<TabcolumsVo>  getTableColumnList(String owner,String  tablecode) {

        return tableColumnManagementService.getTableColumnList(owner,tablecode);
    }


    private Boolean Getselectrolue(String userid) {
        SysUser obj = ISysUserService.getById(userid);
        if (obj != null) {
            if (obj.getuAccount().equals("admin") ||
                    obj.getuAccount().equals("jpmadmin")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
