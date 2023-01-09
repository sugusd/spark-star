package com.isxcode.star;

import lombok.Data;
import org.apache.hadoop.hive.ql.lib.NodeProcessorCtx;

import java.util.ArrayList;
import java.util.List;

@Data
public class NodeProcessorCtxDto implements NodeProcessorCtx {

    private List<String> tableNames = new ArrayList<>();
}
