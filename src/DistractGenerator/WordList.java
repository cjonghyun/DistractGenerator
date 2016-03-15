package DistractGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public abstract class WordList {
	TreeMap<String, String> AVLMap;	
	ArrayList<String> words;
	String original;
	public WordList(String w, TreeMap<String,String> map){
		original = w;
		this.AVLMap = map;
		words = new ArrayList();
	}
	public abstract Object getCondition();
	public abstract boolean add(TestWord t);
	public ArrayList<String> getWordList(){
		return words;
	}
	public String getWords(){
		if(words.size() == 0){
			return "N/A\n";
		}
		String str = "";
		String endWord = words.get(words.size() -1 );
		for(String s: words){
			str += s;
			if(s.equals(endWord))
				str +=  " \n";
			else
				str +=  ", ";
		}
		return str;
	}
	public String getCodes(){
		if(words.size() == 0){
			return "N/A\n";
		}
		String str = "";
		String endWord = words.get(words.size() -1 );
		for(String s: words){
			str += AVLMap.get(s);
			if(s.equals(endWord))
				str +=  " \n";
			else
				str +=  ", ";
		}
		return str;
	}
	public String getWordsCSV(){
		if(words.size() == 0){
			return "N/A\n";
		}
		String str = "";
		String endWord = words.get(words.size() -1 );
		for(String s: words){
			str += s;
			if(s.equals(endWord))
				str +=  " \n";
			else
				str +=  ", ";
		}
		return str;
	}
	public String getCodesCSV(){
		if(words.size() == 0){
			return "N/A\n";
		}
		String str = "";
		String endWord = words.get(words.size() -1 );
		for(String s: words){
			str += AVLMap.get(s);
			if(s.equals(endWord))
				str +=  " \n";
			else
				str +=  ", ";
		}
		return str;
	}
}
