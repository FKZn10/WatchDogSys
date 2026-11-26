package com.jpmsoft.userProducer.dynamicsCodes.njcj;

import com.jpmsoft.userProducer.service.IDynamicsCodeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单tab权限控制
 */
public class FormTabPermission {

    @Resource
    private IDynamicsCodeService dynamicsCodeService;

    public Map<String, Boolean> tabAuth(List<Object> params) {
        Map<String, Boolean> returnData = new HashMap<>();
        try {
            //固定入参 页面类型
            //create：新建界面，edit：修改界面，flow：审批界面，details：详情界面，CarbonCopy：抄送，communication：沟通，supplementary：补充意见 maintain:维护界面
            String pageType = params.get(0).toString();
            //固定入参  表单ID
            String fileId = params.get(1).toString();
            //固定入参 用户ID
            String userId = params.get(3).toString();
            //region 是表单创建人
            String fileCreateSql = "select count(*) from sys_file where FileID='" + fileId + "' and FileCreateUser='" + userId + "'";
            Integer createCount = dynamicsCodeService.selectCount(fileCreateSql);
            System.out.println("动态代码 FormTabPermission,tabAuth 查询值createCount：" + createCount);
            if (createCount != null && createCount > 0) {
                System.out.println("动态代码 FormTabPermission,tabAuth 创建人权限");
                setValue(returnData, true);
                return returnData;
            }
            //endregion

            //region 判断是否是审批人、待审批人、抄送、沟通人员（即流程参与人员）
            StringBuilder sb = new StringBuilder();
            sb.append(" select 1 from sys_task fstk left join SYS_FlowActivity sfa on fstk.taskcurrentactid=sfa.actid where sfa.ActName!='签收'  and fstk.taskuser='").append(userId).append("' and fstk.taskentityid='").append(fileId).append("'");
            //已审批人
            sb.append(" UNION ALL select 1 from SYS_TASK_PROCESSIN fstp  left join SYS_FlowActivity sfa on fstp.tp_actid=sfa.actid  where  sfa.ActName!='签收' and fstp.tp_user='").append(userId).append("' and fstp.tp_entityid='").append(fileId).append("'");
            //抄送
            //sb.append(" UNION ALL select 1 from SYS_CARBONCOPY fscy where fscy.cc_user='").append(userId).append("' and fscy.cc_fileid='").append(fileId).append("'");
            //待沟通
            sb.append(" UNION ALL select 1 from SYS_COORDINATIONTASK fsct where fsct.ct_user='").append(userId).append("' and fsct.ct_fileid='").append(fileId).append("'");
            //已沟通
            sb.append(" UNION ALL select 1 from SYS_COORDINATIONPROCESS fscp where fscp.cp_user='").append(userId).append("' and fscp.cp_fileid='").append(fileId).append("'");
            sb.append("  limit 1 ");

            Integer approved = dynamicsCodeService.selectCount(sb.toString());
            System.out.println("动态代码 FormTabPermission,tabAuth 流程参与人权限" + approved + "  " + sb.toString());
            if (approved != null && approved > 0) {
                System.out.println("动态代码 FormTabPermission,tabAuth 流程参与人权限");
                setValue(returnData, true);
                return returnData;
            }
            //endregion
            setValue(returnData, false);
            return returnData;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("动态代码 FormTabPermission,tabAuth执行异常" + e.getMessage());
            setValue(returnData, true);
            return returnData;
        }
    }


    private void setValue(Map<String, Boolean> returnData, Boolean flag) {
        returnData.put("map", flag);//流程图
        returnData.put("tp", flag);//审批记录
        returnData.put("cc", flag);//抄送
        returnData.put("cm", flag);//沟通
        returnData.put("file", flag);//相关资源
        returnData.put("sup", flag);//监督
    }
}
