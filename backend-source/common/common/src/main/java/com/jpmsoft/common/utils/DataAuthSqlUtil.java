package com.jpmsoft.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据鉴权SQL组装
 */
public class DataAuthSqlUtil {

    public static String onlyInnerAndPartyB(String uType, Map<String, Object> info, ContractAuthorityDTO conAuth) {
        if (Integer.parseInt(uType) == UserTypeEnum.Inner_User.getIndex()) return " where 1=1 ";
        else if (Integer.parseInt(uType) == UserTypeEnum.Out_User.getIndex()) {
            String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
            StringBuffer sb = new StringBuffer();
            sb.append(" left join Con_Contract fcon on fcon.Con_Id=mn.Con_Id ");
            sb.append(" where ( ");
            if (!CollectionUtils.isEmpty(conAuth.getEnables())) sb.append("(");
            if (!CollectionUtils.isEmpty(conAuth.getDisables())) {
                //禁止的合同
                List<String> conIds=conAuth.getDisables();
                sb.append("  mn.Con_Id not in ('" + conIds.get(0) + "'");
                for (int i = 1; i < conIds.size(); i++) {
                    sb.append(",'" + conIds.get(i) + "'");
                }
                sb.append(") and  ");
            } else sb.append(" 1=1 and ");
            sb.append(" ( ");
            sb.append("fcon.Con_Party_B_Id='" + cId + "'");
            sb.append(" or fcon.Con_Party_B1='" + cId + "'");
            sb.append(" or fcon.Con_Party_B2='" + cId + "'");
            sb.append(" or fcon.Con_Party_B3='" + cId + "'");
            sb.append(" or fcon.Con_Party_B4='" + cId + "'");
            sb.append(" or fcon.Con_Party_B5='" + cId + "'");
            sb.append(" or fcon.Con_Party_C_Id='" + cId + "'");
            sb.append(" or fcon.Con_Party_D_Id='" + cId + "'");
            sb.append(" ) ) ");
            if (!CollectionUtils.isEmpty(conAuth.getEnables()))
            { //授权的合同
                List<String> conIds=conAuth.getEnables();
                sb.append(" or mn.Con_Id in ('" + conIds.get(0) + "'");
                for (int i = 1; i < conIds.size(); i++) {
                    sb.append(",'" + conIds.get(i) + "'");
                }
                sb.append(")) ");
            }
            return sb.toString();
        } else return null;
    }


    /**
     * 年度合同鉴权
     * 内部用户不鉴权 外部用户为乙方单位或者在合同白名单内
     *
     * @param uType
     * @param info
     * @param conIds
     * @return
     */
    public static String onlyInnerAndPartyB(String uType, Map<String, Object> info, List<String> conIds) {
        if (Integer.parseInt(uType) == UserTypeEnum.Inner_User.getIndex()) return " where 1=1 ";
        else if (Integer.parseInt(uType) == UserTypeEnum.Out_User.getIndex()) {
            String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
            StringBuffer sb = new StringBuffer();
            sb.append(" left join Con_Contract fcon on fcon.Con_Id=mn.Con_Id ");
            if (!CollectionUtils.isEmpty(conIds)) {
                sb.append(" where (mn.Con_Id not in ('" + conIds.get(0) + "'");
                for (int i = 1; i < conIds.size(); i++) {
                    sb.append(",'" + conIds.get(i) + "'");
                }
                sb.append(") and  ");
            } else sb.append("where (1=1 and ");
            sb.append(" ( ");
            sb.append("fcon.Con_Party_B_Id='" + cId + "'");
            sb.append(" or fcon.Con_Party_B1='" + cId + "'");
            sb.append(" or fcon.Con_Party_B2='" + cId + "'");
            sb.append(" or fcon.Con_Party_B3='" + cId + "'");
            sb.append(" or fcon.Con_Party_B4='" + cId + "'");
            sb.append(" or fcon.Con_Party_B5='" + cId + "'");
            sb.append(" or fcon.Con_Party_C_Id='" + cId + "'");
            sb.append(" or fcon.Con_Party_D_Id='" + cId + "'");
            sb.append(" ) ) ");
            return sb.toString();
        } else return null;
    }

