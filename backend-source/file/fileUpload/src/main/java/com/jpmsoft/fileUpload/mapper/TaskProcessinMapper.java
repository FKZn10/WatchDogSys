package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.TaskProcessResponse;
import com.jpmsoft.fileUpload.pojo.dto.PayApplyApproveDto;
import com.jpmsoft.fileUpload.pojo.entity.TaskProcessin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskProcessinMapper extends BaseMapper<TaskProcessin> {

    /**
     * 查询付款申请书的审批流程记录（支付确认表数据源）
     * <p>
     * 按业务实体ID(支付ID)查已办表, 关联用户表取审批人姓名与电子签名图片,
     * 返回每个审批节点的 节点名/意见/姓名/签名图/时间.
     * 过滤掉 Gateway 类型节点.
     * <p>
     *
     *
     * @param entityId 业务实体ID(支付ID)
     * @return 审批记录列表
     */
    @Select("select tp.tp_dotime as tpDotime, TP.ActName as actName, tp.tp_explain as tpExplain, " +
            "us.U_Name as uName, us.U_Signature as uSignature, " +
            "concat((select Con_Value from SYS_Config where Con_Code='downloadUrl') , '/jpm/downloadOpenFile/', us.U_Signature) as imgUrl " +
            "from Sys_Task_Processin tp " +
            "left join SYS_User us on tp.tp_user=us.U_ID " +
            "left join SYS_FlowActivity act on act.ActID=tp.tp_actid " +
            "where tp.tp_entityid=#{entityId} " +
            "and act.ActType not like '%Gateway' " +
            "order by tp_dotime asc")
    List<PayApplyApproveDto> listPayApplyApprove(@Param("entityId") String entityId);

}
