package DistractGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class DistractGenerator {
	private ArrayList<TestWord> testList;
	private TreeMap<String, String> AVLMap;
	private TreeMap<String, String> AVLCodeMap;
	
	String output;
	public void read(){
		testList = new ArrayList<TestWord>();
		AVLMap = new TreeMap<String,String>();
		AVLCodeMap = new TreeMap<String,String>();
		try {
			File file = new File("input.txt");
            Scanner in = new Scanner(file);
            String avlcode;
            String original;
            String pos;
            String distractor;
            String temp;
            int rank;
            String cat2fl;
            String cat3fl;
            String cat3ll;
            String cat2ll;
            int catln;
            while(in.hasNextLine()){
            	avlcode = in.next();
            	original = in.next();
            	pos = in.next();
            	distractor = in.next();
            	original = original.toLowerCase();
            	distractor =distractor.toLowerCase();
            	while(true){
            		temp = in.next();
            		if(temp.matches("[0-9]+")){
            			rank = Integer.parseInt(temp);
            			break;
            		}
            		distractor += " " + temp;
            	}            	
            	AVLMap.put(distractor, avlcode);
            	AVLCodeMap.put(avlcode, distractor);
            	cat2fl = in.next();
            	cat3fl = in.next();
            	cat3ll = in.next();
            	cat2ll = in.next();
            	catln = in.nextInt();
//            	System.out.println(original);
            	TestWord newWord = new TestWord(avlcode, original, pos, distractor, rank, AVLMap);
            	newWord.set2fl(cat2fl);
            	newWord.set3fl(cat3fl);
            	newWord.set3ll(cat3ll);
            	newWord.set2ll(cat2ll);
            	newWord.setnl(catln);
            	testList.add(newWord);
//            	if(!distractWords.containsKey(distractor))
 //           		distractWords.put(distractor, new Tags(pos, cat2fl, cat3fl, cat3ll, cat2ll, catln));
            }
            in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(testList, new Comparator<TestWord>() {
			@Override
			public int compare(TestWord o1, TestWord o2) {
				return o1.getRank() - o2.getRank();
			}
		});			
	}
	public String getOutput(){
		return output;
	}
	public void generate(String word){
		String[] inputList = word.split("\n"); 
		output = "";
		List<TestWord> result = new ArrayList<TestWord>();
		for(String w:inputList){
			if(w.substring(0, 3).equals("AVL")){
				w = AVLCodeMap.get(w);
			}			
			output += "Original Word:" + w + "\n";
			TestWord target = null;
			for(TestWord t: testList){
				if(t.getWord().equals(w)){
					target = t;
					for(TestWord t2: testList){
						if(!(t2.getWord().equals(t.getWord())) && t.getPos().equals(t2.getPos())){
							int lengthDifference = Math.abs(t2.getDistractor().length() - t.getDistractor().length());
							if(lengthDifference <= 1)
								t.checkWord(t2);
						}					
					}
					result.add(target);
					target.prioritize();	
					output += target.toString();
				}
			}		
			if(target ==null){
				System.out.println( "Can't find the word: " + w);
				output += "Can't find the word " + w + "\n";				
			}		
		}
	}
	public void exportToCSV(String fileName){
		ExportFile csv = new ExportFile(fileName,output);
		csv.exportCsv();
	}
}


