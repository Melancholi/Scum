import java.util.Random;
import java.util.Scanner;
public class Scum{
	private DynaArr deck;
	private Hand[] players;
	private DynaArr pile;
	private int currentPlayerIndex;
	
	
	
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
		
		for(int i=0;i<this.players.length;i++){
			this.players[i].receiveCard(preGameDeck.dealCard());
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
					
		
		
		
		
	public Scum(){
		// generate the deck	
		this.deck = new DynaArr();
		this.deck.generateDeck();
		this.deck.shuffle();
		Scanner playerInput = new Scanner(System.in);
		
		this.players = new Hand[4];
		for(int playerPos=0;playerPos<this.players.length;playerPos++){
			System.out.println("Player: "+ (playerPos+1)+" \nPlease enter the player's name :");
			String playerName = playerInput.nextLine();
			this.players[playerPos] = new Hand(playerName);
		}
		
		while(this.deck.length()>0){
			for(int playerNb =0;playerNb<this.players.length;playerNb++){
				this.players[playerNb].receiveCard(this.deck.dealCard());
			}
		}
		
		this.pile = new DynaArr();
		this.currentPlayerIndex =0;
		
	}
	public void startGame(){
    System.out.println("Welcome to Scum, also called President or Asshole\n");
	choosePositions(this.players);
	
	//initialise with value that gets reset
    Card topCard = new Card(Value.THREE, Status.SPADES);
    boolean playingGame = true;
    
	Scanner playerInput = new Scanner(System.in);
    while (playingGame) {
        Hand currentPlayer = players[currentPlayerIndex];

        topCard = pile.viewTopCard();
		
		
        System.out.println("Player " + (currentPlayer.getName()) + "'s turn\n");
        System.out.println("Hand: " + currentPlayer.getHand() + "\n");

        DynaArr playableCards = getPlayableCards(currentPlayer, topCard);

        String input = "";
        if (playableCards != null) {
            input = getInputFromPlayer(playerInput);
        }

        if (playOrSkip(input)) {
            playCard(currentPlayer, playableCards, playerInput);
			topCard = pile.viewTopCard();
        }else{
			System.out.println("Skipping turn... \n");
		}
		
		
        if (currentPlayer.isEmpty()) {
			System.out.println(currentPlayer.getName()+ " has run out of cards.");
			System.out.println(currentPlayer.getName()+ " has won the game!");
            playingGame = false;
        }else if((playersCantPlay(players,topCard))){
			System.out.println("No more players can play, resetting pile");
			pile.discardPile();
        }else if(!playersCantPlay(players,topCard)){
			System.out.println("The round continues");
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
		}
    }
	

    playerInput.close();
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
	
	public void viewAllPlayersCards(Hand[] usersHand){
		for(int i=0;i<usersHand.length;i++){
			System.out.println(usersHand[i]);
		}
	}
	
	//make input only need one letter p or s
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
				if(playerInput.hasNextInt()){
					indexToPlay = playerInput.nextInt();
					playerInput.nextLine();
					if (indexToPlay >= playableCards.length()) {
						System.out.println("this number is too big, try again");
					} else if (indexToPlay < 0) {
						System.out.println("This number is too small,try again");
					} else {
						Card cardToPlay = playableCards.viewCard(indexToPlay);
						System.out.println("Playing: " + cardToPlay+"\n");
						pile.add(currentPlayer.removeCard(cardToPlay));
						System.out.println("The top card is now: " +pile.viewTopCard()+"\n");
					}
			 
				}else{
					playerInput.nextLine();
					System.out.println("the input you entered was invalid, try again");
					indexToPlay =100;
				}
			}while (indexToPlay >= playableCards.length() || indexToPlay < 0);
		}
	}

	
	
	public static void main(String[]args){
		Scum game = new Scum();
		game.startGame();
		
	}
	
	
}
//TO FINISH FOR COMPLETION:
//add position and scum related ability
//add multiple cards played in one turn(duos, trios)
//add give card mechanic at start game
//add decide position at start game
//make interface more visually appealing





//TO ADD FOR COMPREHENSION:
//Make player turn bigger
// allow p and s to decide action