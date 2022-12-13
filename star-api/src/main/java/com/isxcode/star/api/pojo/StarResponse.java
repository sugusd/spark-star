package com.isxcode.star.api.pojo;

import com.isxcode.star.api.pojo.dto.StarData;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StarResponse extends BaseResponse<StarData> {

    public StarResponse(String code, String msg, StarData starData) {

        super(code, msg, starData);
    }

    public StarResponse(String code, String msg) {

        super(code, msg);
    }
}
