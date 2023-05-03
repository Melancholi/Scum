import java.util.Random;
public class DynaArr{
	private Card[] cards;
	private int pointer;
	
	//Make it take as input the length of the array (FUTURE)
	public DynaArr(){
		this.cards = new Card[54];
		this.pointer = 0;
	}
	public int length(){
		return this.pointer;
	}
	public Card[] getCards(){
		return this.cards;
	}
	public Card viewCard(int index){
		return this.cards[index];
	}
	public Card viewTopCard(){
		if(this.pointer==0){
			return this.cards[this.pointer];
		}
		return this.cards[this.pointer-1];
	}
	
	public void setPointer(int newLength){
		this.pointer= newLength;
	}
	//add card
	
	
	public void add(Card card){
		this.cards[this.pointer] = card; //is right?
		this.pointer++;
	}
	//remove at index

	public Card remove(int index){
		Card removedCard = this.cards[index];	
		for(int i=index;i<this.pointer-1;i++){
			this.cards[i]=this.cards[i+1];
		}
		this.cards[this.pointer-1]=null;
		this.pointer--;
		return removedCard;
	}

		
	
	//take one from top
	public Card dealCard(){
		//this part doesnt change anything but I still kept it
		if(this.pointer==0){
			Card cardToGive = this.cards[this.pointer];
			this.cards[this.pointer] = null;
			return cardToGive;
		}
		
		Card cardToGive = this.cards[this.pointer-1];
		this.cards[this.pointer-1] = null;
		this.pointer--;
		return cardToGive;
	}
	public String toString(){
		String wholePile= "";
		
		for(int i =0;i<this.pointer-1;i++){
			wholePile += "[ " + this.cards[i].toString() + " (" + i + ") "+ "] ,\n";
		}
		if(this.pointer==0){
			return wholePile;
		}else{
		wholePile += "[" + this.cards[this.pointer-1].toString() + " (" + (this.pointer-1) + ") "+ "]";
		return wholePile;
		}
	}
	//DECK RELATED METHODS
	public void shuffle(){
		Random rand = new Random();
		for(int i=this.pointer-1;i>=0;i--){
			int randIndex = rand.nextInt(this.pointer);
			Card tempCard = this.cards[randIndex];
			this.cards[randIndex] = this.cards[i];
			this.cards[i]=tempCard;
		}
	}
	
	public void generateDeck(){
		Status[] rankCard;
		Value[] valueCard;
		rankCard = Status.values();
		valueCard = Value.values();
		
		//I want to set up a loop that will initialise the cards object for every suit and every value
		
		for(int everySuit=0;everySuit<rankCard.length;everySuit++){
			for(int everyValue=0;everyValue<valueCard.length;everyValue++){
				cards[this.pointer]=new Card(valueCard[everyValue],rankCard[everySuit]);
				this.pointer++;
			}
		}
	}
	public void discardPile(){
		for(int i=0;i<this.pointer;i++){
			this.cards[i]=null;
		}
		this.pointer=0;
	}
	public boolean isEmpty(){
		int numberOfNulls=0;
		for(int i=0;i<this.pointer;i++){
			if(this.cards[i] == null){
				numberOfNulls++;
			}
		}
		return numberOfNulls==this.pointer;
	}
	
	//DEBUGGING
	
		 
	// public static void main(String[]args){
		// DynaArr deck= new DynaArr();
		// deck.generateDeck();
		// System.out.println(deck);
	// }
}

	
	