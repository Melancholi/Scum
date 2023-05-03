import java.util.Random;
import java.util.Scanner;
public class Scum{
	private DynaArr deck;
	private Hand[] players;
	private DynaArr pile;
	private int currentPlayerIndex;
	
	
	//MAIN GAME
	public Scum(){
		// generate the deck	
		this.deck = new DynaArr();
		this.deck.generateDeck();
		this.deck.shuffle();
		Scanner playerInput = new Scanner(System.in);
		
		this.players = new Hand[4];
		System.out.println("/////////////////////////////////// CHOOSE PLAYER NAME //////////////////////////////////");
		for(int playerPos=0;playerPos<this.players.length;playerPos++){
			System.out.println("Player: "+ (playerPos+1) + ": ");
			String playerName = playerInput.nextLine();
			this.players[playerPos] = new Hand(playerName);
		}
		System.out.println("/////////////////////////////////// CHOOSING PLAYER RANK //////////////////////////////////");
		choosePositions(this.players);
		while(this.deck.length()>0){
			for(int playerNb =0;playerNb<this.players.length;playerNb++){
				this.players[playerNb].receiveCard(this.deck.dealCard());
			}
		}
		
		this.pile = new DynaArr();
		this.currentPlayerIndex =3;
		
		System.out.println("/////////////////////////////////// EXCHANGING CARDS //////////////////////////////////");
		exchangeCards();
	}
	public void startGame(){
    System.out.println("Welcome to President! (also called Asshole)\n");
	System.out.println("The rules of this game are simple, be the first player to run out of cards!\n");
	System.out.println("TO REMEMBER: The scum has the ability to win a round by playing the same card as the card on top of the pile\n It is very important to remember this when you're the scum, as you will often have low value cards and wont\n be able to play when there are high values cards! \n");
	
	
	
	//initialise with value that gets reset
    Card topCard = new Card(Value.THREE, Status.SPADES);
    boolean playingGame = true;
    
	Scanner playerInput = new Scanner(System.in);
    while (playingGame) {
		System.out.println("/////////////////////////////////// NEW PLAYER TURN //////////////////////////////////");
        Hand currentPlayer = players[currentPlayerIndex];
		

        topCard = pile.viewTopCard();
		
		
        System.out.println((currentPlayer.getName())+" (" +currentPlayer.getPlayerRank()+ ")" + " 's turn\n");
        System.out.println("Hand: " + currentPlayer.getHand() + "\n");

        DynaArr playableCards = getPlayableCards(currentPlayer, topCard);

		//if no playable cards, will lead to skip turn
        String input = "";
        if (playableCards != null) {
            input = getInputFromPlayer(playerInput);
        }
		
		//play, else skip
        if (playOrSkip(input)) {
            playCard(currentPlayer, playableCards, playerInput);
			topCard = pile.viewTopCard();
        }else{
			System.out.println("Skipping turn... \n");
		}
		
		//checking the values for when the scum cancels a card
		boolean scumCanCancel =false;
		Value topCardValue =null;
		Value secondTopValue =null;
		if(pile.length()>=2){
			topCardValue = pile.viewTopCard().getValue();
			secondTopValue = pile.viewCard(pile.length()-2).getValue();
			scumCanCancel = true;
		}
		
		//round win conditions
        if (currentPlayer.isEmpty()) {
			System.out.println(currentPlayer.getName()+ " has run out of cards.\n");
			System.out.println("/////////////////////////////////// "+ currentPlayer.getName()+" wins! //////////////////////////////////\n");
			System.out.println("/////////////////////////////////// END OF GAME //////////////////////////////////");
            playingGame = false;
		//Scum exclusive
        }else if(topCardValue == secondTopValue && scumCanCancel){
			System.out.println("??????????????????? Scum has played a card of the same value, resetting pile ??????????????????????????");
			pile.discardPile();
			System.out.println("/////////////////////////////////// NEW ROUND /////////////////////////////////////////");
			//if you input skip
		}else if(!(playOrSkip(input))){
			currentPlayerIndex = (currentPlayerIndex -1) % players.length;
			if(currentPlayerIndex<0){
				currentPlayerIndex=players.length-1;
			}
			//if no more players can play after , win this round
		}else if((playersCantPlay(players,topCard))){
			System.out.println("No more players can play, resetting pile");
			pile.discardPile();
			System.out.println("/////////////////////////////////// NEW ROUND /////////////////////////////////////////");
			//else, continue on
        }else if(!playersCantPlay(players,topCard)){
			System.out.println("The round continues");
			//Start from top of array, president -->asshole
            currentPlayerIndex = (currentPlayerIndex -1) % players.length;
			if(currentPlayerIndex<0){
				currentPlayerIndex=players.length-1;
			}
		}
    }
    playerInput.close();
	}
	
	
	//GAME METHODS
	public boolean playOrSkip(String input){
		//true is to play, false is to skip
		if(input.equalsIgnoreCase("play")){
			return true;
		}
		return false;
	}   
	
