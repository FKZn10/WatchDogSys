package com.jpmsoft.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Jpm Exception
 * Created By:李文涛 aaron
 * Created Time:2022/6/30 11:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JPMException extends RuntimeException {

    private static final long serialVersionUID = -3619479514609845977L;

    private String msg;
    private int code = 400;


    public JPMException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public JPMException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
