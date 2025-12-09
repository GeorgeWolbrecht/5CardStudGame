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
        
        if (o == null) return 1; // non-null
        return Integer.compare(this.getRank().value, o.getRank().value);
    }

     ImageView getImageView(double width, double height) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        return iv;
    }
}