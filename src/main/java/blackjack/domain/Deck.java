package blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards;

    public Deck() {
        this.cards = createInitialCards();
        Collections.shuffle(this.cards);
    }

    private Stack<Card> createInitialCards() {
        Stack<Card> initialCards = new Stack<>();
        for (CardPattern pattern : CardPattern.values()) {
            for (CardNumber number : CardNumber.values()) {
                initialCards.push(new Card(number, pattern));
            }
        }
        return initialCards;
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("카드가 더 이상 없습니다.");
        }
        return cards.pop();
    }
}
