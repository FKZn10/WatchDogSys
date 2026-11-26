package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.ValidateUtil;

/**
 * 判断工具类
 * create by: Aaron
 */
public class JudgeUtil {

    /**
     * 检查流程状态
     * 审批结束，作废
     * 不可修改
     * 主要使用场景：修改前前置判断
     *
     * @param state
     * @return
     */
    public static Boolean nonModifyState(Integer state) {
        if (state == null
                || state.equals(ApprovalStatusEnum.Invalid.getIndex())
                || state.equals(ApprovalStatusEnum.Already_Over.getIndex()))
            return true;
        return false;
    }

    /**
     * 状态为已审批完成
     * 主要使用场景：删除前前置判断
     *
     * @param state
     * @return
     */
    public static Boolean isApprovedState(Integer state) {
        if (ValidateUtil.isNotNull(state) && state.equals(ApprovalStatusEnum.Already_Over.getIndex()))
            return true;
        return false;
    }

    /**
     * 不是退回发起人或者未发起状态
     *
     * @param state
     * @return
     */
    public static Boolean isReutrnerState(Integer state) {
        if (ApprovalStatusEnum.Not_Started.getIndex() != state &&
                ApprovalStatusEnum.Returned_Initiator.getIndex() != state)
            return false;
        return true;
    }


    @Deprecated
    public static Boolean checkString(String... args) {
        for (String s : args) {
            if (ValidateUtil.isEmpty(s))
                return true;
        }
        return false;
    }

    /**
     * 检查对象是否为null
     *
     * @param args
     * @return
     */
    public static Boolean checkObject(Object... args) {
        for (Object o : args) {
            if (o == null)
                return true;
        }
        return false;
    }

    /**
     * 判断字符是否为空
     * 返回字符本身或空字符
     *
     * @param str
     * @return
     */
    public static String StrIsEmptyRtStr(String str) {
        return ValidateUtil.isEmpty(str) ? "" : str;
    }
}
