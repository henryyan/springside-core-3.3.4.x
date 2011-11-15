package org.springside.modules.orm;

import java.util.ArrayList;
import java.util.List;

public class GridFilter {

	protected String groupOp;
	protected List<GridRule> rules = new ArrayList<GridRule>();

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<GridRule> getRules() {
		return rules;
	}

	public void setRules(List<GridRule> rules) {
		this.rules = rules;
	}

}
