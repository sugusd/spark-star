package com.isxcode.star.api.exception;

import com.isxcode.oxygen.common.response.AbstractException;
import com.isxcode.oxygen.common.response.AbstractExceptionEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StarException extends AbstractException {

    public StarException(AbstractExceptionEnum abstractExceptionEnum) {
        super(abstractExceptionEnum);
    }

    public StarException(String code, String msg) {
        super(code, msg);
    }

    public StarException(String msg) {
        super(msg);
    }
}
