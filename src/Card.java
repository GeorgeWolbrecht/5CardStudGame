public class Card implements Comparable<Card>{
    final Rank rank;
    final Suit suit;
    Image image;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        String fileName = rank.symbol + suit.letter + ".png"; // ex: "AH.png"
        image = new Image(getClass().getResourceAsStream("/cards/" + fileName));
    }

    /**
     * Method to get the Rank of a card
     * @return Rank of card
     */
    Rank getRank(){
        return this.rank;
    }

    /**
     * Method to get the Suit of a card
     * @return Suit of card
     */

    Suit getSuit(){
        return this.suit;
    }

    /**
     * Method to compare Two cards in value
     * @return 1 if card is Higher rank, 0 if card is equal, -1 if card is Lower Rank
     */
    @Override
    public int compareTo(Card o) {
        Rank rankOne = this.getRank();
        Rank rankTwo =this.getRank();

        int difference = rankOne.value - rankTwo.value;
        return difference;
    }

     ImageView getImageView(double width, double height) {
            ImageView iv = new ImageView(image);
            iv.setFitWidth(width);
            iv.setFitHeight(height);
            return iv;
        }
    
}
