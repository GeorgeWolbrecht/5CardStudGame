public class Deck {
        private final List<Card> cards = new ArrayList<>();
        private int currentIndex = 0;

        Deck() {
            for (Suit s : Suit.values()) {
                for (Rank r : Rank.values()) {
                    cards.add(new Card(r, s));
                }
            }
        }

        void shuffle() {
            Collections.shuffle(cards);
            currentIndex = 0;
        }

        Card dealCard() {
            if (currentIndex >= cards.size()) shuffle();
            return cards.get(currentIndex++);
        }
    }
