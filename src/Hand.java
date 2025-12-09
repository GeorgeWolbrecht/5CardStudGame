import java.util.Arrays;

public class Hand implements Comparable<Hand>{
    int handSize = 5;
    Card[] cards;
    Card[] sortedCards;

    boolean isStraight;
    boolean isFlush;
    boolean isFullHouse;
    boolean isTwoPair;
    boolean isOnePair;

    Card[] fourOfAKind;
    Card[] threeOfAKind;
    Card[] twoOfAKind;
    Card[] highCard;


    public Hand(){
        cards = new Card[handSize];

        fourOfAKind = new Card[4]; //Logically, most cards available for a 4 of a kind
        threeOfAKind = new Card[3]; //Logically, most cards available while making legal 3 of a kind
        twoOfAKind = new Card[4]; //Logically, accounts for most possible combinations of 2 of a kind
        highCard = new Card[5]; //Highest amount of cards that can be high card

        isFlush = false;
        isStraight = false;
        isFullHouse = false;
        isTwoPair = false;

        // sortCards(); hand isn't dealt yet
    }

    void sortCards() {
        // If any card null, skip sorting
        for (Card c : cards) {
            if (c == null) {
                sortedCards = null;
                isFlush = isStraight = isFullHouse = isTwoPair = false;
                return;
            }
        }
        // All cards are non null
        // Card[] tempArray = cards;
        Card[] tempArray = Arrays.copyOf(cards, handSize);
        Arrays.sort(tempArray);
        sortedCards = tempArray;

        setFlush();
        setStraight();
        evaluateHand();
    }

    void evaluateHand() {
        // method assumes sortedCards is not null and has 5 cards
        if (sortedCards == null) return;

        // reset helpers
        fourOfAKind = new Card[4];
        threeOfAKind = new Card[3];
        twoOfAKind = new Card[4];
        highCard = new Card[5];

        isFullHouse = false;
        isTwoPair = false;

        // if flush pr straight, set highCard to sortedCards
        if (isFlush || isStraight) {
            highCard = Arrays.copyOf(sortedCards, sortedCards.length);
            return;
        }

        // counting by rank
        int[] counts = new int[15]; // index by rank value 
        for (Card c : sortedCards) {
            counts[c.getRank().value]++;
        }

        // find counts to identify pairs/three/four
        for (int val = 2; val <= 14; val++) {
            if (counts[val] == 4) {
                // find the card(s) with that rank
                int idx = 0;
                for (Card c : sortedCards) if (c.getRank().value == val) fourOfAKind[idx++] = c;
            } else if (counts[val] == 3) {
                int idx = 0;
                for (Card c : sortedCards) if (c.getRank().value == val) threeOfAKind[idx++] = c;
            } else if (counts[val] == 2) {
                int idx = 0;
                for (Card c : sortedCards) if (c.getRank().value == val) twoOfAKind[idx++] = c;
            }
        }

        // full house 
        boolean hasThree = threeOfAKind[0] != null;
        boolean hasPair = twoOfAKind[0] != null;
        if (hasThree && hasPair) isFullHouse = true;

        // two pair 
        int pairCount = 0;
        for (int val = 2; val <= 14; val++) if (counts[val] == 2) pairCount++;
        isTwoPair = (pairCount == 2);
        isOnePair = (pairCount == 1);

        // if none, set high cards
        if (!isFullHouse && !isTwoPair && !hasThree && fourOfAKind[0] == null) {
            // fill highcard array with sortedCards decending by rank
            Card[] desc = Arrays.copyOf(sortedCards, sortedCards.length);
            // sortedCards is ascending, so reverse
            for (int i = 0; i < desc.length / 2; i++) {
                Card tmp = desc[i];
                desc[i] = desc[desc.length - 1 - i];
                desc[desc.length - 1 - i] = tmp;
            }
            highCard = desc;
        }
    }

    void setFlush() {
        if (sortedCards == null) return;
        
        Suit chosen = cards[0].getSuit();
        for (int i = 1; i < handSize; i++) {
            if (sortedCards[i].getSuit() != chosen) {
                isFlush = false;
                return;
            }
        }
       isFlush = true;
    }

    void setStraight(){
        if (sortedCards == null) return;

        boolean consecutive = true;
        for (int i = 0; i < handSize - 1; i++) {
            int cur = sortedCards[i].getRank().value;
            int next = sortedCards[i + 1].getRank().value;
            if (next != cur + 1) {
                consecutive = false;
                break;
            }
        }
        // special case: Straight 5 high
        boolean special = (sortedCards[0].getRank() == Rank.TWO &&
                           sortedCards[1].getRank() == Rank.THREE &&
                           sortedCards[2].getRank() == Rank.FOUR && 
                           sortedCards[3].getRank() == Rank.FIVE &&
                           sortedCards[4].getRank() == Rank.ACE);
        isStraight = consecutive || special;
    }

    int size() {
        return handSize;
    }

    Card get(int i) {
        return cards[i];
    }

    public void clear() {
        cards = new Card[5];
        sortedCards = null;
        isFlush = isStraight = isFullHouse = isTwoPair = false;
    }