    /**
     * 重载方法
     * 合同权限配置功能其他业务服务改造完毕后将弃用
     *
     */
    public static String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String uType, Map<String, Object> info, List<String> conIds, String pKey)
    {
        return getAuthSql(tableName,uId, dbCreateFiled, userAuth, uType,info,conIds, null, pKey);
    }


    public static String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String uType, Map<String, Object> info, List<String> conIds, List<String> bmdConIds, String pKey)
    {
        return  getAuthSql( tableName,  uId,  dbCreateFiled,  userAuth,  uType,  info, conIds, bmdConIds,  pKey,null);
    }

    /**
     * 外部用户
     * constructionControl  true 是监理单位或全过程咨询单位
     * constructionUnit true 是其他类型单位用户
     * 内部用户
     * departments 可查看数据所属组织机构列表
     * departments  null 则具有所有权限
     * departments 空集合（非null） 内部用户仅看组织机构
     * <p>
     * param cId                 单位ID
     * param constructionControl true 是监理单位或全过程咨询单位
     * param constructionUnit    true 是其他类型单位用户
     * param departments         可查看数据所属组织机构列表
     *
     * @param uId           用户ID
     * @param dbCreateFiled 当前操作主表上的创建人数据库字段
     * @param userAuth      枚举类 OutUserAuthEnum
     * @param uType         用户类型
     * @param info          用户信息
     * @param conIds        黑名单合同ID
     * @param bmdConIds     白名单合同ID
     * @param pKey          表单项目外键名（tableName为空时生效）
     * @return
     */
    public static String getAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String uType, Map<String, Object> info, List<String> conIds, List<String> bmdConIds, String pKey,String conAlise) {
        List<String> departments = new ArrayList<>();
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        Boolean constructionControl = info.containsKey("constructionControl") && ValidateUtil.isNotNull(info.get("constructionControl")) ? Boolean.parseBoolean(info.get("constructionControl").toString()) : null;
        Boolean constructionUnit = info.containsKey("constructionUnit") && ValidateUtil.isNotNull(info.get("constructionUnit")) ? Boolean.parseBoolean(info.get("constructionUnit").toString()) : null;
        Object departmentJson = info.containsKey("departments") && ValidateUtil.isNotNull(info.get("departments")) ? info.get("departments") : null;
        if (ValidateUtil.isEmpty(departmentJson)) departments = new ArrayList<>();
        else if (departmentJson.toString().equals("all")) departments = null;
        else
            departments = JacksonUtils.parseObject(JacksonUtils.toJSONString(departmentJson), new TypeReference<List<String>>() {
            });
        StringBuffer sb = new StringBuffer();
        LinkedListByTableName(ValidateUtil.isEmpty(tableName) ? "as#h#$!uhdfi" : tableName, pKey, sb);
        if (Integer.parseInt(uType) == UserTypeEnum.Inner_User.getIndex()) {//内部用户
            sb.append(" where 1=1 ");
            //内部用户不鉴权外部用户无权或全部项目授权 p_auth_type=2项目单独完全按项目组织机构授权
            if (OutUserAuthEnum.Only_Inner.getIndex() == userAuth|| departments == null)
            {
                sb.append("and (fpro.p_auth_type is null or fpro.p_auth_type=1 or (fpro.p_auth_type=2 and fpro.P_Id in (");
                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='" + uId + "'))) ");
            }
            else if (departments != null && departments.size() < 1) {
                List<String> dutyDepartmentIds=info.containsKey("dutyDepartmentIds") && ValidateUtil.isNotNull(info.get("dutyDepartmentIds")) ?  departments = JacksonUtils.parseObject(JacksonUtils.toJSONString(info.get("dutyDepartmentIds")), new TypeReference<List<String>>() {
                }) : null;
                //仅查看项目组织机构

                sb.append("and fpro.P_Id in (");
                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='" + uId + "' ");
                if (!CollectionUtils.isEmpty(dutyDepartmentIds))
                {//负责人按部门授权
                    sb.append("or pup.u_id in (select kspu.u_id from SYS_Position_User kspu left join SYS_Position ksp on ksp.p_id=kspu.p_id where ksp.d_id in (");
                    sb.append("'" + dutyDepartmentIds.get(0) + "'");
                    for (int i = 1; i < dutyDepartmentIds.size(); i++) {
                        sb.append(",'" + dutyDepartmentIds.get(i) + "'");
                    }
                    sb.append(")) ");
                }
                sb.append(")");
            } else if (departments != null && departments.size() > 0) {
                sb.append("and (((fpro.p_auth_type is null or fpro.p_auth_type=1) ");
                sb.append("and  (fpro.P_Competent_Department in (");
                sb.append("'" + departments.get(0) + "'");
                for (int i = 1; i < departments.size(); i++) {
                    sb.append(",'" + departments.get(i) + "'");
                }
                sb.append(") ");
                if (!ValidateUtil.isEmpty(tableName) && tableName.equals("Gantt_Plan")) {
                    sb.append(" or (mn." + dbCreateFiled + "=" + "'" + uId + "' or ");
                    sb.append(" (select C_Id from  Sys_User where U_Id='" + uId + "')= (select C_Id from  Sys_User where U_Id=mn.Plan_Create_Uid) ");
                    sb.append(" ) ");
                } else if (!ValidateUtil.isEmpty(tableName) && tableName.equals("function_time_config")) {
                    sb.append(" or b." + dbCreateFiled + "=" + "'" + uId + "'  ");
                } else if (!ValidateUtil.isEmpty(dbCreateFiled))
                    sb.append(" or " + dbCreateFiledAlias(dbCreateFiled) + "=" + "'" + uId + "'");
                sb.append(") ");
                sb.append(") or (fpro.P_Id in (");
                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='" + uId + "'))");
                sb.append(") ");
            }
