package net.student.request;

import java.util.ArrayList;
import java.util.List;

public class JqGridFilter {
    private String groupOp;//过滤条件关系 and/or
    private List<JqGridRule> rules = new ArrayList<JqGridRule>(0);//过滤条件
    public JqGridFilter() {
        
    }

    public String getGroupOp() {
        return groupOp;
    }
    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }
    public List<JqGridRule> getRules() {
        return rules;
    }
    public void setRules(List<JqGridRule> rules) {
        this.rules = rules;
    }
}
