import java.util.Arrays;

public class Hand implements Comparable<Hand>{
    int handSize = 5;
    Card[] cards;
    Card[] sortedCards;

    boolean isStraight;
    boolean isFlush;
    boolean isFullHouse;
    boolean isTwoPair;

    Card[] fourOfAKind;
    Card[] threeOfAKind;
    Card[] twoOfAKind;
    Card[] highCard;


    public Hand(){
        cards = new Card[handSize];
        sortedCards = sortCards(cards);
        setFlush();
        setStraight();
        evaluateHand();
    }

    Card[] sortCards(Card[] unsortedArray){
        Card[] tempArray = unsortedArray;
        Arrays.sort(tempArray);
        Card[] returnArray = new Card[handSize];
        for(int i = 0; i < tempArray.length; i++){
            returnArray[i] = tempArray[tempArray.length-(i+1)];
        }
        return returnArray;
    }

    void evaluateHand(){
        //Evaluate hand for scoring
        //Check if it is a straight flush, flush, or straight
        if(isFlush|| isStraight){
            highCard = sortedCards;
        }
        else{
            
        }
        

    }

    void setFlush(){
        Suit chosen = cards[0].getSuit();
        if(cards[1].getSuit() == chosen){
            if(cards[2].getSuit() == chosen){
                if(cards[3].getSuit() == chosen){
                    if(cards[4].getSuit() == chosen){
                        isFlush = true;
                    }
                }
            }
        }
    }

    void setStraight(){

        //Check ranks
        Rank checkRank = cards[0].getRank();
        for(int i = 1; i < handSize; i++){
            if(checkRank.value != cards[i].getRank().value){
                break;
            }
            checkRank = cards[i].getRank();
            isStraight = true;
        }

        //Special Condition, Straight 5 high
        if(cards[0].getRank() == Rank.ACE){
            if(cards[1].getRank() == Rank.FIVE){
                if(cards[2].getRank() == Rank.FOUR){
                    if(cards[3].getRank() == Rank.THREE){
                        if(cards[4].getRank() == Rank.TWO){
                            isStraight = true;
                        }
                    }
                }
            }
        }
    }

    





    int size() {
        return handSize;
    }

    Card get(int i) {
        return cards[i];
    }

    void set(int i, Card dealCard) {
        cards[i] = dealCard;
    }





    public void clear() {
        cards = new Card[5];
    }

    public void add(Card dealCard, int index) {
        cards[index] = dealCard;
    }

    boolean straightFlush(){
        return isFlush ^ isStraight;
    }

    boolean flushCards(){
        return isFlush;
    }
    boolean straightCards(){
        return isStraight;
    }
    boolean fullHouse(){
        return isFullHouse;
    }
    boolean twoPair(){
        return twoOfAKind[3] != null;
    }


    private Card getFourOfAKind(){
        return fourOfAKind[0];
    }

    private Card getThreeOfAKind(){
        return threeOfAKind[0];
    }

    private Card getTwoOfAKind(){
        return twoOfAKind[0];
    }

    private Card getHighCard(){
        return highCard[0];
    }


    private int compareFourOfAKind(Hand hand){
        return this.getFourOfAKind().compareTo(hand.getFourOfAKind());
    }

    private int compareThreeOfAKind(Hand hand){
        return this.getThreeOfAKind().compareTo(hand.getThreeOfAKind());
    }
    private int compareTwoOfAKind(Hand hand){
        return this.getTwoOfAKind().compareTo(hand.getTwoOfAKind());
    }

    private int compareHighCard(Hand hand){
        return this.getHighCard().compareTo(hand.getHighCard());
    }



    /**
     * Method to compare value of hands
     * @return 1 if this hand has a higher value, 0 if hands are tied, -1 if this hand has a lower value
     */
    @Override
    public int compareTo(Hand o) {
        int wins = 1;
        int loses = -1;


        //Case 1:  Both are Straight Flushes, highest Hand
        if(this.straightFlush() == o.straightFlush() == true){
            return compareHighCard(o);
        }

        //Case 2:  One hand is a straight flush, but not the other
        if(this.straightFlush() == true && o.straightFlush() == false){
            return wins;
        }
        if(this.straightFlush() == false && o.straightFlush() == true){
            return loses;
        }

        //Case 3: Both are Four of a kind
        if(this.getFourOfAKind() != null && o.getFourOfAKind() != null){
            return this.compareFourOfAKind(o);
        }

        //Case 4: One is Four of a Kind, but not the other
        if(this.getFourOfAKind() != null && o.getFourOfAKind() == null){
            return wins;
        }
        if(this.getFourOfAKind() == null && o.getFourOfAKind() != null){
            return loses;
        }

        //Case 5: Both are Full Houses
        if(this.fullHouse() == o.fullHouse() == true){
            return this.compareThreeOfAKind(o);
        }

        //Case 6: One if a full house but not the other
        if(this.fullHouse() == true && o.fullHouse() == false){
            return wins;
        }
        if(this.fullHouse() == false && o.fullHouse() == true){
            return loses;
        }

        //Case 7:  Both are flushes
        if(this.flushCards() == o.flushCards() == true){
            return this.compareHighCard(o);
        }

        //Case 8: One is flush, the other is not
        if(this.flushCards() == true && o.flushCards() == false){
            return wins;
        }
        if(this.flushCards() == false && o.flushCards() == true){
            return loses;
        }

        //Case 9: Both are Straights
        if(this.straightCards() == o.straightCards() == true){
            return this.compareHighCard(o);
        }

        //Case 10: One is a straight but not the other
        if(this.straightCards() == true && o.straightCards() == false){
            return wins;
        }
        if(this.straightCards() == false && o.straightCards() == true){
            return loses;
        }

        //Case 11: Both are 3 of a kind
        if(this.getThreeOfAKind() != null && o.getThreeOfAKind() != null){
            return this.compareThreeOfAKind(o);
        }

        //Case 12: One if 3 of a kind, but the other isn't
        if(this.getThreeOfAKind() != null && o.getThreeOfAKind() == null){
            return wins;
        }
        if(this.getThreeOfAKind() == null && o.getThreeOfAKind() != null){
            return loses;
        }

        //Case 13: Both have 2 pairs
        if(this.twoPair() == o.twoPair() == true){
            return compareTwoOfAKind(o);
        }

        //Case 14: One has two pairs but the other doesn't
        if(this.twoPair() == true && o.twoPair() == false){
            return wins;
        }
        if(this.twoPair() == false && o.twoPair() == true){
            return loses;
        }

        //Case 15:Both have a pair
        if(this.getTwoOfAKind() != null && o.getTwoOfAKind() != null){
            return compareTwoOfAKind(o);
        }

        //Case 16: One has a pair and the other doesn't
        if(this.getTwoOfAKind() != null && o.getTwoOfAKind() == null){
            return wins;
        }
        if(this.getTwoOfAKind() == null && o.getTwoOfAKind() != null){
            return loses;
        }

        //Base Case: both hands are high card only

        return this.compareHighCard(o);

    }





}
