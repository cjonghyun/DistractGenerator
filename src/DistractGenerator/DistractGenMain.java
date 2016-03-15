package DistractGenerator;

import java.util.Scanner;

public class DistractGenMain {

	public static void main(String[] args) {
		DistractGenerator dg = new DistractGenerator();
		dg.read();
	    Scanner scan = new Scanner(System.in);
	    String input;
	    input = scan.nextLine();
	    dg.generate(input);
	    }

}
