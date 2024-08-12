
public class Map {
	String name;
	double defSideWR;
	double attSideWR;
	double defSidePWR;
	double attSidePWR;
	double secDefSide;
	double secAttSide;
	Team defenders;
	Team attackers;
	
	
	public Map(String name, double defSideWR, double attSideWR, double defSidePWR, double attSidePWR, 
			double secDefSide, double secAttSide) {
		this.name = name;
		this.defSideWR = defSideWR;
		this.attSideWR = attSideWR;
		this.defSidePWR = defSidePWR;
		this.attSidePWR = attSidePWR;
		this.secDefSide = secDefSide;
		this.secAttSide = secAttSide;
	}
	
	public void setDetails(String name, double defSideWR, double attSideWR, double defSidePWR, double attSidePWR,
			double secDefSide, double secAttSide) {
		this.name =name;
		this.defSideWR = defSideWR;
		this.attSideWR = attSideWR;
		this.defSidePWR = defSidePWR;
		this.attSidePWR = attSidePWR;
		this.secDefSide = secDefSide;
		this.secAttSide = secAttSide;
	}
	
	public void setName(String name) {
		this.name =name; 
	}
	
	public String getName() {
		return name;
	}
	
	public void setDefSideWR(double wr) {
		this.defSideWR = wr;
	}
	
	public void setAttSideWR(double wr) {
		this.attSideWR = wr;
	}
	
	public void setDefSidePWR(double wr) {
		this.defSidePWR = wr;
	}
	
	public void setAttSidePWR(double wr) {
		this.attSidePWR = wr;
	}
	
	public void setSecDefSide(double wr) {
		this.secDefSide = wr;
	}
	
	public void setSecAttSide(double wr) {
		this.secAttSide = wr;
	}
	
	public void setDefSide(Team def) {
		this.defenders = def;
	}
	
	public void setAttSide(Team att) {
		this.attackers = att;
	}
}