//            else {
//                if (!ValidateUtil.isEmpty(tableName) && tableName.equals("Gantt_Plan")) {
//                    sb.append(" and (mn." + dbCreateFiled + "=" + "'" + uId + "' or ");
//                    sb.append(" (select C_Id from  Sys_User where U_Id='" + uId + "')= (select C_Id from  Sys_User where U_Id=mn.Plan_Create_Uid) ");
//                    sb.append(" ) ");
//                } else if (!ValidateUtil.isEmpty(tableName) && tableName.equals("function_time_config")) {
//                    sb.append(" and b." + dbCreateFiled + "=" + "'" + uId + "'");
//                } else if (!ValidateUtil.isEmpty(dbCreateFiled))
//                    sb.append(" and " + dbCreateFiledAlias(dbCreateFiled) + "=" + "'" + uId + "'");
//            }
        } else if (Integer.parseInt(uType) == UserTypeEnum.Out_User.getIndex()) {//外部用户
            if (userAuth == OutUserAuthEnum.NOT.getIndex() || userAuth == OutUserAuthEnum.Only_Inner.getIndex())
                //当前模块外部用户无权查看
                return null;
            if ((constructionControl == null && constructionUnit == null) || constructionControl == constructionUnit)
                //无法判断外部单位类型
                return null;
            if (userAuth == OutUserAuthEnum.Organization.getIndex()) {
                sb.append(" where ((1=1 ");
                //校验用户是否在项目组织机构
                sb.append("and fpro.P_Id in(");
                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='" + uId + "') ) ");
            }
            else if (userAuth == OutUserAuthEnum.Unit_Auth.getIndex()) {
                Object conTablesJs = info.containsKey("conTables") && ValidateUtil.isNotNull(info.get("conTables")) ? info.get("conTables") : null;
                List<String> conTables = null;
                if (ValidateUtil.isNotNull(conTablesJs))
                    conTables = JacksonUtils.parseArray(JacksonUtils.toJSONString(conTablesJs), String.class);
                boolean conMd = false;
                if (ValidateUtil.isNotEmpty(tableName) && !CollectionUtils.isEmpty(conTables) && !conTables.stream().noneMatch(s -> s.equalsIgnoreCase(tableName))) {
                    if (tableName.equalsIgnoreCase("Con_Contract"))//合同表本身
                    {
                        sb.append(" left join  Con_Contract fcon on fcon.Con_Id=mn.Con_Id  where ");
                        if (!CollectionUtils.isEmpty(bmdConIds))//授权指定合同
                            sb.append(" (((1=1 ");
                        else
                            sb.append(" ( (1=1 ");
                    }
                    else {
                        if (ValidateUtil.isNotEmpty(conAlise))
                             sb.append(" left join  Con_Contract fcon on fcon.Con_Id="+conAlise+".Con_Id  where ( (1=1 ");
                        else
                            sb.append(" left join  Con_Contract fcon on fcon.Con_Id=con.Con_Id  where ( (1=1 ");
                    }
                    //按用户的单位授权
                    sb.append(" and ( fcon.Con_Id in ( select fcon.Con_Id from  Con_Contract fcon where 1=1 ");
                    conMd = true;
                } else {
                    sb.append("  where ( ( ");
                    //按用户的单位授权
                    sb.append(" ( fpro.P_Id in ( select fcon.Con_Project_Id  from  Con_Contract fcon where 1=1 ");
                }
                sb.append(" and  ( ");
                sb.append("fcon.Con_Party_B_Id='" + cId + "'");
                sb.append(" or fcon.Con_Party_B1='" + cId + "'");
                sb.append(" or fcon.Con_Party_B2='" + cId + "'");
                sb.append(" or fcon.Con_Party_B3='" + cId + "'");
                sb.append(" or fcon.Con_Party_B4='" + cId + "'");
                sb.append(" or fcon.Con_Party_B5='" + cId + "'");
                sb.append(" or fcon.Con_Party_C_Id='" + cId + "'");
                sb.append(" or fcon.Con_Party_D_Id='" + cId + "'");
                sb.append(" )) ");
                //或者在项目上是监理或全过程咨询单位
                sb.append(" or fpro.P_Id in ( select P_ID from SYS_Participant_Unit where (C_Type='03' or C_Type='05') and C_Id='").append(cId).append("') ");
                sb.append(" )  ");
                //校验用户是否在项目组织机构
                //if (ValidateUtil.isNotEmpty(tableName) && tableName.equals("Con_Pay"))
                sb.append("and fpro.P_Id in(");
                //else
                //    sb.append("or fpro.P_Id in(");
                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='" + uId + "')");
                //黑名单合同
                if (conMd && !CollectionUtils.isEmpty(conIds)) {
                    sb.append(" and fcon.Con_Id not in ('" + conIds.get(0) + "'");
                    for (int i = 1; i < conIds.size(); i++) {
                        sb.append(",'" + conIds.get(i) + "'");
                    }
                    sb.append(") ");
                }
                sb.append(" )");
                //保函签收外部单位可查看同单位人员创建的数据
                if (ValidateUtil.isNotEmpty(tableName)&&tableName.equals("Guarantee_Letter_Sign"))
                {
                    sb.append(" or ").append(dbCreateFiledAlias(dbCreateFiled) + " in ")
                            .append("(select asu.U_Id from sys_user asu where asu.C_ID='").append(cId).append("') ");
                }
            }