    public void add(Card dealCard, int index) {
        cards[index] = dealCard;
    }

    boolean straightFlush() {
        return isFlush && isStraight;
    }

    boolean flushCards() {
        return isFlush;
    }

    boolean straightCards() {
        return isStraight;
    }

    boolean fullHouse() {
        return isFullHouse;
    }

    boolean twoPair() {
        return isTwoPair;
    }

    private Card getFourOfAKind() {
        return (fourOfAKind != null && fourOfAKind.length > 0) ? fourOfAKind[0] : null;
    }

    private Card getThreeOfAKind() {
        return (threeOfAKind != null && threeOfAKind.length > 0) ? threeOfAKind[0] : null;
    }

    private Card getTwoOfAKind() {
        return (twoOfAKind != null && twoOfAKind.length > 0) ? twoOfAKind[0] : null;
    }

    private Card getHighCard() {
        return (highCard != null && highCard.length > 0) ? highCard[0] : null;
    }

    private int compareFourOfAKind(Hand hand) {
        Card a = this.getFourOfAKind();
        Card b = hand.getFourOfAKind();
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

    private int compareThreeOfAKind(Hand hand) {
        Card a = this.getThreeOfAKind();
        Card b = hand.getThreeOfAKind();
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }
    private int compareTwoOfAKind(Hand hand) {
        Card a = this.getTwoOfAKind();
        Card b = hand.getTwoOfAKind();
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return a.compareTo(b);
    }

    private int compareHighCard(Hand hand) {
        if (this.highCard == null || hand.highCard == null) return 0;
        // compare highest cards in desc order
        for (int i = 0; i < Math.min(this.highCard.length, hand.highCard.length); i++) {
            Card a = this.highCard[i];
            Card b = hand.highCard[i];
            if (a == null && b == null) continue;
            if (a == null) return -1;
            if (b == null) return 1;
            int cmp = a.compareTo(b);
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    // 1 if this hand has a higher value, 0 if hands are tied, -1 if this hand has a lower value
    @Override
    public int compareTo(Hand o) {
        int wins = 1;
        int loses = -1;

        // ensure both hands are sorted
        if (this.sortedCards == null) this.sortCards();
        if (o.sortedCards == null) o.sortCards();


        // Straight Flushes
        if(this.straightFlush() && o.straightFlush()) {
            return Integer.signum(this.compareHighCard(o));
        }
        if (this.straightFlush() && !o.straightFlush()) return wins;
        if (!this.straightFlush() && o.straightFlush()) return loses;

        // Four of a kind
        if(this.getFourOfAKind() != null && o.getFourOfAKind() != null) {
            return Integer.signum(this.compareFourOfAKind(o));
        }
        if (this.getFourOfAKind() != null && o.getFourOfAKind() == null) return wins;
        if (this.getFourOfAKind() == null && o.getFourOfAKind() != null) return loses;

        // Full House
        if(this.fullHouse() && o.fullHouse()) {
            return Integer.signum(this.compareThreeOfAKind(o));
        }
        if (this.fullHouse() && !o.fullHouse()) return wins;
        if (!this.fullHouse() && o.fullHouse()) return loses;

        // Flush
        if(this.flushCards() && o.flushCards()) {
            return Integer.signum(this.compareHighCard(o));
        }
        if (this.flushCards() && !o.flushCards()) return wins;
        if (!this.flushCards() && o.flushCards()) return loses;

        // Straight
        if(this.straightCards() && o.straightCards()) {
            return Integer.signum(this.compareHighCard(o));
        }
        if (this.straightCards() && !o.straightCards()) return wins;
        if (!this.straightCards() && o.straightCards()) return loses;

        // Three of a kind
        if(this.getThreeOfAKind() != null && o.getThreeOfAKind() != null) {
            return Integer.signum(this.compareThreeOfAKind(o));
        }
        if (this.getThreeOfAKind() != null && o.getThreeOfAKind() == null) return wins;
        if (this.getThreeOfAKind() == null && o.getThreeOfAKind() != null) return loses;

        // Two Pair
        if(this.twoPair() && o.twoPair()) {
            return Integer.signum(this.compareTwoOfAKind(o));
        }
        if (this.twoPair() && !o.twoPair()) return wins;
        if (!this.twoPair() && o.twoPair()) return loses;

        // Pair
        if(this.getTwoOfAKind() != null && o.getTwoOfAKind() != null) {
            return Integer.signum(this.compareTwoOfAKind(o));
        }
        if (this.getTwoOfAKind() != null && o.getTwoOfAKind() == null) return wins;
        if (this.getTwoOfAKind() == null && o.getTwoOfAKind() != null) return loses;

        // High card
        return Integer.signum(this.compareHighCard(o));
    }

    public String getHandRank() {
        if (straightFlush()) return "Straight Flush";
        if (getFourOfAKind() != null) return "Four of a Kind";
        if (fullHouse()) return "Full House";
        if (flushCards()) return "Flush";
        if (straightCards()) return "Straight";
        if (getThreeOfAKind() != null) return "Three of a Kind";
        if (isTwoPair) return "TwoPair";
        if (isOnePair) return "OnePair";

        return "High Card";
    }
}