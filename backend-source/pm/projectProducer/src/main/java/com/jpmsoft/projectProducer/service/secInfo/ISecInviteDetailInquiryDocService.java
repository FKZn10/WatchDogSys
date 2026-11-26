package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailInquiryDocDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteDetailInquiryDoc;

import java.util.List;

public interface ISecInviteDetailInquiryDocService extends IService<SecInviteDetailInquiryDoc> {


    void createOrUpdateSecInviteDetailInquiryDoc(List<SecInviteDetailInquiryDocDTO> secInviteDetailInquiryDocs, String id);


    List<SecInviteDetailInquiryDocDTO> getSecInviteDetailInquiryDocByWsidIds(List<String> wsidIdList);

    /**
     * 根据工作方案及询价文件id删除
     * @param wsidId
     */
    void delSecInviteDetailInquiryDocByWsidId(String wsidId);

    /**
     * 根据工作方案及询价文件id批量删除
     * @param wsidIdList
     */
    void delBathByWsidIds(List<String> wsidIdList);


}
