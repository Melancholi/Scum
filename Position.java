public enum Position{
	
	//Since I planned my game for 3~4 players, the vice or neutral roles can not be used
	PRESIDENT("President"),
	VICEPRESIDENT("Vice-President"),
	NEUTRAL("Neutral"),
	VICESCUM("Vice-Asshole"),
	SCUM("Asshole");
	private String value;
	Position(String position){
		this.value=position;
	}
	public String toString(){
		return this.value;
	}
}