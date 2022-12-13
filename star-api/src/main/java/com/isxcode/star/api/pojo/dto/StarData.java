package com.isxcode.star.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StarData {

    private List<String> columnNames;

    private List<List<String>> dataList;

    private String applicationId;

    private String appFinalStatus;

    private String appState;

    private List<String> logList;

    private String error;
}
