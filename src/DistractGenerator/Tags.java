package DistractGenerator;

public class Tags {
	public String pos;
	public String cat3fl;
	public String cat2fl;
	public String cat3ll;
	public String cat2ll;
	int catln;	
	public Tags(String pos, String cat2fl, String cat3fl, String cat3ll, String cat2ll, int catln ){
		this.pos = pos;
		this.cat3fl = cat3fl;
		this.cat2fl = cat2fl;
		this.cat3ll = cat3ll;
		this.cat2ll = cat2ll;
		this.catln = catln;
	}
}
