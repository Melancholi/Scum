public class Hand{
	private DynaArr playerHand;
	private Position playerRank;
	private String name;
	
	
	public Hand(String name){
		this.playerHand = new DynaArr();
		this.name = name;
	}
	
	//if there is no cards that match, i'm fucked
	public Card removeCard(Card card) {
        for (int i = 0; i < playerHand.length(); i++) {
            if (playerHand.getCards()[i].equals(card)) {
                return playerHand.remove(i);
            }
        }
		return null;
    }
	
	public DynaArr getPlayableCards(Card cardTop) {
		Value two = Value.TWO;
		Value ace = Value.ACE;
		Value valueTopCard = cardTop.getValue();
		DynaArr playableCards = new DynaArr();
		//if you're the scum, you can play cards of the same value :))))
		if(this.playerRank == Position.SCUM){
			for(int i=0;i<playerHand.length();i++){
				Card card = playerHand.viewCard(i);
				if(card.getValue() == valueTopCard || card.isBiggerThan(cardTop)){
					playableCards.add(card);
				}
			}
		}else{
			for (int i = 0; i < playerHand.length(); i++) {
				Card card = playerHand.viewCard(i);
				//make sure that you can't play a card that is an ace while there's a card that's ace on top
				if (card.isBiggerThan(cardTop)) {
					playableCards.add(card);
				}
			}
		}
		return playableCards;	
	}
	
	public Card giveHighestCard() {
    Card highestCard = this.playerHand.viewCard(0);
	int biggestCardIndex=0;
		for (int i = 1; i < this.playerHand.length(); i++) {
			Card currentCard = this.playerHand.viewCard(i);
			if (currentCard.isBiggerThan(highestCard)) {
				highestCard = currentCard;
				biggestCardIndex=i;
			}
		}
	highestCard=this.playerHand.remove(biggestCardIndex);
    return highestCard;
	}
	
	public Card giveSmallestCard(){
	Card lowestCard = this.playerHand.viewCard(0);
	int lowestCardIndex=0;
		for (int i = 1; i < this.playerHand.length(); i++) {
			Card card = this.playerHand.viewCard(i);
			if (lowestCard.isBiggerThan(card)) {
				lowestCard = card;
				lowestCardIndex=i;
			}
		}
    lowestCard=this.playerHand.remove(lowestCardIndex);
    return lowestCard;
	}
	
	public void assignPosition(Position rank){
		this.playerRank = rank;
	}
	public void receiveCard(Card card){
		this.playerHand.add(card);
	}
	public DynaArr getHand(){
		return this.playerHand;
	}
	public String playerData(){
		String message ="Player Name: " +this.name+ "  Player Status: "+ this.playerRank;
		return message;
	}
	public void giveName(String playerName){
		this.name=playerName;
	}
	public String toString(){
		return this.playerHand.toString();
	}
	public String getName(){
		return this.name;
	}
	public Position getPlayerRank(){
		return this.playerRank;
	}
	public boolean isEmpty(){
		int numberOfNulls=0;
		for(int i=0;i<playerHand.length();i++){
			if(playerHand.getCards()[i] == null){
				numberOfNulls++;
			}
		}
		return numberOfNulls==playerHand.length();
	}
	
}