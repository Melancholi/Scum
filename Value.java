public enum Value{
	THREE(3,"3"),
	FOUR(4,"4"),
	FIVE(5,"5"),
	SIX(6,"6"),
	SEVEN(7,"7"),
	EIGHT(8,"8"),
	NINE(9,"9"),
	TEN(10,"10"),
	JACK(11,"J"),
	QUEEN(12,"Q"),
	KING(13,"K"),
	ACE(14, "A"),
	TWO(15,"2");
	//makes some methods hard to run, so I'm not using it
	// JOKER(50,"JOKER");
	
	private int value;
	private String stringRepresentation;
	Value(int nbValue,String number){
		this.value= nbValue;
		this.stringRepresentation= number;
	}
	public int getNbValue(){
		return this.value;
	}
	
	public String toString(){
		return ""+this.stringRepresentation;
	}
}
	
