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
	String output;
	public void read(){
		testList = new ArrayList<TestWord>();
		AVLMap = new TreeMap<String,String>();
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
            	while(true){
            		temp = in.next();
            		if(temp.matches("[0-9]+")){
            			rank = Integer.parseInt(temp);
            			break;
            		}
            		distractor += " " + temp;
            	}            	
            	AVLMap.put(distractor, avlcode);
//            	System.out.println(distractor);
//            	rank = in.nextInt();
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
	public List<TestWord> generate(String word){
		output = "Original Word:" + word + "\n";
		List<TestWord> result = new ArrayList<TestWord>();
		TestWord target = null;
		for(TestWord t: testList){
			if(t.getWord().equals(word)){
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
//				System.out.println("------------------------------------\n");
//				System.out.println(output + target.toString());

				output += target.toString();
			}
		}		
		if(target ==null){
			System.out.println( "Can't find the word" );
			return null;
		}		
		return result;
	}
	public void exportToCSV(String fileName){
		ExportFile csv = new ExportFile(fileName,output);
		csv.exportCsv();
	}
}


