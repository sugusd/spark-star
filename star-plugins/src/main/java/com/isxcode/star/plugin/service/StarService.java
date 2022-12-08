package com.isxcode.star.plugin.service;

import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.api.response.StarRequest;
import org.springframework.stereotype.Service;

@Service
public interface StarService {

    StarData queryData(StarRequest starRequest);
}
