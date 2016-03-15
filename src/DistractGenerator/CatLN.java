package DistractGenerator;

import java.util.HashMap;
import java.util.TreeMap;

public class CatLN extends WordList{
	private int condition;
	public CatLN(String w,int cond, TreeMap<String,String> map) {		
		super(w,map);
		condition = cond;
	}
	public Object getCondition(){
		return condition;
	}
	@Override
	public boolean add(TestWord candidate) {
		if(words.contains(candidate.getDistractor()))
			return false;
		int count = (int)candidate.getln().getCondition();
		if(count == condition){
			words.add(candidate.getDistractor());
			return true;
		}
		return false;
	}
}
