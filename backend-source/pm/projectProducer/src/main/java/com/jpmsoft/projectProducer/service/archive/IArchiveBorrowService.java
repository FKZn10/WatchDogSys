package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveBorrowRequest;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveBorrow;


public interface IArchiveBorrowService extends IService<ArchiveBorrow> {

    BaseResponse createOrUpdateArchiveBorrow(ArchiveBorrowRequest request);

    BaseResponse getArchiveBorrowList(ArchiveBorrowRequest request);

    BaseResponse getArchiveBorrowById(String abId);

    void delArchiveBorrowByApId(String apId);
}
