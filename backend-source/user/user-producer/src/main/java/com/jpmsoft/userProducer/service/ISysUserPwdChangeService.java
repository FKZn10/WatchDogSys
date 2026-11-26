package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.SysUserPwdChange;


public interface ISysUserPwdChangeService extends IService<SysUserPwdChange> {

    void insertUserPwdChange(String oldPw, String newPw, String uID, String uName, String accountUId);

    /**
     * 密码是否被使用过(近期3条数据)
     *
     * @param pwd
     * @param uId
     * @return true 已使用过 false未使用
     */
    Boolean checkPwdUsed(String pwd, String uId);


    /**
     * 删除用户密码修改记录
     * @param uId
     */
    void delUserPwdChangeRecord(String uId);

}
