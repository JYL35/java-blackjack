package domain.card;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Card {

    private static final Map<String, Card> ALL_CARDS_CACHE = new HashMap<>();

    static {
        initializeCards();
    }

    private final CardPattern pattern;
    private final CardNumber number;

    public Card(String number, String pattern) {
        this.number = CardNumber.matchCardNumber(number);
        this.pattern = CardPattern.matchCardPattern(pattern);
    }

    public static Card from(String number, String pattern) {
        String key = generateKey(number, pattern);
        if (!ALL_CARDS_CACHE.containsKey(key)) {
            throw new IllegalArgumentException("존재하지 않는 카드 조합입니다: " + key);
        }
        return ALL_CARDS_CACHE.get(key);
    }

    private static String generateKey(String number, String pattern) {
        return number + pattern;
    }

    public int number() {
        return number.getValue();
    }

    public String cardName() {
        return number.getCourt() + pattern.getName();
    }

    private static void initializeCards() {
        for (CardPattern pattern : CardPattern.values()) {
            putCardsByPattern(pattern);
        }
    }

    private static void putCardsByPattern(CardPattern pattern) {
        for (CardNumber number : CardNumber.values()) {
            String key = generateKey(number.getCourt(), pattern.getName());
            ALL_CARDS_CACHE.put(key, new Card(number.getCourt(), pattern.getName()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) {
            return false;
        }
        return pattern == card.pattern && number == card.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, number);
    }
}