	public void choosePositions(Hand[] players){
		System.out.println("Drawing cards for position");
		DynaArr preGameDeck = new DynaArr();
		preGameDeck.generateDeck();
		preGameDeck.shuffle();
		
		for(int i=0;i<this.players.length;i++){
			this.players[i].receiveCard(preGameDeck.dealCard());
		}
		for(int index=0;index<players.length;index++){
			System.out.println(players[index].getName() + ": "+players[index].getHand());
		}
		
		for(int k=0;k<this.players.length;k++){
			for(int l=k+1;l<this.players.length;l++){
				if(this.players[k].getHand().viewTopCard().isBiggerThan(this.players[l].getHand().viewTopCard())){
					Hand tempData = this.players[l];
					players[l] = this.players[k];
					players[k] = tempData;
				}
			}
		}
		players[players.length-1].assignPosition(Position.PRESIDENT);
		players[players.length-2].assignPosition(Position.VICEPRESIDENT);
		players[0].assignPosition(Position.SCUM);
		players[1].assignPosition(Position.VICESCUM);
		
		for(int playerIndex=0;playerIndex<players.length;playerIndex++){
			System.out.println(players[playerIndex].playerData());
		}
	}
	//to give cards depending on your rank in game
	public void exchangeCards(){
		int scumIndex =0;
		int viceScumInd=1;
		int vicePresInd=players.length-2;
		int presIndex=players.length-1;
		
		//decided to do the long way because I couldn't think of another way to do this quicky in 30 mins
		DynaArr smallestCardsPres =new DynaArr();
		for(int i=0;i<2;i++){
			smallestCardsPres.add(this.players[presIndex].giveSmallestCard());
			System.out.println("President throws away " +smallestCardsPres.viewTopCard()+"\n");
		}
		DynaArr biggestCardsScum =new DynaArr();
		for(int k=0;k<2;k++){
			biggestCardsScum.add(this.players[scumIndex].giveHighestCard());
			System.out.println("Scum gives away " +biggestCardsScum.viewTopCard()+"...\n");
		}
		//trade cards
		for(int l=0;l<2;l++){
			System.out.println(this.players[presIndex].getName()+" got "+biggestCardsScum.viewTopCard()+"!!!\n");
			this.players[presIndex].receiveCard(biggestCardsScum.dealCard());
			
			System.out.println(this.players[scumIndex].getName()+" got "+smallestCardsPres.viewTopCard()+"...\n");
			this.players[scumIndex].receiveCard(smallestCardsPres.dealCard());
		}
		
		Card smallestCardVicePres = null;
		Card biggestCardViceScum = null;
		
		biggestCardViceScum = this.players[viceScumInd].giveHighestCard();
		smallestCardVicePres = this.players[vicePresInd].giveSmallestCard();
		
		this.players[vicePresInd].receiveCard(biggestCardViceScum);
		System.out.println(this.players[vicePresInd].getName()+" got "+biggestCardViceScum+"!!!\n");
		
		this.players[viceScumInd].receiveCard(smallestCardVicePres);
		System.out.println(this.players[viceScumInd].getName()+" got "+smallestCardVicePres+"...\n");
		
	}
	
	
	
