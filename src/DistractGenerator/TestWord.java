package DistractGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TestWord {
	private String word;
	private String AVLcode;
	private String pos;
	private int rank;
	private String distractor;
	private WordList cat2fl;
	private WordList cat3fl;
	private WordList cat2ll;
	private WordList cat3ll;
	private WordList catln;
	private Set<String> totalList;
	private TreeMap<String, String> AVLMap;
	private List<ValuedWord> valuedWords;
	public TestWord(String AVL, String word, String pos, String distractor, int rank, TreeMap<String,String> AVLMap){
		this.word = word;
		this.AVLcode = AVL;
		this.pos = pos;
		this.rank = rank;
		this.distractor = distractor;
		totalList = new TreeSet<String>();
		valuedWords= new ArrayList<ValuedWord>();
		this.AVLMap = AVLMap;
	}
	public int getRank(){
		return rank;
	}
	public void set3fl(String fl){
		cat3fl = new Cat3FL(word, fl, AVLMap);
	}
	public void set2fl(String fl){
		cat2fl = new Cat2FL(word, fl, AVLMap);
	}
	public void set3ll(String fl){
		cat3ll = new Cat3LL(word, fl, AVLMap);
	}
	public void set2ll(String fl){
		cat2ll = new Cat2LL(word, fl, AVLMap);
	}
	public void setnl(int num){
		catln = new CatLN(word, num, AVLMap);
	}
	public WordList get3fl(){
		return cat3fl;
	}
	public WordList get2fl(){
		return cat2fl;
	}
	public WordList get3ll(){
		return cat3ll;
	}
	public WordList get2ll(){
		return cat2ll;
	}
	public WordList getln(){
		return catln;
	}
	private int min(int a, int b){
		if(a < b)
			return a;
		return b;
	}
	public int getMatches(){
		return valuedWords.size();
	}
	private int sequencing(String s1, String s2){
		int len1 = s1.length();
		int len2 = s2.length();
		int[][] d = new int[len1 +1][len2 + 1];
		int i,j;
		for(i=0;i<=len1;i++){
			d[i][0] = i;
		}
		for(j=0;j<=len2;j++){
			d[0][j] = j;
		}
		
		for(i=1;i<=len1;i++){
			for(j=1;j<=len2;j++){
				d[i][j] = min(d[i-1][j] + 1, d[i][j-1] + 1);
				if(s1.charAt(i-1) == s2.charAt(j-1)){
					d[i][j] = min(d[i][j], d[i-1][j-1]);
				}				
			}
		}
		return d[len1][len2];
	}
	public void checkWord(TestWord t){
		boolean c1,c2,c3,c4;
		if(!distractor.equals(t.getDistractor()) && !word.equals(t.getDistractor())){
			c1 = cat3fl.add(t);
			c2 = cat2fl.add(t);
			c3 = cat3ll.add(t);
			c4 = cat2ll.add(t);
			if(c1 || c2 || c3 || c4){
				catln.add(t);				
				totalList.add(t.getDistractor());
			}
		}
	}
	public void prioritize(){
		int v;
		for (String s: totalList){
			v = sequencing(distractor, s);
			valuedWords.add(new ValuedWord(s, v));
		}
		Collections.sort(valuedWords, new Comparator<ValuedWord>() {
			@Override
			public int compare(ValuedWord o1, ValuedWord o2) {
				return o1.value - o2.value;
			}
		});			
	}
	public void addLN(TestWord t){
		if(!distractor.equals(t.getDistractor()) && !word.equals(t.getDistractor())){
			catln.add(t);
		}
	}
	
	public String getWord(){
		return word;
	}
	public String getPos(){
		return pos;
	}
	public String getDistractor(){
		return distractor;
	}
	public String toString(){
		String temp;
		temp = "Distractor: " + distractor +" (Rank:" + rank + ")\n";
		temp += "2FL  \n";
		temp += cat2fl.getWords();
//		temp += cat2fl.getCodes() + "\n";
		temp += "3FL  \n";
		temp += cat3fl.getWords() ;
//		temp += cat3fl.getCodes() + "\n";
		temp += "2LL  \n";
		temp += cat2ll.getWords() ;
//		temp += cat2ll.getCodes() + "\n";
		temp += "3LL \n";
		temp += cat3ll.getWords() ;
//		temp += cat3ll.getCodes() + "\n";
		temp += "L# \n";
		temp += catln.getWords() ;
//		temp += catln.getCodes() + "\n";
		temp += "Recommended \n";
		int max_size = 5;
		if(valuedWords.size() < 5)
			max_size = valuedWords.size();
		for(int i=0;i<max_size;i++){
			temp += valuedWords.get(i).word;
			if(i<max_size - 1)
				temp+= ", ";
			else
				temp += "\n";
		}
		String avl;
		for(int i=0;i<max_size;i++){
			avl = AVLMap.get(valuedWords.get(i).word);
			if(avl == null)
				avl = "N/A";
			temp += avl;
		
			if(i<max_size - 1)
				temp+= ", ";
			else
				temp += "\n";
		}
		temp+="\n";
		temp += "----------------------------------------------------------------------------------------------\n";
		return temp;
	}
}

class ValuedWord{
	String word;
	int value;
	public ValuedWord(String word,int value){
		this.word = word;
		this.value = value;
	}
}
