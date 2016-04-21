/**
 * @author Nickolas Evans
 * */
public class Card implements Comparable<Card>{
	private int rank;
	private int suit;

	public Card(int rank, int suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * @return Returns the rank of the card
	 *  0 = 2, 1 = 3, 2 = 4, 3 = 5, 4 = 6, 5 = 7, 6 = 8
	 *  7 = 9, 8 = T, 9 = J, 10 = Q, 11 = K, 12 = A
	 * */
	public int getRank(){
		return rank;
	}
	
	/**
	 * @return Returns the suit of the card 
	 * 0 = D, 1 = H, 2= S, 3= C
	 * */
	public int getSuit(){
		return suit;
	}
	
	@Override
	public int compareTo(Card c){
		return rank-c.getRank();
	}

}
