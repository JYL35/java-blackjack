package blackjack.domain;

import java.util.Objects;

public class Card {
    private final CardNumber number;
    private final CardPattern pattern;

    public Card(CardNumber number, CardPattern pattern) {
        this.number = number;
        this.pattern = pattern;
    }

    public int getScore() {
        return number.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && pattern == card.pattern;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pattern);
    }
}
