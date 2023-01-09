package com.isxcode.star;

import com.isxcode.star.api.exception.StarException;
import org.apache.hadoop.hive.ql.lib.*;
import org.apache.hadoop.hive.ql.parse.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SqlParseUtils implements NodeProcessor {

    public List<String> parseHiveSql(String sql) {

        ASTNode tree;
        try {
            tree = ParseUtils.parse(sql, null);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new StarException(sql + ":sql解析出错");
        }
        while ((tree.getToken() == null) && (tree.getChildCount() > 0)) {
            tree = (ASTNode) tree.getChild(0);
        }

        Map<Rule, NodeProcessor> rules = new LinkedHashMap<Rule, NodeProcessor>();
        NodeProcessorCtxDto nodeProcessorCtxDto = new NodeProcessorCtxDto();
        Dispatcher dispatcher = new DefaultRuleDispatcher(this, rules, nodeProcessorCtxDto);
        GraphWalker ogw = new DefaultGraphWalker(dispatcher);
        ArrayList<Node> topNodes = new ArrayList<Node>();
        topNodes.add(tree);
        try {
            ogw.startWalking(topNodes, null);
        } catch (SemanticException e) {
            e.printStackTrace();
            throw new StarException(sql + ":sql解析异常");
        }

        return nodeProcessorCtxDto.getTableNames();
    }

    @Override
    public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs)  {

        ASTNode pt = (ASTNode) nd;
        NodeProcessorCtxDto parseHiveSqlDto = (NodeProcessorCtxDto) procCtx;
        if (pt.getToken().getType() == HiveParser.TOK_TABNAME) {
            parseHiveSqlDto.getTableNames().add(BaseSemanticAnalyzer.getUnescapedName((ASTNode) pt.getChild(0)));
        }
        return parseHiveSqlDto;
    }
}
