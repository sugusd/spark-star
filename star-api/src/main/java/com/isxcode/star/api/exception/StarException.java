package com.isxcode.star.api.exception;

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
