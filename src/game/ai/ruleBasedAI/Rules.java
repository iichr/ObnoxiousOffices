package game.ai.ruleBasedAI;

import java.io.Serializable;
import java.util.ArrayList;

import game.ai.ruleBasedAI.WorkingMemory.activityValues;

/**
 * 
 * @author Atanas K. Harbaliev. Created on 22.02.2017
 *
 */
public class Rules implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//arraylist containing all rules
	private ArrayList<WorkingMemory> rules = new ArrayList<WorkingMemory>();
	
	/**
	 * Get methond
	 * @return returns an arraylist containing all rules
	 */
	public ArrayList<WorkingMemory> getRules(WorkingMemory wm) {
		//                               isWorking			 isHacking			isRefreshing	  hasProgressedMore
		rules.add(wm.setAllAndReturn(activityValues.No,  activityValues.No,  activityValues.No,  activityValues.No));
		rules.add(wm.setAllAndReturn(activityValues.Yes, activityValues.No,  activityValues.No,  activityValues.No));
		rules.add(wm.setAllAndReturn(activityValues.No,  activityValues.Yes, activityValues.No,  activityValues.No));
		rules.add(wm.setAllAndReturn(activityValues.No,  activityValues.No,  activityValues.Yes, activityValues.No));
		rules.add(wm.setAllAndReturn(activityValues.No,  activityValues.No,  activityValues.Yes, activityValues.Yes));
		rules.add(wm.setAllAndReturn(activityValues.No,  activityValues.Yes, activityValues.No,  activityValues.Yes));
		rules.add(wm.setAllAndReturn(activityValues.Yes, activityValues.No,  activityValues.No,  activityValues.Yes));
		rules.add(wm.setAllAndReturn(activityValues.No,  activityValues.No,  activityValues.No,  activityValues.Yes)); 
		
		return rules;
	}
}


/*

*/