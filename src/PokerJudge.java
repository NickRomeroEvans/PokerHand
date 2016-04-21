import java.util.ArrayList;

/**@author Nickolas Evans
 * 
 * 
 * Takes in two parameters representing two poker hands and 
 * return a message indicating which hand wins.
 * */
public class PokerJudge {
	private static final String RANKS = "23456789TJQKA"; //2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace
	private static final String SUITES = "DHSC"; // Diamonds, Hearts, Spades, Clubs
	/**
	 * Return a message indicating which hand wins.
	 * @param args representing two poker hands
	 * */
	public static void main(String[] args){
		if(args.length != 2 || (args[0].length() != 10 && args.length != 10)){
			System.out.println("Invalid input PokerJudge requires two poker hands of five cards");
			System.exit(0);
		}
		
		PokerHand player1 = makeHand(args[0]);
		PokerHand player2 = makeHand(args[1]);
		
		int result = player1.compareTo(player2);
		if(result >  0){
			System.out.println("Hand one wins");
		}else if(result == 0){
			System.out.println("Both hands are equal");
		}else{
			System.out.println("Hand two wins");
		}
	}
	
	/**
	 * @param hand is the string representing a pokerhand
	 * @return Pokerhand that represents the Pokerhand object of the giving string hand
	 * */
	public static PokerHand makeHand(String hand){
		String[] values = hand.split("");
		ArrayList<Card> newHand= new ArrayList<Card>();
		for(int i = 1; i < hand.length(); i+=2){
			int rank = RANKS.lastIndexOf(values[i]);
			int suit = SUITES.lastIndexOf(values[i+1]);
			newHand.add(new Card(rank,suit));
		}
		return new PokerHand(newHand);
	}
}
