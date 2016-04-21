
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nickolas Evans
 * 
 * Represents a five card poker hand
 * */
public class PokerHand implements Comparable<PokerHand>{
	private ArrayList<Card> hand;
	private int handValue; 
	private int[] highCards; // Used to compare among same value hands
	private HashMap<Integer, Integer> rankCount;
	
	

	public PokerHand(ArrayList<Card> newHand){
		Collections.sort(newHand);
		this.hand = newHand;
		highCards = new int[5];
		rankCount = countCards(newHand);
		determineHand();
	}
	
	/**
	 * Counts the number of times each rank occurs in the hand
	 * */
	private HashMap<Integer, Integer> countCards(List<Card> hands){
		HashMap<Integer,Integer> rankC = new HashMap<Integer, Integer>();
		for(Card c : hands){
			Integer rank = new Integer(c.getRank());
			Integer count = rankC.get(rank);
			if( count == null){
				rankC.put(rank,1);
			}else{
				count++;
				rankC.put(rank, count);
			}
		}
		return rankC;
	}
	
	/**
	 * Fills the hand with a different list of cards
	 * */
	public boolean fillHand(ArrayList<Card> newHand){	
		Collections.sort(newHand);
		this.hand = newHand;
		highCards = new int[5];
		rankCount = countCards(newHand);
		determineHand();
		return true;
	}
	
	/**
	 * @return Returns a list of the cards in hand.
	 * */
	public List<Card> getHand(){
		return Collections.unmodifiableList(hand);
	}
	/**
	 * determines the value of the hand with handValue[0] = 0 being an invalid hand
	 * 1 = HighCard		4 = Three of a kind	 7 = Full House
	 * 2 = 1 Pair		5 = Straight		 8 = Four of a kind
	 * 3 = 2 Pair		6 = Flush			 9 = Straight Flush
	 * */
	private void determineHand(){
		if(isFlush()){
			if(isStraight()){
				handValue = 9;	//Straight flush
			}else{
				handValue = 6; // Flush
			}
		}else{
			if(rankCount.containsValue(4)){
				handValue = 8; 	// Four of a kind
			}else if(rankCount.containsValue(3)){
				if(rankCount.containsValue(2)){
					handValue = 7; // Full House
				}else{
					handValue = 4; // Three of a kind
				}
			}else if(rankCount.containsValue(2)){
				if(rankCount.size() == 3){
					handValue = 3; // Two pair
				}else{
					handValue = 2; // one pair
				}
			}else if(isStraight()){
				handValue = 5; // Straight
			}else{
				handValue = 1; // High card
			}
		}
		highCards();

	}
	
	/**
	 * Determines the value of the high cards in the hand
	 * */
	private void highCards(){
		if(isStraight()){ //Gets 5,9
			if(hand.get(4).getRank() == 12 && hand.get(3).getRank() == 3){ // If ace is low for straight
				highCards[0] = 3;
			}else{
				highCards[0] = hand.get(4).getRank();
			}
		}else if(handValue == 1 || handValue == 6){  //Gets high cards for handValues 1,6
			for(int i = 0; i < 5; i++){
				highCards[i] = hand.get(4-i).getRank();
			}
		}else if(handValue == 8 || handValue == 7){
			//Get 8,7
			int amount = rankCount.get(hand.get(0).getRank());
			if(amount > 2){
				highCards[0] = hand.get(0).getRank();
				highCards[1] = hand.get(4).getRank();
			}else{
				highCards[1] = hand.get(0).getRank();
				highCards[0] = hand.get(4).getRank();
			}
		}else if(handValue == 4){
			int currentRank = hand.get(4).getRank();
			int amount = rankCount.get(currentRank);
			if(amount > 1){
				highCards[0] = currentRank;
				highCards[1] = hand.get(1).getRank();
				highCards[2] = hand.get(0).getRank();
			}else{
				highCards[1] = currentRank;
				currentRank = hand.get(2).getRank();
				if(rankCount.get(currentRank) > 1){
					highCards[0] = currentRank;
					highCards[2] = hand.get(0).getRank();
				}else{
					highCards[2] = currentRank;
					highCards[0] = hand.get(0).getRank();
				}
			}
		}else if(handValue == 3){
			int currentRank = hand.get(4).getRank();
			int amount = rankCount.get(currentRank);
			if(amount > 1){
				highCards[0] = currentRank;
				currentRank = hand.get(2).getRank();
				amount = rankCount.get(currentRank);
				if(amount > 1){
					highCards[1] = currentRank;
					highCards[2] = hand.get(0).getRank();
				}else{
					highCards[2] = currentRank;
					highCards[1] = hand.get(0).getRank();
				}
			}else{
				//Single high card
				highCards[2] = currentRank;
				highCards[0] = hand.get(2).getRank();
				highCards[1] = hand.get(0).getRank();
			}
		}else{ // handValue == 2
			
			int nD = 0; //nD is one until the pair is found Used to find the double
			for(int i = 0; i < 5; i++){
				int currentRank = hand.get(i).getRank();
				int amount = rankCount.get(currentRank);
				if(amount > 1){
					highCards[0] = currentRank;
					nD = 2;
				}else{
					highCards[3-i+nD] = currentRank;
				}
			}
			
		}
	}
	
	/**
	 * @return Returns true if all five cards have the same suite
	 */
	public boolean isFlush(){
		for(int i =0; i < 4; i++){
			if(hand.get(i).getSuit() != hand.get(i+1).getSuit()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return Returns true for any five consecutive cards.
	 * */
	public boolean isStraight(){
		for(int i =0; i < 4; i++){
			if(hand.get(i+1).getRank() - hand.get(i).getRank() != 1 &&
					(hand.get(i+1).getRank() != 12 ||hand.get(i).getRank() != 3)){
				return false;
			}
		}
		return true;
	}
	/**
	 * @return Returns the value of the hand returns -1 if the PokerHand is empty
	 * */
	public int getValue(){
		return handValue;
	}

	@Override
	public int compareTo(PokerHand o) {
		if(handValue != o.getValue()){
			return handValue -o.getValue();
		}else{
			int[] otherHighCards = o.getHighCards();
			if(isStraight()){
				return highCards[0] - otherHighCards[0];
			}else{
				for(int i = 0; i < 5; i++){
					if(highCards[i] != otherHighCards[i]){
						return highCards[i] - otherHighCards[i];
					}
				}
				return 0;
			}
		}
	}

	/**
	 * @return Returns the array of ranks of the highcards necessary to determine rank
	 *  What each int value maps to in terms of card rank.
	 *  0 = 2, 1 = 3, 2 = 4, 3 = 5, 4 = 6, 5 = 7, 6 = 8
	 *  7 = 9, 8 = T, 9 = J, 10 = Q, 11 = K, 12 = A
	 * */
	public int[] getHighCards() {
		int[] returnHighCards = new int[5];
		for(int i = 0; i < 5; i++){
			returnHighCards[i] = highCards[i];
		}
		return returnHighCards;
	}
	
}
