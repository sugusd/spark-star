package com.isxcode.star.api.pojo;

import com.isxcode.star.api.exception.StarExceptionEnum;
import com.isxcode.star.api.pojo.dto.StarData;
import lombok.Builder;
import lombok.Data;

/**
 * star 请求返回体
 */
@Data
@Builder
public class StarResponse {

    private String code;

    private String message;

    private StarData starData;

    public StarResponse() {
    }

    public StarResponse(String code, String message, StarData starData) {
        this.code = code;
        this.message = message;
        this.starData = starData;
    }

    public StarResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public StarResponse(StarExceptionEnum starExceptionEnum) {

        this.code = starExceptionEnum.getCode();
        this.message = starExceptionEnum.getMessage();
    }
}