	public boolean playersCantPlay(Hand[] users,Card topCardPile){
		for(int i=0;i<users.length;i++){
			if(!(users[i].getPlayableCards(topCardPile).isEmpty())){
				return false;
			}
		}
		return true;
	}
	
	public DynaArr getPlayableCards(Hand currentPlayer, Card topCard) {
		DynaArr playableCards;
		if (topCard == null) {
			System.out.println("There are no cards in your pile\n");
			System.out.println("You can play any card in your deck\n");
			playableCards = currentPlayer.getHand();
		} else {
			System.out.println("The card on top of the pile is a: " + topCard + "\n");
			playableCards = currentPlayer.getPlayableCards(topCard);
			if(playableCards.isEmpty()){
				playableCards =null;
			}
			if (playableCards != null) {
				System.out.println("These are the cards that you can play: \n" + playableCards+"\n");
			} else {
				System.out.println("There are no cards to play\n\n");
			}
		}
		return playableCards;
	}
	
		
	public void viewAllPlayersCards(){
		for(int i=0;i<this.players.length;i++){
			System.out.println(this.players[i].getName()+"'s hand: " +this.players[i]+"\n");
		}
	}
	
	public String getInputFromPlayer(Scanner playerInput) {
		String input = "";
		System.out.println("\nDo you want to play a card (Play) or skip (Skip)? The input is case insensitive\n");
		do {
			input = playerInput.nextLine();
			if (!(input.equalsIgnoreCase("play")) && !(input.equalsIgnoreCase("skip"))) {
				System.out.println("Try again, input is not from two choices available\n");
			}
		} while (!(input.equalsIgnoreCase("play")) && !(input.equalsIgnoreCase("skip")));
		return input;
	}
	
	public void playCard(Hand currentPlayer, DynaArr playableCards, Scanner playerInput) {
		
		int indexToPlay = 0;
		//check for when player has one card left and input plays, play the card automatically
		if(playableCards.length()==1){
			Card cardToPlay = playableCards.viewCard(indexToPlay);
			pile.add(currentPlayer.removeCard(cardToPlay));
			System.out.println("The top card is now: " +pile.viewTopCard()+"\n");
		}else{
		//else ask for input from user
			do {
				System.out.println("Please select the index (number next to card ex: (0) ) that you want to play\n");
				//found it online, very useful, no need to do trys and what not
				if(playerInput.hasNextInt()){
					indexToPlay = playerInput.nextInt();
					playerInput.nextLine();
					System.out.println(playableCards.length());
					if (indexToPlay >= playableCards.length()) {
						System.out.println("this number is too big, try again");
					} else if (indexToPlay < 0) {
						System.out.println("This number is too small,try again");
					} else {
						Card cardToPlay = playableCards.viewCard(indexToPlay);
						System.out.println("Playing: " + cardToPlay+"\n");
						pile.add(currentPlayer.removeCard(cardToPlay));
						System.out.println("The top card is now: " +pile.viewTopCard()+"\n");
						//if I don't put this, I can't play cards that are at the end of the array( ex: index 12 of array of 13 cards), i don't know why????????
						if(indexToPlay>=playableCards.length()){
						indexToPlay-=1;
						}
					}
				}else{
					playerInput.nextLine();
					System.out.println("the input you entered was invalid, try again");
					indexToPlay =100;
				}
			}while (indexToPlay >= playableCards.length() || indexToPlay < 0);
		}
		//could have just done \n
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public static void main(String[]args){
		Scum game = new Scum();
		game.startGame();
		
	}
}





