package com.isxcode.star.common.response;

import com.isxcode.star.api.pojo.BaseResponse;
import lombok.Getter;
import lombok.Setter;

public class SuccessException extends RuntimeException {

	@Setter @Getter private BaseResponse<Object> baseResponse;

	public SuccessException(BaseResponse<Object> baseResponse) {

		this.baseResponse = baseResponse;
	}
}
