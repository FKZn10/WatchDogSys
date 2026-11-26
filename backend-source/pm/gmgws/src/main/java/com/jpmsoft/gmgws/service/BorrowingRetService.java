package com.jpmsoft.gmgws.service;

import com.jpmsoft.common.base.BaseResponse;

public interface BorrowingRetService {
    BaseResponse getLoanStill(String uid);

    BaseResponse getReimbursement(String uid);
}
