public enum Status{
	CLUBS("CLUBS"),
	DIAMOND("DIAMOND"),
	SPADES("SPADES"),
	HEARTS("HEARTS");
	// JOKER("JOKER")

	private String value;
	Status(String suitCard){
			this.value = suitCard;
	}
	public String toString(){
		return this.value;
	}
}
