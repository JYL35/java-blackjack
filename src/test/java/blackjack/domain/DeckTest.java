package blackjack.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeckTest {
    @Test
    @DisplayName("덱에서 카드를 한 장 뽑는다.")
    void draw() {
        Deck deck = new Deck();
        Card card = deck.draw();
        assertThat(card).isNotNull();
    }
}
