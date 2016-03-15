package DistractGenerator;

import java.util.HashMap;
import java.util.TreeMap;

public class Cat3LL extends WordList{
	private String condition;
	public Cat3LL(String w,String cond, TreeMap<String,String> map) {		
		super(w,map);
		condition = cond;
	}
	public String getCondition(){
		return condition;
	}
	@Override
	public boolean add(TestWord candidate) {
		if(words.contains(candidate.getDistractor()))
			return false;
		String word = (String)candidate.get3ll().getCondition();
		if(word.equals(condition)){
			words.add(candidate.getDistractor());
			return true;
		}
		return false;
	}
}
