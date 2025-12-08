import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Card implements Comparable<Card>{
    final Rank rank;
    final Suit suit;
    Image image;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        String fileName = rank.symbol + suit.letter + ".png"; // ex: "AH.png"
        try {
            image = new Image(getClass().getResourceAsStream("/cards/" + fileName));
        } catch (Exception e) {
            image = null;
        }
    }

    Rank getRank(){
        return this.rank;
    }

    Suit getSuit(){
        return this.suit;
    }

    @Override
    public int compareTo(Card o) {
        
        /*
        Rank rankOne = this.getRank();
        Rank rankTwo =this.getRank();

        int difference = rankOne.value - rankTwo.value;
        return difference;
        */
        if (o == null) return 1; // non-null
        return Integer.compare(this.getRank().value, o.getRank().value);
    }

     ImageView getImageView(double width, double height) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        return iv;
    }
    enum Suit {
        HEARTS("H"),SPADES("S"),DIAMONDS("D"),CLOVES("C");
        final String letter;
        Suit(String l){letter=l;}
    }
    enum Rank {
        TWO(2,"2"), THREE(3,"3"), FOUR(4,"4"), FIVE(5,"5"), SIX(6,"6"),
        SEVEN(7,"7"), EIGHT(8,"8"), NINE(9,"9"), TEN(10,"10"),
       JACK(11,"J"), QUEEN(12,"Q"), KING(13,"K"), ACE(14,"A");
        final int value; final String symbol;
        Rank(int v,String s){value=v;symbol=s;
    }
}
}
