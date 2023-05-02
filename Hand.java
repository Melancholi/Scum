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
	
	public DynaArr getPlayableCards(Card pileTop) {
		Value two = Value.TWO;
		Value ace = Value.ACE;
		DynaArr playableCards = new DynaArr();
		for (int i = 0; i < playerHand.length(); i++) {
			Card card = playerHand.viewCard(i);
			//make sure that you can't play a card that is an ace while there's a card that's ace on top
			if (card.isBiggerThan(pileTop)) {
				playableCards.add(card);
			}
		}
		return playableCards;	
	}								//make card[] or dynaArr
	public DynaArr getPlayableCombos(Card pileTop) {
    DynaArr playableCombos = new DynaArr();
    if (pile.length() >= 4) {
        int count = 0;
        Card[] lastFourCards = new Card[4];
        for (int i = pile.length() - 1; i >= pile.length() - 4; i--) {
            lastFourCards[count++] = pile.viewCard(i);
        }
        if (lastFourCards[0].getValue() == lastFourCards[1].getValue() &&
            lastFourCards[0].getValue() == lastFourCards[2].getValue() &&
            lastFourCards[0].getValue() == lastFourCards[3].getValue()) {
            // Four cards of the same value
            for (int i = 0; i < playerHand.length() - 3; i++) {
                DynaArr combo = new DynaArr();
                Card[] cards = new Card[4];
                cards[0] = playerHand.viewCard(i);
                cards[1] = playerHand.viewCard(i + 1);
                cards[2] = playerHand.viewCard(i + 2);
                cards[3] = playerHand.viewCard(i + 3);
                if (cards[0].isBiggerThan(lastFourCards[0]) &&
                    cards[0].getValue() == cards[1].getValue() &&
                    cards[0].getValue() == cards[2].getValue() &&
                    cards[0].getValue() == cards[3].getValue()) {
                    combo.add(cards[0]);
                    combo.add(cards[1]);
                    combo.add(cards[2]);
                    combo.add(cards[3]);
                    playableCombos.add(combo);
                }
            }
        } else if (lastFourCards[0].getValue() == lastFourCards[1].getValue() &&
                   lastFourCards[0].getValue() == lastFourCards[2].getValue()) {
            // Three cards of the same value
            for (int i = 0; i < playerHand.length() - 2; i++) {
                DynaArr combo = new DynaArr();
                Card[] cards = new Card[3];
                cards[0] = playerHand.viewCard(i);
                cards[1] = playerHand.viewCard(i + 1);
                cards[2] = playerHand.viewCard(i + 2);
                if (cards[0].isBiggerThan(lastFourCards[0]) &&
                    cards[0].getValue() == cards[1].getValue() &&
                    cards[0].getValue() == cards[2].getValue()) {
                    combo.add(cards[0]);
                    combo.add(cards[1]);
                    combo.add(cards[2]);
                    playableCombos.add(combo);
                }
            }
        } else if (lastFourCards[0].getValue() == lastFourCards[1].getValue()) {
            // Two cards of the same value
            for (int i = 0; i < playerHand.length() - 1; i++) {
                DynaArr combo = new DynaArr();
                Card[] cards = new Card[2];
                cards[0] = playerHand.viewCard(i);
                cards[1] = playerHand.viewCard(i + 1);
                if (cards[0].isBiggerThan(lastFourCards[0]) &&
                    cards[0].getValue() == cards[1].getValue()) {
                    combo.add(cards[0]);
                    combo.add(cards[1]);
                    playableCombos.add(combo);
                }
            }
        }
    }
    return playableCombos;
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