//            if (constructionControl != null && constructionControl == true) { //是监理单位或全过程咨询单位
//                sb.append(" where ((1=1 ");
//                if (conIds != null && conIds.size() > 0) {
//                    sb.append("and fpro.P_Id in ( select fcon.P_Id from   Con_Contract fcon where 1=1 ");
//                    sb.append(" and (fcon.Con_Supervision_Org='" + cId + "'");
//                    sb.append("and fcon.Con_Id not in ('" + conIds.get(0) + "'");
//                    for (int i = 1; i < conIds.size(); i++) {
//                        sb.append(",'" + conIds.get(i) + "'");
//                    }
//                    sb.append(")) ");
//                }
//                //校验用户是否在项目组织机构
//                sb.append(" and fpro.P_Id in(");
//                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
//                sb.append(" where pup.U_ID='" + uId + "') )");
//            } else if (constructionUnit != null && constructionUnit == true) {  //其他类型单位用户
//
//            }
            if (!ValidateUtil.isEmpty(tableName) && tableName.equals("Gantt_Plan")) {
                //2022年5月17日调整：原因找：进度计划模块，项目下监理单位和造价咨询单位可以查看该项目所有外部单位创建的进度计划数据，该部分鉴权调整至GanttPlanMapper文件中
                // sb.append(" and (mn." + dbCreateFiled + "=" + "'" + uId + "' or ");
                // sb.append(" ((select C_Id from  Sys_User where U_Id='" + uId + "')= (select C_Id from  Sys_User where U_Id=mn.Plan_Create_Uid) ");
                // sb.append(" and (select U_Type from  Sys_User where U_Id=mn.Plan_Create_Uid) = 2");
                // sb.append(" ) ) )");
                sb.append(" ) ");
            } else if (!ValidateUtil.isEmpty(tableName) && tableName.equals("function_time_config")) {
                sb.append(" or b." + dbCreateFiled + "=" + "'" + uId + "' ) ");
            } else if (!ValidateUtil.isEmpty(dbCreateFiled))
                sb.append(" or " + dbCreateFiledAlias(dbCreateFiled) + "=" + "'" + uId + "' ) ");
            else  sb.append(" ) ");

            if (!ValidateUtil.isEmpty(tableName) && tableName.equalsIgnoreCase("Con_Contract")
                    &&!CollectionUtils.isEmpty(bmdConIds)) //合同模块白名单合同可见
            {
                sb.append(" or fcon.Con_Id in ('" + bmdConIds.get(0) + "'");
                for (int i = 1; i < bmdConIds.size(); i++) {
                    sb.append(",'" + bmdConIds.get(i) + "'");
                }
                sb.append(") )");
            }

        } else {
            //未知用户类型
            return null;
        }
        return sb.toString();
    }

    /**
     * 仅根据项目组织机构鉴权
     *
     * @param tableName
     * @param uId
     * @return
     */
    public static String onlyProOrganizationAuth(String tableName, String uId) {
        StringBuffer sb = new StringBuffer();
        LinkedListByTableName(ValidateUtil.isEmpty(tableName) ? "as#h#$!uhdfi" : tableName, null, sb);
        sb.append(" where 1=1 ");
        sb.append(" and fpro.P_Id in( ");
        sb.append(" select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
        sb.append(" where pup.U_ID='" + uId + "') ");
        return sb.toString();
    }

    /**
     * 是否是外部用户
     *
     * @param uType
     * @return
     */
    public static Boolean checkIsOutUser(String uType) {
        if (ValidateUtil.isEmpty(uType) || Integer.parseInt(uType) != UserTypeEnum.Inner_User.getIndex()) return true;
        else return false;
    }


    /**
     * 仅根据用户组织机构鉴权
     * 流程处理人、抄送人、协同人
     *
     * @param uId
     * @param createUidFiled
     * @param tCode
     * @param primaryKey
     * @param departments    组织机构以及下级组织机构
     * @return
     */
    public static String getAuthSqlOnlyCheckUserDepartment(String uId, String createUidFiled, String tCode, String primaryKey, List<String> departments,String alise) {
        if (departments == null || departments.size() < 1) return null;
        StringBuffer sb = new StringBuffer();
        sb.append(" where (").append(alise).append(".").append(createUidFiled).append(" in (");
        sb.append(" select PositionUser.U_Id from SYS_Position_User PositionUser left join SYS_Position sysPosition on sysPosition.P_ID=PositionUser.P_ID where sysPosition.D_Id in ( ");
        sb.append("'" + departments.get(0) + "'");
        for (int i = 1; i < departments.size(); i++) {
            sb.append(",'" + departments.get(i) + "'");
        }
        sb.append(" )) ");
        //endregion

        //region 流程处理人、抄送人、协同人
        sb.append(" or (").append(alise).append(".").append(primaryKey).append(" in");
        sb.append("(" + " select tmp.entityId from " + " (" + " select tp_entityid as entityId  from Sys_Task_Processin where tp_user='" + uId + "'" + " union" + " select CC_FileID as entityId  from SYS_CarbonCopy where  CC_User='" + uId + "'" + " union" + " select CP_FileID as entityId  from SYS_CoordinationProcess where  CP_User='" + uId + "'" + " ) tmp left join  Sys_File sf  on tmp.entityId=sf.FileId " + " where sf.T_Code='" + tCode + "'" + " )))");
        //endregion
        return sb.toString();
    }

    public static String getAuthSqlOnlyCheckUserDepartment(String uId, String createUidFiled, String tCode, String primaryKey, List<String> departments) {
        return getAuthSqlOnlyCheckUserDepartment(uId,createUidFiled,tCode,primaryKey,departments,"mn");
    }

    protected static void LinkedListByTableName(String tableName, String pKey, StringBuffer sb) {
        //表名全部转小写对比  低代码表名不区分大小写
        switch (tableName.toLowerCase()) {
            case "con_contract":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.Con_Project_Id  ");
                break;
            case "con_contract_list":
            case "con_ec":
            case "con_ec_fee":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=con.Con_Project_Id ");
                break;
            case "pe_info":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.Pe_Project_Id  ");
                break;
            case "function_time_config":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=b.project_id  ");
                break;
            case "pe_result":
            case "pe_info_collect_dtl":
            case "pe_meetingconfirm_back":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=p.Pe_Project_Id  ");
                break;
            case "guarantee_letter_back":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=gls.P_ID  ");
                break;
            case "archive_file":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=ap.P_Id  ");
                break;
            default:
                if (ValidateUtil.isEmpty(pKey)) sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.P_ID ");
                else sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn." + pKey + " ");
                break;
        }
    }

    /**
     * 将创建人字段组装成  主体表别名.创建人字段
     * 用于SQL组装
     *
     * @param dbCreateFiled
     * @return
     */
    public static String dbCreateFiledAlias(String dbCreateFiled) {
        if (dbCreateFiled.contains(".")) return dbCreateFiled;
        else return "mn." + dbCreateFiled;
    }

    public static String alias(String dbCreateFiled) {
        if (dbCreateFiled.contains(".")) return dbCreateFiled.split("\\.")[0];
        else return "mn";
    }

    /**
     * 获取主表别名
     *
     * @param dbCreateFiled
     * @return
     */
    public static String getFiledAlias(String dbCreateFiled) {
        if (dbCreateFiled.contains(".")) return dbCreateFiled.split("\\.")[0];
        else return "mn";
    }

    /**
     * 获取创建人字段别名
     * 用于确认当前主体表单sql 别名
     *
     * @param dbCreateFiled
     * @return
     */
    public static String getDbCreateFiledAlias(String dbCreateFiled) {
        String[] alias = dbCreateFiled.split("\\.");
        if (alias.length > 1) return alias[0];
        else return "mn";
    }

    /**
     * 创建人和审批人鉴权
     * @param uId
     * @param createUidFiled
     * @param primaryKey
     * @return
     */
    public static String getAuthSqlOnlyCheckUser(String uId, String createUidFiled,  String primaryKey) {
        StringBuffer sb = new StringBuffer();
        sb.append(" where (mn." + createUidFiled + " ='"+uId+"' ");
        sb.append(" or (mn." + primaryKey + " in");
        sb.append("(" + " select  tp_entityid   from Sys_Task_Processin where tp_user='" + uId + "')))");
        return sb.toString();
    }

    /**
     * 创建人和审批人鉴权（城建）
     *
     * @param uId
     * @param createUidFiled
     * @param primaryKey
     * @return
     */
    public static String getAuthSqlOnlyCheckUserCJ(String uId, String createUidFiled, String primaryKey, String cId, String state, Boolean isProject) {
        StringBuilder sb = new StringBuilder();
        String createUidSql = createUidFiled.contains(".") ? createUidFiled : "mn." + createUidFiled;
        String primaryKeySql = primaryKey.contains(".") ? primaryKey : "mn." + primaryKey;
        sb.append(" left join Sys_User asu on asu.U_Id=").append(createUidSql).append(" ");
        // 创建人
        sb.append(" where (").append(createUidSql).append(" ='").append(uId).append("' ");
        // 流程处理人、抄送人、协同人
        sb.append(" or (").append(primaryKeySql).append(" in");
        sb.append("(" + " select tmp.entityId from " + " (" + " select tp_entityid as entityId  from Sys_Task_Processin where tp_user='").append(uId).append("'")
                .append(" union")
                .append(" select CC_FileID as entityId  from SYS_CarbonCopy where  CC_User='").append(uId).append("'")
                .append(" union")
                .append(" select CP_FileID as entityId  from SYS_CoordinationProcess where  CP_User='").append(uId).append("'")
                .append(" ) tmp left join  Sys_File sf  on tmp.entityId=sf.FileId ").append(" )) ");
        if (isProject) {
            sb.append(" or (asu.C_Id = '").append(cId).append("' and mn.ppr_id is not null and mn.ppr_id <> '' and mn.").append(state).append(" = 0) ");
        }
        sb.append(")");
        return sb.toString();
    }

    public static  String getAuthSql(String uId, String createUidFiled,  String primaryKey,String userType){
        StringBuffer sb = new StringBuffer();
        // 创建人
        if (createUidFiled.contains(".")) {
            sb.append(" where (").append(createUidFiled).append(" ='").append(uId).append("' ");
        } else {
            sb.append(" where (mn.").append(createUidFiled).append(" ='").append(uId).append("' ");
        }
        //region 流程处理人、抄送人、协同人
        if (primaryKey.contains(".")) {
            sb.append(" or (").append(primaryKey).append(" in");
        } else {
            sb.append(" or ( mn.").append(primaryKey).append(" in");
        }
        sb.append("(" + " select tmp.entityId from " + " (" + " select tp_entityid as entityId  from Sys_Task_Processin where tp_user='" + uId + "'" + " union" + " select CC_FileID as entityId  from SYS_CarbonCopy where  CC_User='" + uId + "'" + " union" + " select CP_FileID as entityId  from SYS_CoordinationProcess where  CP_User='" + uId + "'" + " ) tmp left join  Sys_File sf  on tmp.entityId=sf.FileId " + " )))");
        //内部用户可以看到所有的数据

        //外部用户只能看到自己企业创建的数据
//        sb.append(" or (").append(userType).append("='02'");
//        sb.append(" and ").append(" )");

        //endregion
        return sb.toString();
    }

    /**
     * 华润authSql
     * 外部用户
     * constructionControl  true 是监理单位或全过程咨询单位
     * constructionUnit true 是其他类型单位用户
     * 内部用户
     * departments 可查看数据所属组织机构列表
     * departments  null 则具有所有权限
     * departments 空集合（非null） 内部用户仅看组织机构
     * <p>
     * param cId                 单位ID
     * param constructionControl true 是监理单位或全过程咨询单位
     * param constructionUnit    true 是其他类型单位用户
     * param departments         可查看数据所属组织机构列表
     *
     * @param uId           用户ID
     * @param dbCreateFiled 当前操作主表上的创建人数据库字段
     * @param userAuth      枚举类 OutUserAuthEnum
     * @param uType         用户类型
     * @param info          用户信息
     * @param pKey          表单项目外键名（tableName为空时生效）
     * @return
     */
    public static String getCrAuthSql(String tableName, String uId, String dbCreateFiled, Integer userAuth, String uType, Map<String, Object> info, String pKey) {
        List<String> departments;
        String cId = info.containsKey("cId") && ValidateUtil.isNotNull(info.get("cId")) ? info.get("cId").toString() : null;
        Boolean constructionControl = info.containsKey("constructionControl") && ValidateUtil.isNotNull(info.get("constructionControl")) ? Boolean.parseBoolean(info.get("constructionControl").toString()) : null;
        Boolean constructionUnit = info.containsKey("constructionUnit") && ValidateUtil.isNotNull(info.get("constructionUnit")) ? Boolean.parseBoolean(info.get("constructionUnit").toString()) : null;
        Object departmentJson = info.containsKey("departments") && ValidateUtil.isNotNull(info.get("departments")) ? info.get("departments") : null;
        if (ValidateUtil.isEmpty(departmentJson)) {
            departments = new ArrayList<>();
        } else if (departmentJson.toString().equals("all")) {
            departments = null;
        } else {
            departments = JacksonUtils
                    .parseObject(JacksonUtils
                            .toJSONString(departmentJson), new TypeReference<List<String>>() {
                    });
        }
        StringBuffer sb = new StringBuffer();
        if (StringUtils.equalsIgnoreCase(pKey, "PROJECT_ID")) {
            LinkedCrListByTableName(ValidateUtil.isEmpty(tableName) ? "as#h#$!uhdfi" : tableName, pKey, sb);
        } else {
            LinkedCrListByTableNamePkey(ValidateUtil.isEmpty(tableName) ? "as#h#$!uhdfi" : tableName, pKey, sb);
        }
        if (Integer.parseInt(uType) == UserTypeEnum.Inner_User.getIndex()) {//内部用户
            sb.append(" where 1=1 ");
            if (Objects.equals(OutUserAuthEnum.Only_Inner.getIndex(), userAuth)) {
                //内部用户不鉴权 外部用户无权
                return "where 1=1";
            }

            if (departments == null) {
                //具有所有查看权限
                return sb.toString();
            } else if (departments.size() < 1) {
                //仅查看项目组织机构
                sb.append("and fpro.P_Id in(");
                sb.append("select dp.P_Id from SYS_Department_P dp left join  SYS_Position_P pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='").append(uId).append("') ");
            } else {
                sb.append("and  (fpro.P_Competent_Department in (");
                sb.append("'").append(departments.get(0)).append("'");
                for (int i = 1; i < departments.size(); i++) {
                    sb.append(",'").append(departments.get(i)).append("'");
                }
                sb.append(") ");
                if (!ValidateUtil.isEmpty(tableName) && tableName.equals("Gantt_Plan")) {
                    sb.append(" or (mn.").append(dbCreateFiled).append("=").append("'").append(uId).append("' or ");
                    sb.append(" (select C_Id from Sys_User where U_Id='").append(uId).append("')= (select C_Id from Sys_User where U_Id=mn.Plan_Create_Uid) ");
                    sb.append(" ) ");
                } else if (!ValidateUtil.isEmpty(tableName) && tableName.equals("function_time_config")) {
                    sb.append(" or b.").append(dbCreateFiled).append("=").append("'").append(uId).append("'  ");
                } else if (!ValidateUtil.isEmpty(dbCreateFiled))
                    sb.append(" or ").append(dbCreateFiledAlias(dbCreateFiled)).append("=").append("'").append(uId).append("'");
                sb.append(") ");
            }
            //外部用户
        } else if (Integer.parseInt(uType) == UserTypeEnum.Out_User.getIndex()) {
            if (Objects.equals(userAuth, OutUserAuthEnum.NOT.getIndex()) || Objects.equals(userAuth, OutUserAuthEnum.Only_Inner.getIndex())) {
                //当前模块外部用户无权查看
                return null;
            }
            if ((constructionControl == null && constructionUnit == null) || constructionControl == constructionUnit) {
                //无法判断外部单位类型
                return null;
            }

            if (Objects.equals(userAuth, OutUserAuthEnum.Organization.getIndex())) {
                sb.append(" where ((1=1 ");
                //校验用户是否在项目组织机构
                sb.append("and fpro.P_Id in(");
                sb.append("select dp.P_Id from  SYS_Department_P dp left join  SYS_Position_P pp on pp.PD_ID=dp.PD_ID left join SYS_Position_User_P pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='").append(uId).append("') ) ");
            } else if (Objects.equals(userAuth, OutUserAuthEnum.Unit_Auth.getIndex())) {
                Object conTablesJs = info.containsKey("conTables") && ValidateUtil.isNotNull(info.get("conTables")) ? info.get("conTables") : null;
                List<String> conTables = null;
                if (ValidateUtil.isNotNull(conTablesJs)) {
                    conTables = JacksonUtils.parseArray(JacksonUtils.toJSONString(conTablesJs), String.class);
                }
                boolean conMd = false;
                if (ValidateUtil.isNotEmpty(tableName) && !CollectionUtils.isEmpty(conTables) && !conTables.stream().noneMatch(s -> s.equalsIgnoreCase(tableName))) {
                    //合同表本身
                    if (tableName.equalsIgnoreCase("jpmcontract_contract")) {
                        // todo 这里要改为jpmcontract_contract
                        sb.append(" left join jpmcontract_contract fcon on fcon.CONTRACT_ID = mn.CONTRACT_ID left join other_party_b_unit partyB on partyB.PARENT_ID = fcon.CONTRACT_ID where ( (1=1 ");
                    } else {
                        sb.append(" left join jpmcontract_contract fcon on fcon.PROJECT_ID = fpro.P_ID left join other_party_b_unit partyB on partyB.PARENT_ID = fcon.CONTRACT_ID WHERE ( ( 1 = 1 ");
                    }
                    //按用户的单位授权
                    sb.append(" and ( fcon.CONTRACT_ID in ( select fcon.CONTRACT_ID from jpmcontract_contract fcon left join other_party_b_unit partyB on partyB.PARENT_ID = fcon.CONTRACT_ID where 1 = 1 ");
                    conMd = true;
                } else {
                    sb.append(" where ( ( ");
                    //按用户的单位授权
                    sb.append(" ( fpro.P_Id in ( select fcon.PROJECT_ID from jpmcontract_contract fcon left join other_party_b_unit partyB on partyB.PARENT_ID = fcon.CONTRACT_ID where 1 = 1 ");
                }
                sb.append(" and ( ");
                sb.append("fcon.PARTY_B_UNIT_ID = '").append(cId).append("'");
                sb.append(" or partyB.COMPANY_ID ='").append(cId).append("'");
                /*sb.append(" or fcon.Con_Party_B2='" + cId + "'"); // 连接jpmcontract_contract合同表其他乙方是子表
                sb.append(" or fcon.Con_Party_B3='" + cId + "'");
                sb.append(" or fcon.Con_Party_B4='" + cId + "'");
                sb.append(" or fcon.Con_Party_C_Id='" + cId + "'");
                sb.append(" or fcon.Con_Party_D_Id='" + cId + "'");*/
                sb.append(" )) ");
                //或者在项目上是监理或全过程咨询单位
                sb.append(" or fpro.P_Id in ( select P_ID from SYS_Participant_Unit where (C_Type='03' or C_Type='05') and C_Id='").append(cId).append("') ");
                sb.append(" ) ");
                //校验用户是否在项目组织机构
                //if (ValidateUtil.isNotEmpty(tableName) && tableName.equals("Con_Pay"))
                sb.append("and fpro.P_Id in(");
                //else
                //    sb.append("or fpro.P_Id in(");
                sb.append("select dp.P_Id from  SYS_Department_P  dp left join  SYS_Position_P  pp on pp.PD_ID=dp.PD_ID left join  SYS_Position_User_P  pup on pp.PP_ID=pup.PP_ID ");
                sb.append(" where pup.U_ID='").append(uId).append("')");
                //黑名单合同
                /*if (conMd && !CollectionUtils.isEmpty(conIds)) {
                    sb.append(" and fcon.CONTRACT_ID not in ('").append(conIds.get(0)).append("'");
                    for (int i = 1; i < conIds.size(); i++) {
                        sb.append(",'").append(conIds.get(i)).append("'");
                    }
                    sb.append(") ");
                }*/
                sb.append(" )");
            }
            if (!ValidateUtil.isEmpty(tableName) && tableName.equals("Gantt_Plan")) {
                sb.append(" ) ");
            } else if (!ValidateUtil.isEmpty(tableName) && tableName.equals("function_time_config")) {
                sb.append(" or b.").append(dbCreateFiled).append("=").append("'").append(uId).append("' ) ");
            } else if (!ValidateUtil.isEmpty(dbCreateFiled))
                sb.append(" or ").append(dbCreateFiledAlias(dbCreateFiled)).append("=").append("'").append(uId).append("' ) ");
            else sb.append(" ) ");
        } else {
            //未知用户类型
            return null;
        }
        return sb.toString();
    }
    protected static void LinkedCrListByTableName(String tableName, String pKey, StringBuffer sb) {
        switch (tableName) {
            case "jpmcontract_contract":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.Project_Id  ");
                break;
            case "Con_Contract_List":
            case "Con_Ec":
            case "Con_Ec_Fee":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=con.Con_Project_Id ");
                break;
            case "Pe_Info":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.Pe_Project_Id  ");
                break;
            case "function_time_config":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=b.project_id  ");
                break;
            case "Pe_Result":
            case "Pe_Info_Collect_Dtl":
            case "Pe_MeetingConfirm_Back":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=p.Pe_Project_Id  ");
                break;
            case "Guarantee_Letter_Back":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=gls.P_ID  ");
                break;
            case "Archive_File":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=ap.P_Id  ");
                break;
            default:
                if (ValidateUtil.isEmpty(pKey)) sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.P_ID ");
                else sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn." + pKey + " ");
                break;
        }
    }

    protected static void LinkedCrListByTableNamePkey(String tableName, String pKey, StringBuffer sb) {
        switch (tableName) {
            case "jpmcontract_contract":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.Project_Id  ");
                break;
            case "Con_Contract_List":
            case "Con_Ec":
            case "Con_Ec_Fee":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=con.Con_Project_Id ");
                break;
            case "Pe_Info":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.Pe_Project_Id  ");
                break;
            case "function_time_config":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=b.project_id  ");
                break;
            case "Pe_Result":
            case "Pe_Info_Collect_Dtl":
            case "Pe_MeetingConfirm_Back":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=p.Pe_Project_Id  ");
                break;
            case "Guarantee_Letter_Back":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=gls.P_ID  ");
                break;
            case "Archive_File":
                sb.append(" left join  Pre_Project fpro on fpro.P_Id=ap.P_Id  ");
                break;
            default:
                if (ValidateUtil.isEmpty(pKey)) sb.append(" left join  Pre_Project fpro on fpro.P_Id=mn.P_ID ");
                else sb.append(" left join  Pre_Project fpro on fpro.P_Id=" + pKey + " ");
                break;
        }
    }
}
