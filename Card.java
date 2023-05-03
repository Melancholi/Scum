public class Card{
	private Status status;
	private Value value;
	
	public Card(Value nbValue, Status StrStatus){
		this.value=nbValue;
		this.status=StrStatus;
	}
	public String toString(){
		return ""+this.value+" "+this.status;
	}
	public Value getValue(){
		return this.value;
	}
	public Status getStatus(){
		return this.status;
	}
	public boolean isBiggerThan(Card otherCard){
		int originalValue = this.value.getNbValue();
		if(otherCard ==null){
			return false;
		}
		int valueToCompare = otherCard.getValue().getNbValue();
		
		return originalValue>valueToCompare;
	}
	public boolean equals(Card otherCard) {
        if(otherCard==null){
			return false;
		}
        return this.value.equals(otherCard.getValue()) && this.status.equals(otherCard.getStatus());
    }
	
	//DEBUGGING
	// public static void main(String[]args){
		// Card card = new Card(Value.TWO,Status.SPADES);
		// Card cardSmall = new Card(Value.THREE,Status.SPADES);
		// System.out.println(card.isBiggerThan(cardSmall));
	// }
}
		
		
		